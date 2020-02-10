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
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*

@FlowPreview
@ExperimentalCoroutinesApi
class OnEachLatestSample {

  @Sample
  fun onEachLatestSample() = runBlocking {

    flow {
      emit(1)
      delay(50)
      emit(2)
    }
      .onEachLatest { value ->
        println("Collecting $value")
        delay(100)            // Emulate work
        println("$value collected")
      }
      .launchIn(this)

    //  prints "Collecting 1, Collecting 2, 2 collected"
  }
}

