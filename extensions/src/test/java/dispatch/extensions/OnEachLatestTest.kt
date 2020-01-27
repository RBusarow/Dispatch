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

import io.kotlintest.*
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.test.*
import org.junit.jupiter.api.*

@ExperimentalCoroutinesApi
internal class OnEachLatestTest {

  @Test
  fun `fast collectors should be able to complete normally`() = runBlockingTest {

    val output = mutableListOf<Int>()

    flow {
      emit(1)
      delay(10)
      emit(2)
    }.onEachLatest {
      output.add(it)
    }
      .collect()

    output shouldBe listOf(1, 2)

  }

  @Test
  fun `slow collectors should be cancelled upon new values`() = runBlockingTest {

    val output = mutableListOf<Int>()

    flow {
      emit(1)
      emit(2)
    }.onEachLatest {
      delay(50)
      output.add(it)
    }
      .collect()

    output shouldBe listOf(2)

  }
}
