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

package dispatch.android.lifecycle

import dispatch.core.DefaultDispatcherProvider
import dispatch.core.DispatcherProvider
import dispatch.core.MainImmediateCoroutineScope
import dispatch.core.mainDispatcher
import dispatch.internal.test.android.FakeLifecycleOwner
import dispatch.internal.test.android.LiveDataTest
import dispatch.internal.test.shouldBeSupervisorJob
import dispatch.internal.test.shouldEqualFolded
import hermit.test.junit.HermitJUnit5
import io.kotest.matchers.shouldBe
import io.kotest.matchers.types.shouldBeInstanceOf
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineName
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.Job
import kotlinx.coroutines.ObsoleteCoroutinesApi
import kotlinx.coroutines.newSingleThreadContext
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import kotlin.coroutines.ContinuationInterceptor

@OptIn(DelicateCoroutinesApi::class)
@ObsoleteCoroutinesApi
@ExperimentalCoroutinesApi
internal class LifecycleScopeFactoryTest :
  HermitJUnit5(),
  LiveDataTest {

  val job = Job()
  val dispatcher = newSingleThreadContext("single thread dispatcher")
  val dispatcherProvider = DispatcherProvider()
  val exceptionHandler = CoroutineExceptionHandler { _, _ -> }
  val coroutineName = CoroutineName("name")

  val originContext = job + dispatcher + dispatcherProvider + exceptionHandler + coroutineName

  val mainDispatcher = newSingleThreadContext("main dispatcher")

  val lifecycleOwner by resets { FakeLifecycleOwner() }

  @BeforeAll
  fun beforeAll() {
    LifecycleScopeFactory.reset()
    Dispatchers.setMain(mainDispatcher)
  }

  @AfterEach
  fun afterEach() {
    LifecycleScopeFactory.reset()
  }

  @AfterAll
  fun afterAll() {
    Dispatchers.resetMain()
  }

  @Test
  fun `default factory should be a default MainImmediateContext`() = runTest {

    val scope = LifecycleScopeFactory.create(lifecycleOwner.lifecycle)

    scope.coroutineContext[DispatcherProvider] shouldBe DefaultDispatcherProvider.get()

    scope.coroutineContext[Job]!!.shouldBeSupervisorJob()

    scope.coroutineContext[ContinuationInterceptor] shouldBe Dispatchers.Main
  }

  @Test
  fun `a custom factory should be used after being set`() = runTest {

    LifecycleScopeFactory.set { originContext }

    val scope = LifecycleScopeFactory.create(lifecycleOwner.lifecycle)

    scope.coroutineContext shouldEqualFolded originContext + mainDispatcher
  }

  @Test
  fun `reset after setting a custom factory should return to the default`() = runTest {

    LifecycleScopeFactory.set { originContext }

    val custom = LifecycleScopeFactory.create(lifecycleOwner.lifecycle)

    custom.coroutineContext shouldEqualFolded originContext + mainDispatcher

    LifecycleScopeFactory.reset()

    val default = LifecycleScopeFactory.create(lifecycleOwner.lifecycle)

    default.coroutineContext[DispatcherProvider] shouldBe DefaultDispatcherProvider.get()

    default.coroutineContext[Job]!!.shouldBeSupervisorJob()

    default.coroutineContext[ContinuationInterceptor] shouldBe Dispatchers.Main

    default.shouldBeInstanceOf<MainImmediateCoroutineScope>()
  }
}
