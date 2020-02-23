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
import kotlinx.coroutines.channels.*
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.sync.*

@FlowPreview
@ExperimentalCoroutinesApi
internal fun <T> Flow<T>.asSharedFlow(
  scope: CoroutineScope, cacheHistory: Int
): Flow<T> = SharedFlow(this, scope, cacheHistory)

/**
 * An auto-resetting [broadcast] flow.  It tracks the number of active collectors, and automatically resets when
 * the number reaches 0.
 *
 * `SharedFlow` has an optional [cache], where the latest _n_ elements emitted by the source Flow will be replayed to
 * late collectors.
 *
 * ### Upon reset
 * 1) The underlying [BroadcastChannel] is closed. A new BroadcastChannel will be created when a new collector starts.
 * 2) The cache is reset.  New collectors will not receive values from before the reset, but will generate a new cache.
 */
@ExperimentalCoroutinesApi
@FlowPreview
internal class SharedFlow<T>(
  private val sourceFlow: Flow<T>,
  private val scope: CoroutineScope,
  private val cacheHistory: Int
) : AbstractFlow<T>() {

  private var refCount = 0
  private var cache = CircularArray<T>(cacheHistory)
  private val mutex = Mutex(false)

  init {
    require(cacheHistory >= 0) { "cacheHistory parameter must be at least 0, but was $cacheHistory" }
  }

  override suspend fun collectSafely(
    collector: FlowCollector<T>
  ) = collector.emitAll(createFlow())

  // Replay happens per new collector, if cacheHistory > 0.
  private suspend fun createFlow(): Flow<T> = getChannel()
    .asFlow()
    .replayIfNeeded()
    .onCompletion { onCollectEnd() }

  private suspend fun getChannel(): BroadcastChannel<T> = mutex.withLock {
    refCount++
    lazyChannelRef.value
  }

  // lazy holder for the BroadcastChannel, which is reset whenever all collection ends
  private var lazyChannelRef = createLazyChannel()

  // must be lazy so that the broadcast doesn't begin immediately after a reset
  private fun createLazyChannel() = lazy(LazyThreadSafetyMode.NONE) {
    sourceFlow.cacheIfNeeded()
      .broadcastIn(scope)
  }

  private fun Flow<T>.replayIfNeeded(): Flow<T> = if (cacheHistory > 0) {
    onStart {
      cache.forEach {
        emit(it)
      }
    }
  } else this

  private fun Flow<T>.cacheIfNeeded(): Flow<T> = if (cacheHistory > 0) {
    onEach { value ->
      // While flowing, also record all values in the cache.
      cache.add(value)
    }
  } else this

  private suspend fun onCollectEnd() = mutex.withLock { if (--refCount == 0) reset() }

  private fun reset() {
    cache = CircularArray(cacheHistory)

    lazyChannelRef.value.cancel()
    lazyChannelRef = createLazyChannel()
  }
}

