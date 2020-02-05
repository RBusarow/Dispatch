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

package dispatch.android.lifecycle

import androidx.lifecycle.*
import dispatch.android.lifecycle.internal.*
import kotlinx.coroutines.*

/**
 * Executes [block] one time, the next time the [Lifecycle]'s state is at least [Lifecycle.State.CREATED].
 *
 * If the lifecycle is already in this state, [block] will be executed immediately.
 *
 * @see [LifecycleCoroutineScope.launchEveryCreate] for repeating behavior.
 */
suspend fun <T> LifecycleOwner.onNextCreate(
  block: suspend CoroutineScope.() -> T
): T? = lifecycle.onNext(Lifecycle.State.CREATED, block)

/**
 * Executes [block] one time, the next time the [Lifecycle]'s state is at least [Lifecycle.State.CREATED].
 *
 * If the lifecycle is already in this state, [block] will be executed immediately.
 *
 * @see [LifecycleCoroutineScope.launchEveryCreate] for repeating behavior.
 */
suspend fun <T> Lifecycle.onNextCreate(
  block: suspend CoroutineScope.() -> T
): T? = onNext(Lifecycle.State.CREATED, block)

/**
 * Executes [block] one time, the next time the [Lifecycle]'s state is at least [Lifecycle.State.STARTED].
 *
 * If the lifecycle is already in this state, [block] will be executed immediately.
 *
 * @see [LifecycleCoroutineScope.launchEveryStart] for repeating behavior.
 */
suspend fun <T> LifecycleOwner.onNextStart(
  block: suspend CoroutineScope.() -> T
): T? = lifecycle.onNext(Lifecycle.State.STARTED, block)

/**
 * Executes [block] one time, the next time the [Lifecycle]'s state is at least [Lifecycle.State.STARTED].
 *
 * If the lifecycle is already in this state, [block] will be executed immediately.
 *
 * @see [LifecycleCoroutineScope.launchEveryStart] for repeating behavior.
 */
suspend fun <T> Lifecycle.onNextStart(
  block: suspend CoroutineScope.() -> T
): T? = onNext(Lifecycle.State.STARTED, block)

/**
 * Executes [block] one time, the next time the [Lifecycle]'s state is at least [Lifecycle.State.RESUMED].
 *
 * If the lifecycle is already in this state, [block] will be executed immediately.
 *
 * @see [LifecycleCoroutineScope.launchEveryResume] for repeating behavior.
 */
suspend fun <T> LifecycleOwner.onNextResume(
  block: suspend CoroutineScope.() -> T
): T? = lifecycle.onNext(Lifecycle.State.RESUMED, block)

/**
 * Executes [block] one time, the next time the [Lifecycle]'s state is at least [Lifecycle.State.RESUMED].
 *
 * If the lifecycle is already in this state, [block] will be executed immediately.
 *
 * @see [LifecycleCoroutineScope.launchEveryResume] for repeating behavior.
 */
suspend fun <T> Lifecycle.onNextResume(
  block: suspend CoroutineScope.() -> T
): T? = onNext(Lifecycle.State.RESUMED, block)

