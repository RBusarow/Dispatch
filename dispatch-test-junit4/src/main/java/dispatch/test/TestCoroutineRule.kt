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

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineScheduler
import kotlinx.coroutines.test.TestDispatcher
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.rules.TestRule
import org.junit.rules.TestWatcher
import org.junit.runner.Description
import kotlin.coroutines.ContinuationInterceptor

/**
 * A basic JUnit 4 [TestRule] which creates a new [TestProvidedCoroutineScope] for each test, sets
 * [Dispatchers.Main], and resets [Dispatchers.Main] afterwards.
 *
 * The rule itself implements [TestProvidedCoroutineScope], so it can be used as follows:
 *
 * ### Before the test:
 * * [Dispatchers.Main] is set to the [main][dispatch.core.DispatcherProvider.main] dispatcher in
 *   this scope's [dispatcherProvider].
 *
 * ### After the test:
 * * [Dispatchers.Main] is reset via [Dispatchers.resetMain].
 *
 * ### Requires JUnit 4.
 *
 * ```kotlin
 * dependencies {
 *   testImplementation("junit:junit:<latest version>")
 *   -- or --
 *   testImplementation("org.junit.vintage:junit-vintage-engine:<latest version>")
 * }
 * ```
 *
 * @param factory *optional* factory for a custom [TestProvidedCoroutineScope]. If a factory is not
 *     provided, the resultant scope uses the same
 *     [TestDispatcher][kotlinx.coroutines.test.TestDispatcher] for each property in its
 *     [TestDispatcherProvider]
 * @see TestRule
 * @see TestScope
 * @see TestProvidedCoroutineScope
 * @sample dispatch.core.test.samples.TestCoroutineRuleSample
 * @sample dispatch.core.test.samples.TestCoroutineRuleWithFactorySample
 */
@ExperimentalCoroutinesApi
public class TestCoroutineRule(
  factory: () -> TestProvidedCoroutineScope = { TestProvidedCoroutineScope() }
) : TestWatcher(),
  TestProvidedCoroutineScope by factory() {

  /**
   * The underlying [TestDispatcher][kotlinx.coroutines.test.TestDispatcher] which is responsible
   * for virtual time control.
   *
   * @see TestDispatcher
   * @see TestCoroutineScheduler
   */
  public val dispatcher: TestDispatcher =
    coroutineContext[ContinuationInterceptor] as TestDispatcher

  /** @suppress */
  override fun starting(description: Description) {
    Dispatchers.setMain(dispatcherProvider.main)
  }

  /** @suppress */
  override fun finished(description: Description) {
    Dispatchers.resetMain()
  }
}
