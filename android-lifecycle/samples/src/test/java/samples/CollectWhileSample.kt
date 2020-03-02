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

package samples

import android.content.*
import androidx.core.app.*
import androidx.fragment.app.*
import androidx.lifecycle.*
import androidx.test.core.app.*
import dispatch.android.lifecycle.*
import dispatch.android.lifecycle.LifecycleCoroutineScope.MinimumStatePolicy.*
import dispatch.android.lifecycle.lifecycleScope
import dispatch.core.test.*
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import org.junit.*
import org.junit.runner.*
import org.robolectric.*

@ExperimentalCoroutinesApi
@RunWith(RobolectricTestRunner::class)
class CollectWhileSample {

  @get:Rule val testScope = TestCoroutineRule()

  lateinit var context: Context

  @Before
  fun setUp() {
    context = ApplicationProvider.getApplicationContext()
    LifecycleScopeFactory.set { testScope }
  }

  @Test
  fun launchOnCreateRestartingSample() = runBlocking {

    class SomeViewModel : ViewModel() {
      val someFlow = flow {
        repeat(100) {
          emit(it)
        }
      }
    }

    class SomeFragment : ComponentActivity() {

      val viewModel = SomeViewModel()

      init {

        // "observe" the viewModel's Flow from *every* ON_CREATE event until the following ON_DESTROY event
        viewModel.someFlow.onEach {
          print("collect $it")
        }
          // A coroutine will be created when the Lifecycle's state reaches CREATED,
          // and will be cancelled when the state reaches DESTROYED.
          // If this lifecycle were to somehow reach CREATED again
          // (which is technically possible in a custom LifecycleOwner implementation),
          // the coroutine would be recreated.
          .launchOnCreate(lifecycleScope)
      }
    }
  }

  @Test
  fun launchOnCreateCancellingSample() = runBlocking {

    class SomeViewModel : ViewModel() {
      val someFlow = flow {
        repeat(100) {
          emit(it)
        }
      }
    }

    class SomeFragment : ComponentActivity() {

      val viewModel = SomeViewModel()

      init {

        // "observe" the viewModel's Flow from the first ON_CREATE event until the first ON_DESTROY event
        viewModel.someFlow.onEach {
          print("collect $it")
        }
          // A coroutine will be created when the Lifecycle's state reaches CREATED,
          // and will be cancelled when the state reaches DESTROYED.
          // The coroutine will never be recreated.
          .launchOnCreate(lifecycleScope, minimumStatePolicy = CANCEL)
      }
    }
  }

  @Test
  fun launchOnStartRestartingSample() = runBlocking {

    class SomeViewModel : ViewModel() {
      val someFlow = flow {
        repeat(100) {
          emit(it)
        }
      }
    }

    class SomeFragment : Fragment() {

      val viewModel by viewModels<SomeViewModel>()

      // Note that because this is a Fragment, "observation" must start after onAttach
      // so that the viewModel can be accessed safely
      override fun onAttach(context: Context) {
        super.onAttach(context)

        // "observe" the viewModel's Flow from *every* ON_START event until the following ON_STOP event
        viewModel.someFlow.onEach {
          print("collect $it")
        }
          // A coroutine will be created when the Lifecycle's state reaches STARTED,
          // and will be cancelled when the state reaches CREATED.
          // If this lifecycle reaches STARTED again, the coroutine will be recreated.
          .launchOnStart(lifecycleScope)
      }
    }
  }

  @Test
  fun launchOnStartCancellingSample() = runBlocking {

    class SomeViewModel : ViewModel() {
      val someFlow = flow {
        repeat(100) {
          emit(it)
        }
      }
    }

    class SomeFragment : Fragment() {

      val viewModel by viewModels<SomeViewModel>()

      // Note that because this is a Fragment, "observation" must start after onAttach
      // so that the viewModel can be accessed safely
      override fun onAttach(context: Context) {
        super.onAttach(context)

        // "observe" the viewModel's Flow from the first ON_START event until the first ON_STOP event
        viewModel.someFlow.onEach {
          print("collect $it")
        }
          // A coroutine will be created when the Lifecycle's state reaches STARTED,
          // and will be cancelled when the state reaches CREATED.
          // If this lifecycle reaches STARTED again, the coroutine will NOT be recreated.
          .launchOnStart(lifecycleScope, minimumStatePolicy = CANCEL)
      }
    }
  }

  @Test
  fun launchOnResumeRestartingSample() = runBlocking {

    class SomeViewModel : ViewModel() {
      val someFlow = flow {
        repeat(100) {
          emit(it)
        }
      }
    }

    class SomeFragment : Fragment() {

      val viewModel by viewModels<SomeViewModel>()

      // Note that because this is a Fragment, "observation" must start after onAttach
      // so that the viewModel can be accessed safely
      override fun onAttach(context: Context) {
        super.onAttach(context)

        // "observe" the viewModel's Flow from *every* ON_RESUME event until the following ON_PAUSE event
        viewModel.someFlow.onEach {
          print("collect $it")
        }
          // A coroutine will be created when the Lifecycle's state reaches RESUMED,
          // and will be cancelled when the state reaches STARTED.
          // If this lifecycle reaches RESUMED again, the coroutine will be recreated.
          .launchOnResume(lifecycleScope)
      }
    }
  }

  @Test
  fun launchOnResumeCancellingSample() = runBlocking {

    class SomeViewModel : ViewModel() {
      val someFlow = flow {
        repeat(100) {
          emit(it)
        }
      }
    }

    class SomeFragment : Fragment() {

      val viewModel by viewModels<SomeViewModel>()

      // Note that because this is a Fragment, "observation" must start after onAttach
      // so that the viewModel can be accessed safely
      override fun onAttach(context: Context) {
        super.onAttach(context)

        // "observe" the viewModel's Flow from the first ON_RESUME event until the first ON_PAUSE event
        viewModel.someFlow.onEach {
          print("collect $it")
        }
          // A coroutine will be created when the Lifecycle's state reaches RESUMED,
          // and will be cancelled when the state reaches STARTED.
          // If this lifecycle reaches RESUMED again, the coroutine will NOT be recreated.
          .launchOnResume(lifecycleScope, minimumStatePolicy = CANCEL)
      }
    }
  }

}

inline fun <reified VM : ViewModel> Fragment.viewModels(
  noinline ownerProducer: () -> ViewModelStoreOwner = { this },
  noinline factoryProducer: (() -> ViewModelProvider.Factory)? = null
) = createViewModelLazy(VM::class, { ownerProducer().viewModelStore }, factoryProducer)
