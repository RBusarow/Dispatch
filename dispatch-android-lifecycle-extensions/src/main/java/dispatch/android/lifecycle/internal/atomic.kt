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

package dispatch.android.lifecycle.internal

import androidx.lifecycle.*
import dispatch.android.lifecycle.*
import kotlinx.coroutines.*
import java.util.concurrent.*

/**
 * Concurrent getOrPut, that is safe for concurrent maps.
 *
 * Returns the value for the given [key]. If the key is not found in the map, calls the [defaultValue] function,
 * puts its result into the map under the given key and returns it.
 *
 * This method guarantees not to put the value into the map if the key is already there,
 * but the [defaultValue] function may be invoked even if the key is already in the map.
 */
internal inline fun ConcurrentMap<Lifecycle, LifecycleCoroutineScope>.atomicGetOrPut(
  key: Lifecycle,
  defaultValue: () -> LifecycleCoroutineScope
): LifecycleCoroutineScope {

  val existingOrNew = getOrPut(key, defaultValue)

  val secondGet = get(key)!!

  if (existingOrNew != secondGet) {
    existingOrNew.coroutineContext.cancel()
    existingOrNew.lifecycle.removeObserver(LifecycleCoroutineScopeStore)
  }

  return secondGet
}
