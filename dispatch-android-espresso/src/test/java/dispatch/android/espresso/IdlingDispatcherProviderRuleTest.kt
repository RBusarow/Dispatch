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

package dispatch.android.espresso

import androidx.test.espresso.IdlingRegistry
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkStatic
import io.mockk.unmockkStatic
import io.mockk.verify
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestWatcher
import org.junit.runner.Description
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class IdlingDispatcherProviderRuleTest {

  val idlingDispatcherProvider = IdlingDispatcherProvider()

  @JvmField @Rule
  val mockkRule = EspressoMockingWrapper()

  @JvmField @Rule
  val idlingRule = IdlingDispatcherProviderRule { idlingDispatcherProvider }

  @Test
  fun `rule's DispatcherProvider should be what is returned by the factory`() {
    idlingRule.dispatcherProvider shouldBe idlingDispatcherProvider
  }

  @Test
  fun `dispatcherProvider should be registered with IdlingRegistry before test begins`() {

    val registry = mockkRule.idlingRegistry

    verify { registry.register(idlingDispatcherProvider.default.counter) }
    verify { registry.register(idlingDispatcherProvider.io.counter) }
    verify { registry.register(idlingDispatcherProvider.main.counter) }
    verify { registry.register(idlingDispatcherProvider.mainImmediate.counter) }
    verify { registry.register(idlingDispatcherProvider.unconfined.counter) }
  }
}

class EspressoMockingWrapper : TestWatcher() {

  val idlingRegistry = mockk<IdlingRegistry>(relaxed = true)

  override fun starting(description: Description) {
    mockkStatic(IdlingRegistry::class)
    every { IdlingRegistry.getInstance() } returns idlingRegistry
  }

  override fun finished(description: Description) {
    unmockkStatic(IdlingRegistry::class)
  }
}
