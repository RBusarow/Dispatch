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

import kotlinx.coroutines.*
import kotlinx.coroutines.test.*
import org.junit.jupiter.api.extension.*
import kotlin.coroutines.*
import kotlinx.coroutines.test.runBlockingTest as kotlinxRunBlockingTest

@ExperimentalCoroutinesApi
@ExtendWith(CoroutineTestExtension::class)
interface CoroutineTest {
  var testScope: TestProvidedCoroutineScope

  fun runBlockingTest(
    context: CoroutineContext = EmptyCoroutineContext,
    testBody: suspend TestCoroutineScope.() -> Unit
  ) = kotlinxRunBlockingTest(testScope.coroutineContext + context, testBody)
}

@ExperimentalCoroutinesApi
class CoroutineTestExtension : TestInstancePostProcessor, BeforeAllCallback, AfterEachCallback,
  AfterAllCallback {

  val dispatcher = TestCoroutineDispatcher()
  val testScope = TestProvidedCoroutineScope(dispatcher)

  override fun postProcessTestInstance(testInstance: Any?, context: ExtensionContext?) {

    (testInstance as? CoroutineTest)?.let { coroutineTest ->

      coroutineTest.testScope = testScope
    }
  }

  override fun beforeAll(context: ExtensionContext?) {
    Dispatchers.setMain(dispatcher)
  }

  override fun afterEach(context: ExtensionContext?) {
    testScope.cleanupTestCoroutines()
  }

  override fun afterAll(context: ExtensionContext?) {
    Dispatchers.resetMain()
  }

}

