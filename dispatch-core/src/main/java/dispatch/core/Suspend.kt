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

package dispatch.core

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.currentCoroutineContext
import kotlinx.coroutines.withContext
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext

/**
 * Calls the specified suspending block with a given coroutine context, suspends until it completes,
 * and returns the result.
 *
 * Uses the [default][DispatcherProvider.default] [dispatcher][CoroutineDispatcher].
 *
 * The specific `dispatcherProvider` instance to be used is determined *after* the [context]
 * parameter has been [folded][CoroutineContext.fold] into the receiver's context.
 *
 * The selected [DispatcherProvider] is essentially the first non-null result from:
 * - the [context] parameter
 * - the receiver [CoroutineScope]'s [coroutineContext][CoroutineScope.coroutineContext]
 * - [DefaultDispatcherProvider.get] Extracts the [DispatcherProvider] from the [CoroutineScope]
 *   receiver
 *
 * @sample dispatch.core.samples.WithContextSample.withDefaultSample
 * @see withContext
 */
public suspend fun <T> withDefault(
  context: CoroutineContext = EmptyCoroutineContext,
  block: suspend CoroutineScope.() -> T
): T {
  val newContext = context + getProvider(context).default
  return withContext(newContext, block)
}

/**
 * Calls the specified suspending block with a given coroutine context, suspends until it completes,
 * and returns the result.
 *
 * Uses the [io][DispatcherProvider.io] [dispatcher][CoroutineDispatcher].
 *
 * The specific `dispatcherProvider` instance to be used is determined *after* the [context]
 * parameter has been [folded][CoroutineContext.fold] into the receiver's context.
 *
 * The selected [DispatcherProvider] is essentially the first non-null result from:
 * - the [context] parameter
 * - the receiver [CoroutineScope]'s [coroutineContext][CoroutineScope.coroutineContext]
 * - [DefaultDispatcherProvider.get] Extracts the [DispatcherProvider] from the [CoroutineScope]
 *   receiver
 *
 * @sample dispatch.core.samples.WithContextSample.withIOSample
 * @see withContext
 */
public suspend fun <T> withIO(
  context: CoroutineContext = EmptyCoroutineContext,
  block: suspend CoroutineScope.() -> T
): T {
  val newContext = context + getProvider(context).io
  return withContext(newContext, block)
}

/**
 * Calls the specified suspending block with a given coroutine context, suspends until it completes,
 * and returns the result.
 *
 * Uses the [main][DispatcherProvider.main] [dispatcher][CoroutineDispatcher].
 *
 * The specific `dispatcherProvider` instance to be used is determined *after* the [context]
 * parameter has been [folded][CoroutineContext.fold] into the receiver's context.
 *
 * The selected [DispatcherProvider] is essentially the first non-null result from:
 * - the [context] parameter
 * - the receiver [CoroutineScope]'s [coroutineContext][CoroutineScope.coroutineContext]
 * - [DefaultDispatcherProvider.get] Extracts the [DispatcherProvider] from the [CoroutineScope]
 *   receiver
 *
 * @sample dispatch.core.samples.WithContextSample.withMainSample
 * @see withContext
 */
public suspend fun <T> withMain(
  context: CoroutineContext = EmptyCoroutineContext,
  block: suspend CoroutineScope.() -> T
): T {
  val newContext = context + getProvider(context).main
  return withContext(newContext, block)
}

/**
 * Calls the specified suspending block with a given coroutine context, suspends until it completes,
 * and returns the result.
 *
 * Uses the [mainImmediate][DispatcherProvider.mainImmediate] [dispatcher][CoroutineDispatcher].
 *
 * The specific `dispatcherProvider` instance to be used is determined *after* the [context]
 * parameter has been [folded][CoroutineContext.fold] into the receiver's context.
 *
 * The selected [DispatcherProvider] is essentially the first non-null result from:
 * - the [context] parameter
 * - the receiver [CoroutineScope]'s [coroutineContext][CoroutineScope.coroutineContext]
 * - [DefaultDispatcherProvider.get] Extracts the [DispatcherProvider] from the [CoroutineScope]
 *   receiver
 *
 * @sample dispatch.core.samples.WithContextSample.withMainImmediateSample
 * @see withContext
 */
public suspend fun <T> withMainImmediate(
  context: CoroutineContext = EmptyCoroutineContext,
  block: suspend CoroutineScope.() -> T
): T {
  val newContext = context + getProvider(context).mainImmediate
  return withContext(newContext, block)
}

/**
 * Calls the specified suspending block with a given coroutine context, suspends until it completes,
 * and returns the result.
 *
 * Uses the [unconfined][DispatcherProvider.unconfined] [dispatcher][CoroutineDispatcher].
 *
 * The specific `dispatcherProvider` instance to be used is determined *after* the [context]
 * parameter has been [folded][CoroutineContext.fold] into the receiver's context.
 *
 * The selected [DispatcherProvider] is essentially the first non-null result from:
 * - the [context] parameter
 * - the receiver [CoroutineScope]'s [coroutineContext][CoroutineScope.coroutineContext]
 * - [DefaultDispatcherProvider.get] Extracts the [DispatcherProvider] from the [CoroutineScope]
 *   receiver
 *
 * @sample dispatch.core.samples.WithContextSample.withUnconfinedSample
 * @see withContext
 */
public suspend fun <T> withUnconfined(
  context: CoroutineContext = EmptyCoroutineContext,
  block: suspend CoroutineScope.() -> T
): T {
  val newContext = context + getProvider(context).unconfined
  return withContext(newContext, block)
}

internal suspend fun getProvider(
  newContext: CoroutineContext
): DispatcherProvider = newContext[DispatcherProvider]
  ?: currentCoroutineContext().dispatcherProvider
