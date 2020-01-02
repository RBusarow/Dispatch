/*
 * Copyright (C) 2019-2020 Rick Busarow
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
import kotlinx.coroutines.test.*
import org.amshove.kluent.*
import org.junit.jupiter.api.*
import kotlin.coroutines.*

@ExperimentalCoroutinesApi
internal class CoroutineScopesTest {

  val job = Job()

  val dispatcherProvider = DispatcherProvider()

  @Nested
  inner class `Default CoroutineScope` {

    @Test
    fun `no args should use SupervisorJob`() {

      val scope = DefaultCoroutineScope()

      scope.coroutineContext[Job]!!.shouldBeSupervisorJob()
    }

    @Test
    fun `job arg should be used in coroutineContext`() {

      val scope = DefaultCoroutineScope(job = job)

      scope.coroutineContext[Job] shouldBe job
    }

    @Test
    fun `no args should use DefaultDispatcherProvider`() {

      val scope = DefaultCoroutineScope()

      scope.coroutineContext[DispatcherProvider]!!.shouldBeTypeOf<DefaultDispatcherProvider>()
    }

    @Test
    fun `dispatcherProvider arg should be used in coroutineContext`() {

      val scope = DefaultCoroutineScope(dispatcherProvider = dispatcherProvider)

      scope.coroutineContext[DispatcherProvider] shouldBe dispatcherProvider
    }

    @Test
    fun `coroutineContext should use Dispatchers_Default`() {

      val scope = DefaultCoroutineScope()

      scope.coroutineContext[ContinuationInterceptor] shouldBe Dispatchers.Default
    }
  }

  @Nested
  inner class `IO CoroutineScope` {

    @Test
    fun `no args should use SupervisorJob`() {

      val scope = IOCoroutineScope()

      scope.coroutineContext[Job]!!.shouldBeSupervisorJob()
    }

    @Test
    fun `job arg should be used in coroutineContext`() {

      val scope = IOCoroutineScope(job = job)

      scope.coroutineContext[Job] shouldBe job
    }

    @Test
    fun `no args should use IODispatcherProvider`() {

      val scope = IOCoroutineScope()

      scope.coroutineContext[DispatcherProvider]!!.shouldBeTypeOf<DefaultDispatcherProvider>()
    }

    @Test
    fun `dispatcherProvider arg should be used in coroutineContext`() {

      val scope = IOCoroutineScope(dispatcherProvider = dispatcherProvider)

      scope.coroutineContext[DispatcherProvider] shouldBe dispatcherProvider
    }

    @Test
    fun `coroutineContext should use Dispatchers_IO`() {

      val scope = IOCoroutineScope()

      scope.coroutineContext[ContinuationInterceptor] shouldBe Dispatchers.IO
    }

    @Nested
    inner class `Main CoroutineScope` {

      val dispatcher = TestCoroutineDispatcher()

      @BeforeAll
      fun beforeAll() {
        Dispatchers.setMain(dispatcher)
      }

      @AfterAll
      fun afterAll() {
        Dispatchers.resetMain()
      }

      @Test
      fun `no args should use SupervisorJob`() {

        val scope = MainCoroutineScope()

        scope.coroutineContext[Job]!!.shouldBeSupervisorJob()
      }

      @Test
      fun `job arg should be used in coroutineContext`() {

        val scope = MainCoroutineScope(job = job)

        scope.coroutineContext[Job] shouldBe job
      }

      @Test
      fun `no args should use MainDispatcherProvider`() {

        val scope = MainCoroutineScope()

        scope.coroutineContext[DispatcherProvider]!!.shouldBeTypeOf<DefaultDispatcherProvider>()
      }

      @Test
      fun `dispatcherProvider arg should be used in coroutineContext`() {

        val scope = MainCoroutineScope(dispatcherProvider = dispatcherProvider)

        scope.coroutineContext[DispatcherProvider] shouldBe dispatcherProvider
      }

      @Test
      fun `coroutineContext should use Dispatchers_Main`() {

        val scope = MainCoroutineScope()

        scope.coroutineContext[ContinuationInterceptor] shouldBe Dispatchers.Main
      }
    }

  }

  @Nested
  inner class `Main immediate CoroutineScope` {

    val dispatcher = TestCoroutineDispatcher()

    @BeforeAll
    fun beforeAll() {
      Dispatchers.setMain(dispatcher)
    }

    @AfterAll
    fun afterAll() {
      Dispatchers.resetMain()
    }

    @Test
    fun `no args should use SupervisorJob`() {

      val scope = MainImmediateCoroutineScope()

      scope.coroutineContext[Job]!!.shouldBeSupervisorJob()
    }

    @Test
    fun `job arg should be used in coroutineContext`() {

      val scope = MainImmediateCoroutineScope(job = job)

      scope.coroutineContext[Job] shouldBe job
    }

    @Test
    fun `no args should use MainDispatcherProvider`() {

      val scope = MainImmediateCoroutineScope()

      scope.coroutineContext[DispatcherProvider]!!.shouldBeTypeOf<DefaultDispatcherProvider>()
    }

    @Test
    fun `dispatcherProvider arg should be used in coroutineContext`() {

      val scope =
        MainImmediateCoroutineScope(dispatcherProvider = dispatcherProvider)

      scope.coroutineContext[DispatcherProvider] shouldBe dispatcherProvider
    }

    @Test
    fun `coroutineContext should use Dispatchers_Main`() {

      val scope = MainImmediateCoroutineScope()

      // This is weak.  I'm not sure how to differentiate between Main and Main.immediate
      scope.coroutineContext[ContinuationInterceptor] shouldBe Dispatchers.Main.immediate
    }
  }

  @Nested
  inner class `Unconfined CoroutineScope` {

    @Test
    fun `no args should use SupervisorJob`() {

      val scope = UnconfinedCoroutineScope()

      scope.coroutineContext[Job]!!.shouldBeSupervisorJob()
    }

    @Test
    fun `job arg should be used in coroutineContext`() {

      val scope = UnconfinedCoroutineScope(job = job)

      scope.coroutineContext[Job] shouldBe job
    }

    @Test
    fun `no args should use UnconfinedDispatcherProvider`() {

      val scope = UnconfinedCoroutineScope()

      scope.coroutineContext[DispatcherProvider]!!.shouldBeTypeOf<DefaultDispatcherProvider>()
    }

    @Test
    fun `dispatcherProvider arg should be used in coroutineContext`() {

      val scope =
        UnconfinedCoroutineScope(dispatcherProvider = dispatcherProvider)

      scope.coroutineContext[DispatcherProvider] shouldBe dispatcherProvider
    }

    @Test
    fun `coroutineContext should use Dispatchers_Unconfined`() {

      val scope = UnconfinedCoroutineScope()

      scope.coroutineContext[ContinuationInterceptor] shouldBe Dispatchers.Unconfined
    }
  }

}
