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

package dispatch.android.viewmodel

import dispatch.core.DefaultDispatcherProvider
import dispatch.core.DispatcherProvider
import dispatch.core.MainCoroutineScope
import dispatch.core.mainDispatcher
import dispatch.internal.test.shouldBeSupervisorJob
import dispatch.internal.test.shouldEqualFolded
import io.kotest.matchers.shouldBe
import io.kotest.matchers.types.shouldBeInstanceOf
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineName
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.Job
import kotlinx.coroutines.ObsoleteCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import kotlin.coroutines.ContinuationInterceptor

@ObsoleteCoroutinesApi
@ExperimentalCoroutinesApi
internal class ViewModelScopeFactoryTest {

  val job = Job()
  val dispatcher = StandardTestDispatcher(name = "single thread dispatcher")
  val dispatcherProvider = DispatcherProvider()
  val exceptionHandler = CoroutineExceptionHandler { _, _ -> }
  val coroutineName = CoroutineName("name")

  val originContext = job + dispatcher + dispatcherProvider + exceptionHandler + coroutineName

  val mainDispatcher = StandardTestDispatcher(name = "main dispatcher")

  @BeforeAll
  fun beforeAll() {
    Dispatchers.setMain(mainDispatcher)
  }

  @AfterEach
  fun afterEach() {
    ViewModelScopeFactory.reset()
  }

  @AfterAll
  fun afterAll() {
    Dispatchers.resetMain()
  }

  @Test
  fun `default factory should be a default MainCoroutineScope`() = runTest {

    val scope = ViewModelScopeFactory.create()

    scope.coroutineContext[DispatcherProvider] shouldBe DefaultDispatcherProvider.get()

    scope.coroutineContext[Job]!!.shouldBeSupervisorJob()

    scope.coroutineContext[ContinuationInterceptor] shouldBe Dispatchers.Main

    scope.shouldBeInstanceOf<MainCoroutineScope>()
  }

  @Test
  fun `a custom factory should be used after being set`() = runTest {

    ViewModelScopeFactory.set { MainCoroutineScope(originContext) }

    val scope = ViewModelScopeFactory.create()

    scope.coroutineContext shouldEqualFolded originContext + mainDispatcher
  }

  @Test
  fun `reset after setting a custom factory should return to the default`() = runTest {

    ViewModelScopeFactory.set { MainCoroutineScope(originContext) }

    val custom = ViewModelScopeFactory.create()

    custom.coroutineContext shouldEqualFolded originContext + mainDispatcher

    ViewModelScopeFactory.reset()

    val default = ViewModelScopeFactory.create()

    default.coroutineContext[DispatcherProvider] shouldBe DefaultDispatcherProvider.get()

    default.coroutineContext[Job]!!.shouldBeSupervisorJob()

    default.coroutineContext[ContinuationInterceptor] shouldBe Dispatchers.Main

    default.shouldBeInstanceOf<MainCoroutineScope>()
  }
}
