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
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestCoroutineScheduler
import kotlinx.coroutines.test.TestDispatcher
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import kotlin.coroutines.ContinuationInterceptor
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext

/**
 * Delegates to [runBlocking], but injects a [DispatcherProvider] into the created [CoroutineScope].
 *
 * The resultant [CoroutineContext] will use a
 * [BlockingEventLoop][kotlinx.coroutines.BlockingEventLoop] as its default
 * [ContinuationInterceptor].
 *
 * If the `context` does not contain a `DispatcherProvider`, a [TestDispatcherProvider] will be
 * created using the [kotlinx.coroutines.BlockingEventLoop] interceptor.
 *
 * @param context The base `CoroutineContext` which will be modified to use a
 *   [TestDispatcherProvider]. [EmptyCoroutineContext] is used if one is not provided.
 * @param block the action to be performed
 * @sample dispatch.test.samples.BuildersSample.runBlockingProvidedSample
 * @see runBlocking
 * @see testProvided
 */
@DelicateCoroutinesApi
@ExperimentalCoroutinesApi
public fun runBlockingProvided(
  context: CoroutineContext = EmptyCoroutineContext,
  block: suspend CoroutineScope.() -> Unit
): Unit = runBlocking(context) {

  val existingDispatcherProvider = context[DispatcherProvider]

  val newContext = if (existingDispatcherProvider == null) {
    coroutineContext + TestDispatcherProvider()
  } else coroutineContext

  CoroutineScope(newContext).block()
}

/**
 * Delegates to [runTest], but injects a [DispatcherProvider] into the created [TestScope].
 *
 * If the `context`'s [ContinuationInterceptor] is not a [TestDispatcher], then a new
 * [TestDispatcher] will be created.
 *
 * If the `context` does not contain a `DispatcherProvider`, a [TestDispatcherProvider] will be
 * created using the `TestCoroutineDispatcher`.
 *
 * @param context The base `CoroutineContext` which will be modified to use a
 *   `TestCoroutineDispatcher` and `TestDispatcherProvider`.
 *   [EmptyCoroutineContext] is used if one is not provided.
 * @param testBody the action to be performed
 * @sample dispatch.test.samples.BuildersSample.testProvidedSample
 * @see runTest
 * @see runBlockingProvided
 */
@ExperimentalCoroutinesApi
public fun testProvided(
  context: CoroutineContext = EmptyCoroutineContext,
  testBody: suspend TestScope.() -> Unit
) {
  val scheduler = context[TestCoroutineScheduler]

  val dispatcher = context[ContinuationInterceptor] as? TestDispatcher
    ?: StandardTestDispatcher(scheduler)

  testProvidedInternal(dispatcher, context, testBody)
}

/**
 * Delegates to [runTest], but injects a [DispatcherProvider] into the created [TestScope].
 *
 * If the `context`'s [ContinuationInterceptor] is not a [TestDispatcher], then a new
 * [TestDispatcher] will be created.
 *
 * If the `context` does not contain a `DispatcherProvider`, a [TestDispatcherProvider] will be
 * created using the `TestCoroutineDispatcher`.
 *
 * @param context The base `CoroutineContext` which will be modified to use a
 *   `TestCoroutineDispatcher` and `TestDispatcherProvider`.
 *   [EmptyCoroutineContext] is used if one is not provided.
 * @param testBody the action to be performed
 * @sample dispatch.test.samples.BuildersSample.testProvidedSample
 * @see runTest
 * @see runBlockingProvided
 */
@ExperimentalCoroutinesApi
public fun testProvidedUnconfined(
  context: CoroutineContext = EmptyCoroutineContext,
  testBody: suspend TestScope.() -> Unit
) {

  val scheduler = context[TestCoroutineScheduler]

  val existingUnconfined = when (val existing = context[ContinuationInterceptor]) {
    is TestDispatcher -> existing.takeIf { !it.isDispatchNeeded(context) }
    else -> null
  }
  val dispatcher = existingUnconfined ?: UnconfinedTestDispatcher(scheduler)
  testProvidedInternal(dispatcher, context, testBody)
}

@ExperimentalCoroutinesApi
private fun testProvidedInternal(
  dispatcher: TestDispatcher,
  context: CoroutineContext = EmptyCoroutineContext,
  testBody: suspend TestScope.() -> Unit
) {
  val dispatcherProvider = context[DispatcherProvider] as? TestDispatcherProvider
    ?: TestDispatcherProvider(dispatcher)

  val combinedContext =
    context.minusKey(CoroutineExceptionHandler) + dispatcher + dispatcherProvider

  return TestScope(context = combinedContext).runTest {
    testBody.invoke(this)
  }
}

/**
 * Delegates to [runTest], but injects a [DispatcherProvider] into the created [TestScope].
 *
 * If the `context`'s [ContinuationInterceptor] is not a [TestDispatcher], then a new
 * [TestDispatcher] will be created.
 *
 * If the `context` does not contain a `DispatcherProvider`, a [TestDispatcherProvider] will be
 * created using the `TestCoroutineDispatcher`.
 *
 * @sample dispatch.test.samples.BuildersSample.testProvidedSample
 * @sample dispatch.test.samples.BuildersSample.testProvidedExtensionSample
 * @see runTest
 * @see runBlockingProvided
 */
@ExperimentalCoroutinesApi
public fun TestProvidedCoroutineScope.testProvided(
  testBody: suspend TestScope.() -> Unit
): Unit = testProvided(coroutineContext, testBody)
