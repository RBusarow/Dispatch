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
import androidx.fragment.app.*
import androidx.lifecycle.*
import androidx.test.core.app.*
import dispatch.android.lifecycle.*
import dispatch.android.lifecycle.LifecycleCoroutineScope.MinimumStatePolicy.*
import dispatch.android.lifecycle.lifecycleScope
import dispatch.core.test.*
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.*
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

    val channel = Channel<String>()
    val history = mutableListOf<String>()

    class SomeViewModel : ViewModel() {
      val someFlow = flow {
        repeat(100) {
          emit(it)
        }
      }
    }

    class SomeFragment : Fragment() {

      val viewModel by viewModels<SomeViewModel>()

      override fun onAttach(context: Context) {
        super.onAttach(context)

        viewModel.someFlow.onEach {
          channel.send("$it")
        }
          .launchOnCreate(lifecycleScope)
      }
    }
  }

  @Test
  fun launchOnCreateCancellingSample() = runBlocking {

    val channel = Channel<String>()
    val history = mutableListOf<String>()

    class SomeViewModel : ViewModel() {
      val someFlow = flow {
        repeat(100) {
          emit(it)
        }
      }
    }

    class SomeFragment : Fragment() {

      val viewModel by viewModels<SomeViewModel>()

      override fun onAttach(context: Context) {
        super.onAttach(context)

        viewModel.someFlow.onEach {
          channel.send("$it")
        }
          .launchOnCreate(lifecycleScope, minimumStatePolicy = CANCEL)
      }
    }
  }

  @Test
  fun launchOnStartRestartingSample() = runBlocking {

    val channel = Channel<String>()
    val history = mutableListOf<String>()

    class SomeViewModel : ViewModel() {
      val someFlow = flow {
        repeat(100) {
          emit(it)
        }
      }
    }

    class SomeFragment : Fragment() {

      val viewModel by viewModels<SomeViewModel>()

      override fun onAttach(context: Context) {
        super.onAttach(context)

        viewModel.someFlow.onEach {
          channel.send("$it")
        }
          .launchOnStart(lifecycleScope)
      }
    }
  }

  @Test
  fun launchOnStartCancellingSample() = runBlocking {

    val channel = Channel<String>()
    val history = mutableListOf<String>()

    class SomeViewModel : ViewModel() {
      val someFlow = flow {
        repeat(100) {
          emit(it)
        }
      }
    }

    class SomeFragment : Fragment() {

      val viewModel by viewModels<SomeViewModel>()

      override fun onAttach(context: Context) {
        super.onAttach(context)

        viewModel.someFlow.onEach {
          channel.send("$it")
        }
          .launchOnStart(lifecycleScope, minimumStatePolicy = CANCEL)
      }
    }
  }

  @Test
  fun launchOnResumeRestartingSample() = runBlocking {

    val channel = Channel<String>()
    val history = mutableListOf<String>()

    class SomeViewModel : ViewModel() {
      val someFlow = flow {
        repeat(100) {
          emit(it)
        }
      }
    }

    class SomeFragment : Fragment() {

      val viewModel by viewModels<SomeViewModel>()

      override fun onAttach(context: Context) {
        super.onAttach(context)

        viewModel.someFlow.onEach {
          channel.send("$it")
        }
          .launchOnResume(lifecycleScope)
      }
    }
  }

  @Test
  fun launchOnResumeCancellingSample() = runBlocking {

    val channel = Channel<String>()

    class SomeViewModel : ViewModel() {
      val someFlow = flow {
        repeat(100) {
          emit(it)
        }
      }
    }

    class SomeFragment : Fragment() {

      val viewModel by viewModels<SomeViewModel>()

      override fun onAttach(context: Context) {
        super.onAttach(context)

        viewModel.someFlow.onEach {
          channel.send("$it")
        }
          .launchOnResume(lifecycleScope, minimumStatePolicy = CANCEL)
      }
    }
  }

}

inline fun <reified VM : ViewModel> Fragment.viewModels(
  noinline ownerProducer: () -> ViewModelStoreOwner = { this },
  noinline factoryProducer: (() -> ViewModelProvider.Factory)? = null
) = createViewModelLazy(VM::class, { ownerProducer().viewModelStore }, factoryProducer)
