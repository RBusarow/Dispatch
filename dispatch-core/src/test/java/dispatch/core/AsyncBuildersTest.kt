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
internal class AsyncBuildersTest {

  val testProvider = DispatcherProvider(
    default = UnconfinedTestDispatcher(),
    io = UnconfinedTestDispatcher(),
    main = UnconfinedTestDispatcher(),
    mainImmediate = UnconfinedTestDispatcher(),
    unconfined = UnconfinedTestDispatcher()
  )

  @Nested
  inner class `async default` {

    @Test
    fun `empty context arg should use receiver context with provided default dispatcher`() =
      runBlocking(testProvider) {

        val ctx: CoroutineContext = coroutineContext + testProvider.default

        asyncDefault { coroutineContext shouldEqualFolded ctx }.join()
      }

    @Test
    fun `context should override specified provider dispatcher`() = runBlocking(testProvider) {

      val job = Job()
      val ctx: CoroutineContext = coroutineContext + testProvider.default

      asyncDefault(testProvider.io + job) {
        coroutineContext shouldEqualFolded ctx + testProvider.io + job
      }.join()
    }
  }

  @Nested
  inner class `async io` {

    @Test
    fun `empty context arg should use receiver context with provided io dispatcher`() =
      runBlocking(testProvider) {

        val ctx: CoroutineContext = coroutineContext + testProvider.io

        asyncIO { coroutineContext shouldEqualFolded ctx }.join()
      }

    @Test
    fun `context should override specified provider dispatcher`() = runBlocking(testProvider) {

      val job = Job()
      val ctx: CoroutineContext = coroutineContext + testProvider.io

      asyncIO(testProvider.default + job) {
        coroutineContext shouldEqualFolded ctx + testProvider.default + job
      }.join()
    }
  }

  @Nested
  inner class `async main` {

    @Test
    fun `empty context arg should use receiver context with provided main dispatcher`() =
      runBlocking(testProvider) {

        val ctx: CoroutineContext = coroutineContext + testProvider.main

        asyncMain { coroutineContext shouldEqualFolded ctx }.join()
      }

    @Test
    fun `context should override specified provider dispatcher`() = runBlocking(testProvider) {

      val job = Job()
      val ctx: CoroutineContext = coroutineContext + testProvider.main

      asyncMain(testProvider.io + job) {
        coroutineContext shouldEqualFolded ctx + testProvider.io + job
      }.join()
    }
  }

  @Nested
  inner class `async main immediate` {

    @Test
    fun `empty context arg should use receiver context with provided mainImmediate dispatcher`() =
      runBlocking(testProvider) {

        val ctx: CoroutineContext = coroutineContext + testProvider.mainImmediate

        asyncMainImmediate { coroutineContext shouldEqualFolded ctx }.join()
      }

    @Test
    fun `context should override specified provider dispatcher`() = runBlocking(testProvider) {

      val job = Job()
      val ctx: CoroutineContext = coroutineContext + testProvider.mainImmediate

      asyncMainImmediate(testProvider.io + job) {
        coroutineContext shouldEqualFolded ctx + testProvider.io + job
      }.join()
    }
  }

  @Nested
  inner class `async unconfined` {

    @Test
    fun `empty context arg should use receiver context with provided mainImmediate dispatcher`() =
      runBlocking(testProvider) {

        val ctx: CoroutineContext = coroutineContext + testProvider.unconfined

        asyncUnconfined { coroutineContext shouldEqualFolded ctx }.join()
      }

    @Test
    fun `context should override specified provider dispatcher`() = runBlocking(testProvider) {

      val job = Job()
      val ctx: CoroutineContext = coroutineContext + testProvider.unconfined

      asyncUnconfined(testProvider.io + job) {
        coroutineContext shouldEqualFolded ctx + testProvider.io + job
      }.join()
    }
  }
}
