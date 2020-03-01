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
import dispatch.android.lifecycle.LifecycleCoroutineScope.MinimumStatePolicy.*
import dispatch.core.*
import dispatch.extensions.channel.*
import dispatch.extensions.flow.*
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.*
import kotlinx.coroutines.flow.*
import java.util.concurrent.atomic.*

@Suppress("EXPERIMENTAL_API_USAGE")
internal fun LifecycleCoroutineScope.launchOn(
  minimumState: Lifecycle.State,
  statePolicy: LifecycleCoroutineScope.MinimumStatePolicy,
  block: suspend LifecycleCoroutineScope.() -> Unit
): Job = when (statePolicy) {
  CANCEL        -> launch { lifecycle.onNext(minimumState, block) }
  RESTART_EVERY -> launchEvery(minimumState, block)
}

@Suppress("EXPERIMENTAL_API_USAGE")
internal suspend fun <T> Lifecycle.onNext(
  minimumState: Lifecycle.State, block: suspend LifecycleCoroutineScope.() -> T
): T? {

  var result: T? = null
  val stateReached = AtomicBoolean(false)

  try {
    // suspend until the lifecycle's flow has reached the minimum state, then move on
    eventFlow(minimumState)
      .onEachLatest { stateIsHighEnough ->
        if (stateIsHighEnough) {
          stateReached.compareAndSet(false, true)
          coroutineScope {
            result = LifecycleCoroutineScope(
              lifecycle = this@onNext,
              coroutineScope = MainImmediateCoroutineScope(coroutineContext)
            ).block()
          }
          throw FlowCancellationException()
        }
      }
      .collectUntil { stateIsHighEnough ->
        !stateIsHighEnough && stateReached.get()
      }
  } catch (e: FlowCancellationException) {
    // do nothing
  }

  return result
}

@Suppress("EXPERIMENTAL_API_USAGE")
internal fun LifecycleCoroutineScope.launchEvery(
  minimumState: Lifecycle.State, block: suspend LifecycleCoroutineScope.() -> Unit
): Job = lifecycle.eventFlow(minimumState)
  // Respond to every change in the Flow, cancelling execution of the previous onEach if it hasn't already finished.
  // This is responsible for cancelling Jobs when a Lifecycle.State dips below the threshold,
  // such as going from CREATED to DESTROYED in launchWhileCreated().
  .onEachLatest { active ->

    if (active) {
      // Create a CoroutineScope which is tied to the receiver LifecycleCoroutineScope.
      // This new CoroutineScope will be automatically cancelled when the parent scope is cancelled,
      // or when onEachLatest executes for a new value.
      coroutineScope { block() }
    }
  }
  // Use the receiver LifecycleCoroutineScope's Job, but ensure that this coroutine is launch immediately from Main.
  // Lifecycle observers can only be added/removed from Main.
  .launchIn(this)

/**
 * Distinct Flow representing `true` if the state is at or above [minimumState], and `false` when below.
 *
 * The flow ends when the lifecycle is [destroyed][Lifecycle.State.DESTROYED]
 */
@Suppress("EXPERIMENTAL_API_USAGE")
internal fun Lifecycle.eventFlow(
  minimumState: Lifecycle.State
): Flow<Boolean> = callbackFlow<Boolean> {

  val observer = LifecycleEventObserver { _, _ ->

    // send true if the state is high enough, false if not
    sendBlockingOrNull(currentState.isAtLeast(minimumState))

    // if lifecycle is destroyed, send the last value to cancel the last block and then close the channel
    if (currentState == Lifecycle.State.DESTROYED) {
      channel.close()
    }
  }

  addObserver(observer)

  // When the channel is closed, remove the observer.
  awaitClose { removeObserver(observer) }
}
  .flowOnMainImmediate()
  // Don't send [true, true] since the second true would cancel the already-active block.
  .distinctUntilChanged()

private class FlowCancellationException : CancellationException("Flow was aborted")
