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
import dispatch.android.lifecycle.DispatchLifecycleScope
import dispatch.android.lifecycle.DispatchLifecycleScope.MinimumStatePolicy.CANCEL
import dispatch.android.lifecycle.DispatchLifecycleScope.MinimumStatePolicy.RESTART_EVERY
import dispatch.core.flowOnMainImmediate
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.Job
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.currentCoroutineContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.takeWhile
import kotlinx.coroutines.flow.transformLatest
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.coroutines.CoroutineContext

internal fun DispatchLifecycleScope.launchOn(
  context: CoroutineContext,
  minimumState: Lifecycle.State,
  statePolicy: DispatchLifecycleScope.MinimumStatePolicy,
  block: suspend CoroutineScope.() -> Unit
): Job = when (statePolicy) {
  CANCEL -> launch { lifecycle.onNext(context, minimumState, block) }
  RESTART_EVERY -> launchEvery(context, minimumState, block)
}

internal suspend fun <T> Lifecycle.onNext(
  context: CoroutineContext,
  minimumState: Lifecycle.State,
  block: suspend CoroutineScope.() -> T
): T? {

  var result: T? = null
  var stateReached = false
  var completed = false

  // suspend until the lifecycle's flow has reached the minimum state, then move on
  eventFlow(minimumState)
    .onEachLatest { stateIsHighEnough ->

      println("                                          on each --  $stateIsHighEnough")

      if (stateIsHighEnough) {
        stateReached = true
        coroutineScope {
          withContext(context + currentCoroutineContext()[Job]!!) {
            result = block()
          }
        }
        completed = true
      }
    }
    .collectUntil { stateIsHighEnough ->

      println("                                          high enough -- ${!stateIsHighEnough} -- reached $stateReached")

      stateReached && (completed || !stateIsHighEnough)
    }

  return result
}

internal fun DispatchLifecycleScope.launchEvery(
  context: CoroutineContext,
  minimumState: Lifecycle.State,
  block: suspend CoroutineScope.() -> Unit
): Job = lifecycle.eventFlow(minimumState)
  // Respond to every change in the Flow, cancelling execution of the previous onEach if it hasn't already finished.
  // This is responsible for cancelling Jobs when a Lifecycle.State dips below the threshold,
  // such as going from CREATED to DESTROYED in launchWhileCreated().
  .onEachLatest { active ->

    if (active) {
      // Create a CoroutineScope which is tied to the receiver LifecycleCoroutineScope.
      // This new CoroutineScope will be automatically cancelled when the parent scope is cancelled,
      // or when onEachLatest executes for a new value.
      coroutineScope {
        withContext(context + coroutineContext[Job]!!) { block() }
      }
    }
  }
  // Use the receiver LifecycleCoroutineScope's Job, but ensure that this coroutine is launch immediately from Main.
  // Lifecycle observers can only be added/removed from Main.
  .launchIn(this)

/**
 * Distinct Flow representing `true` if the state is at or above [minimumState], and `false` when
 * below.
 *
 * The flow ends when the lifecycle is [destroyed][Lifecycle.State.DESTROYED]
 */
@OptIn(ExperimentalCoroutinesApi::class)
internal fun Lifecycle.eventFlow(
  minimumState: Lifecycle.State
): Flow<Boolean> {

  val flow = MutableSharedFlow<Lifecycle.State>(
    // replay of 1 emits the current value to new collectors
    replay = 1
  )

  // immediately send the current state.  Otherwise there's no events until the lifecycle changes.
  flow.tryEmit(currentState)

  val observer = LifecycleEventObserver { owner, _ ->

    println("emit state -- ${owner.lifecycle.currentState}")

    // send true if the state is high enough, false if not
    flow.tryEmit(owner.lifecycle.currentState)
  }

  addObserver(observer)

  return flow
    .takeWhile {

      println("new state -- $it   ${Thread.currentThread().id}")

      // stop collecting when the lifecycle is destroyed.  This triggers `onCompletion` below
      it != Lifecycle.State.DESTROYED
    }
    .mapLatest { it.isAtLeast(minimumState) }
    .onCompletion {
      // remove the LifecycleEventObserver from the Lifecycle receiver when the flow collection ends
      removeObserver(observer)
      if (minimumState == Lifecycle.State.CREATED) {
        // If minimumState is CREATED, then we're listening for the DESTROYED state to send false.
        // But DESTROYED is filtered out above and cancels the Flow, so `false` is never sent.
        // If we reach this point with a CREATED minimumState, then the lifecycle is DESTROYED,
        // so we send a final `false` so that the downstream collectors know they can stop.
        emit(false)
      }
    }
    .flowOnMainImmediate()
    // Don't send [true, true] since the second true would cancel the already-active block.
    .distinctUntilChanged()
}

/** Terminal operator which collects the given [Flow] until the [predicate] returns true. */
private suspend fun <T> Flow<T>.collectUntil(
  predicate: suspend (T) -> Boolean
) = takeWhile { !predicate(it) }.collect()

/**
 * Returns a flow which performs the given [action] on each value of the original flow.
 *
 * The crucial difference from [onEach] is that when the original flow emits a new value, the
 * [action] block for previous value is cancelled.
 */
@OptIn(ExperimentalCoroutinesApi::class)
internal fun <T> Flow<T>.onEachLatest(action: suspend (T) -> Unit) = transformLatest { value ->
  action(value)
  return@transformLatest emit(value)
}
