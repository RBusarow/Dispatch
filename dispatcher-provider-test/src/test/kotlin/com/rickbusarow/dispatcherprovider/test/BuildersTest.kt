/*
 * Copyright (C) 2019 Rick Busarow
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

package com.rickbusarow.dispatcherprovider.test

import com.rickbusarow.dispatcherprovider.DispatcherProvider
import com.rickbusarow.dispatcherprovider.dispatcherProvider
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.TestCoroutineScope
import org.amshove.kluent.shouldBe
import org.amshove.kluent.shouldBeInstanceOf
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
  inner class `run blocking provided` {

    @Test
    fun `default params should create scope with TestDispatcherProvider`() = runBlockingProvided {

      val dispatcherProvider = coroutineContext.dispatcherProvider

      dispatcherProvider.shouldBeInstanceOf<TestDispatcherProvider>()
    }

    @Test
    fun `specified provider param should be used in CoroutineContext`() =
      runBlockingProvided(testProvider) {

        val dispatcherProvider = coroutineContext.dispatcherProvider

        dispatcherProvider shouldBe testProvider
      }

    @Test
    fun `specified context param should be used in CoroutineContext`() {

      val ctx = TestCoroutineContext()

      runBlockingProvided(ctx) {

        coroutineContext[TestCoroutineContext] shouldBe ctx
      }
    }

  }

  @Nested
  inner class `run blocking test provided` {

    @Test
    fun `default params should create scope with TestDispatcherProvider`() =
      runBlockingTestProvided {

        val dispatcherProvider = coroutineContext.dispatcherProvider

        dispatcherProvider.shouldBeInstanceOf<TestDispatcherProvider>()
      }

    @Test
    fun `specified provider param should be used in CoroutineContext`() =
      runBlockingTestProvided(testProvider) {

        val dispatcherProvider = coroutineContext.dispatcherProvider

        dispatcherProvider shouldBe testProvider
      }

    @Test
    fun `specified context param should be used in CoroutineContext`() {

      val ctx = TestCoroutineContext()

      runBlockingTestProvided(ctx) {

        coroutineContext[TestCoroutineContext] shouldBe ctx
      }
    }

    @Test
    fun `CoroutineScope receiver should be TestCoroutineScope`() = runBlockingTestProvided {

      this.shouldBeInstanceOf<TestCoroutineScope>()
    }
  }

  class TestCoroutineContext : CoroutineContext.Element {
    override val key: CoroutineContext.Key<*> get() = Key

    companion object Key : CoroutineContext.Key<TestCoroutineContext>
  }

}
