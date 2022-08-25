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

package dispatch.core

import io.kotest.matchers.shouldBe
import io.kotest.matchers.types.shouldBeInstanceOf
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.Job
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import kotlin.coroutines.CoroutineContext

@ExperimentalCoroutinesApi
internal class CoroutineScopeExtKtTest {

  val provider = DispatcherProvider(
    default = UnconfinedTestDispatcher(),
    io = UnconfinedTestDispatcher(),
    main = UnconfinedTestDispatcher(),
    mainImmediate = UnconfinedTestDispatcher(),
    unconfined = UnconfinedTestDispatcher()
  )

  @Test
  fun `CoroutineScope default dispatcher`() {

    CoroutineScope(provider).defaultDispatcher shouldBe provider.default
  }

  @Test
  fun `CoroutineScope io dispatcher`() {

    CoroutineScope(provider).ioDispatcher shouldBe provider.io
  }

  @Test
  fun `CoroutineScope main dispatcher`() {

    CoroutineScope(provider).mainDispatcher shouldBe provider.main
  }

  @Test
  fun `CoroutineScope mainImmediate dispatcher`() {

    CoroutineScope(provider).mainImmediateDispatcher shouldBe provider.mainImmediate
  }

  @Test
  fun `CoroutineScope unconfined dispatcher`() {

    CoroutineScope(provider).unconfinedDispatcher shouldBe provider.unconfined
  }

  @Nested
  inner class `CoroutineScope dispatcherProvider property` {

    @Test
    fun `call from scope with existing provider should return that provider`() {

      val scope = CoroutineScope(provider)

      scope.dispatcherProvider shouldBe provider
    }

    @Test
    fun `call from scope without provider should return new one`() {

      val scope = MainScope()

      scope.dispatcherProvider.shouldBeInstanceOf<DispatcherProvider>()
    }
  }

  @Nested
  inner class `CoroutineContext dispatcherProvider property` {

    @Test
    fun `call from context with existing provider should return that provider`() {

      val context = Job() + provider

      context.dispatcherProvider shouldBe provider
    }

    @Test
    fun `call from context without provider should return new one`() {

      val context: CoroutineContext = Job()

      context.dispatcherProvider.shouldBeInstanceOf<DispatcherProvider>()
    }
  }
}
