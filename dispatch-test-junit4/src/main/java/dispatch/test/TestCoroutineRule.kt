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
import kotlinx.coroutines.cancel
import kotlinx.coroutines.test.TestCoroutineScope
import kotlinx.coroutines.test.TestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.rules.TestRule
import org.junit.rules.TestWatcher
import org.junit.runner.Description
import kotlin.coroutines.ContinuationInterceptor
import kotlin.coroutines.CoroutineContext

/**
 * A basic JUnit 4 [TestRule] which creates a new [TestProvidedCoroutineScope] for each test, sets
 * [Dispatchers.Main], and calls [cancel] afterwards.
 *
 * The rule itself implements [TestProvidedCoroutineScope], so it can be used as follows:
 *
 * ### Before the test:
 * * [Dispatchers.Main] is set to the [TestDispatcher] used by the [CoroutineContext].
 *
 * ### After the test:
 * * [cancel] is called to ensure there are no leaking coroutines.
 * * [Dispatchers.Main] is reset via [Dispatchers.resetMain].
 *
 * ### Requires JUnit 4.
 *
 * ```kotlin
 * dependencies {
 *   testImplementation("junit:junit:4.12")
 *   -- or --
 *   testImplementation("org.junit.vintage:junit-vintage-engine:5.5.1")
 * }
 * ```
 *
 * @param factory *optional* factory for a custom [TestProvidedCoroutineScope]. If a factory is not
 *   provided, the resultant scope uses the same [TestDispatcher]
 *   for each property in its [TestDispatcherProvider]
 * @sample dispatch.core.test.samples.TestCoroutineRuleSample
 * @sample dispatch.core.test.samples.TestCoroutineRuleWithFactorySample
 * @see TestRule
 * @see TestCoroutineScope
 * @see TestProvidedCoroutineScope
 */
@ExperimentalCoroutinesApi
public class TestCoroutineRule(
  factory: () -> TestProvidedCoroutineScope = { TestProvidedCoroutineScope() }
) : TestWatcher(),
  TestProvidedCoroutineScope by factory() {

  /** The underlying [TestDispatcher] which is responsible for virtual time control. */
  public val dispatcher: TestDispatcher =
    coroutineContext[ContinuationInterceptor] as TestDispatcher

  /** @suppress */
  override fun starting(description: Description) {
    Dispatchers.setMain(dispatcher)
  }

  /** @suppress */
  override fun finished(description: Description) {
    dispatcher.cancel()
    Dispatchers.resetMain()
  }
}
