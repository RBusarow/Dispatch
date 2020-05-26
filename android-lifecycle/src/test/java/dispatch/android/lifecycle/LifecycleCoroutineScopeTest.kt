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

package dispatch.android.lifecycle

import androidx.lifecycle.*
import dispatch.core.test.*
import io.kotest.matchers.*
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.*
import kotlinx.coroutines.flow.*
import org.junit.jupiter.api.*

@FlowPreview
@CoroutineTest
@ExperimentalCoroutinesApi
class LifecycleCoroutineScopeTest(
  val testScope: TestProvidedCoroutineScope
) {

  lateinit var lifecycleOwner: LifecycleOwner
  lateinit var lifecycle: LifecycleRegistry

  lateinit var scope: LifecycleCoroutineScope

  @BeforeEach
  fun beforeEach() {

    lifecycleOwner = LifecycleOwner { lifecycle }
    lifecycle = LifecycleRegistry(lifecycleOwner)

    scope = LifecycleCoroutineScope(lifecycle, testScope)
  }

  @Nested
  inner class `launch every create` {

    @Test
    fun `block should immediately execute if already created`() = runBlocking {

      lifecycle.handleLifecycleEvent(Lifecycle.Event.ON_CREATE)

      var executed = false

      scope.launchOnCreate { executed = true }

      executed shouldBe true
    }

    @Test
    fun `block should not immediately execute if screen is not created`() = runBlocking {

      lifecycle.handleLifecycleEvent(Lifecycle.Event.ON_DESTROY)

      var executed = false

      scope.launchOnCreate { executed = true }

      executed shouldBe false
    }

    @Test
    fun `block should stop when screen is destroyed`() = runBlocking {

      val input = Channel<Int>()
      val output = mutableListOf<Int>()
      var completed = false

      lifecycle.handleLifecycleEvent(Lifecycle.Event.ON_CREATE)

      scope.launchOnCreate {
        input.consumeAsFlow()
          .onCompletion { completed = true }
          .collect { output.add(it) }
      }

      input.send(1)
      input.send(2)
      input.send(3)

      lifecycle.handleLifecycleEvent(Lifecycle.Event.ON_DESTROY)

      output shouldBe listOf(1, 2, 3)
      completed shouldBe true
    }
  }

  @Nested
  inner class `launch every start` {

    @Test
    fun `block should immediately execute if already started`() = runBlocking {

      lifecycle.handleLifecycleEvent(Lifecycle.Event.ON_START)

      var executed = false

      scope.launchOnStart { executed = true }

      executed shouldBe true
    }

    @Test
    fun `block should not immediately execute if screen is not started`() = runBlocking {

      Lifecycle.Event.values()
        .filter {
          when (it) {
            Lifecycle.Event.ON_CREATE  -> true
            Lifecycle.Event.ON_STOP    -> true
            Lifecycle.Event.ON_DESTROY -> true
            else                       -> false
          }
        }
        .forEach { event ->

          lifecycle.handleLifecycleEvent(event)

          var executed = false

          scope.launchOnStart { executed = true }

          executed shouldBe false
        }
    }

    @Test
    fun `block should stop when screen is stopped`() = runBlocking {

      val input = Channel<Int>()
      val output = mutableListOf<Int>()
      var completed = false

      lifecycle.handleLifecycleEvent(Lifecycle.Event.ON_START)

      scope.launchOnStart {
        input.consumeAsFlow()
          .onCompletion { completed = true }
          .collect { output.add(it) }
      }

      input.send(1)
      input.send(2)
      input.send(3)

      lifecycle.handleLifecycleEvent(Lifecycle.Event.ON_STOP)

      output shouldBe listOf(1, 2, 3)
      completed shouldBe true
    }
  }

  @Nested
  inner class `launch every resume` {

    @Test
    fun `block should immediately execute if already resumed`() = runBlocking {

      lifecycle.handleLifecycleEvent(Lifecycle.Event.ON_RESUME)

      var executed = false

      scope.launchOnResume { executed = true }

      executed shouldBe true
    }

    @Test
    fun `block should not immediately execute if screen is not resumed`() = runBlocking {

      Lifecycle.Event.values()
        .filter {
          when (it) {
            Lifecycle.Event.ON_CREATE  -> true
            Lifecycle.Event.ON_START   -> true
            Lifecycle.Event.ON_PAUSE   -> true
            Lifecycle.Event.ON_STOP    -> true
            Lifecycle.Event.ON_DESTROY -> true
            else                       -> false
          }
        }
        .forEach { event ->

          lifecycle.handleLifecycleEvent(event)

          var executed = false

          scope.launchOnResume { executed = true }

          executed shouldBe false
        }
    }

    @Test
    fun `block should stop when screen is paused`() = runBlocking {

      val input = Channel<Int>()
      val output = mutableListOf<Int>()
      var completed = false

      lifecycle.handleLifecycleEvent(Lifecycle.Event.ON_RESUME)

      scope.launchOnResume {
        input.consumeAsFlow()
          .onCompletion { completed = true }
          .collect { output.add(it) }
      }

      input.send(1)
      input.send(2)
      input.send(3)

      lifecycle.handleLifecycleEvent(Lifecycle.Event.ON_PAUSE)

      output shouldBe listOf(1, 2, 3)
      completed shouldBe true
    }
  }
}
