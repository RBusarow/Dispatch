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

package dispatch.core.test

import dispatch.core.*
import io.kotlintest.*
import kotlinx.coroutines.*
import org.junit.jupiter.api.*
import org.junit.jupiter.api.extension.*
import kotlin.coroutines.*

@ExperimentalCoroutinesApi
class TestCoroutineExtensionTest {

  val customScope = TestProvidedCoroutineScope()
  @JvmField @RegisterExtension val customFactoryExtension = TestCoroutineExtension { customScope }
  @JvmField @RegisterExtension val defaultExtension = TestCoroutineExtension()

  val customHistory = mutableSetOf<TestProvidedCoroutineScope>()
  val defaultHistory = mutableListOf<TestProvidedCoroutineScope>()

  @BeforeEach
  fun beforeEach() {

    // by accessing these testScope properties and not crashing,
    // we also ensure the initialization sequence
    customHistory.add(customFactoryExtension.testScope)
    defaultHistory.add(defaultExtension.testScope)
  }

  @AfterAll
  fun afterAll() {

    customHistory.size shouldBe 1

    defaultHistory.distinct().size shouldBe 2

  }

  @Test
  fun `a no-arg extension should use a default TestProvidedCoroutineScope`() {
    val dispatcherProvider = defaultExtension.testScope.coroutineContext[DispatcherProvider]!!

    val allDispatchers = setOf(
      dispatcherProvider.default,
      dispatcherProvider.io,
      dispatcherProvider.main,
      dispatcherProvider.mainImmediate,
      dispatcherProvider.unconfined,
      defaultExtension.dispatcher,
      defaultExtension.testScope.coroutineContext[ContinuationInterceptor]!!
    )

    allDispatchers.size shouldBe 1
  }

  @Test
  fun `a custom factory extension should use use the custom factory`() {

    val context = customFactoryExtension.testScope.coroutineContext

    context shouldBe customScope.coroutineContext

  }
}

