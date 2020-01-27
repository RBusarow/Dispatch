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

package com.rickbusarow.dispatcherprovider

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob
import kotlin.coroutines.ContinuationInterceptor
import kotlin.coroutines.CoroutineContext

/**
 * Marker interface which designates a [CoroutineScope] with a [CoroutineDispatcher] of `default`.
 */
public interface DefaultCoroutineScope : CoroutineScope

/**
 * Marker interface which designates a [CoroutineScope] with a [CoroutineDispatcher] of `io`.
 */
public interface IOCoroutineScope : CoroutineScope

/**
 * Marker interface which designates a [CoroutineScope] with a [CoroutineDispatcher] of `main`.
 */
public interface MainCoroutineScope : CoroutineScope

/**
 * Marker interface which designates a [CoroutineScope] with a [CoroutineDispatcher] of `mainImmediate`.
 */
public interface MainImmediateCoroutineScope : CoroutineScope

/**
 * Marker interface which designates a [CoroutineScope] with a [CoroutineDispatcher] of `unconfined`.
 */
public interface UnconfinedCoroutineScope : CoroutineScope

/**
 * Factory function for a [DefaultCoroutineScope] with a [DispatcherProvider].
 * Dispatch defaults to the `default` property of the `DispatcherProvider`.
 *
 * @param job [Job] to be used for the resulting `CoroutineScope`.  Uses a [SupervisorJob] if one is not provided.
 * @param dispatcherProvider [DispatcherProvider] to be used for the resulting `CoroutineScope`.  Uses a [DefaultDispatcherProvider] if one is not provided.
 *
 * @see CoroutineScope
 */
public fun DefaultCoroutineScope(
  job: Job = SupervisorJob(),
  dispatcherProvider: DispatcherProvider = DefaultDispatcherProvider()
): DefaultCoroutineScope = object : DefaultCoroutineScope {
  override val coroutineContext = job + dispatcherProvider.default + dispatcherProvider
}

/**
 * Factory function for a [DefaultCoroutineScope] with a [DispatcherProvider].
 * Dispatch defaults to the `default` property of the `DispatcherProvider`.
 *
 * @param coroutineContext [CoroutineContext] to be used for the resulting `CoroutineScope`.
 * Any existing [ContinuationInterceptor] will be overwritten.
 * If the `CoroutineContext` does not already contain a `DispatcherProvider`, a [DefaultDispatcherProvider] will be added.
 *
 * @see CoroutineScope
 */
public fun DefaultCoroutineScope(
  coroutineContext: CoroutineContext
): DefaultCoroutineScope = object : DefaultCoroutineScope {
  override val coroutineContext = coroutineContext.withDefaultElements { default }
}

/**
 * Factory function for an [IOCoroutineScope] with a [DispatcherProvider].
 * Dispatch defaults to the `io` property of the `DispatcherProvider`.
 *
 * @param job [Job] to be used for the resulting `CoroutineScope`.  Uses a [SupervisorJob] if one is not provided.
 * @param dispatcherProvider [DispatcherProvider] to be used for the resulting `CoroutineScope`.  Uses a [DefaultDispatcherProvider] if one is not provided.
 *
 * @see CoroutineScope
 */
public fun IOCoroutineScope(
  job: Job = SupervisorJob(),
  dispatcherProvider: DispatcherProvider = DefaultDispatcherProvider()
): IOCoroutineScope = object : IOCoroutineScope {
  override val coroutineContext = job + dispatcherProvider.io + dispatcherProvider
}

/**
 * Factory function for a [IOCoroutineScope] with a [DispatcherProvider].
 * Dispatch defaults to the `io` property of the `DispatcherProvider`.
 *
 * @param coroutineContext [CoroutineContext] to be used for the resulting `CoroutineScope`.
 * Any existing [ContinuationInterceptor] will be overwritten.
 * If the `CoroutineContext` does not already contain a `DispatcherProvider`, a [DefaultDispatcherProvider] will be added.
 *
 * @see CoroutineScope
 */
public fun IOCoroutineScope(
  coroutineContext: CoroutineContext
): IOCoroutineScope = object : IOCoroutineScope {
  override val coroutineContext = coroutineContext.withDefaultElements { io }
}

/**
 * Factory function for a [MainCoroutineScope] with a [DispatcherProvider].
 * Dispatch defaults to the `main` property of the `DispatcherProvider`.
 *
 * @param job [Job] to be used for the resulting `CoroutineScope`.  Uses a [SupervisorJob] if one is not provided.
 * @param dispatcherProvider [DispatcherProvider] to be used for the resulting `CoroutineScope`.  Uses a [DefaultDispatcherProvider] if one is not provided.
 *
 * @see CoroutineScope
 */
