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

package samples

import dispatch.extensions.flow.*
import io.kotlintest.*
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*

@ExperimentalCoroutinesApi
class FirstOrNullSample {

  @Sample
  fun firstOrNullSample() = runBlocking {

    val firstFlow = flowOf(1, 2, 3, 4)
    firstFlow.firstOrNull() shouldBe 1

    val secondFlow = flowOf<Int>()
    secondFlow.firstOrNull() shouldBe null
  }
}
