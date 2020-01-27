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

package com.rickbusarow.dispatcherprovider

import kotlinx.coroutines.*
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.runBlockingTest
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import kotlin.coroutines.CoroutineContext

@ExperimentalCoroutinesApi
internal class BuildersTest {

  val testProvider = object : DispatcherProvider {
    override val default: CoroutineDispatcher = TestCoroutineDispatcher()
    override val io: CoroutineDispatcher = TestCoroutineDispatcher()
    override val main: CoroutineDispatcher = TestCoroutineDispatcher()
    override val mainImmediate: CoroutineDispatcher = TestCoroutineDispatcher()
    override val unconfined: CoroutineDispatcher = TestCoroutineDispatcher()
  }

  @Nested
  inner class `suspend builders` {

    @Nested
    inner class `with default` {

      @Test
      fun `empty context arg should use receiver context with provided default dispatcher`() =
        runBlockingProvided {

          val ctx: CoroutineContext = coroutineContext + testProvider.default

          withDefault { coroutineContext shouldEqualFolded ctx }
        }

      @Test
      fun `context should override specified provider dispatcher`() = runBlockingProvided {

        val job = Job()
        val ctx: CoroutineContext = coroutineContext + testProvider.default

        withDefault(testProvider.io + job) {
          coroutineContext shouldEqualFolded ctx + testProvider.io + job
        }
      }
    }

    @Nested
    inner class `with io` {

      @Test
      fun `empty context arg should use receiver context with provided io dispatcher`() =
        runBlockingProvided {

          val ctx: CoroutineContext = coroutineContext + testProvider.io

          withIO { coroutineContext shouldEqualFolded ctx }
        }

      @Test
      fun `context should override specified provider dispatcher`() = runBlockingProvided {

        val job = Job()
        val ctx: CoroutineContext = coroutineContext + testProvider.io

        withIO(testProvider.default + job) {
          coroutineContext shouldEqualFolded ctx + testProvider.default + job
        }
      }
    }

    @Nested
    inner class `with main` {

      @Test
      fun `empty context arg should use receiver context with provided main dispatcher`() =
        runBlockingProvided {

          val ctx: CoroutineContext = coroutineContext + testProvider.main

          withMain { coroutineContext shouldEqualFolded ctx }
        }

      @Test
      fun `context should override specified provider dispatcher`() = runBlockingProvided {

        val job = Job()
        val ctx: CoroutineContext = coroutineContext + testProvider.main

        withMain(testProvider.default + job) { coroutineContext shouldEqualFolded ctx + testProvider.default + job }
      }

    }

    @Nested
    inner class `with main immediate` {

      @Test
      fun `empty context arg should use receiver context with provided mainImmediate dispatcher`() =
        runBlockingProvided {

          val ctx: CoroutineContext = coroutineContext + testProvider.mainImmediate

          withMainImmediate { coroutineContext shouldEqualFolded ctx }
        }

      @Test
      fun `context should override specified provider dispatcher`() = runBlockingProvided {

        val job = Job()
        val ctx: CoroutineContext = coroutineContext + testProvider.mainImmediate

        withMainImmediate(testProvider.default + job) { coroutineContext shouldEqualFolded ctx + testProvider.default + job }
      }
    }

    @Nested
    inner class `with unconfined` {

      @Test
      fun `empty context arg should use receiver context with provided unconfined dispatcher`() =
        runBlockingProvided {

          val ctx: CoroutineContext = coroutineContext + testProvider.unconfined

          withUnconfined { coroutineContext shouldEqualFolded ctx }
        }

      @Test
      fun `context should override specified provider dispatcher`() = runBlockingProvided {

        val job = Job()
        val ctx: CoroutineContext = coroutineContext + testProvider.unconfined

        withUnconfined(testProvider.default + job) {
          coroutineContext shouldEqualFolded ctx + testProvider.default + job
        }
      }
    }
  }

