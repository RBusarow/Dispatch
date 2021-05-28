/*
 * Copyright (C) 2021 Rick Busarow
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

@file:Suppress("TooManyFunctions")

package dispatch.core

import kotlinx.coroutines.*
import kotlin.coroutines.*

/**
 * Factory function for an [IOCoroutineScope] with a [DispatcherProvider].
 * Dispatch defaults to the `io` property of the `DispatcherProvider`.
 *
 * @param job [Job] to be used for the resulting `CoroutineScope`.  Uses a [SupervisorJob] if one is not provided.
 * @param dispatcherProvider [DispatcherProvider] to be used for the resulting `CoroutineScope`.  Uses [DefaultDispatcherProvider.get] if one is not provided.
 *
 * @see CoroutineScope
 */
public fun IOCoroutineScope(
  job: Job = SupervisorJob(),
  dispatcherProvider: DispatcherProvider = DefaultDispatcherProvider.get()
): IOCoroutineScope = object : IOCoroutineScope {
  override val coroutineContext = job + dispatcherProvider.io + dispatcherProvider
}

/**
 * Factory function for a [IOCoroutineScope] with a [DispatcherProvider].
 * Dispatch defaults to the `io` property of the `DispatcherProvider`.
 *
 * @param coroutineContext [CoroutineContext] to be used for the resulting `CoroutineScope`.
 * Any existing [ContinuationInterceptor] will be overwritten.
 * If the `CoroutineContext` does not already contain a `DispatcherProvider`, [DefaultDispatcherProvider.get] will be added.
 *
 * @see CoroutineScope
 */
public fun IOCoroutineScope(
  coroutineContext: CoroutineContext
): IOCoroutineScope = object : IOCoroutineScope {
  override val coroutineContext = coroutineContext.withDefaultElements { io }
}
