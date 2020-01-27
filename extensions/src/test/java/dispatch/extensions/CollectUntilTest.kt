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
import io.kotlintest.*
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.*
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.test.*
import org.junit.jupiter.api.*

@FlowPreview
@ExperimentalCoroutinesApi
internal class CollectUntilTest : BaseTest() {

  @Test
  fun `should stop collecting immediately after finding a match`() = runBlockingTest {

    val flow = flowOf(2, 3, 4).onEach {
      if (it == 4) {
        fail("should not be reached")
      }
    }

    expect(1)

    flow
      .onEach { expect(it) }
      .collectUntil { it == 3 }

    finish(4)
  }

  @Test
  fun `should collect to the end if none match`() = runBlockingTest {

    val flow = flowOf(2, 3, 4, 5, 6)

    expect(1)

    flow
      .onEach { expect(it) }
      .collectUntil { it == 7 }

    finish(7)
  }

  @Test
  fun `should suspend until completed or a match is found`() = runBlockingTest {

    val channel = Channel<Int>()

    val flow = channel.consumeAsFlow()

    val job = launch(start = CoroutineStart.UNDISPATCHED) {

      expect(1)

      flow.collectUntil { it == 2 }

      expect(3)
    }

    channel.send(1)

    expect(2)

    channel.send(2)

    job.join()

    finish(4)
  }

}

