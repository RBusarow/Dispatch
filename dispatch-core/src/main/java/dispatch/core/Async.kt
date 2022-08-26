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

@file:Suppress("DeferredIsResult")

package dispatch.core

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.CoroutineStart
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.async
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext

/**
 * Creates a coroutine and returns its future result as an implementation of [Deferred].
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
 * @sample dispatch.core.samples.AsyncSample.asyncDefaultSample
 * @see async
 */
public fun <T> CoroutineScope.asyncDefault(
  context: CoroutineContext = EmptyCoroutineContext,
  start: CoroutineStart = CoroutineStart.DEFAULT,
  block: suspend CoroutineScope.() -> T
): Deferred<T> = async(context + getProvider(context).default, start, block)

/**
 * Creates a coroutine and returns its future result as an implementation of [Deferred].
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
 * @sample dispatch.core.samples.AsyncSample.asyncIOSample
 * @see async
 */
public fun <T> CoroutineScope.asyncIO(
  context: CoroutineContext = EmptyCoroutineContext,
  start: CoroutineStart = CoroutineStart.DEFAULT,
  block: suspend CoroutineScope.() -> T
): Deferred<T> = async(context + getProvider(context).io, start, block)

/**
 * Creates a coroutine and returns its future result as an implementation of [Deferred].
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
 * @sample dispatch.core.samples.AsyncSample.asyncMainSample
 * @see async
 */
public fun <T> CoroutineScope.asyncMain(
  context: CoroutineContext = EmptyCoroutineContext,
  start: CoroutineStart = CoroutineStart.DEFAULT,
  block: suspend CoroutineScope.() -> T
): Deferred<T> = async(context + getProvider(context).main, start, block)

/**
 * Creates a coroutine and returns its future result as an implementation of [Deferred].
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
 * @sample dispatch.core.samples.AsyncSample.asyncMainImmediateSample
 * @see async
 */
public fun <T> CoroutineScope.asyncMainImmediate(
  context: CoroutineContext = EmptyCoroutineContext,
  start: CoroutineStart = CoroutineStart.DEFAULT,
  block: suspend CoroutineScope.() -> T
): Deferred<T> = async(context + getProvider(context).mainImmediate, start, block)

/**
 * Creates a coroutine and returns its future result as an implementation of [Deferred].
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
 * @sample dispatch.core.samples.AsyncSample.asyncUnconfinedSample
 * @see async
 */
public fun <T> CoroutineScope.asyncUnconfined(
  context: CoroutineContext = EmptyCoroutineContext,
  start: CoroutineStart = CoroutineStart.DEFAULT,
  block: suspend CoroutineScope.() -> T
): Deferred<T> = async(context + getProvider(context).unconfined, start, block)
