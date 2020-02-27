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
import kotlinx.coroutines.channels.*
import kotlinx.coroutines.flow.*

/**
 * Creates a [broadcast] coroutine which collects the [Flow] receiver and shares with multiple collectors.
 *
 * A [BroadcastChannel] with [default][Channel.Factory.BUFFERED] buffer size is created.
 * Use [buffer] operator on the flow before calling `shareIn` to specify a value other than
 * default and to control what happens when data is produced faster than it is consumed,
 * that is to control back-pressure behavior.
 *
 * Concurrent collectors will all collect from a single [broadcast] flow.  This flow will be cancelled automatically
 * when it is no longer being collected, and the underlying channel will be closed.
 *
 * If a new collector is added after the channel has been closed, a new channel will be created.
 *
 * By default, this flow is effectively **stateless** in that collectors will only receive values emitted after collection begins.
 *
 * ### Caching
 *
 * When a shared flow is [cached][cache], the values are recorded as they are emitted from the source Flow.
 * They are then replayed for each new subscriber.
 *
 * When a shared flow is reset, the cached values are cleared.
 *
 * In order to have cached values persist across resets, use `cache(n)` before `shareIn(...)`.
 *
 *
 * ### Cancellation semantics
 * 1) Flow consumer is cancelled when the original channel is cancelled.
 * 2) Flow consumer completes normally when the original channel completes (~is closed) normally.
 * 3) Collection is cancelled when the (scope)[CoroutineScope] parameter is cancelled,
 * thereby ending the consumer when it has run out of elements.
 * 4) If the flow consumer fails with an exception, subscription is cancelled.
 *
 * @param scope The [CoroutineScope] used to create the [broadcast] coroutine.  Cancellation of this scope
 * will close the underlying [BroadcastChannel].
 * @param cacheHistory (default = 0).  Any value greater than zero will add a [cache] to the shared Flow.
 *
 * @see cache
 *
 * @sample samples.ShareSample.shareSample
 * @sample samples.ShareSample.shareWithCacheSample
 * @sample samples.ShareSample.shareWithResetSample
 */
@ExperimentalCoroutinesApi
@FlowPreview
fun <T> Flow<T>.shareIn(
  scope: CoroutineScope, cacheHistory: Int = 0
): Flow<T> = asSharedFlow(scope, cacheHistory)

