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
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.Job
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import kotlin.coroutines.CoroutineContext

@ExperimentalCoroutinesApi
internal class LaunchBuildersTest {

  val testProvider = DispatcherProvider(
    default = UnconfinedTestDispatcher(),
    io = UnconfinedTestDispatcher(),
    main = UnconfinedTestDispatcher(),
    mainImmediate = UnconfinedTestDispatcher(),
    unconfined = UnconfinedTestDispatcher()
  )

  @Nested
  inner class `launch default` {

    @Test
    fun `empty context arg should use receiver context with provided default dispatcher`() =
      runBlocking<Unit>(testProvider) {

        val ctx: CoroutineContext = coroutineContext + testProvider.default

        launchDefault { coroutineContext shouldEqualFolded ctx }
      }

    @Test
    fun `context should override specified provider dispatcher`() =
      runBlocking<Unit>(testProvider) {

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
      runBlocking<Unit>(testProvider) {

        val ctx: CoroutineContext = coroutineContext + testProvider.io

        launchIO { coroutineContext shouldEqualFolded ctx }
      }

    @Test
    fun `context should override specified provider dispatcher`() =
      runBlocking<Unit>(testProvider) {

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
      runBlocking<Unit>(testProvider) {

        val ctx: CoroutineContext = coroutineContext + testProvider.main

        launchMain { coroutineContext shouldEqualFolded ctx }
      }

    @Test
    fun `context should override specified provider dispatcher`() =
      runBlocking<Unit>(testProvider) {

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
      runBlocking<Unit>(testProvider) {

        val ctx: CoroutineContext = coroutineContext + testProvider.mainImmediate

        launchMainImmediate { coroutineContext shouldEqualFolded ctx }
      }

    @Test
    fun `context should override specified provider dispatcher`() =
      runBlocking<Unit>(testProvider) {

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
      runBlocking<Unit>(testProvider) {

        val ctx: CoroutineContext = coroutineContext + testProvider.unconfined

        launchUnconfined { coroutineContext shouldEqualFolded ctx }
      }

    @Test
    fun `context should override specified provider dispatcher`() =
      runBlocking<Unit>(testProvider) {

        val job = Job()
        val ctx: CoroutineContext = coroutineContext + testProvider.unconfined

        launchUnconfined(testProvider.io + job) {
          coroutineContext shouldEqualFolded ctx + testProvider.io + job
        }
      }
  }
}
