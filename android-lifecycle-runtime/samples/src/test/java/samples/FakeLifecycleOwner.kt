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

package samples

import androidx.lifecycle.*
import kotlinx.coroutines.*

@ObsoleteCoroutinesApi
abstract class FakeLifecycleOwner(
  private val mainDispatcher: CoroutineDispatcher = newSingleThreadContext(
    "FakeLifecycleOwner main"
  ),
  initialState: Lifecycle.State = Lifecycle.State.INITIALIZED
) : LifecycleOwner {

  private val registry: LifecycleRegistry by lazy { LifecycleRegistry(this) }

  init {
    when (initialState) {
      Lifecycle.State.DESTROYED -> destroy()
      Lifecycle.State.CREATED   -> create()
      Lifecycle.State.STARTED   -> start()
      Lifecycle.State.RESUMED   -> resume()
      else                      -> Unit
    }
  }

  override fun getLifecycle(): LifecycleRegistry = registry

  fun create() = runBlocking(mainDispatcher) {
    lifecycle.handleLifecycleEvent(Lifecycle.Event.ON_CREATE)
  }

  fun start() = runBlocking(mainDispatcher) {
    lifecycle.handleLifecycleEvent(Lifecycle.Event.ON_START)
  }

  fun resume() = runBlocking(mainDispatcher) {
    lifecycle.handleLifecycleEvent(Lifecycle.Event.ON_RESUME)
  }

  fun pause() = runBlocking(mainDispatcher) {
    lifecycle.handleLifecycleEvent(Lifecycle.Event.ON_PAUSE)
  }

  fun stop() = runBlocking(mainDispatcher) {
    lifecycle.handleLifecycleEvent(Lifecycle.Event.ON_STOP)
  }

  fun destroy() = runBlocking(mainDispatcher) {
    lifecycle.handleLifecycleEvent(Lifecycle.Event.ON_DESTROY)
  }

  private suspend fun getObserverCount(): Int =
    withContext(mainDispatcher) {
      registry.observerCount
    }
}
