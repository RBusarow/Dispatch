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

import dispatch.internal.test.TrimAssertion
import dispatch.internal.test.shouldEqualFolded
import hermit.test.junit.HermitJUnit5
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext

@ExperimentalCoroutinesApi
internal class AsyncBuildersTest : HermitJUnit5() {

  val baseProvider by resets {
    object : DispatcherProvider {
      override val default: CoroutineDispatcher = TestCoroutineDispatcher()
      override val io: CoroutineDispatcher = TestCoroutineDispatcher()
      override val main: CoroutineDispatcher = TestCoroutineDispatcher()
      override val mainImmediate: CoroutineDispatcher = TestCoroutineDispatcher()
      override val unconfined: CoroutineDispatcher = TestCoroutineDispatcher()
    }
  }

  val secondProvider by resets {
    object : DispatcherProvider {
      override val default: CoroutineDispatcher = TestCoroutineDispatcher()
      override val io: CoroutineDispatcher = TestCoroutineDispatcher()
      override val main: CoroutineDispatcher = TestCoroutineDispatcher()
      override val mainImmediate: CoroutineDispatcher = TestCoroutineDispatcher()
      override val unconfined: CoroutineDispatcher = TestCoroutineDispatcher()
    }
  }

  @Nested
  inner class `async default` {

    @Test
    fun `empty context arg should use receiver context with provided default dispatcher`() =
      runBlocking(baseProvider) {

        val ctx: CoroutineContext = coroutineContext + baseProvider.default

        asyncDefault {
          coroutineContext shouldEqualFolded ctx
        }
          .join()
      }

    @Test
    fun `context dispatcher should not override extension function dispatcher`() =
      runBlocking(baseProvider) {

        val ctx: CoroutineContext = coroutineContext + baseProvider.default

        asyncDefault(baseProvider.io) {
          coroutineContext shouldEqualFolded ctx
        }
          .join()
      }

    @Test
    fun `context argument provider should override extension function provider`() =
      runBlocking(baseProvider) {

        val ctx: CoroutineContext = coroutineContext + secondProvider + secondProvider.default

        asyncDefault(secondProvider) {
          coroutineContext shouldEqualFolded ctx
        }
          .join()
      }
  }

  @Nested
  inner class `async io` {

    @Test
    fun `empty context arg should use receiver context with provided io dispatcher`() =
      runBlocking(baseProvider) {

        val ctx: CoroutineContext = coroutineContext + baseProvider.io

        asyncIO {
          coroutineContext shouldEqualFolded ctx
        }
          .join()
      }

    @Test
    fun `context dispatcher should not override extension function dispatcher`() =
      runBlocking(baseProvider) {

        val ctx: CoroutineContext = coroutineContext + baseProvider.io

        asyncIO(baseProvider.default) {
          coroutineContext shouldEqualFolded ctx
        }
          .join()
      }

    @Test
    fun `context argument provider should override extension function provider`() =
      runBlocking(baseProvider) {

        val ctx: CoroutineContext = coroutineContext + secondProvider + secondProvider.io

        asyncIO(secondProvider) {
          coroutineContext shouldEqualFolded ctx
        }
          .join()
      }
  }

  @Nested
  inner class `async main` {

    @Test
    fun `empty context arg should use receiver context with provided main dispatcher`() =
      runBlocking(baseProvider) {

        val ctx: CoroutineContext = coroutineContext + baseProvider.main

        asyncMain {
          coroutineContext shouldEqualFolded ctx
        }
          .join()
      }

    @Test
    fun `context dispatcher should not override extension function dispatcher`() =
      runBlocking(baseProvider) {

        val ctx: CoroutineContext = coroutineContext + baseProvider.main

        asyncMain(baseProvider.default) {
          coroutineContext shouldEqualFolded ctx
        }
          .join()
      }

    @Test
    fun `context argument provider should override extension function provider`() =
      runBlocking(baseProvider) {

        val ctx: CoroutineContext = coroutineContext + secondProvider + secondProvider.main

        asyncMain(secondProvider) {
          coroutineContext shouldEqualFolded ctx
        }
          .join()
      }
  }

  @Nested
  inner class `async main immediate` {

    @Test
    fun `empty context arg should use receiver context with provided mainImmediate dispatcher`() =
      runBlocking(baseProvider) {

        val ctx: CoroutineContext = coroutineContext + baseProvider.mainImmediate

        asyncMainImmediate {
          coroutineContext shouldEqualFolded ctx
        }
          .join()
      }

    @Test
    fun `context dispatcher should not override extension function dispatcher`() =
      runBlocking(baseProvider) {

        val ctx: CoroutineContext = coroutineContext + baseProvider.mainImmediate

        asyncMainImmediate(baseProvider.default) {
          coroutineContext shouldEqualFolded ctx
        }
          .join()
      }

    @Test
    fun `context argument provider should override extension function provider`() =
      runBlocking(baseProvider) {

        val ctx: CoroutineContext = coroutineContext + secondProvider + secondProvider.mainImmediate

        asyncMainImmediate(secondProvider) {
          coroutineContext shouldEqualFolded ctx
        }
          .join()
      }
  }

  @Nested
  inner class `async unconfined` {

    @Test
    fun `empty context arg should use receiver context with provided mainImmediate dispatcher`() =
      runBlocking(baseProvider) {

        val ctx: CoroutineContext = coroutineContext + baseProvider.unconfined

        asyncUnconfined {
          coroutineContext shouldEqualFolded ctx
        }
          .join()
      }

    @Test
    fun `context dispatcher should not override extension function dispatcher`() =
      runBlocking(baseProvider) {

        val ctx: CoroutineContext = coroutineContext + baseProvider.unconfined

        asyncUnconfined(baseProvider.default) {
          coroutineContext shouldEqualFolded ctx
        }
          .join()
      }

    @Test
    fun `context argument provider should override extension function provider`() =
      runBlocking(baseProvider) {

        val ctx: CoroutineContext = coroutineContext + secondProvider + secondProvider.unconfined

        asyncUnconfined(secondProvider) {
          coroutineContext shouldEqualFolded ctx
        }
          .join()
      }
  }

  @TrimAssertion
  fun runBlocking(
    context: CoroutineContext = EmptyCoroutineContext,
    testAction: suspend CoroutineScope.() -> Unit
  ) {
    kotlinx.coroutines.runBlocking(context = context, block = testAction)
  }
}
