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

package dispatch.internal.test.android

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.Lifecycle.Event.ON_CREATE
import androidx.lifecycle.Lifecycle.Event.ON_DESTROY
import androidx.lifecycle.Lifecycle.Event.ON_RESUME
import androidx.lifecycle.Lifecycle.Event.ON_START
import androidx.lifecycle.Lifecycle.State.INITIALIZED
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LifecycleRegistry
import dispatch.core.withMain

open class FakeLifecycleOwner(
  initialState: Lifecycle.State = Lifecycle.State.INITIALIZED
) : LifecycleOwner {

  val fakeLifecycle by lazy {

    @Suppress("VisibleForTests")
    LifecycleRegistry.createUnsafe(this)
      .also { registry ->
        when (initialState) {
          Lifecycle.State.DESTROYED -> {
            // We're no longer able to transition from INITIALIZED to DESTROYED. So if we need to do that
            // for a test, just 'create' first and then go down.
            if (registry.currentState == INITIALIZED) {
              registry.handleLifecycleEvent(ON_CREATE)
            }
            registry.handleLifecycleEvent(ON_DESTROY)
          }

          Lifecycle.State.CREATED -> registry.handleLifecycleEvent(ON_CREATE)
          Lifecycle.State.STARTED -> registry.handleLifecycleEvent(ON_START)
          Lifecycle.State.RESUMED -> registry.handleLifecycleEvent(ON_RESUME)
          else -> Unit
        }
      }
  }

  override fun getLifecycle(): LifecycleRegistry = synchronized(this) { fakeLifecycle }

  suspend fun stepDown() = when (lifecycle.currentState) {
    Lifecycle.State.DESTROYED -> throw IllegalArgumentException("already destroyed")
    Lifecycle.State.INITIALIZED -> throw IllegalArgumentException(
      "cannot transition straight from initialized to destroyed"
    )

    Lifecycle.State.CREATED -> destroy()
    Lifecycle.State.STARTED -> stop()
    Lifecycle.State.RESUMED -> pause()
  }

  suspend fun stepUp() = when (lifecycle.currentState) {
    Lifecycle.State.DESTROYED -> throw IllegalArgumentException("already destroyed")
    Lifecycle.State.INITIALIZED -> create()
    Lifecycle.State.CREATED -> start()
    Lifecycle.State.STARTED -> resume()
    Lifecycle.State.RESUMED -> throw IllegalArgumentException("already resumed")
  }

  suspend fun create() = withMain {
    lifecycle.handleLifecycleEvent(Lifecycle.Event.ON_CREATE)
  }

  suspend fun start() = withMain {
    lifecycle.handleLifecycleEvent(Lifecycle.Event.ON_START)
  }

  suspend fun resume() = withMain {

    println(" resuming --   ${Thread.currentThread().id}")
    lifecycle.handleLifecycleEvent(Lifecycle.Event.ON_RESUME)
  }

  suspend fun pause() = withMain {
    lifecycle.handleLifecycleEvent(Lifecycle.Event.ON_PAUSE)
  }

  suspend fun stop() = withMain {
    lifecycle.handleLifecycleEvent(Lifecycle.Event.ON_STOP)
  }

  suspend fun initialize() = withMain {
    lifecycle.currentState = Lifecycle.State.INITIALIZED
  }

  suspend fun destroy() = withMain {
    // We're no longer able to transition from INITIALIZED to DESTROYED. So if we need to do that
    // for a test, just 'create' first and then go down.
    if (lifecycle.currentState == Lifecycle.State.INITIALIZED) {
      lifecycle.handleLifecycleEvent(Lifecycle.Event.ON_CREATE)
    }
    lifecycle.handleLifecycleEvent(Lifecycle.Event.ON_DESTROY)
  }

  fun getObserverCount(): Int {
    return fakeLifecycle.observerCount
  }
}
