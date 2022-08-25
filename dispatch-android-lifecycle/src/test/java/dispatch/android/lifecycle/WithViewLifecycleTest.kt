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

import dispatch.core.MainImmediateCoroutineScope
import dispatch.core.dispatcherProvider
import dispatch.internal.test.android.FakeFragment
import dispatch.internal.test.android.FakeLifecycleOwner
import dispatch.internal.test.shouldEqualFolded
import dispatch.internal.test.shouldNotEqualFolded
import dispatch.test.testProvided
import io.kotest.matchers.shouldBe
import io.kotest.matchers.types.shouldBeSameInstanceAs
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(manifest = Config.NONE)
@ExperimentalCoroutinesApi
internal class WithViewLifecycleTest {

  lateinit var fragmentLifecycleOwner: FakeLifecycleOwner
  lateinit var viewLifecycleOwner: FakeLifecycleOwner

  fun test(testAction: suspend TestScope.() -> Unit) {

    testProvided(UnconfinedTestDispatcher()) {

      fragmentLifecycleOwner = FakeLifecycleOwner(mainDispatcher = dispatcherProvider.main)
      viewLifecycleOwner = FakeLifecycleOwner(mainDispatcher = dispatcherProvider.main)

      fragmentLifecycleOwner.start()
      viewLifecycleOwner.create()

      testAction()

      fragmentLifecycleOwner.destroy()
      viewLifecycleOwner.destroy()
    }
  }

  @Test
  fun `when the livedata goes from null to non-null, lambda should be invoked`() = test {

    var invocations = 0

    val fragment = FakeFragment(fragmentLifecycleOwner)

    withViewLifecycle(fragment) { invocations++ }

    invocations shouldBe 0

    fragment.setFakeViewLifecycleOwner(viewLifecycleOwner)

    invocations shouldBe 1
  }

  @Test
  fun `when the livedata goes from null to non-null, lambda should be invoked every time`() = test {

    var invocations = 0

    val fragment = FakeFragment(fragmentLifecycleOwner)

    withViewLifecycle(fragment) { invocations++ }

    invocations shouldBe 0

    fragment.setFakeViewLifecycleOwner(viewLifecycleOwner)

    invocations shouldBe 1

    fragment.setFakeViewLifecycleOwner(null)
    fragment.setFakeViewLifecycleOwner(viewLifecycleOwner)

    invocations shouldBe 2

    fragment.setFakeViewLifecycleOwner(null)
    fragment.setFakeViewLifecycleOwner(viewLifecycleOwner)

    invocations shouldBe 3
  }

  @Test
  fun `when the livedata gets set to non-null, lambda should be invoked every time`() = test {

    var invocations = 0

    val fragment = FakeFragment(fragmentLifecycleOwner)

    withViewLifecycle(fragment) { invocations++ }

    invocations shouldBe 0

    fragment.setFakeViewLifecycleOwner(viewLifecycleOwner)

    invocations shouldBe 1

    fragment.setFakeViewLifecycleOwner(viewLifecycleOwner)

    invocations shouldBe 2

    fragment.setFakeViewLifecycleOwner(viewLifecycleOwner)

    invocations shouldBe 3
  }

  @Test
  fun `when the view lifecycle is destroyed, lambda should be cancelled`() = test {

    lateinit var job: Job

    val fragment = FakeFragment(fragmentLifecycleOwner)

    withViewLifecycle(fragment) {
      job = launch {
        // never completes, so the Job should be active until the scope is destroyed
        CompletableDeferred<Int>().await()
      }
    }

    fragment.setFakeViewLifecycleOwner(viewLifecycleOwner)

    viewLifecycleOwner.create()
    viewLifecycleOwner.destroy()

    job.isCancelled shouldBe true
  }

  @Test
  fun `when the fragment lifecycle is destroyed, lambda be cancelled`() = test {

    lateinit var job: Job

    val fragment = FakeFragment(fragmentLifecycleOwner)

    withViewLifecycle(fragment) {
      job = launch {
        // never completes, so the Job should be active until the scope is destroyed
        CompletableDeferred<Int>().await()
      }
    }

    fragment.setFakeViewLifecycleOwner(viewLifecycleOwner)

    fragmentLifecycleOwner.create()
    fragmentLifecycleOwner.destroy()

    job.isActive shouldBe false
  }

