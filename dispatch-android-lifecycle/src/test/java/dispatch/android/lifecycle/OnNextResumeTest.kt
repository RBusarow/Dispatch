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
class OnNextResumeTest : BaseTest(),
  LiveDataTest {

  val lifecycleOwner by resets { FakeLifecycleOwner() }
  val lifecycle by resets { lifecycleOwner.lifecycle }

  @Nested
  inner class `Lifecycle version` {

    @Test
    fun `block should immediately execute if already resumed`() = testProvided {

      lifecycleOwner.resume()

      var executed = false

      lifecycle.onNextResume { executed = true }

      executed shouldBe true
    }

    @Test
    fun `block should not immediately execute if lifecycle is not resumed`() = testProvided {

      lifecycleOwner.pause()

      var executed = false

      val job = launch { lifecycle.onNextResume { executed = true } }

      executed shouldBe false

      job.cancelAndJoin()
    }

    @Test
    fun `block should pause when lifecycle is destroyed`() = testProvided {

      val input = Channel<Int>()
      val output = mutableListOf<Int>()
      var completed = false

      lifecycleOwner.resume()

      val observer = launch {
        lifecycle.onNextResume {
          input.consumeAsFlow()
            .onCompletion { completed = true }
            .collect { output.add(it) }
        }
      }

      input.send(1)
      input.send(2)
      input.send(3)


      lifecycleOwner.pause()
      lifecycleOwner.destroy()
      output shouldBe listOf(1, 2, 3)

      observer.join()

      completed shouldBe true
    }

    @Test
    fun `block should not execute twice when lifecycle is resumed twice`() = testProvided {

      lifecycleOwner.resume()

      lifecycle.onNextResume { expect(1) }

      lifecycleOwner.pause()

      expect(2)

      lifecycleOwner.resume()

      finish(3)
    }

    @Test
    fun `block should return value if allowed to complete`() = testProvided {

      lifecycleOwner.resume()

      val result = lifecycle.onNextResume { true }

      result shouldBe true
    }

    @Test
    fun `block should return null if not allowed to complete before destroy`() = testProvided {

      lifecycleOwner.resume()

      val resultDeferred = async(start = LAZY) {
        lifecycle.onNextResume<Boolean> {
          fail { "should be unreachable" }
        }
      }

      lifecycleOwner.destroy()

      resultDeferred.await() shouldBe null
    }

    @Test
    fun `block context should respect context parameter`() = testProvided {

      lifecycleOwner.resume()

      var dispatcher: ContinuationInterceptor? = null

      lifecycle.onNextResume(ioDispatcher) {
        dispatcher = coroutineContext[ContinuationInterceptor]
      }

      dispatcher shouldBe ioDispatcher
    }
  }

  @Nested
  inner class `LifecycleOwner version` {

    @Test
    fun `block should immediately execute if already resumed`() = testProvided {

      lifecycleOwner.resume()

      var executed = false

      lifecycleOwner.onNextResume { executed = true }

      executed shouldBe true
    }

    @Test
    fun `block should not immediately execute if lifecycle is not resumed`() = testProvided {

      lifecycleOwner.pause()

      var executed = false

      val job = launch { lifecycleOwner.onNextResume { executed = true } }

      executed shouldBe false

      job.cancelAndJoin()
    }

    @Test
    fun `block should pause when lifecycle is destroyed`() = testProvided {

      val input = Channel<Int>()
      val output = mutableListOf<Int>()
      var completed = false

      lifecycleOwner.resume()

      val observer = launch {
        lifecycleOwner.onNextResume {
          input.consumeAsFlow()
            .onCompletion { completed = true }
            .collect { output.add(it) }
        }
      }

      input.send(1)
      input.send(2)
      input.send(3)

      lifecycleOwner.pause()
      lifecycleOwner.destroy()

      output shouldBe listOf(1, 2, 3)

      observer.join()

      completed shouldBe true
    }

    @Test
    fun `block should not execute twice when lifecycle is resumed twice`() = testProvided {

      lifecycleOwner.resume()

      lifecycleOwner.onNextResume { expect(1) }

      lifecycleOwner.pause()

      expect(2)

      lifecycleOwner.resume()

      finish(3)
    }

    @Test
    fun `block should return value if allowed to complete`() = testProvided {

      lifecycleOwner.resume()

      val result = lifecycleOwner.onNextResume { true }

      result shouldBe true
    }

    @Test
    fun `block should return null if not allowed to complete before destroy`() = testProvided {

      lifecycleOwner.resume()

      val resultDeferred = async {
        lifecycleOwner.onNextResume<Boolean> {
          fail { "should be unreachable" }
        }
      }

      lifecycleOwner.destroy()

      resultDeferred.await() shouldBe null
    }

    @Test
    fun `block context should respect context parameter`() = testProvided {

      lifecycleOwner.resume()

      var dispatcher: ContinuationInterceptor? = null

      lifecycleOwner.onNextResume(ioDispatcher) {
        dispatcher = coroutineContext[ContinuationInterceptor]
      }

      dispatcher shouldBe ioDispatcher
    }
  }
}
