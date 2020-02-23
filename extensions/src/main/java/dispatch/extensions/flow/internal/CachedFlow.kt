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

package dispatch.extensions.flow.internal

import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*

@ExperimentalCoroutinesApi
@FlowPreview
internal fun <T> Flow<T>.asCachedFlow(
  cacheHistory: Int
): Flow<T> {

  require(cacheHistory > 0) { "cacheHistory parameter must be greater than 0, but was $cacheHistory" }

  val cache = CircularArray<T>(cacheHistory)

  return onEach { value ->
    // While flowing, also record all values in the cache.
    cache.add(value)
  }.onStart {
    // Before emitting any values in sourceFlow,
    // emit any cached values starting with the oldest.
    cache.forEach { emit(it) }
  }
}
