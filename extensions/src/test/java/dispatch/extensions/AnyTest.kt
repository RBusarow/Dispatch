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

import com.rickbusarow.dispatcherprovider.*
import io.kotlintest.*
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.sync.*
import kotlinx.coroutines.test.*
import org.junit.jupiter.api.*

@ExperimentalCoroutinesApi
internal class AnyTest {

  @Test
  fun `should return true if predicate matches an existing element`() = runBlockingTest {

    val flow = flowOf(1, 2, 3, 4, 5)

    flow.any { it == 1 } shouldBe true
  }

  @Test
  fun `should return true immediately after finding a match`() = runBlockingTest {

    val flow = flowOf(1, 2, 3, 4, 5).onEach {
      if (it > 2) {
        throw AssertionError("this flow should be stopped at 2")
      }
    }

    flow.any { it == 2 } shouldBe true
  }

  @Test
  fun `should return false if none match`() = runBlockingTest {

    val flow = flowOf(1, 2, 3, 4, 5)

    flow.any { it == 6 } shouldBe false
  }

  @Test
  fun `should suspend until completed or a match is found`() = runBlockingTest {

    val lock = Mutex(true)

    val f = flow {
      lock.withLock {
        emit(1)
      }
    }

    val anyDeferred = asyncUnconfined {
      f.any { true }
    }

    anyDeferred.isActive shouldBe true

    lock.unlock()

    anyDeferred.await() shouldBe true
  }

}
