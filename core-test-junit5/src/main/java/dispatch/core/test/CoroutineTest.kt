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

/**
 * Convenience interface which tags a test class with a JUnit 5 extension.  This creates a new instance
 * of [testScope] before each test, optionally using a custom [testScopeFactory].
 *
 * ### Before Each:
 * * A new [TestProvidedCoroutineScope] is created using [testScopeFactory].
 * * [Dispatchers.Main] is set to the [TestCoroutineDispatcher] used by the [CoroutineContext].
 *
 * ### After Each:
 * * [cleanupTestCoroutines][TestCoroutineScope.cleanupTestCoroutines] is called to ensure there are no leaking coroutines.  Any unfinished coroutine
 * will throw an [UncompletedCoroutinesError].
 * * [Dispatchers.Main] is reset via [Dispatchers.resetMain].
 *
 * ### Requires JUnit 5.
 * ``` groovy
 * dependencies {
 *   testImplementation "org.junit.jupiter:junit-jupiter:5.5.1"
 * }
 * ```
 * @sample samples.CoroutineTestSample
 * @sample samples.CoroutineTestWithFactorySample
 * @see TestCoroutineExtension
 */
@ExperimentalCoroutinesApi
@ExtendWith(TestCoroutineExtension::class)
interface CoroutineTest {

  val testScopeFactory: () -> TestProvidedCoroutineScope
    get() = { TestProvidedCoroutineScope() }

  var testScope: TestProvidedCoroutineScope

  fun runBlockingTest(
    context: CoroutineContext = EmptyCoroutineContext,
    testBody: suspend TestCoroutineScope.() -> Unit
  ): Unit = runBlockingTestProvided(
    testScope.coroutineContext + context,
    testBody
  )
}

/**
 * Convenience interface for tagging a test class with a JUnit 5 extension.  This creates a new instance
 * of [testScope] before each test, optionally using a custom [factory].
 *
 * ### Before Each:
 * * A new [TestProvidedCoroutineScope] is created using [factory].
 * * [Dispatchers.Main] is set to the [TestCoroutineDispatcher] used by the [CoroutineContext].
 *
 * ### After Each:
 * * [cleanupTestCoroutines][TestCoroutineScope.cleanupTestCoroutines] is called to ensure there are no leaking coroutines.  Any unfinished coroutine
 * will throw an [UncompletedCoroutinesError].
 * * [Dispatchers.Main] is reset via [Dispatchers.resetMain].
 *
 * ### Requires JUnit 5.
 * ``` groovy
 * dependencies {
 *   testImplementation "org.junit.jupiter:junit-jupiter:5.5.1"
 * }
 * ```
 *
 * @param factory *optional* factory for a custom [TestProvidedCoroutineScope].  If a factory is not provided,
 * the resultant scope uses the same [TestCoroutineDispatcher] for each property in its [TestDispatcherProvider]
 * @sample samples.TestCoroutineExtensionSample
 * @sample samples.TestCoroutineExtensionWithFactorySample
 */
@ExperimentalCoroutinesApi
class TestCoroutineExtension(
  private val factory: () -> TestProvidedCoroutineScope = { TestProvidedCoroutineScope() }
) : TestInstancePostProcessor, BeforeEachCallback, AfterEachCallback {

  lateinit var testScope: TestProvidedCoroutineScope
  lateinit var dispatcher: TestCoroutineDispatcher

  private var testInstance: CoroutineTest? = null

  override fun postProcessTestInstance(testInstance: Any?, context: ExtensionContext?) {
    this.testInstance = testInstance as? CoroutineTest
  }

  override fun beforeEach(context: ExtensionContext) {

    testScope = testInstance?.testScopeFactory?.invoke() ?: factory()
    dispatcher = testScope.coroutineContext[ContinuationInterceptor] as TestCoroutineDispatcher

    testInstance?.let { instance ->

      instance.testScope = testScope
    }
    Dispatchers.setMain(dispatcher)
  }

  override fun afterEach(context: ExtensionContext) {
    testScope.cleanupTestCoroutines()
    Dispatchers.resetMain()
  }

}
