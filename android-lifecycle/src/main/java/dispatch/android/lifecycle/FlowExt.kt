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
import dispatch.android.lifecycle.LifecycleCoroutineScope.*
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*

/**
 * Lifecycle-aware function for collecting a [Flow] while time the [Lifecycle.State]
 * is at least [Lifecycle.State.CREATED].
 *
 * [Collection][collect] is performed using this [LifecycleCoroutineScope] to create a coroutine.
 *
 * Collection is cancelled when this [LifecycleCoroutineScope] is cancelled,
 * or when the [LifecycleCoroutineScope.lifecycle]'s [Lifecycle.State] drops below [Lifecycle.State.CREATED].
 *
 * @param minimumStatePolicy *optional* - the way this [Job] will behave when passing below the minimum
 * state or re-entering.  Uses [MinimumStatePolicy.RESTART_EVERY] by default.
 * @sample samples.CollectWhileSample.launchOnCreateCancellingSample
 * @sample samples.CollectWhileSample.launchOnCreateRestartingSample
 */
fun <T> Flow<T>.launchOnCreate(
  scope: LifecycleCoroutineScope,
  minimumStatePolicy: MinimumStatePolicy = MinimumStatePolicy.RESTART_EVERY
) {
  scope.launchOnCreate(minimumStatePolicy) {
    collect()
  }
}

/**
 * Lifecycle-aware function for collecting a [Flow] while time the [Lifecycle.State]
 * is at least [Lifecycle.State.CREATED].
 *
 * [Collection][collect] is performed using this [LifecycleCoroutineScope] to create a coroutine.
 *
 * Collection is cancelled when this [LifecycleCoroutineScope] is cancelled,
 * or when the [LifecycleCoroutineScope.lifecycle]'s [Lifecycle.State] drops below [Lifecycle.State.STARTED].
 *
 * @param minimumStatePolicy *optional* - the way this [Job] will behave when passing below the minimum
 * state or re-entering.  Uses [MinimumStatePolicy.RESTART_EVERY] by default.
 * @sample samples.CollectWhileSample.launchOnStartCancellingSample
 * @sample samples.CollectWhileSample.launchOnStartRestartingSample
 */
fun <T> Flow<T>.launchOnStart(
  scope: LifecycleCoroutineScope,
  minimumStatePolicy: MinimumStatePolicy = MinimumStatePolicy.RESTART_EVERY
) {
  scope.launchOnStart(minimumStatePolicy) {
    collect()
  }
}

/**
 * Lifecycle-aware function for collecting a [Flow] while time the [Lifecycle.State]
 * is at least [Lifecycle.State.CREATED].
 *
 * [Collection][collect] is performed using this [LifecycleCoroutineScope] to create a coroutine.
 *
 * Collection is cancelled when this [LifecycleCoroutineScope] is cancelled,
 * or when the [LifecycleCoroutineScope.lifecycle]'s [Lifecycle.State] drops below [Lifecycle.State.RESUMED].
 *
 * @param minimumStatePolicy *optional* - the way this [Job] will behave when passing below the minimum
 * state or re-entering.  Uses [MinimumStatePolicy.RESTART_EVERY] by default.
 * @sample samples.CollectWhileSample.launchOnResumeCancellingSample
 * @sample samples.CollectWhileSample.launchOnResumeRestartingSample
 */
fun <T> Flow<T>.launchOnResume(
  scope: LifecycleCoroutineScope,
  minimumStatePolicy: MinimumStatePolicy = MinimumStatePolicy.RESTART_EVERY
) {
  scope.launchOnResume(minimumStatePolicy) {
    collect()
  }
}
