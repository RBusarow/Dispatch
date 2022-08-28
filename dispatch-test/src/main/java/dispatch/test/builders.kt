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

import dispatch.core.DispatcherProvider
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestCoroutineScope
import kotlinx.coroutines.test.TestDispatcher
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import kotlin.coroutines.ContinuationInterceptor
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext

/**
 * Delegates to [runBlockingTest], but injects a [DispatcherProvider] into the created
 * [TestCoroutineScope].
 *
 * If the `context`'s [ContinuationInterceptor] is not a
 * [TestDispatcher][kotlinx.coroutines.test.TestDispatcher], then a new
 * [TestDispatcher][kotlinx.coroutines.test.TestDispatcher] will be created.
 *
 * If the `context` does not contain a `DispatcherProvider`, a [TestDispatcherProvider] will be
 * created using the `TestDispatcher`.
 *
 * @param context The base `CoroutineContext` which will be modified to use a
 *     [TestDispatcher][kotlinx.coroutines.test.TestDispatcher] and [TestDispatcherProvider].
 *     [EmptyCoroutineContext] is used if one is not provided.
 * @param testBody the action to be performed
 * @sample dispatch.test.samples.BuildersSample.testProvidedSample
 * @see runBlockingTest
 */
@ExperimentalCoroutinesApi
public fun testProvided(
  context: CoroutineContext = EmptyCoroutineContext,
  testBody: suspend TestScope.() -> Unit
) {

  val dispatcher = context[ContinuationInterceptor]
    ?.let { existing ->
      require(existing is TestDispatcher) {
        "The dispatcher passed to `testProvided` must implement " +
          "${TestDispatcher::class.qualifiedName}.  It was: $existing"
      }
      existing
    }
    ?: StandardTestDispatcher()

  Dispatchers.setMain(dispatcher)

  val dispatcherProvider = context[DispatcherProvider]
    ?: TestDispatcherProvider(dispatcher)

  val combinedContext = context + dispatcher + dispatcherProvider

  runTest(context = combinedContext, testBody = testBody)

  Dispatchers.resetMain()
}

@ExperimentalCoroutinesApi
public fun testProvidedUnconfined(
  context: CoroutineContext = EmptyCoroutineContext,
  testBody: suspend TestScope.() -> Unit
) {

  val dispatcher = context[ContinuationInterceptor]
    ?.let { existing ->
      require(existing is TestDispatcher) {
        "The dispatcher passed to `testProvided` must implement " +
          "${TestDispatcher::class.qualifiedName}.  It was: $existing"
      }
      existing.takeIf { it.isDispatchNeeded(EmptyCoroutineContext) }
    }
    ?: UnconfinedTestDispatcher()
  return testProvided(dispatcher, testBody)
}

/**
 * Delegates to [runBlockingTest], but injects a [DispatcherProvider] into the created
 * [TestCoroutineScope].
 *
 * If the `context`'s [ContinuationInterceptor] is not a
 * [TestDispatcher][kotlinx.coroutines.test.TestDispatcher], then a new
 * [TestDispatcher][kotlinx.coroutines.test.TestDispatcher] will be created.
 *
 * If the `context` does not contain a `DispatcherProvider`, a [TestDispatcherProvider] will be
 * created using the `TestDispatcher`.
 *
 * @sample dispatch.test.samples.BuildersSample.testProvidedSample
 * @sample dispatch.test.samples.BuildersSample.testProvidedExtensionSample
 * @see runBlockingTest
 */
@ExperimentalCoroutinesApi
public fun TestDispatchScope.testProvided(
  testBody: suspend TestScope.() -> Unit
): Unit = testProvided(coroutineContext, testBody)

/**
 * Delegates to [runBlockingTest], but injects a [DispatcherProvider] into the created
 * [TestCoroutineScope].
 *
 * If the `context`'s [ContinuationInterceptor] is not a
 * [TestDispatcher][kotlinx.coroutines.test.TestDispatcher], then a new
 * [TestDispatcher][kotlinx.coroutines.test.TestDispatcher] will be created.
 *
 * If the `context` does not contain a `DispatcherProvider`, a [TestDispatcherProvider] will be
 * created using the `TestDispatcher`.
 *
 * @sample dispatch.test.samples.BuildersSample.testProvidedSample
 * @sample dispatch.test.samples.BuildersSample.testProvidedExtensionSample
 * @see runBlockingTest
 */
@ExperimentalCoroutinesApi
public fun TestScope.testProvided(
  testBody: suspend TestScope.() -> Unit
): Unit = testProvided(coroutineContext, testBody)

@ExperimentalCoroutinesApi
public fun TestScope.asTestDispatchScope(): TestDispatchScope = TestDispatchScope(coroutineContext)
