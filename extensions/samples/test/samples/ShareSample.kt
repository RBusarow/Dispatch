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
class ShareSample {

  @Sample
  fun shareSample() = runBlocking {

    val sourceFlow = flowOf(1, 2, 3, 4, 5)
      .onStart { println("start source") }
      .onEach { println("emit $it") }
      .onCompletion { println("complete source") }
      .shareIn(this)
    val a = async { sourceFlow.toList() }
    val b = async { sourceFlow.toList() }  // collect concurrently
    println(a.await())
    println(b.await())
    println("** break **")
    println(sourceFlow.toList())

    /*
    prints:

      start source
      emit 1
      emit 2
      emit 3
      emit 4
      emit 5
      complete source
      [1, 2, 3, 4, 5]
      [1, 2, 3, 4, 5]
      ** break **
      start source
      emit 1
      emit 2
      emit 3
      emit 4
      emit 5
      complete source
     */
  }

  @Sample
  fun shareWithCacheSample() = runBlocking {

    val sourceFlow = flowOf(1, 2, 3, 4, 5)
      .onEach {
        delay(50)
        println("emit $it")

      }
      .shareIn(this, 1)

    val a = async { sourceFlow.toList() }
    delay(125)

    val b = async {
      // begin collecting after "emit 3"
      sourceFlow.toList()
    }

    println(a.await())
    println(b.await())

    println("** break **")

    println(sourceFlow.toList())   // the shared flow has been reset, so the cached values are cleared

    /*
    prints:

      emit 1
      emit 2
      emit 3
      emit 4
      emit 5
      [1, 2, 3, 4, 5]
      [2, 3, 4, 5]
       ** break **
      emit 1
      emit 2
      emit 3
      emit 4
      emit 5
      [1, 2, 3, 4, 5]
     */
  }

  @Sample
  fun shareWithResetSample() = runBlocking {

    // resets cache whenever the Flow is reset
    flowOf(1, 2, 3)
      .shareIn(this, 3)

    // persists cache across resets
    flowOf(1, 2, 3)
      .cache(3)
      .shareIn(this)
  }
}
