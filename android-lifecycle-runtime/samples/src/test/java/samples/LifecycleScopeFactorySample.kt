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

@file:Suppress("EXPERIMENTAL_API_USAGE")

package samples

import androidx.lifecycle.*
import dispatch.android.espresso.*
import dispatch.android.lifecycle.*
import dispatch.android.lifecycle.lifecycleScope
import dispatch.core.*
import dispatch.core.test.*
import io.kotlintest.*
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import org.junit.jupiter.api.*

class LifecycleScopeFactorySample {

  @Sample
  fun setLifecycleScopeFactoryProductionSample() {

    class MyApplication : Application {

      override fun onCreate() {
        LifecycleScopeFactory.set { MainImmediateCoroutineScope() }
      }
    }
  }

  @Sample
  fun setLifecycleScopeFactoryEspressoSample() {

    class MyEspressoTest {

      @Before
      fun setUp() {
        LifecycleScopeFactory.set { MainImmediateIdlingCoroutineScope() }
      }
    }
  }

  private annotation class Before

}

@ExperimentalCoroutinesApi
class LifecycleCoroutineScopeSample : CoroutineTest {

  override lateinit var testScope: TestProvidedCoroutineScope

  @BeforeEach
  fun beforeEach() {
    LifecycleScopeFactory.set { testScope }
  }

  @Sample
  fun launchEveryCreateSample() = runBlocking {

    val output = mutableListOf<String>()

    class SomeViewModel {
      val someFlow = flow {
        repeat(500) {
          delay(10)
          emit(it)
        }
      }
    }

    class SomeFragment : Fragment() {

      val viewModel = SomeViewModel()

      init {
        println(3)
        lifecycleScope.launchEveryCreate {
          println(1)
          viewModel.someFlow.collect {
            println(2)
            output.add("$it")
          }
        }
      }
    }

    testScope.pauseDispatcher()

    val fragment = SomeFragment()

    fragment.lifecycle.addObserver(LifecycleEventObserver { _, event ->
      println("event --> $event")
    })

    fragment.create()

    delay(3500)

    output.add("destroying")
    fragment.destroy()

    delay(30)

    output.add("creating")
    fragment.create()

    delay(30)
    output.add("destroying")
    fragment.destroy()

    output shouldBe listOf(
      "1",
      "2",
      "3",
      "destroying",
      "creating",
      "5",
      "6",
      "7",
      "destroying"
    )

  }

  @Sample
  fun launchEveryStartSample() = runBlocking {

    val output = mutableListOf<String>()

    class SomeViewModel {
      val someFlow = flow {
        repeat(500) {
          //          delay(10)
          emit(it)
        }
      }
    }

    class SomeFragment : Fragment() {

      val viewModel = SomeViewModel()

      init {
        println(3)
        lifecycleScope.launchEveryStart {
          println(1)
          viewModel.someFlow.collect {
            println(2)
            output.add("$it")
          }
        }
      }
    }

    val fragment = SomeFragment()

    println("starting at --> ${fragment.lifecycle.currentState}")

    fragment.lifecycle.addObserver(LifecycleEventObserver { _, event ->
      println("event --> $event \t\t now at at --> ${fragment.lifecycle.currentState}")
    })

    fragment.start()

    println(fragment.lifecycle.observerCount)

    println("destroying")
    output.add("destroying")
    fragment.stop()

    delay(30)

    println("creating")
    output.add("creating")
    fragment.start()

    delay(30)
    println("destroying")
    output.add("destroying")
    fragment.stop()

    output shouldBe listOf(
      "1",
      "2",
      "3",
      "destroying",
      "creating",
      "5",
      "6",
      "7",
      "destroying"
    )

  }
}

abstract class Fragment : FakeLifecycleOwner(initialState = Lifecycle.State.STARTED)
