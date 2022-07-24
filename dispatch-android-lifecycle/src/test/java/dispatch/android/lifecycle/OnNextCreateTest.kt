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

import dispatch.core.ioDispatcher
import dispatch.internal.test.BaseTest
import dispatch.internal.test.android.FakeLifecycleOwner
import dispatch.internal.test.android.LiveDataTest
import dispatch.test.testProvided
import dispatch.test.testProvidedUnconfined
import io.kotest.matchers.shouldBe
import kotlinx.coroutines.CoroutineStart.LAZY
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.async
import kotlinx.coroutines.cancelAndJoin
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.launch
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.fail
import kotlin.coroutines.ContinuationInterceptor

@FlowPreview
@ExperimentalCoroutinesApi
class OnNextCreateTest :
  BaseTest(),
  LiveDataTest {

  val lifecycleOwner by resets { FakeLifecycleOwner() }
  val lifecycle by resets { lifecycleOwner.lifecycle }

  @Nested
  inner class `Lifecycle version` {

    @Test
    fun `block should immediately execute if already created`() = testProvided {

      lifecycleOwner.create()

      var executed = false

      lifecycle.onNextCreate { executed = true }

      executed shouldBe true
    }

    @Test
    fun `block should not immediately execute if lifecycle is not created`() = testProvided {

      lifecycleOwner.create()
      lifecycleOwner.destroy()

      var executed = false

      val job = launch { lifecycle.onNextCreate { executed = true } }

      executed shouldBe false

      job.cancelAndJoin()
    }

    @Test
    fun `block should stop when lifecycle is destroyed`() = testProvided {

      val input = Channel<Int>()
      val output = mutableListOf<Int>()
      var completed = false

      lifecycleOwner.create()

      val observer = launch {
        lifecycle.onNextCreate {
          input.consumeAsFlow()
            .onCompletion { completed = true }
            .collect { output.add(it) }
        }
      }

      input.send(1)
      input.send(2)
      input.send(3)

      lifecycleOwner.destroy()

      output shouldBe listOf(1, 2, 3)

      observer.join()

      completed shouldBe true
    }

    @Test
    fun `block should not execute twice when lifecycle is created twice`() = testProvided {

      lifecycleOwner.create()

      lifecycle.onNextCreate { expect(1) }

      lifecycleOwner.destroy()

      expect(2)

      lifecycleOwner.create()

      finish(3)
    }

    @Test
    fun `block should return value if allowed to complete`() = testProvided {

      lifecycleOwner.create()

      val result = lifecycle.onNextCreate { true }

      result shouldBe true
    }

    @Test
    fun `block should return null if not allowed to complete before destroy`() = testProvided {

      lifecycleOwner.create()

      val resultDeferred = async(start = LAZY) {
        lifecycle.onNextCreate<Boolean> {
          fail { "should be unreachable" }
        }
      }

      lifecycleOwner.destroy()

      resultDeferred.await() shouldBe null
    }

    @Test
    fun `block context should respect context parameter`() = testProvided {

      lifecycleOwner.create()

      var dispatcher: ContinuationInterceptor? = null

      lifecycle.onNextCreate(ioDispatcher) {
        dispatcher = coroutineContext[ContinuationInterceptor]
      }

      dispatcher shouldBe ioDispatcher
    }
  }

  @Nested
  inner class `LifecycleOwner version` {

    @Test
    fun `block should immediately execute if already created`() = testProvided {

      lifecycleOwner.create()

      var executed = false

      lifecycleOwner.onNextCreate { executed = true }

      executed shouldBe true
    }

    @Test
    fun `block should not immediately execute if lifecycle is not created`() = testProvided {

      lifecycleOwner.create()
      lifecycleOwner.destroy()

      var executed = false

      val job = launch { lifecycleOwner.onNextCreate { executed = true } }

      executed shouldBe false

      job.cancelAndJoin()
    }

    @Test
    fun `block should stop when lifecycle is destroyed`() = testProvidedUnconfined {

      val input = Channel<Int>()
      val output = mutableListOf<Int>()
      var completed = false

      lifecycleOwner.create()
      lifecycleOwner.start()
      lifecycleOwner.resume()

      val observer = launch {
        lifecycleOwner.onNextCreate {
          input.consumeAsFlow()
            .onCompletion { completed = true }
            .collect { output.add(it) }
        }
      }

      input.send(1)
      input.send(2)
      input.send(3)

      lifecycleOwner.pause()
      lifecycleOwner.stop()
      lifecycleOwner.destroy()

      output shouldBe listOf(1, 2, 3)

      observer.join()

      completed shouldBe true
    }

    @Test
    fun `block should not execute twice when lifecycle is created twice`() = testProvided {

      lifecycleOwner.create()

      lifecycleOwner.onNextCreate { expect(1) }

      lifecycleOwner.destroy()

      expect(2)

      lifecycleOwner.create()

      finish(3)
    }

    @Test
    fun `block should return value if allowed to complete`() = testProvided {

      lifecycleOwner.create()

      val result = lifecycleOwner.onNextCreate { true }

      result shouldBe true
    }

    @Test
    fun `block should return null if not allowed to complete before destroy`() = testProvided {

      lifecycleOwner.create()

      val resultDeferred = async {
        lifecycleOwner.onNextCreate<Boolean> {
          fail { "should be unreachable" }
        }
      }

      lifecycleOwner.destroy()

      resultDeferred.await() shouldBe null
    }

    @Test
    fun `block context should respect context parameter`() = testProvided {

      lifecycleOwner.create()

      var dispatcher: ContinuationInterceptor? = null

      lifecycleOwner.onNextCreate(ioDispatcher) {
        dispatcher = coroutineContext[ContinuationInterceptor]
      }

      dispatcher shouldBe ioDispatcher
    }
  }
}
