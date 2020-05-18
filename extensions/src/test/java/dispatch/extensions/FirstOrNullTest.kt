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

import dispatch.internal.test.*
import io.kotest.matchers.*
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.*
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.test.*
import org.junit.jupiter.api.*

@FlowPreview
@ExperimentalCoroutinesApi
internal class FirstOrNullTest : BaseTest() {

  @Test
  fun `should stop collecting immediately after receiving an element`() = runBlockingTest {

    val flow = flowOf(2, 3).onEach {
      if (it == 3) {
        fail("should not be reached")
      }
    }

    expect(1)

    flow
      .onEach { expect(2) }
      .firstOrNull()

    finish(3)
  }

  @Test
  fun `should return first element in flow`() = runBlockingTest {

    val channel = Channel<Int>()

    val flow = channel.consumeAsFlow()

    val job = launch(start = CoroutineStart.UNDISPATCHED) {

      flow.firstOrNull() shouldBe 1
    }

    channel.send(1)

    job.join()
  }

  @Test
  fun `should suspend if flow is not cancelled but not emitting`() = runBlockingTest {

    val channel = Channel<Int>()

    val flow = channel.consumeAsFlow()

    val job = launch(start = CoroutineStart.UNDISPATCHED) {

      expect(1)

      flow.firstOrNull()

      expect(2)
    }

    channel.send(1)

    expect(3)

    job.join()

    finish(4)
  }

  @Test
  fun `should return null if flow is empty`() = runBlockingTest {

    val flow = flowOf<Unit>()

    flow.firstOrNull() shouldBe null
  }

}
