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
import kotlinx.coroutines.test.TestCoroutineScope
import kotlinx.coroutines.test.TestDispatcher
import kotlin.coroutines.ContinuationInterceptor
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext

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
public interface TestDispatchScope :
  DefaultCoroutineScope,
  IOCoroutineScope,
  MainCoroutineScope,
  MainImmediateCoroutineScope,
  UnconfinedCoroutineScope {
  /** single [DispatcherProvider] promise for the [TestProvidedCoroutineScope] */
  public val dispatcherProvider: DispatcherProvider
}

/** @suppress internal use only */
@ExperimentalCoroutinesApi
internal class TestDispatchScopeImpl(
  override val dispatcherProvider: DispatcherProvider,
  context: CoroutineContext = EmptyCoroutineContext
) : TestDispatchScope,
  CoroutineScope by CoroutineScope(context + dispatcherProvider)

/**
 * Creates a [TestDispatchScope] implementation with optional parameters of
 * [TestDispatcher][kotlinx.coroutines.test.TestDispatcher], [TestDispatcherProvider], and a generic
 * [CoroutineContext].
 *
 * The resultant `TestDispatchScope` will utilize a single `StandardTestDispatcher`
 * for all the [CoroutineDispatcher] properties of its [DispatcherProvider], and the
 * [ContinuationInterceptor] Key of the `CoroutineContext` will also return that `TestDispatcher`.
 */
@ExperimentalCoroutinesApi
public fun TestDispatchScope(
  context: CoroutineContext = EmptyCoroutineContext,
  dispatcherProvider: DispatcherProvider = context[DispatcherProvider]
    ?: (context[ContinuationInterceptor] as? TestDispatcher)
      ?.let { existing ->
        TestDispatcherProvider(existing)
      } ?: TestDispatcherProvider()
): TestDispatchScope = TestDispatchScopeImpl(
  dispatcherProvider = dispatcherProvider,
  context = context
)
