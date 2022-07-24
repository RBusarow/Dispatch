/*
 * Copyright (C) 2022 Rick Busarow
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package dispatch.test

import dispatch.core.DefaultCoroutineScope
import dispatch.core.DispatcherProvider
import dispatch.core.IOCoroutineScope
import dispatch.core.MainCoroutineScope
import dispatch.core.MainImmediateCoroutineScope
import dispatch.core.UnconfinedCoroutineScope
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestCoroutineScheduler
import kotlinx.coroutines.test.TestCoroutineScope
import kotlinx.coroutines.test.TestDispatcher
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.currentTime
import kotlin.coroutines.ContinuationInterceptor
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext
import kotlin.time.ExperimentalTime
import kotlin.time.TimeSource

/**
 * A polymorphic testing [CoroutineScope] interface.
 *
 * This single interface implements: [TestCoroutineScope] [DefaultCoroutineScope] [IOCoroutineScope]
 * [MainCoroutineScope] [MainImmediateCoroutineScope] [UnconfinedCoroutineScope]
 *
 * This means that it can be injected into any class or function regardless of what type of
 * `CoroutineScope` is required.
 */
@ExperimentalCoroutinesApi
public interface TestProvidedCoroutineScope :
  DefaultCoroutineScope,
  IOCoroutineScope,
  MainCoroutineScope,
  MainImmediateCoroutineScope,
  UnconfinedCoroutineScope {
  /** single [DispatcherProvider] promise for the [TestProvidedCoroutineScope] */
  public val dispatcherProvider: TestDispatcherProvider

  /** The delay-skipping scheduler used by the test dispatchers running the code in this scope. */
  @ExperimentalCoroutinesApi
  public val testScheduler: TestCoroutineScheduler

  /**
   * The [test scheduler][TestScope.testScheduler] as a [TimeSource].
   *
   * @see TestCoroutineScheduler.timeSource
   */
  @ExperimentalCoroutinesApi
  @ExperimentalTime
  public val testTimeSource: TimeSource

  /**
   * The current virtual time on [testScheduler][TestScope.testScheduler].
   *
   * @see TestCoroutineScheduler.currentTime
   */
  @ExperimentalCoroutinesApi
  public val currentTime: Long

  /**
   * Advances the [testScheduler][TestScope.testScheduler] to the point where there are no tasks
   * remaining.
   *
   * @see TestCoroutineScheduler.advanceUntilIdle
   */
  @ExperimentalCoroutinesApi
  public fun advanceUntilIdle()

  /**
   * Run any tasks that are pending at the current virtual time, according to the
   * [testScheduler][TestScope.testScheduler].
   *
   * @see TestCoroutineScheduler.runCurrent
   */
  @ExperimentalCoroutinesApi
  public fun runCurrent()

  /**
   * Moves the virtual clock of this dispatcher forward by [the specified amount][delayTimeMillis],
   * running the scheduled tasks in the meantime.
   *
   * In contrast with `TestCoroutineScope.advanceTimeBy`, this function does not run the tasks
   * scheduled at the moment [currentTime] + [delayTimeMillis].
   *
   * @throws IllegalStateException if passed a negative [delay][delayTimeMillis].
   * @see TestCoroutineScheduler.advanceTimeBy
   */
  @ExperimentalCoroutinesApi
  public fun advanceTimeBy(delayTimeMillis: Long)
}

/** @suppress internal use only */
@ExperimentalCoroutinesApi
internal class TestProvidedCoroutineScopeImpl(
  override val dispatcherProvider: TestDispatcherProvider,
  private val delegateScope: TestScope
) : TestProvidedCoroutineScope,
  CoroutineScope by delegateScope {
  @ExperimentalCoroutinesApi
  override val testScheduler: TestCoroutineScheduler
    get() = delegateScope.testScheduler

  /**
   * The [test scheduler][TestScope.testScheduler] as a [TimeSource].
   *
   * @see TestCoroutineScheduler.timeSource
   */
  @ExperimentalCoroutinesApi
  @ExperimentalTime
  public override val testTimeSource: TimeSource
  get() = testScheduler.timeSource

  /**
   * The current virtual time on [testScheduler][TestScope.testScheduler].
   *
   * @see TestCoroutineScheduler.currentTime
   */
  @ExperimentalCoroutinesApi
  public override val currentTime: Long
    get() = testScheduler.currentTime

  /**
   * Advances the [testScheduler][TestScope.testScheduler] to the point where there are no tasks
   * remaining.
   *
   * @see TestCoroutineScheduler.advanceUntilIdle
   */
  @ExperimentalCoroutinesApi
  public override fun advanceUntilIdle(): Unit = testScheduler.advanceUntilIdle()

  /**
   * Run any tasks that are pending at the current virtual time, according to the
   * [testScheduler][TestScope.testScheduler].
   *
   * @see TestCoroutineScheduler.runCurrent
   */
  @ExperimentalCoroutinesApi
  public override fun runCurrent(): Unit = testScheduler.runCurrent()

  /**
   * Moves the virtual clock of this dispatcher forward by [the specified amount][delayTimeMillis],
   * running the scheduled tasks in the meantime.
   *
   * In contrast with `TestCoroutineScope.advanceTimeBy`, this function does not run the tasks
   * scheduled at the moment [currentTime] + [delayTimeMillis].
   *
   * @throws IllegalStateException if passed a negative [delay][delayTimeMillis].
   * @see TestCoroutineScheduler.advanceTimeBy
   */
  @ExperimentalCoroutinesApi
  public override fun advanceTimeBy(delayTimeMillis: Long): Unit =
    testScheduler.advanceTimeBy(delayTimeMillis)
}

/**
 * Creates a [TestProvidedCoroutineScope] implementation with optional parameters of
 * [TestDispatcher], [TestDispatcherProvider], and a generic [CoroutineContext].
 *
 * The resultant `TestProvidedCoroutineScope` will utilize a single `TestCoroutineDispatcher`
 * for all the [CoroutineDispatcher] properties of its [DispatcherProvider], and the
 * [ContinuationInterceptor] Key of the `CoroutineContext` will also return that
 * `TestCoroutineDispatcher`.
 */
@ExperimentalCoroutinesApi
public fun TestProvidedCoroutineScope(
  dispatcher: TestDispatcher = StandardTestDispatcher(),
  dispatcherProvider: TestDispatcherProvider = TestDispatcherProvider(dispatcher),
  context: CoroutineContext = EmptyCoroutineContext
): TestProvidedCoroutineScope {

  return TestProvidedCoroutineScopeImpl(
    dispatcherProvider = dispatcherProvider,
    delegateScope = TestScope(context + dispatcherProvider)
  )
}
