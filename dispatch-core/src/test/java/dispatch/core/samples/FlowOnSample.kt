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

package dispatch.core.samples

import dispatch.core.flowOnDefault
import dispatch.core.flowOnIO
import dispatch.core.flowOnMain
import dispatch.core.flowOnMainImmediate
import dispatch.core.flowOnUnconfined
import dispatch.internal.test.Sample5
import dispatch.internal.test.dispatcherName
import dispatch.internal.test.someDispatcherProvider
import io.kotest.matchers.shouldBe
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking

@ExperimentalCoroutinesApi
class FlowOnSample {

  @Sample5
  fun flowOnDefaultSample() = runBlocking(someDispatcherProvider) {

    dispatcherName() shouldBe "runBlocking thread"

    flow {

      dispatcherName() shouldBe "default"

      emit(Unit)
    }.flowOnDefault() // switch to the "default" dispatcher for everything upstream
      .collect() // collect the flow from the "main" dispatcher
  }

  @Sample5
  fun flowOnIOSample() = runBlocking(someDispatcherProvider) {

    dispatcherName() shouldBe "runBlocking thread"

    flow {

      dispatcherName() shouldBe "io"

      emit(Unit)
    }.flowOnIO() // switch to the "io" dispatcher for everything upstream
      .collect() // collect the flow from the "main" dispatcher
  }

  @Sample5
  fun flowOnMainSample() = runBlocking(someDispatcherProvider) {

    dispatcherName() shouldBe "runBlocking thread"

    flow {

      dispatcherName() shouldBe "main"

      emit(Unit)
    }.flowOnMain() // switch to the "main" dispatcher for everything upstream
      .collect() // collect the flow from the "default" dispatcher
  }

  @Sample5
  fun flowOnMainImmediateSample() =
    runBlocking(someDispatcherProvider) {

      dispatcherName() shouldBe "runBlocking thread"

      flow {

        dispatcherName() shouldBe "main immediate"

        emit(Unit)
      }.flowOnMainImmediate() // switch to the "mainImmediate" dispatcher for everything upstream
        .collect() // collect the flow from the "main" dispatcher
    }

  @Sample5
  fun flowOnUnconfinedSample() = runBlocking(someDispatcherProvider) {

    dispatcherName() shouldBe "runBlocking thread"

    flow {

      dispatcherName() shouldBe "unconfined"

      emit(Unit)
    }.flowOnUnconfined() // switch to the "unconfined" dispatcher for everything upstream
      .collect() // collect the flow from the "main" dispatcher
  }
}
