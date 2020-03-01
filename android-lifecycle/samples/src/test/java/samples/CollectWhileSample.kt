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
  fun collectWhileCreatedOnceSample() = runBlocking {

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

      override fun onStart() {
        super.onStart()
        viewModel.someFlow.onEach {
          channel.send("$it")
        }
          .launchOnCreate(lifecycleScope)
      }
    }
  }

//  @Test
//  fun collectWhileCreatedRestartingSample() = runBlocking {
//
//    val channel = Channel<String>()
//    val history = mutableListOf<String>()
//
//    class SomeViewModel {
//      val someFlow = flow {
//        repeat(100) {
//          emit(it)
//        }
//      }
//    }
//
//    class SomeFragment : Fragment() {
//
//      val viewModel = SomeViewModel()
//
//      init {
//
//        lifecycleScope.launchOnCreate {
//          viewModel.someFlow.collect {
//            channel.send("$it")
//          }
//        }
//      }
//    }
//
//    val fragment = SomeFragment()
//
//    history.add("creating")
//    fragment.create()
//
//    repeat(3) {
//      history.add(channel.receive())
//    }
//
//    // destroying the lifecycle cancels the lifecycleScope
//    history.add("destroying")
//    fragment.destroy()
//
//    history shouldBe listOf(
//      "creating",
//      "0",
//      "1",
//      "2",
//      "destroying"
//    )
//  }
//
//  @Test
//  fun collectWhileStartedOnceSample() = runBlocking {
//
//    val channel = Channel<String>()
//    val history = mutableListOf<String>()
//
//    class SomeViewModel {
//      val someFlow = flow {
//        repeat(100) {
//          emit(it)
//        }
//      }
//    }
//
//    class SomeFragment : Fragment() {
//
//      val viewModel = SomeViewModel()
//
//      init {
//        lifecycleScope.launchOnStart(minimumStatePolicy = CANCEL) {
//          viewModel.someFlow.collect {
//            channel.send("$it")
//          }
//        }
//      }
//    }
//
//    val fragment = SomeFragment()
//
//    history.add("starting")
//    fragment.start()
//
//    repeat(3) {
//      history.add(channel.receive())
//    }
//
//    // stopping the lifecycle cancels the existing Job
//    history.add("stopping")
//    fragment.stop()
//
//    // starting the lifecycle does not create a new Job
//
//    history shouldBe listOf(
//      "starting",
//      "0",
//      "1",
//      "2",
//      "stopping"
//    )
//  }
//
//  @Test
//  fun collectWhileStartedRestartingSample() = runBlocking {
//
//    val channel = Channel<String>()
//    val history = mutableListOf<String>()
//
//    class SomeViewModel {
//      val someFlow = flow {
//        repeat(100) {
//          emit(it)
//        }
//      }
//    }
//
//    class SomeFragment : Fragment() {
//
//      val viewModel = SomeViewModel()
//
//      init {
//        lifecycleScope.launchOnStart {
//          viewModel.someFlow.collect {
//            channel.send("$it")
//          }
//        }
//      }
//    }
//
//    val fragment = SomeFragment()
//
//    history.add("starting")
//    fragment.start()
//
//    repeat(3) {
//      history.add(channel.receive())
//    }
//
//    // stopping the lifecycle cancels the existing Job
//    history.add("stopping")
//    fragment.stop()
//
//    // starting the lifecycle creates a new Job
//    history.add("starting")
//    fragment.start()
//
//    repeat(3) {
//      history.add(channel.receive())
//    }
//
//    history.add("stopping")
//    fragment.stop()
//
//    history shouldBe listOf(
//      "starting",
//      "0",
//      "1",
//      "2",
//      "stopping",
//      "starting",
//      "0",
//      "1",
//      "2",
//      "stopping"
//    )
//  }
//
//  @Test
//  fun collectWhileResumedOnceSample() = runBlocking {
//
//    val channel = Channel<String>()
//    val history = mutableListOf<String>()
//
//    class SomeViewModel {
//      val someFlow = flow {
//        repeat(100) {
//          emit(it)
//        }
//      }
//    }
//
//    class SomeFragment : Fragment() {
//
//      val viewModel = SomeViewModel()
//
//      init {
//        lifecycleScope.launchOnResume(minimumStatePolicy = CANCEL) {
//          viewModel.someFlow.collect {
//            channel.send("$it")
//          }
//        }
//      }
//    }
//
//    val fragment = SomeFragment()
//
//    history.add("resuming")
//    fragment.resume()
//
//    repeat(3) {
//      history.add(channel.receive())
//    }
//
//    // pausing the lifecycle cancels the existing Job
//    history.add("pausing")
//    fragment.pause()
//
//    // resuming the lifecycle does not create a new Job
//
//    history shouldBe listOf(
//      "resuming",
//      "0",
//      "1",
//      "2",
//      "pausing"
//    )
//  }
//
//  @Test
//  fun collectWhileResumedRestartingSample() = runBlocking {
//
//    val channel = Channel<String>()
//    val history = mutableListOf<String>()
//
//    class SomeViewModel {
//      val someFlow = flow {
//        repeat(100) {
//          emit(it)
//        }
//      }
//    }
//
//    class SomeFragment : Fragment() {
//
//      val viewModel = SomeViewModel()
//
//      init {
//        lifecycleScope.launchOnResume {
//          viewModel.someFlow.collect {
//            channel.send("$it")
//          }
//        }
//      }
//    }
//
//    val fragment = SomeFragment()
//
//    history.add("resuming")
//    fragment.resume()
//
//    repeat(3) {
//      history.add(channel.receive())
//    }
//
//    // pausing the lifecycle cancels the existing Job
//    history.add("pausing")
//    fragment.pause()
//
//    // resuming the lifecycle creates a new Job
//    history.add("resuming")
//    fragment.resume()
//
//    repeat(3) {
//      history.add(channel.receive())
//    }
//
//    history.add("pausing")
//    fragment.pause()
//
//    history shouldBe listOf(
//      "resuming",
//      "0",
//      "1",
//      "2",
//      "pausing",
//      "resuming",
//      "0",
//      "1",
//      "2",
//      "pausing"
//    )
//  }

}

inline fun <reified VM : ViewModel> Fragment.viewModels(
  noinline ownerProducer: () -> ViewModelStoreOwner = { this },
  noinline factoryProducer: (() -> ViewModelProvider.Factory)? = null
) = createViewModelLazy(VM::class, { ownerProducer().viewModelStore }, factoryProducer)
