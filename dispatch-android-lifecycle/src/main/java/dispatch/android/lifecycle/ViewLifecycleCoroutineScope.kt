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

package dispatch.android.lifecycle

import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import dispatch.android.lifecycle.internal.bindViewLifecycleCoroutineScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlin.coroutines.CoroutineContext

/**
 * [DispatchLifecycleScope] instance which is tied to a [Fragment's][Fragment] View
 * [lifecycle][Lifecycle].
 */
public class ViewLifecycleCoroutineScope internal constructor(
  lifecycle: Lifecycle,
  coroutineContext: CoroutineContext
) : DispatchLifecycleScope(lifecycle, coroutineContext) {

  /**
   * Every time the View [Lifecycle State][androidx.lifecycle.Lifecycle.State] reaches
   * [CREATED][androidx.lifecycle.Lifecycle.State.CREATED], create a new coroutine and collect this
   * [Flow].
   *
   * @see kotlinx.coroutines.flow.launchIn
   */
  public fun <T> Flow<T>.launchOnCreate(): Job = launchOnCreate(
    minimumStatePolicy = MinimumStatePolicy.RESTART_EVERY
  ) { collect() }

  /**
   * Every time the View [Lifecycle State][androidx.lifecycle.Lifecycle.State] reaches
   * [STARTED][androidx.lifecycle.Lifecycle.State.STARTED], create a new coroutine and collect this
   * [Flow].
   *
   * @see kotlinx.coroutines.flow.launchIn
   */
  public fun <T> Flow<T>.launchOnStart(): Job = launchOnStart(
    minimumStatePolicy = MinimumStatePolicy.RESTART_EVERY
  ) { collect() }

  /**
   * Every time the View [Lifecycle State][androidx.lifecycle.Lifecycle.State] reaches
   * [RESUMED][androidx.lifecycle.Lifecycle.State.RESUMED], create a new coroutine and collect this
   * [Flow].
   *
   * @see kotlinx.coroutines.flow.launchIn
   */
  public fun <T> Flow<T>.launchOnResume(): Job = launchOnResume(
    minimumStatePolicy = MinimumStatePolicy.RESTART_EVERY
  ) { collect() }
}

/**
 * [CoroutineScope] helper for a [Fragment]'s
 * [ViewLifecycleOwner][androidx.fragment.app.FragmentViewLifecycleOwner].
 *
 * This function observes a `Fragment`'s
 * [viewLifecycleOwnerLiveData][androidx.fragment.app.Fragment.getViewLifecycleOwnerLiveData],
 * and invokes [block].
 *
 * @sample dispatch.android.lifecycle.samples.WithViewLifecycleScopeSample.sample
 */
@ExperimentalCoroutinesApi
public fun CoroutineScope.withViewLifecycle(
  fragment: Fragment,
  block: ViewLifecycleCoroutineScope.() -> Unit
): Job {

  return bindViewLifecycleCoroutineScope(this, fragment, block)
}
