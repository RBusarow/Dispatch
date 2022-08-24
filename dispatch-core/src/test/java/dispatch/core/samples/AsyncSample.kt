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

import dispatch.core.asyncDefault
import dispatch.core.asyncIO
import dispatch.core.asyncMain
import dispatch.core.asyncMainImmediate
import dispatch.core.asyncUnconfined
import dispatch.internal.test.Sample5
import dispatch.internal.test.dispatcherName
import dispatch.internal.test.someDispatcherProvider
import io.kotest.matchers.shouldBe
import kotlinx.coroutines.runBlocking

class AsyncSample {

  @Sample5
  fun asyncDefaultSample() = runBlocking(someDispatcherProvider) {

    dispatcherName() shouldBe "runBlocking thread"

    asyncDefault {

      dispatcherName() shouldBe "default"
    }.join()
  }

  @Sample5
  fun asyncIOSample() = runBlocking(someDispatcherProvider) {

    dispatcherName() shouldBe "runBlocking thread"

    asyncIO {

      dispatcherName() shouldBe "io"
    }.join()
  }

  @Sample5
  fun asyncMainSample() = runBlocking(someDispatcherProvider) {

    dispatcherName() shouldBe "runBlocking thread"

    asyncMain {

      dispatcherName() shouldBe "main"
    }.join()
  }

  @Sample5
  fun asyncMainImmediateSample() = runBlocking(someDispatcherProvider) {

    dispatcherName() shouldBe "runBlocking thread"

    asyncMainImmediate {

      dispatcherName() shouldBe "main immediate"
    }.join()
  }

  @Sample5
  fun asyncUnconfinedSample() = runBlocking(someDispatcherProvider) {

    dispatcherName() shouldBe "runBlocking thread"

    asyncUnconfined {

      dispatcherName() shouldBe "unconfined"
    }.join()
  }
}
