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

import dispatch.internal.test.android.FakeFragment
import dispatch.internal.test.android.FakeLifecycleOwner
import dispatch.internal.test.android.LiveDataTest
import io.kotest.matchers.shouldBe
import io.kotest.matchers.types.shouldBeSameInstanceAs
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(manifest = Config.NONE)
@ExperimentalCoroutinesApi
internal class WithViewLifecycleScopeExtensionTest : LiveDataTest {

  val main = UnconfinedTestDispatcher(name = "main")

  val fragmentLifecycleOwner = FakeLifecycleOwner(mainDispatcher = main)
  val viewLifecycleOwner = FakeLifecycleOwner(mainDispatcher = main)

  @Before
  fun setUp() {
    fragmentLifecycleOwner.start()
    viewLifecycleOwner.create()
  }

  @After
  fun tearDown() {
    fragmentLifecycleOwner.destroy()
    viewLifecycleOwner.destroy()
  }

  @Test
  fun `when the livedata goes from null to non-null, lambda should be invoked`() {

    var invocations = 0

    val fragment = FakeFragment(fragmentLifecycleOwner)

    with(fragment) {
      withViewLifecycleScope { invocations++ }
    }

    invocations shouldBe 0

    fragment.setFakeViewLifecycleOwner(viewLifecycleOwner)

    invocations shouldBe 1
  }

  @Test
  fun `when the livedata goes from null to non-null, lambda should be invoked every time`() {

    var invocations = 0

    val fragment = FakeFragment(fragmentLifecycleOwner)

    with(fragment) {
      withViewLifecycleScope { invocations++ }
    }

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
  fun `when the livedata gets set to non-null, lambda should be invoked every time`() {

    var invocations = 0

    val fragment = FakeFragment(fragmentLifecycleOwner)

    with(fragment) {
      withViewLifecycleScope { invocations++ }
    }

    invocations shouldBe 0

    fragment.setFakeViewLifecycleOwner(viewLifecycleOwner)

    invocations shouldBe 1

    fragment.setFakeViewLifecycleOwner(viewLifecycleOwner)

    invocations shouldBe 2

    fragment.setFakeViewLifecycleOwner(viewLifecycleOwner)

    invocations shouldBe 3
  }

  @Test
  fun `when the view lifecycle is destroyed, lambda should be cancelled`() {

    lateinit var job: Job

    val fragment = FakeFragment(fragmentLifecycleOwner)

    with(fragment) {
      withViewLifecycleScope {
        job = launch {
          // never completes, so the Job should be active until the scope is destroyed
          CompletableDeferred<Int>().await()
        }
      }
    }

    fragment.setFakeViewLifecycleOwner(viewLifecycleOwner)

    viewLifecycleOwner.create()
    viewLifecycleOwner.destroy()

    job.isCancelled shouldBe true
  }

  @Test
  fun `when the fragment lifecycle is destroyed, lambda be cancelled`() {

    lateinit var job: Job

    val fragment = FakeFragment(fragmentLifecycleOwner)

    with(fragment) {
      withViewLifecycleScope {
        job = launch {
          // never completes, so the Job should be active until the scope is destroyed
          CompletableDeferred<Int>().await()
        }
      }
    }

    fragment.setFakeViewLifecycleOwner(viewLifecycleOwner)

    fragmentLifecycleOwner.create()
    fragmentLifecycleOwner.destroy()

    job.isActive shouldBe false
  }

  @Test
  fun `the lambda's LifecycleScope should correspond to the view lifecycle`() = runBlocking {

    lateinit var job: Job

    val fragment = FakeFragment(fragmentLifecycleOwner)

    with(fragment) {
      withViewLifecycleScope {
        job = launch {

          lifecycle shouldBeSameInstanceAs viewLifecycleOwner.lifecycle
        }
      }
    }

    fragment.setFakeViewLifecycleOwner(viewLifecycleOwner)

    job.join()
  }
}
