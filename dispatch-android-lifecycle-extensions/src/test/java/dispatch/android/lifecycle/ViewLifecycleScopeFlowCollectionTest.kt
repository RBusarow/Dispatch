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

import dispatch.core.dispatcherProvider
import dispatch.internal.test.android.FakeFragment
import dispatch.internal.test.android.FakeLifecycleOwner
import dispatch.internal.test.android.LiveDataTest
import dispatch.test.testProvided
import io.kotest.matchers.shouldBe
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(manifest = Config.NONE)
@ExperimentalCoroutinesApi
internal class ViewLifecycleScopeFlowCollectionTest : LiveDataTest {

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
  fun `launchOnCreate collection should only happen while at least CREATED`() = test {

    val fragment = FakeFragment(fragmentLifecycleOwner)

    val flow = MutableStateFlow(0)
    val collected = mutableListOf<Int>()

    with(fragment) {
      withViewLifecycleScope {
        flow.onEach { collected.add(it) }.launchOnCreate()
      }
    }

    collected shouldBe listOf<Int>()

    fragment.setFakeViewLifecycleOwner(viewLifecycleOwner)

    viewLifecycleOwner.create()

    collected shouldBe listOf(0)

    flow.value = 1

    collected shouldBe listOf(0, 1)

    viewLifecycleOwner.destroy()

    flow.value = 2

    collected shouldBe listOf(0, 1)
  }

  @Test
  fun `launchOnStart collection should only happen while at least STARTED`() = test {

    val fragment = FakeFragment(fragmentLifecycleOwner)

    val flow = MutableStateFlow(0)
    val collected = mutableListOf<Int>()

    with(fragment) {
      withViewLifecycleScope {
        flow.onEach { collected.add(it) }
          .launchOnStart()
      }
    }

    collected shouldBe listOf<Int>()

    fragment.setFakeViewLifecycleOwner(viewLifecycleOwner)

    viewLifecycleOwner.start()

    collected shouldBe listOf(0)

    flow.value = 1

    collected shouldBe listOf(0, 1)

    viewLifecycleOwner.stop()

    flow.value = 2

    collected shouldBe listOf(0, 1)
  }

  @Test
  fun `launchOnResume collection should only happen while RESUMED`() = test {

    val fragment = FakeFragment(fragmentLifecycleOwner)

    val flow = MutableStateFlow(0)
    val collected = mutableListOf<Int>()

    with(fragment) {
      withViewLifecycleScope {
        flow.onEach { collected.add(it) }.launchOnResume()
      }
    }

    collected shouldBe listOf<Int>()

    fragment.setFakeViewLifecycleOwner(viewLifecycleOwner)

    viewLifecycleOwner.resume()

    collected shouldBe listOf(0)

    flow.value = 1

    collected shouldBe listOf(0, 1)

    viewLifecycleOwner.pause()

    flow.value = 2

    collected shouldBe listOf(0, 1)
  }
}
