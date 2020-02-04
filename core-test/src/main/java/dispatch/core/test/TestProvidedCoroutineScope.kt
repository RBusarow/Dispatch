/*
 * Copyright (C) 2020 Rick Busarow
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

package dispatch.core.test

import dispatch.core.*
import kotlinx.coroutines.*
import kotlinx.coroutines.test.*
import kotlin.coroutines.*

/**
 * A polymorphic testing [CoroutineScope] interface.
 *
 * This single interface implements:
 *  [TestCoroutineScope]
 *  [DefaultCoroutineScope]
 *  [IOCoroutineScope]
 *  [MainCoroutineScope]
 *  [MainImmediateCoroutineScope]
 *  [UnconfinedCoroutineScope]
 *
 * This means that it can be injected into any class or function
 * regardless of what type of `CoroutineScope` is required.
 */
@ExperimentalCoroutinesApi
interface TestProvidedCoroutineScope : TestCoroutineScope,
  DefaultCoroutineScope,
  IOCoroutineScope,
  MainCoroutineScope,
  MainImmediateCoroutineScope,
  UnconfinedCoroutineScope {

  val dispatcherProvider: DispatcherProvider
}

@ExperimentalCoroutinesApi
internal class TestProvidedCoroutineScopeImpl(
  override val dispatcherProvider: DispatcherProvider,
  context: CoroutineContext = EmptyCoroutineContext
) : TestProvidedCoroutineScope,
  TestCoroutineScope by TestCoroutineScope(context + dispatcherProvider)

/**
 * Creates a [TestProvidedCoroutineScope] implementation with optional parameters of
 * [TestCoroutineDispatcher], [TestDispatcherProvider], and a generic [CoroutineContext].
 *
 * The resultant `TestProvidedCoroutineScope` will utilize a single `TestCoroutineDispatcher`
 * for all the [CoroutineDispatcher] properties of its [DispatcherProvider],
 * and the [ContinuationInterceptor] Key of the `CoroutineContext` will also return that `TestCoroutineDispatcher`.
 */
@ExperimentalCoroutinesApi
fun TestProvidedCoroutineScope(
  dispatcher: TestCoroutineDispatcher = TestCoroutineDispatcher(),
  dispatcherProvider: TestDispatcherProvider = TestDispatcherProvider(dispatcher),
  context: CoroutineContext = EmptyCoroutineContext
): TestProvidedCoroutineScope =
  TestProvidedCoroutineScopeImpl(
    dispatcherProvider = dispatcherProvider,
    context = context + dispatcher
  )
