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
import kotlinx.coroutines.CoroutineStart
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext

/**
 * Launches a new coroutine without blocking the current thread and returns a reference
 * to the coroutine as a [Job]. The coroutine is cancelled when the resulting job is
 * [cancelled][Job.cancel].
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
 * @sample dispatch.core.samples.LaunchSample.launchDefaultSample
 * @see launch
 */
public fun CoroutineScope.launchDefault(
  context: CoroutineContext = EmptyCoroutineContext,
  start: CoroutineStart = CoroutineStart.DEFAULT,
  block: suspend CoroutineScope.() -> Unit
): Job = launch(context + getProvider(context).default, start, block)

/**
 * Launches a new coroutine without blocking the current thread and returns a reference
 * to the coroutine as a [Job]. The coroutine is cancelled when the resulting job is
 * [cancelled][Job.cancel].
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
 * @sample dispatch.core.samples.LaunchSample.launchIOSample
 * @see launch
 */
public fun CoroutineScope.launchIO(
  context: CoroutineContext = EmptyCoroutineContext,
  start: CoroutineStart = CoroutineStart.DEFAULT,
  block: suspend CoroutineScope.() -> Unit
): Job = launch(context + getProvider(context).io, start, block)

/**
 * Launches a new coroutine without blocking the current thread and returns a reference
 * to the coroutine as a [Job]. The coroutine is cancelled when the resulting job is
 * [cancelled][Job.cancel].
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
 * @sample dispatch.core.samples.LaunchSample.launchMainSample
 * @see launch
 */
public fun CoroutineScope.launchMain(
  context: CoroutineContext = EmptyCoroutineContext,
  start: CoroutineStart = CoroutineStart.DEFAULT,
  block: suspend CoroutineScope.() -> Unit
): Job = launch(context + getProvider(context).main, start, block)

/**
 * Launches a new coroutine without blocking the current thread and returns a reference
 * to the coroutine as a [Job]. The coroutine is cancelled when the resulting job is
 * [cancelled][Job.cancel].
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
 * @sample dispatch.core.samples.LaunchSample.launchMainImmediateSample
 * @see launch
 */
public fun CoroutineScope.launchMainImmediate(
  context: CoroutineContext = EmptyCoroutineContext,
  start: CoroutineStart = CoroutineStart.DEFAULT,
  block: suspend CoroutineScope.() -> Unit
): Job = launch(context + getProvider(context).mainImmediate, start, block)

/**
 * Launches a new coroutine without blocking the current thread and returns a reference
 * to the coroutine as a [Job]. The coroutine is cancelled when the resulting job is
 * [cancelled][Job.cancel].
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
 * @sample dispatch.core.samples.LaunchSample.launchUnconfinedSample
 * @see launch
 */
public fun CoroutineScope.launchUnconfined(
  context: CoroutineContext = EmptyCoroutineContext,
  start: CoroutineStart = CoroutineStart.DEFAULT,
  block: suspend CoroutineScope.() -> Unit
): Job = launch(context + getProvider(context).unconfined, start, block)

internal fun CoroutineScope.getProvider(
  context: CoroutineContext
): DispatcherProvider = context[DispatcherProvider] ?: coroutineContext.dispatcherProvider
