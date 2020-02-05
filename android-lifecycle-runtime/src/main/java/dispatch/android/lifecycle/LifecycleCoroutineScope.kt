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
import dispatch.core.*
import kotlinx.coroutines.*

/**
 * [CoroutineScope] instance for the [LifecycleOwner].
 * By default, it uses the [Dispatchers.Main.immediate][kotlinx.coroutines.MainCoroutineDispatcher.immediate] dispatcher.
 *
 * The `lifecycleScope` instance is created automatically upon first access,
 * from the factory set in [LifecycleScopeFactory].
 *
 * The type of [CoroutineScope] created is configurable via [LifecycleScopeFactory.set].
 *
 * The `viewModelScope` is automatically cancelled when the [LifecycleOwner]'s [lifecycle][LifecycleOwner.getLifecycle]'s [Lifecycle.State] drops to [Lifecycle.State.DESTROYED].
 */
val LifecycleOwner.lifecycleScope: LifecycleCoroutineScope
  get() = LifecycleCoroutineScopeStore.get(this.lifecycle)

/**
 * [MainImmediateCoroutineScope] instance which is tied to a [Lifecycle].
 *
 * The [CoroutineScope] provides lifecycle-aware [launch] functions
 * which will automatically start upon reaching their associated [Lifecycle.Event],
 * then automatically cancel upon the [lifecycle] dropping below that state.  Reaching
 * that state again will start a new [Job].
 */
class LifecycleCoroutineScope(
  internal val lifecycle: Lifecycle,
  private val coroutineScope: MainImmediateCoroutineScope
) : MainImmediateCoroutineScope by coroutineScope {

  /**
   * Lifecycle-aware function for launching a coroutine any time the [Lifecycle.State]
   * is at least [Lifecycle.State.CREATED].
   *
   * [block] is executed using the receiver [CoroutineScope]'s [Job] as a parent,
   * but always executes using [Dispatchers.Main] as its [CoroutineDispatcher].
   *
   * Execution of [block] is cancelled when the receiver [CoroutineScope] is cancelled,
   * or when [lifecycle]'s [Lifecycle.State] drops below [Lifecycle.State.CREATED].
   *
   * example:
   *
   * ```
   * class SomeFragment : Fragment {
   *
   *   init {
   *     lifecycleScope.launchEveryCreate {
   *       viewModel.someFlow.collect {
   *         printLn("new value --> $it")
   *       }
   *     }
   *   }
   * }
   *```
   */
  fun launchEveryCreate(
    block: suspend CoroutineScope.() -> Unit
  ): Job = launchEvery(Lifecycle.State.CREATED, block)

  /**
   * Lifecycle-aware function for launching a coroutine any time the [Lifecycle.State]
   * is at least [Lifecycle.State.STARTED].
   *
   * [block] is executed using the receiver [CoroutineScope]'s [Job] as a parent,
   * but always executes using [Dispatchers.Main] as its [CoroutineDispatcher].
   *
   * Execution of [block] is cancelled when the receiver [CoroutineScope] is cancelled,
   * or when [lifecycle]'s [Lifecycle.State] drops below [Lifecycle.State.STARTED].
   *
   * example:
   *
   * ```
   * class SomeFragment : Fragment {
   *
   *   init {
   *     lifecycleScope.launchEveryStart {
   *       viewModel.someFlow.collect {
   *         printLn("new value --> $it")
   *       }
   *     }
   *   }
   * }
   *```
   */
  fun launchEveryStart(
    block: suspend CoroutineScope.() -> Unit
  ): Job = launchEvery(Lifecycle.State.STARTED, block)

  /**
   * Lifecycle-aware function for launching a coroutine any time the [Lifecycle.State]
   * is at least [Lifecycle.State.RESUMED].
   *
   * [block] is executed using the receiver [CoroutineScope]'s [Job] as a parent,
   * but always executes using [Dispatchers.Main] as its [CoroutineDispatcher].
   *
   * Execution of [block] is cancelled when the receiver [CoroutineScope] is cancelled,
   * or when [lifecycle]'s [Lifecycle.State] drops below [Lifecycle.State.RESUMED].
   *
   * example:
   *
   * ```
   * class SomeFragment : Fragment {
   *
   *   init {
   *     lifecycleScope.launchEveryResume {
   *       viewModel.someFlow.collect {
   *         printLn("new value --> $it")
   *       }
   *     }
   *   }
   * }
   *```
   */
  fun launchEveryResume(
    block: suspend CoroutineScope.() -> Unit
  ): Job = launchEvery(Lifecycle.State.RESUMED, block)

}