public fun MainCoroutineScope(
  job: Job = SupervisorJob(),
  dispatcherProvider: DispatcherProvider = DefaultDispatcherProvider()
): MainCoroutineScope = object : MainCoroutineScope {
  override val coroutineContext = job + dispatcherProvider.main + dispatcherProvider
}

/**
 * Factory function for a [MainCoroutineScope] with a [DispatcherProvider].
 * Dispatch defaults to the `main` property of the `DispatcherProvider`.
 *
 * @param coroutineContext [CoroutineContext] to be used for the resulting `CoroutineScope`.
 * Any existing [ContinuationInterceptor] will be overwritten.
 * If the `CoroutineContext` does not already contain a `DispatcherProvider`, a [DefaultDispatcherProvider] will be added.
 *
 * @see CoroutineScope
 */
public fun MainCoroutineScope(
  coroutineContext: CoroutineContext
): MainCoroutineScope = object : MainCoroutineScope {
  override val coroutineContext = coroutineContext.withDefaultElements { main }
}

/**
 * Factory function for a [MainImmediateCoroutineScope] with a [DispatcherProvider].
 * Dispatch defaults to the `mainImmediate` property of the `DispatcherProvider`.
 *
 * @param job [Job] to be used for the resulting `CoroutineScope`.  Uses a [SupervisorJob] if one is not provided.
 * @param dispatcherProvider [DispatcherProvider] to be used for the resulting `CoroutineScope`.  Uses a [DefaultDispatcherProvider] if one is not provided.
 *
 * @see CoroutineScope
 */
public fun MainImmediateCoroutineScope(
  job: Job = SupervisorJob(), dispatcherProvider:
  DispatcherProvider = DefaultDispatcherProvider()
): MainImmediateCoroutineScope = object :
  MainImmediateCoroutineScope {
  override val coroutineContext = job + dispatcherProvider.mainImmediate +
      dispatcherProvider
}

/**
 * Factory function for a [MainImmediateCoroutineScope] with a [DispatcherProvider].
 * Dispatch defaults to the `mainImmediate` property of the `DispatcherProvider`.
 *
 * @param coroutineContext [CoroutineContext] to be used for the resulting `CoroutineScope`.
 * Any existing [ContinuationInterceptor] will be overwritten.
 * If the `CoroutineContext` does not already contain a `DispatcherProvider`, a [DefaultDispatcherProvider] will be added.
 *
 * @see CoroutineScope
 */
public fun MainImmediateCoroutineScope(
  coroutineContext: CoroutineContext
): MainImmediateCoroutineScope = object : MainImmediateCoroutineScope {
  override val coroutineContext = coroutineContext.withDefaultElements { mainImmediate }
}

/**
 * Factory function for a [UnconfinedCoroutineScope] with a [DispatcherProvider].
 * Dispatch defaults to the `unconfined` property of the `DispatcherProvider`.
 *
 * @param job [Job] to be used for the resulting `CoroutineScope`.  Uses a [SupervisorJob] if one is not provided.
 * @param dispatcherProvider [DispatcherProvider] to be used for the resulting `CoroutineScope`.  Uses a [DefaultDispatcherProvider] if one is not provided.
 *
 * @see CoroutineScope
 */
public fun UnconfinedCoroutineScope(
  job: Job = SupervisorJob(),
  dispatcherProvider: DispatcherProvider = DefaultDispatcherProvider()
): UnconfinedCoroutineScope = object : UnconfinedCoroutineScope {
  override val coroutineContext = job + dispatcherProvider.unconfined + dispatcherProvider
}

/**
 * Factory function for a [UnconfinedCoroutineScope] with a [DispatcherProvider].
 * Dispatch defaults to the `unconfined` property of the `DispatcherProvider`.
 *
 * @param coroutineContext [CoroutineContext] to be used for the resulting `CoroutineScope`.
 * Any existing [ContinuationInterceptor] will be overwritten.
 * If the `CoroutineContext` does not already contain a `DispatcherProvider`, a [DefaultDispatcherProvider] will be added.
 *
 * @see CoroutineScope
 */
public fun UnconfinedCoroutineScope(
  coroutineContext: CoroutineContext
): UnconfinedCoroutineScope = object : UnconfinedCoroutineScope {
  override val coroutineContext = coroutineContext.withDefaultElements { unconfined }
}

private inline fun CoroutineContext.withDefaultElements(
  dispatcherPromise: DispatcherProvider.() -> ContinuationInterceptor
): CoroutineContext {

  val job = get(Job) ?: SupervisorJob()
  val provider = get(DispatcherProvider) ?: DefaultDispatcherProvider()

  return this + job + provider + provider.dispatcherPromise()
}
