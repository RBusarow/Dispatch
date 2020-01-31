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


package dispatch.android.internal

import androidx.lifecycle.*
import dispatch.android.*
import dispatch.core.*
import dispatch.extensions.*
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.*
import kotlinx.coroutines.flow.*

@Suppress("EXPERIMENTAL_API_USAGE")
internal fun <T> LifecycleCoroutineScope.launchOnlyWhen(
  minimumState: Lifecycle.State, block: suspend CoroutineScope.() -> T
): Job = callbackFlow<Boolean> {

  val observer = LifecycleEventObserver { _, _ ->

    // send true if the state is high enough, false if not
    sendBlocking(lifecycle.currentState.isAtLeast(minimumState))

    // if screen is destroyed, send the last value to cancel the last block and then close the channel
    if (lifecycle.currentState == Lifecycle.State.DESTROYED) {
      channel.close()
    }
  }

  lifecycle.addObserver(observer)

  // When the channel is closed, remove the observer.
  awaitClose { lifecycle.removeObserver(observer) }
}
  // Don't send [true, true] since the second true would cancel the already-active block.
  .distinctUntilChanged()
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
  .launchIn(this + dispatcherProvider.mainImmediate)
