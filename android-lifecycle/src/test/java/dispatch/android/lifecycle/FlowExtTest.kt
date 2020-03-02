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
import io.kotlintest.*
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.*
import kotlinx.coroutines.flow.*
import org.junit.jupiter.api.*

@FlowPreview
@ExperimentalCoroutinesApi
class FlowExtTest : CoroutineTest {

  override lateinit var testScope: TestProvidedCoroutineScope

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
    fun `collect should stop when lifecycle is destroyed`() = runBlocking {

      val input = Channel<Int>()
      val output = mutableListOf<Int>()
      var completed = false

      lifecycle.handleLifecycleEvent(Lifecycle.Event.ON_CREATE)

      input.consumeAsFlow()
        .onCompletion { completed = true }
        .onEach { output.add(it) }
        .launchOnCreate(scope)

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
    fun `collect should stop when lifecycle is stopped`() = runBlocking {

      val input = Channel<Int>()
      val output = mutableListOf<Int>()
      var completed = false

      lifecycle.handleLifecycleEvent(Lifecycle.Event.ON_START)

      input.consumeAsFlow()
        .onCompletion { completed = true }
        .onEach { output.add(it) }
        .launchOnStart(scope)

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
    fun `collect should stop when lifecycle is paused`() = runBlocking {

      val input = Channel<Int>()
      val output = mutableListOf<Int>()
      var completed = false

      lifecycle.handleLifecycleEvent(Lifecycle.Event.ON_RESUME)

      input.consumeAsFlow()
        .onCompletion { completed = true }
        .onEach { output.add(it) }
        .launchOnResume(scope)

      input.send(1)
      input.send(2)
      input.send(3)

      lifecycle.handleLifecycleEvent(Lifecycle.Event.ON_PAUSE)

      output shouldBe listOf(1, 2, 3)
      completed shouldBe true
    }

    @Test
    fun `collect should resume when lifecycle is resumed`() = runBlocking {

      val input = Channel<Int>()
      val output = mutableListOf<Int>()
      var completed = false

      lifecycle.handleLifecycleEvent(Lifecycle.Event.ON_RESUME)

      input.consumeAsFlow()
        .onCompletion { completed = true }
        .onEach { output.add(it) }
        .launchOnResume(scope)

      input.send(1)
      input.send(2)
      input.send(3)

      lifecycle.handleLifecycleEvent(Lifecycle.Event.ON_PAUSE)
      lifecycle.handleLifecycleEvent(Lifecycle.Event.ON_RESUME)

      input.send(4)
      input.send(5)
      input.send(6)

      lifecycle.handleLifecycleEvent(Lifecycle.Event.ON_PAUSE)

      output shouldBe listOf(1, 2, 3, 4, 5, 6)
      completed shouldBe true
    }
  }
}

