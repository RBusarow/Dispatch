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

import dispatch.core.withDefault
import dispatch.core.withIO
import dispatch.core.withMain
import dispatch.core.withMainImmediate
import dispatch.core.withUnconfined
import dispatch.internal.test.Sample5
import dispatch.internal.test.dispatcherName
import dispatch.internal.test.someDispatcherProvider
import io.kotest.matchers.shouldBe
import kotlinx.coroutines.runBlocking

class WithContextSample {

  @Sample5
  fun withDefaultSample() = runBlocking(someDispatcherProvider) {

    dispatcherName() shouldBe "runBlocking thread"

    withDefault {

      dispatcherName() shouldBe "default"
    }
  }

  @Sample5
  fun withIOSample() = runBlocking(someDispatcherProvider) {

    dispatcherName() shouldBe "runBlocking thread"

    withIO {

      dispatcherName() shouldBe "io"
    }
  }

  @Sample5
  fun withMainSample() = runBlocking(someDispatcherProvider) {

    dispatcherName() shouldBe "runBlocking thread"

    withMain {

      dispatcherName() shouldBe "main"
    }
  }

  @Sample5
  fun withMainImmediateSample() = runBlocking(someDispatcherProvider) {

    dispatcherName() shouldBe "runBlocking thread"

    withMainImmediate {

      dispatcherName() shouldBe "main immediate"
    }
  }

  @Sample5
  fun withUnconfinedSample() = runBlocking(someDispatcherProvider) {

    dispatcherName() shouldBe "runBlocking thread"

    withUnconfined {

      dispatcherName() shouldBe "unconfined"
    }
  }
}
