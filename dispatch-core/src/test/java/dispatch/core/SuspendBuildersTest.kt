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

import dispatch.internal.test.shouldEqualFolded
import hermit.test.junit.HermitJUnit5
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.TestCoroutineDispatcher
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import kotlin.coroutines.CoroutineContext

@ExperimentalCoroutinesApi
internal class SuspendBuildersTest : HermitJUnit5() {

  val baseProvider by resets {
    DispatcherProvider(
      default = TestCoroutineDispatcher(),
      io = TestCoroutineDispatcher(),
      main = TestCoroutineDispatcher(),
      mainImmediate = TestCoroutineDispatcher(),
      unconfined = TestCoroutineDispatcher()
    )
  }

  val secondProvider by resets {
    DispatcherProvider(
      default = TestCoroutineDispatcher(),
      io = TestCoroutineDispatcher(),
      main = TestCoroutineDispatcher(),
      mainImmediate = TestCoroutineDispatcher(),
      unconfined = TestCoroutineDispatcher()
    )
  }

  @Nested
  inner class `with default` {

    @Test
    fun `empty context arg should use receiver context with provided default dispatcher`() =
      runBlockingProvided {

        val ctx: CoroutineContext = coroutineContext + baseProvider.default

        withDefault { coroutineContext shouldEqualFolded ctx }
      }

    @Test
    fun `context dispatcher should not override extension function dispatcher`() =
      runBlockingProvided {

        val ctx: CoroutineContext = coroutineContext + baseProvider.default

        withDefault(baseProvider.io) {
          coroutineContext shouldEqualFolded ctx
        }
      }

    @Test
    fun `context argument provider should override extension function provider`() =
      runBlocking(baseProvider) {

        val ctx: CoroutineContext = coroutineContext + secondProvider + secondProvider.default

        withDefault(secondProvider) {
          coroutineContext shouldEqualFolded ctx
        }
      }
  }

  @Nested
  inner class `with io` {

    @Test
    fun `empty context arg should use receiver context with provided io dispatcher`() =
      runBlockingProvided {

        val ctx: CoroutineContext = coroutineContext + baseProvider.io

        withIO { coroutineContext shouldEqualFolded ctx }
      }

    @Test
    fun `context dispatcher should not override extension function dispatcher`() =
      runBlockingProvided {

        val ctx: CoroutineContext = coroutineContext + baseProvider.io

        withIO(baseProvider.default) {
          coroutineContext shouldEqualFolded ctx
        }
      }

    @Test
    fun `context argument provider should override extension function provider`() =
      runBlocking(baseProvider) {

        val ctx: CoroutineContext = coroutineContext + secondProvider + secondProvider.io

        withIO(secondProvider) {
          coroutineContext shouldEqualFolded ctx
        }
      }
  }

  @Nested
  inner class `with main` {

    @Test
    fun `empty context arg should use receiver context with provided main dispatcher`() =
      runBlockingProvided {

        val ctx: CoroutineContext = coroutineContext + baseProvider.main

        withMain { coroutineContext shouldEqualFolded ctx }
      }

    @Test
    fun `context dispatcher should not override extension function dispatcher`() =
      runBlockingProvided {

        val ctx: CoroutineContext = coroutineContext + baseProvider.main

        withMain(baseProvider.default) {
          coroutineContext shouldEqualFolded ctx
        }
      }

    @Test
    fun `context argument provider should override extension function provider`() =
      runBlocking(baseProvider) {

        val ctx: CoroutineContext = coroutineContext + secondProvider + secondProvider.main

        withMain(secondProvider) {
          coroutineContext shouldEqualFolded ctx
        }
      }
  }

  @Nested
  inner class `with main immediate` {

    @Test
    fun `empty context arg should use receiver context with provided mainImmediate dispatcher`() =
      runBlockingProvided {

        val ctx: CoroutineContext = coroutineContext + baseProvider.mainImmediate

        withMainImmediate { coroutineContext shouldEqualFolded ctx }
      }

    @Test
    fun `context dispatcher should not override extension function dispatcher`() =
      runBlockingProvided {

        val ctx: CoroutineContext = coroutineContext + baseProvider.mainImmediate

        withMainImmediate(baseProvider.default) {
          coroutineContext shouldEqualFolded ctx
        }
      }

    @Test
    fun `context argument provider should override extension function provider`() =
      runBlocking(baseProvider) {

        val ctx: CoroutineContext = coroutineContext + secondProvider + secondProvider.mainImmediate

        withMainImmediate(secondProvider) {
          coroutineContext shouldEqualFolded ctx
        }
      }
  }

  @Nested
  inner class `with unconfined` {

    @Test
    fun `empty context arg should use receiver context with provided unconfined dispatcher`() =
      runBlockingProvided {

        val ctx: CoroutineContext = coroutineContext + baseProvider.unconfined

        withUnconfined { coroutineContext shouldEqualFolded ctx }
      }

    @Test
    fun `context dispatcher should not override extension function dispatcher`() =
      runBlockingProvided {

        val ctx: CoroutineContext = coroutineContext + baseProvider.unconfined

        withUnconfined(baseProvider.default) {
          coroutineContext shouldEqualFolded ctx
        }
      }

    @Test
    fun `context argument provider should override extension function provider`() =
      runBlocking(baseProvider) {

        val ctx: CoroutineContext = coroutineContext + secondProvider + secondProvider.unconfined

        withUnconfined(secondProvider) {
          coroutineContext shouldEqualFolded ctx
        }
      }
  }

  fun runBlockingProvided(
    block: suspend CoroutineScope.() -> Unit
  ) = runBlocking(baseProvider, block)
}
