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

@file:JvmMultifileClass
@file:JvmName("FlowKt")

package dispatch.extensions.flow

import dispatch.extensions.flow.internal.*
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*

/**
 * Terminal operator which suspends until the [Flow] has completed
 * or a value has been emitted which matches the provided [predicate].
 *
 * Returns true immediately upon collecting a matching value.
 *
 * This terminal operator returns false if the flow completes without ever matching.
 *
 * If the flow being collected never completes and never emits a matching value, the function will suspend indefinitely.
 *
 * @sample samples.AnySample.anySample
 */
suspend fun <T> Flow<T>.any(predicate: (T) -> Boolean): Boolean {

  var result = false
  try {
    collect { value ->
      if (predicate(value)) {
        result = true
        throw FlowCancellationException()
      }
    }
  } catch (e: FlowCancellationException) {
    // Do nothing
  }
  return result
}

/**
 * Terminal operator which collects the given [Flow] until the [predicate] returns true.
 *
 * @sample samples.CollectUntilSample.collectUntilSample
 */
@ExperimentalCoroutinesApi
suspend fun <T> Flow<T>.collectUntil(
  predicate: suspend (T) -> Boolean
) = takeWhile { !predicate(it) }.collect()

/**
 * Terminal operator which suspends until the [Flow] has emitted one value, then immediately returns that value.
 *
 * Returns null if the `Flow` completes without emitting any values.
 *
 * If the flow being collected never completes and never emits a matching value, the function will suspend indefinitely.
 *
 * @sample samples.FirstOrNullSample.firstOrNullSample
 */
@ExperimentalCoroutinesApi
suspend fun <T> Flow<T>.firstOrNull(): T? {
  var result: T? = null
  try {
    take(1).collect { value ->
      result = value
      throw FlowCancellationException()
    }
  } catch (e: FlowCancellationException) {
    // Do nothing
  }
  return result
}

