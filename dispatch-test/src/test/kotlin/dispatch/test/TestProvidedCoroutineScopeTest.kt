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

package dispatch.test

import dispatch.core.DefaultCoroutineScope
import dispatch.core.IOCoroutineScope
import dispatch.core.MainCoroutineScope
import dispatch.core.MainImmediateCoroutineScope
import dispatch.core.UnconfinedCoroutineScope
import dispatch.core.dispatcherProvider
import io.kotest.matchers.shouldBe
import io.kotest.matchers.types.shouldBeInstanceOf
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

@ExperimentalCoroutinesApi
internal class TestProvidedCoroutineScopeTest {

  val dispatcher = StandardTestDispatcher()
  val provider = TestDispatcherProvider(dispatcher)

  @Nested
  inner class `marker interfaces` {

    val scope = TestDispatchScope()

    @Test
    fun `TestProvidedCoroutineScope should implement DefaultCoroutineScope`() {

      scope.shouldBeInstanceOf<DefaultCoroutineScope>()
    }

    @Test
    fun `TestProvidedCoroutineScope should implement IOCoroutineScope`() {

      scope.shouldBeInstanceOf<IOCoroutineScope>()
    }

    @Test
    fun `TestProvidedCoroutineScope should implement MainCoroutineScope`() {

      scope.shouldBeInstanceOf<MainCoroutineScope>()
    }

    @Test
    fun `TestProvidedCoroutineScope should implement MainImmediateCoroutineScope`() {

      scope.shouldBeInstanceOf<MainImmediateCoroutineScope>()
    }

    @Test
    fun `TestProvidedCoroutineScope should implement UnconfinedCoroutineScope`() {

      scope.shouldBeInstanceOf<UnconfinedCoroutineScope>()
    }
  }

  @Nested
  inner class `impl` {

    @Test
    fun `scope implementation should contain DispatcherProvider property`() {

      val scope = TestDispatchScopeImpl(provider)

      scope.coroutineContext.dispatcherProvider shouldBe provider
    }

    @Test
    fun `DispatcherProvider property should override context arg`() {

      val scope = TestDispatchScopeImpl(
        dispatcherProvider = provider,
        context = TestDispatcherProvider()
      )

      scope.coroutineContext.dispatcherProvider shouldBe provider
    }
  }

  @Nested
  inner class `factory function` {

    @Test
    fun `scope should contain DispatcherProvider property`() {

      val scope = TestDispatchScope(dispatcherProvider = provider)

      scope.coroutineContext.dispatcherProvider shouldBe provider
    }

    @Test
    fun `dispatcher arg should be used to create default DispatcherProvider`() {

      val scope = TestDispatchScope(context = dispatcher)

      scope.dispatcherProvider.default shouldBe dispatcher
      scope.dispatcherProvider.io shouldBe dispatcher
      scope.dispatcherProvider.main shouldBe dispatcher
      scope.dispatcherProvider.mainImmediate shouldBe dispatcher
      scope.dispatcherProvider.unconfined shouldBe dispatcher
    }

    @Test
    fun `DispatcherProvider arg should override context arg`() {

      val scope = TestDispatchScope(
        context = TestDispatcherProvider(),
        dispatcherProvider = provider
      )

      scope.coroutineContext.dispatcherProvider shouldBe provider
    }
  }

  @Suppress("DEPRECATION")
  @Nested
  inner class `legacy factory function` {

    @Test
    fun `scope should contain DispatcherProvider property`() {

      val scope = TestProvidedCoroutineScope(dispatcherProvider = provider)

      scope.coroutineContext.dispatcherProvider shouldBe provider
    }

    @Test
    fun `dispatcher arg should be used to create default DispatcherProvider`() {

      val legacyDispatcher = kotlinx.coroutines.test.TestCoroutineDispatcher()

      val scope = TestProvidedCoroutineScope(dispatcher = legacyDispatcher)

      scope.dispatcherProvider.default shouldBe legacyDispatcher
      scope.dispatcherProvider.io shouldBe legacyDispatcher
      scope.dispatcherProvider.main shouldBe legacyDispatcher
      scope.dispatcherProvider.mainImmediate shouldBe legacyDispatcher
      scope.dispatcherProvider.unconfined shouldBe legacyDispatcher
    }

    @Test
    fun `DispatcherProvider arg should override context arg`() {

      val scope = TestProvidedCoroutineScope(
        dispatcherProvider = provider,
        context = TestDispatcherProvider()
      )

      scope.coroutineContext.dispatcherProvider shouldBe provider
    }
  }
}
