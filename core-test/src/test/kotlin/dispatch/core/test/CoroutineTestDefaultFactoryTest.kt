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

import io.kotlintest.*
import kotlinx.coroutines.*
import org.junit.jupiter.api.*
import kotlin.coroutines.*

@ExperimentalCoroutinesApi
class CoroutineTestDefaultFactoryTest : CoroutineTest {

  override lateinit var testScope: TestProvidedCoroutineScope

  val defaultHistory = mutableListOf<TestProvidedCoroutineScope>()

  @BeforeEach
  fun beforeEach() {

    // by accessing this testScope property and not crashing,
    // we also ensure the initialization sequence
    defaultHistory.add(testScope)
  }

  @AfterAll
  fun afterAll() {

    defaultHistory.distinct().size shouldBe 2

  }

  @Test
  fun `a no-arg extension should use a default TestProvidedCoroutineScope`() {
    val dispatcherProvider = testScope.dispatcherProvider

    val allDispatchers = setOf(
      dispatcherProvider.default,
      dispatcherProvider.io,
      dispatcherProvider.main,
      dispatcherProvider.mainImmediate,
      dispatcherProvider.unconfined,
      testScope.coroutineContext[ContinuationInterceptor]!!
    )

    allDispatchers.size shouldBe 1
  }

  @Test
  fun `this function exists so that we set testScope multiple times`() {

    // TestScope should be set before every test.
    // This function means that we set testScope twice, which allows us to check
    // that a different instance is returned each time thanks to the factory.

    // The check is done in afterAll().
  }
}
