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

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStore
import androidx.lifecycle.ViewModelStoreOwner
import dispatch.core.DefaultDispatcherProvider
import dispatch.core.DispatcherProvider
import dispatch.core.MainCoroutineScope
import dispatch.core.MainImmediateCoroutineScope
import dispatch.core.mainDispatcher
import dispatch.internal.test.shouldBeSupervisorJob
import dispatch.internal.test.shouldEqualFolded
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import io.kotest.matchers.types.shouldBeInstanceOf
import io.kotest.matchers.types.shouldBeSameInstanceAs
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineName
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.Job
import kotlinx.coroutines.ObsoleteCoroutinesApi
import kotlinx.coroutines.isActive
import kotlinx.coroutines.runBlocking
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
internal class DispatchViewModelTest {

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
  fun `accessing the property repeatedly should return the same scope`() = runBlocking {

    val viewModel = TestViewModel()

    val fresh = viewModel.viewModelScope

    val cached = viewModel.viewModelScope

    fresh shouldBeSameInstanceAs cached
  }

  @Test
  fun `different ViewModels should get different scopes`() = runBlocking {

    val fresh = TestViewModel().viewModelScope

    val alsoFresh = TestViewModel().viewModelScope

    fresh shouldNotBe alsoFresh
  }

  @Test
  fun `default factory should be a default MainImmediateCoroutineScope`() = runTest {

    val scope = TestViewModel().viewModelScope

    scope.coroutineContext[DispatcherProvider] shouldBe DefaultDispatcherProvider.get()

    scope.coroutineContext[Job]!!.shouldBeSupervisorJob()

    scope.coroutineContext[ContinuationInterceptor] shouldBe Dispatchers.Main.immediate

    scope.shouldBeInstanceOf<MainCoroutineScope>()
  }

  @Test
  fun `a custom factory should be used after being set`() = runTest {

    ViewModelScopeFactory.set { MainImmediateCoroutineScope(originContext) }

    val scope = TestViewModel().viewModelScope

    scope.coroutineContext shouldEqualFolded originContext + mainDispatcher
  }

  @Test
  fun `an initialized scope should be cancelled during onCleared`() = runTest {

    val store = ViewModelStore()

    val owner = ViewModelStoreOwner { store }

    val viewModel = ViewModelProvider(owner)[TestViewModel::class.java]

    val scope = viewModel.viewModelScope

    scope.isActive shouldBe true

    store.clear()

    scope.isActive shouldBe false
  }

  class TestViewModel : DispatchViewModel()
}
