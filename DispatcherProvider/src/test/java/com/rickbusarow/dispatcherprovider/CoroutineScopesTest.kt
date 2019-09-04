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

package com.rickbusarow.dispatcherprovider

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.amshove.kluent.shouldBe
import org.amshove.kluent.shouldBeInstanceOf
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import kotlin.coroutines.ContinuationInterceptor

@ExperimentalCoroutinesApi
internal class CoroutineScopesTest {

  val job = Job()
  val supervisorJob = SupervisorJob()

  val dispatcherProvider = DispatcherProvider()

  @Nested
  inner class `Default CoroutineScope` {

    @Test
    fun `no args should use SupervisorJob`() {

      val scope: DefaultCoroutineScope = DefaultCoroutineScope()

      scope.coroutineContext[Job]!!.shouldBeSupervisorJob()
    }

    @Test
    fun `job arg should be used in coroutineContext`() {

      val scope: DefaultCoroutineScope = DefaultCoroutineScope(job = job)

      scope.coroutineContext[Job] shouldBe job
    }

    @Test
    fun `no args should use DefaultDispatcherProvider`() {

      val scope: DefaultCoroutineScope = DefaultCoroutineScope()

      scope.coroutineContext[DispatcherProvider]!!.shouldBeInstanceOf<DefaultDispatcherProvider>()
    }

    @Test
    fun `dispatcherProvider arg should be used in coroutineContext`() {

      val scope: DefaultCoroutineScope =
        DefaultCoroutineScope(dispatcherProvider = dispatcherProvider)

      scope.coroutineContext[DispatcherProvider] shouldBe dispatcherProvider
    }

    @Test
    fun `coroutineContext should use Dispatchers_Default`() {

      val scope: DefaultCoroutineScope = DefaultCoroutineScope()

      scope.coroutineContext[ContinuationInterceptor] shouldBe Dispatchers.Default
    }
  }

  @Nested
  inner class `IO CoroutineScope` {


    @Test
    fun `no args should use SupervisorJob`() {

      val scope: IOCoroutineScope = IOCoroutineScope()

      scope.coroutineContext[Job]!!.shouldBeSupervisorJob()
    }

    @Test
    fun `job arg should be used in coroutineContext`() {

      val scope: IOCoroutineScope = IOCoroutineScope(job = job)

      scope.coroutineContext[Job] shouldBe job
    }

    @Test
    fun `no args should use IODispatcherProvider`() {

      val scope: IOCoroutineScope = IOCoroutineScope()

      scope.coroutineContext[DispatcherProvider]!!.shouldBeInstanceOf<DefaultDispatcherProvider>()
    }

    @Test
    fun `dispatcherProvider arg should be used in coroutineContext`() {

      val scope: IOCoroutineScope = IOCoroutineScope(dispatcherProvider = dispatcherProvider)

      scope.coroutineContext[DispatcherProvider] shouldBe dispatcherProvider
    }

    @Test
    fun `coroutineContext should use Dispatchers_IO`() {

      val scope: IOCoroutineScope = IOCoroutineScope()

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

        val scope: MainCoroutineScope = MainCoroutineScope()

        scope.coroutineContext[Job]!!.shouldBeSupervisorJob()
      }

      @Test
      fun `job arg should be used in coroutineContext`() {

        val scope: MainCoroutineScope = MainCoroutineScope(job = job)

        scope.coroutineContext[Job] shouldBe job
      }

      @Test
      fun `no args should use MainDispatcherProvider`() {

        val scope: MainCoroutineScope = MainCoroutineScope()

        scope.coroutineContext[DispatcherProvider]!!.shouldBeInstanceOf<DefaultDispatcherProvider>()
      }

      @Test
      fun `dispatcherProvider arg should be used in coroutineContext`() {

        val scope: MainCoroutineScope = MainCoroutineScope(dispatcherProvider = dispatcherProvider)

        scope.coroutineContext[DispatcherProvider] shouldBe dispatcherProvider
      }

      @Test
      fun `coroutineContext should use Dispatchers_Main`() {

        val scope: MainCoroutineScope = MainCoroutineScope()

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

      val scope: MainImmediateCoroutineScope = MainImmediateCoroutineScope()

      scope.coroutineContext[Job]!!.shouldBeSupervisorJob()
    }

    @Test
    fun `job arg should be used in coroutineContext`() {

      val scope: MainImmediateCoroutineScope = MainImmediateCoroutineScope(job = job)

      scope.coroutineContext[Job] shouldBe job
    }

    @Test
    fun `no args should use MainDispatcherProvider`() {

      val scope: MainImmediateCoroutineScope = MainImmediateCoroutineScope()

      scope.coroutineContext[DispatcherProvider]!!.shouldBeInstanceOf<DefaultDispatcherProvider>()
    }

    @Test
    fun `dispatcherProvider arg should be used in coroutineContext`() {

      val scope: MainImmediateCoroutineScope =
        MainImmediateCoroutineScope(dispatcherProvider = dispatcherProvider)

      scope.coroutineContext[DispatcherProvider] shouldBe dispatcherProvider
    }

    @Test
    fun `coroutineContext should use Dispatchers_Main`() {

      val scope: MainImmediateCoroutineScope = MainImmediateCoroutineScope()

      // This is weak.  I'm not sure how to differentiate between Main and Main.immediate
      scope.coroutineContext[ContinuationInterceptor] shouldBe Dispatchers.Main.immediate
    }
  }

  @Nested
  inner class `Unconfined CoroutineScope` {


    @Test
    fun `no args should use SupervisorJob`() {

      val scope: UnconfinedCoroutineScope = UnconfinedCoroutineScope()

      scope.coroutineContext[Job]!!.shouldBeSupervisorJob()
    }

    @Test
    fun `job arg should be used in coroutineContext`() {

      val scope: UnconfinedCoroutineScope = UnconfinedCoroutineScope(job = job)

      scope.coroutineContext[Job] shouldBe job
    }

    @Test
    fun `no args should use UnconfinedDispatcherProvider`() {

      val scope: UnconfinedCoroutineScope = UnconfinedCoroutineScope()

      scope.coroutineContext[DispatcherProvider]!!.shouldBeInstanceOf<DefaultDispatcherProvider>()
    }

    @Test
    fun `dispatcherProvider arg should be used in coroutineContext`() {

      val scope: UnconfinedCoroutineScope =
        UnconfinedCoroutineScope(dispatcherProvider = dispatcherProvider)

      scope.coroutineContext[DispatcherProvider] shouldBe dispatcherProvider
    }

    @Test
    fun `coroutineContext should use Dispatchers_Unconfined`() {

      val scope: UnconfinedCoroutineScope = UnconfinedCoroutineScope()

      scope.coroutineContext[ContinuationInterceptor] shouldBe Dispatchers.Unconfined
    }
  }

}
