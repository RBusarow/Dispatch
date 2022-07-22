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

package dispatch.test

import dispatch.internal.test.shouldEqualFolded
import io.kotest.matchers.shouldBe
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.Job
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestDispatcher
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import kotlin.coroutines.ContinuationInterceptor

@OptIn(ExperimentalCoroutinesApi::class)
internal val customScope = TestProvidedCoroutineScope()

@OptIn(ExperimentalCoroutinesApi::class)
class CustomFactory : CoroutineTestExtension.ScopeFactory() {
  override fun create(): TestProvidedCoroutineScope = customScope
}

@CoroutineTest(CustomFactory::class)
@ExperimentalCoroutinesApi
class CoroutineTestCustomFactoryTest(
  val testScope: TestProvidedCoroutineScope
) {

  @Test
  fun `a custom factory extension should use use the custom factory`() {

    testScope shouldBe customScope
  }

  @Test
  fun `testProvided with default context should use testScope`() {

    println("new scope -- ${TestProvidedCoroutineScope().coroutineContext[CoroutineExceptionHandler]}")
    println("new dispatcherProvider -- ${TestDispatcherProvider()[CoroutineExceptionHandler]}")

    println("standard -- ${StandardTestDispatcher()[CoroutineExceptionHandler]}")

    println("scope -- ${testScope.coroutineContext[CoroutineExceptionHandler]}")

    testScope.testProvided {

      // RBT adds a SupervisorJob when there is no Job, so we really only need to check the other properties
      coroutineContext shouldEqualFolded testScope.coroutineContext + coroutineContext[Job]!!
    }
  }

  @Test
  fun `runBlockingTest with context arg should use testScope + context arg`() {

    val job = Job()

    val dispatcher = testScope.coroutineContext[ContinuationInterceptor] as TestDispatcher
    val dispatcherProvider = testScope.dispatcherProvider

    TestProvidedCoroutineScope(
      dispatcher,
      dispatcherProvider,
      testScope.coroutineContext + job
    ).testProvided {

      // RBT adds a SupervisorJob when there is no Job, so we really only need to check the other properties
      coroutineContext shouldEqualFolded testScope.coroutineContext + job
    }
  }

  @Nested
  inner class `nested classes` {

    @Test
    fun `a custom factory extension should use use the custom factory`() {

      testScope shouldBe customScope
    }

    @Test
    fun `testProvided with default context should use testScope`() = testScope.testProvided {

      // RBT adds a SupervisorJob when there is no Job, so we really only need to check the other properties
      coroutineContext shouldEqualFolded testScope.coroutineContext + coroutineContext[Job]!!
    }

    @Test
    fun `runBlockingTest with context arg should use testScope + context arg`() {

      val job = Job()

      val dispatcher =
        testScope.coroutineContext[ContinuationInterceptor] as TestDispatcher
      val dispatcherProvider = testScope.dispatcherProvider

      TestProvidedCoroutineScope(
        dispatcher,
        dispatcherProvider,
        testScope.coroutineContext + job
      ).testProvided {

        // RBT adds a SupervisorJob when there is no Job, so we really only need to check the other properties
        coroutineContext shouldEqualFolded testScope.coroutineContext + job
      }
    }
  }
}