  @Nested
  inner class `launch builders` {

    @Nested
    inner class `launch default` {

      @Test
      fun `empty context arg should use receiver context with provided default dispatcher`() =
        runBlockingJoined {

          val ctx: CoroutineContext = coroutineContext + testProvider.default

          launchDefault { coroutineContext shouldEqualFolded ctx }
        }

      @Test
      fun `context should override specified provider dispatcher`() = runBlockingJoined {

        val job = Job()
        val ctx: CoroutineContext = coroutineContext + testProvider.default

        launchDefault(testProvider.io + job) {
          coroutineContext shouldEqualFolded ctx + testProvider.io + job
        }
      }
    }

    @Nested
    inner class `launch io` {

      @Test
      fun `empty context arg should use receiver context with provided io dispatcher`() =
        runBlockingJoined {

          val ctx: CoroutineContext = coroutineContext + testProvider.io

          launchIO { coroutineContext shouldEqualFolded ctx }
        }

      @Test
      fun `context should override specified provider dispatcher`() = runBlockingJoined {

        val job = Job()
        val ctx: CoroutineContext = coroutineContext + testProvider.io

        launchIO(testProvider.default + job) {
          coroutineContext shouldEqualFolded ctx + testProvider.default + job
        }
      }
    }

    @Nested
    inner class `launch main` {

      @Test
      fun `empty context arg should use receiver context with provided main dispatcher`() =
        runBlockingJoined {

          val ctx: CoroutineContext = coroutineContext + testProvider.main

          launchMain { coroutineContext shouldEqualFolded ctx }
        }

      @Test
      fun `context should override specified provider dispatcher`() = runBlockingJoined {

        val job = Job()
        val ctx: CoroutineContext = coroutineContext + testProvider.main

        launchMain(testProvider.io + job) {
          coroutineContext shouldEqualFolded ctx + testProvider.io + job
        }
      }
    }

    @Nested
    inner class `launch main immediate` {

      @Test
      fun `empty context arg should use receiver context with provided mainImmediate dispatcher`() =
        runBlockingJoined {

          val ctx: CoroutineContext = coroutineContext + testProvider.mainImmediate

          launchMainImmediate { coroutineContext shouldEqualFolded ctx }
        }

      @Test
      fun `context should override specified provider dispatcher`() = runBlockingJoined {

        val job = Job()
        val ctx: CoroutineContext = coroutineContext + testProvider.mainImmediate

        launchMainImmediate(testProvider.io + job) {
          coroutineContext shouldEqualFolded ctx + testProvider.io + job
        }
      }
    }

    @Nested
    inner class `launch unconfined` {

      @Test
      fun `empty context arg should use receiver context with provided mainImmediate dispatcher`() =
        runBlockingJoined {

          val ctx: CoroutineContext = coroutineContext + testProvider.unconfined

          launchUnconfined { coroutineContext shouldEqualFolded ctx }
        }

      @Test
      fun `context should override specified provider dispatcher`() = runBlockingJoined {

        val job = Job()
        val ctx: CoroutineContext = coroutineContext + testProvider.unconfined

        launchUnconfined(testProvider.io + job) {
          coroutineContext shouldEqualFolded ctx + testProvider.io + job
        }
      }
    }

  }

  @Nested
  inner class `async builders` {

    @Nested
    inner class `async default` {

      @Test
      fun `empty context arg should use receiver context with provided default dispatcher`() =
        runBlockingJoined {

          val ctx: CoroutineContext = coroutineContext + testProvider.default

          asyncDefault { coroutineContext shouldEqualFolded ctx }
        }

      @Test
      fun `context should override specified provider dispatcher`() = runBlockingJoined {

        val job = Job()
        val ctx: CoroutineContext = coroutineContext + testProvider.default

        asyncDefault(testProvider.io + job) {
          coroutineContext shouldEqualFolded ctx + testProvider.io + job
        }
      }
    }

    @Nested
    inner class `async io` {

      @Test
      fun `empty context arg should use receiver context with provided io dispatcher`() =
        runBlockingJoined {

          val ctx: CoroutineContext = coroutineContext + testProvider.io

          asyncIO { coroutineContext shouldEqualFolded ctx }
        }

      @Test
      fun `context should override specified provider dispatcher`() = runBlockingJoined {

        val job = Job()
        val ctx: CoroutineContext = coroutineContext + testProvider.io

        asyncIO(testProvider.default + job) {
          coroutineContext shouldEqualFolded ctx + testProvider.default + job
        }
      }
    }

    @Nested
    inner class `async main` {

      @Test
      fun `empty context arg should use receiver context with provided main dispatcher`() =
        runBlockingJoined {

          val ctx: CoroutineContext = coroutineContext + testProvider.main

          asyncMain { coroutineContext shouldEqualFolded ctx }
        }

      @Test
      fun `context should override specified provider dispatcher`() = runBlockingJoined {

        val job = Job()
        val ctx: CoroutineContext = coroutineContext + testProvider.main

        asyncMain(testProvider.io + job) {
          coroutineContext shouldEqualFolded ctx + testProvider.io + job
        }
      }
    }

    @Nested
    inner class `async main immediate` {

      @Test
      fun `empty context arg should use receiver context with provided mainImmediate dispatcher`() =
        runBlockingJoined {

          val ctx: CoroutineContext = coroutineContext + testProvider.mainImmediate

          asyncMainImmediate { coroutineContext shouldEqualFolded ctx }
        }

      @Test
      fun `context should override specified provider dispatcher`() = runBlockingJoined {

        val job = Job()
        val ctx: CoroutineContext = coroutineContext + testProvider.mainImmediate

        asyncMainImmediate(testProvider.io + job) {
          coroutineContext shouldEqualFolded ctx + testProvider.io + job
        }
      }
    }

    @Nested
    inner class `async unconfined` {

      @Test
      fun `empty context arg should use receiver context with provided mainImmediate dispatcher`() =
        runBlockingJoined {

          val ctx: CoroutineContext = coroutineContext + testProvider.unconfined

          asyncUnconfined { coroutineContext shouldEqualFolded ctx }
        }

      @Test
      fun `context should override specified provider dispatcher`() = runBlockingJoined {

        val job = Job()
        val ctx: CoroutineContext = coroutineContext + testProvider.unconfined

        asyncUnconfined(testProvider.io + job) {
          coroutineContext shouldEqualFolded ctx + testProvider.io + job
        }
      }
    }

  }

  fun runBlockingProvided(block: suspend CoroutineScope.() -> Unit) =
    runBlocking(testProvider, block)

  fun runBlockingJoined(block: suspend CoroutineScope.() -> Unit) =
    runBlocking(testProvider) { coroutineScope(block) }

  fun foo() = runBlockingTest { }

}

