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

package dispatch.android.lifecycle.internal

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LifecycleRegistry
import dispatch.core.launchMainImmediate
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancel
import java.util.concurrent.CancellationException
import kotlin.coroutines.CoroutineContext

internal class DispatchLifecycleScopeBinding(
  private val lifecycle: Lifecycle,
  private val coroutineContext: CoroutineContext
) : LifecycleEventObserver {

  fun bind() {

    if (lifecycle.currentState == Lifecycle.State.DESTROYED) {
      cancelDestroyed()
    } else {
      CoroutineScope(coroutineContext).launchMainImmediate {

        if (lifecycle.currentState == Lifecycle.State.DESTROYED) {
          cancelDestroyed()
        } else {
          lifecycle.addObserver(this@DispatchLifecycleScopeBinding)
        }
      }
    }
    coroutineContext[Job]?.invokeOnCompletion {
      lifecycle.removeObserver(this@DispatchLifecycleScopeBinding)
    }
  }

  private fun cancelDestroyed() {
    lifecycle.removeObserver(this@DispatchLifecycleScopeBinding)
    coroutineContext.cancel(
      LifecycleCancellationException(
        lifecycle = lifecycle,
        minimumState = Lifecycle.State.INITIALIZED
      )
    )
  }

  override fun onStateChanged(source: LifecycleOwner, event: Lifecycle.Event) {
    if (!lifecycle.currentState.isAtLeast(Lifecycle.State.INITIALIZED)) {
      cancelDestroyed()
    }
  }
}

internal class LifecycleCancellationException(
  lifecycle: Lifecycle,
  minimumState: Lifecycle.State
) : CancellationException("Lifecycle $lifecycle dropped below minimum state: $minimumState")
