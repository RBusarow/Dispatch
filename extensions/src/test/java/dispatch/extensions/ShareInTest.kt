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

package dispatch.extensions

import dispatch.core.test.*
import dispatch.extensions.flow.*
import io.kotest.matchers.*
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.*
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.sync.*
import org.junit.jupiter.api.*

@FlowPreview
@ExperimentalCoroutinesApi
internal class ShareInTest : CoroutineTest {

  override lateinit var testScope: TestProvidedCoroutineScope

  @Test
  fun `one collector should reset the source when done`() = runBlockingTest {

    var startInvocations = 0
    var completeInvocations = 0

    val flow = flowOf(1, 2, 3, 4, 5)
      .onStart { startInvocations++ }
      .onCompletion { completeInvocations++ }
      .shareIn(this)

    val one = flow.toList()

    one shouldBe listOf(1, 2, 3, 4, 5)

    startInvocations shouldBe 1
    completeInvocations shouldBe 1

  }

  @Test
  fun `sequential collectors should reset the source each time`() = runBlockingTest {

    var startInvocations = 0
    var completeInvocations = 0

    val flow = flowOf(1, 2, 3, 4, 5)
      .onStart { startInvocations++ }
      .onCompletion { completeInvocations++ }
      .shareIn(this)

    val one = flow.toList()
    val two = flow.toList()

    one shouldBe listOf(1, 2, 3, 4, 5)
    two shouldBe listOf(1, 2, 3, 4, 5)

    startInvocations shouldBe 2
    completeInvocations shouldBe 2

  }

  @Test
  fun `concurrent collectors should share one source`() = runBlocking {

    var startInvocations = 0
    var completeInvocations = 0

    val lock = Channel<Unit>()

    val flow = flowOf(1, 2, 3, 4, 5)
      .onStart { startInvocations++ }
      .onCompletion { completeInvocations++ }
      .shareIn(this)

    val one = async {
      flow.onEach {
          lock.receive()
        }
        .toList()
    }
    val two = async {
      flow.onEach {
          lock.send(Unit)
        }
        .toList()
    }

    one.await() shouldBe listOf(1, 2, 3, 4, 5)
    two.await() shouldBe listOf(1, 2, 3, 4, 5)

    startInvocations shouldBe 1
    completeInvocations shouldBe 1
  }

  @Test
  fun `late collectors should only get new values`() = runBlocking {

    val lock = BroadcastChannel<Unit>(1)

    val sharedLock = lock.openSubscription()
    val oneLock = lock.openSubscription()

    val flow = flowOf(1, 2, 3, 4, 5)
      .onEach { sharedLock.receive() }
      .shareIn(this)

    val one = async {
      flow.onEach { oneLock.receive() }
        .toList()
    }

    lock.send(Unit) // emit(1)
    lock.send(Unit) // emit(2)

    val two = async {

      lock.send(Unit) // emit(3) after this coroutine has started

      flow.onEach {
          lock.send(Unit)
        }
        .toList()
    }

    one.await() shouldBe listOf(1, 2, 3, 4, 5)
    two.await() shouldBe listOf(3, 4, 5)
  }

  @Test
  fun `cache should replay for late collectors`() = runBlocking {

    val sourceLock = Mutex(true)
    val collectorLock = Mutex(true)

    val flow = flowOf(1, 2, 3, 4, 5)
      .onEach {
        if (it == 4) sourceLock.withLock { } // wait for second consumer to begin before continuing
      }
      .shareIn(this, 2)

    val one = async {
      flow.onEach { if (it == 2) collectorLock.unlock() }
        .toList()
    }

    val two = async {

      collectorLock.withLock {
        flow.onEach { if (it == 3) sourceLock.unlock() }
          .toList()
      }
    }

    one.await() shouldBe listOf(1, 2, 3, 4, 5)
    two.await() shouldBe listOf(2, 3, 4, 5)

  }

  @Test
  fun `refCount of zero should reset the cache`() = runBlockingTest {

    val flow = flowOf(1, 2, 3, 4, 5)
      .shareIn(this, 2)

    val collect1 = flow.toList()

    collect1 shouldBe listOf(1, 2, 3, 4, 5)

    val collect2 = flow.toList()

    collect2 shouldBe listOf(1, 2, 3, 4, 5)

  }

  @Test
  fun `refCount of zero should cancel the internal channel`() = runBlockingTest {

    val sourceBroadcastChannel = BroadcastChannel<Int>(1)

    val sourceFlow = sourceBroadcastChannel.asFlow()

    val sharedFlow = sourceFlow.shareIn(this)

    /*
    first sharing session begins
     */
    val listOneDeferred = async(start = CoroutineStart.UNDISPATCHED) {
      sharedFlow
        .take(1)
        .toList()
    }

    yield() // ensure that the "listOneDeferred" async has begun collection before sending

    sourceBroadcastChannel.send(1)

    listOneDeferred.await() shouldBe listOf(1)

    val newReceiveChannel = sourceBroadcastChannel.openSubscription()

    val sendList = listOf(2, 3, 4, 5)

    /*
    second sharing session begins
     */
    val listTwoDeferred = async(start = CoroutineStart.UNDISPATCHED) {
      sharedFlow.take(sendList.size)
        .toList()
    }

    val listThreeDeferred = async {
      newReceiveChannel.consumeAsFlow()
        .shareIn(this)
        .take(sendList.size)
        .toList()
    }

    sourceBroadcastChannel.isClosedForSend shouldBe false

    yield() // ensure that the "listTwoDeferred" async has begun collection before sending

    sendList.forEach {
      sourceBroadcastChannel.send(it)
    }

    // Reaching here means that the sends aren't suspending,
    // which means that the internal channel created during the first sharing session was properly closed,
    // else there would be deadlock.
    listTwoDeferred.await() shouldBe sendList
    listThreeDeferred.await() shouldBe sendList

    sourceBroadcastChannel.isClosedForSend shouldBe false

  }

}