  @Test
  fun `the lambda's LifecycleScope should correspond to the view lifecycle`() = test {

    lateinit var job: Job

    val fragment = FakeFragment(fragmentLifecycleOwner)

    withViewLifecycle(fragment) {
      job = launch {

        lifecycle shouldBeSameInstanceAs viewLifecycleOwner.lifecycle
      }
    }

    fragment.setFakeViewLifecycleOwner(viewLifecycleOwner)

    job.join()
  }

  @Test
  fun `receiver CoroutineScope should not be cancelled when Fragment's lifecycle is destroyed`() =
    test {

      /*
      This extension creates a ViewLifecycleScope without knowing where the receiver CoroutineScope came from.

      It creates a child scope from the receiver, and that scope is automatically cancelled when the Fragment is destroyed,
      but the source scope should be unchanged.
       */

      val fragment = FakeFragment(fragmentLifecycleOwner)

      val receiverScope =
        MainImmediateCoroutineScope(Job() + coroutineContext)

      var internalScope: CoroutineScope? = null

      receiverScope.withViewLifecycle(fragment) {
        launch {

          internalScope = this

          lifecycle shouldBeSameInstanceAs viewLifecycleOwner.lifecycle
        }
      }

      fragment.setFakeViewLifecycleOwner(viewLifecycleOwner)

      viewLifecycleOwner.destroy()
      fragmentLifecycleOwner.destroy()
      internalScope!!.coroutineContext[Job]!!.isActive shouldBe false

      receiverScope.coroutineContext[Job]!!.isActive shouldBe true
    }

  @Test
  fun `subsequent viewLifecycles should restart the lamba after the initial is destroyed`() =
    test {

      val fragment = FakeFragment(fragmentLifecycleOwner)

      val receiverJob = Job()

      val receiverScope =
        MainImmediateCoroutineScope(receiverJob + coroutineContext)

      val control = Channel<Int>()
      val deadlock = CompletableDeferred<Unit>()

      var count = 0

      receiverScope.withViewLifecycle(fragment) {

        launch {
          control.send(++count)

          deadlock.await()
        }
      }

      fragment.setFakeViewLifecycleOwner(viewLifecycleOwner)

      control.receive() shouldBe 1

      viewLifecycleOwner.destroy()

      val secondView = FakeLifecycleOwner(mainDispatcher = dispatcherProvider.main)

      fragment.setFakeViewLifecycleOwner(secondView)

      control.receive() shouldBe 2
    }

  @Test
  fun `subsequent viewLifecycles should restart the lamba after the initial is set to null`() =
    test {

      val fragment = FakeFragment(fragmentLifecycleOwner)

      val receiverJob = Job()

      val receiverScope =
        MainImmediateCoroutineScope(receiverJob + coroutineContext)

      val control = Channel<Int>()
      val deadlock = CompletableDeferred<Unit>()

      var count = 0

      receiverScope.withViewLifecycle(fragment) {

        launch {
          control.send(++count)

          deadlock.await()
        }
      }

      fragment.setFakeViewLifecycleOwner(viewLifecycleOwner)

      control.receive() shouldBe 1

      fragment.setFakeViewLifecycleOwner(null)

      val secondView = FakeLifecycleOwner(mainDispatcher = dispatcherProvider.main)

      fragment.setFakeViewLifecycleOwner(secondView)

      control.receive() shouldBe 2
    }

  @Test
  fun `receiver CoroutineScope and View scope contexts should be identical except for the Job`() =
    test {

      val fragment = FakeFragment(fragmentLifecycleOwner)

      val receiverScope =
        MainImmediateCoroutineScope(Job() + coroutineContext)

      var internalScope: CoroutineScope? = null

      receiverScope.withViewLifecycle(fragment) {
        launch {

          internalScope = this

          lifecycle shouldBeSameInstanceAs viewLifecycleOwner.lifecycle
        }
      }

      fragment.setFakeViewLifecycleOwner(viewLifecycleOwner)

      viewLifecycleOwner.destroy()
      fragmentLifecycleOwner.destroy()

      val receiverContext = receiverScope.coroutineContext

      val internalContext = internalScope!!.coroutineContext

      val internalJob = internalContext[Job]!!

      receiverContext shouldNotEqualFolded internalContext

      receiverContext + internalJob shouldEqualFolded internalContext
    }
}
