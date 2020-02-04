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

package dispatch.android.espresso

import androidx.test.espresso.*
import dispatch.core.*
import kotlinx.coroutines.*
import kotlinx.coroutines.test.*
import org.junit.rules.*
import org.junit.runner.*

/**
 * A basic JUnit 4 [TestRule] which creates a new [IdlingDispatcherProvider] for each test,
 * registering all [IdlingDispatcher]s with [IdlingRegistry] before `@Before` and unregistering them after `@After`.
 *
 * The rule takes an optional [IdlingDispatcherProvider] factory, in which case it only handles registration.
 *
 * When doing Espresso testing, it's important that the same [IdlingDispatcher]s are used throughout a codebase.
 * For this reason, it's a good idea to use a dependency injection framework just as Dagger or Koin
 * to provide [CoroutineScope]s.
 *
 * ```
 * class SomeEspressoTest {
 *
 *   // Retrieve the DispatcherProvider from a dependency graph,
 *   // so that the same one is used throughout the codebase.
 *   val customDispatcherProvider = testAppComponent.customDispatcherProvider
 *
 *   @JvmField @Rule val idlingRule = IdlingDispatcherProviderRule { IdlingDispatcherProvider(customDispatcherProvider) }
 *
 *   @Test
 *   fun testThings() = runBlocking {
 *
 *     // for the duration of this test
 *
 *   }
 * }
 * ```
 *
 * If using the `lifecycleScope` and `viewModelScope` properties,
 * be sure to use the versions from the `dispatch-android-lifecycle` artifacts to make use of their settable factories.
 *
 * ```
 * import dispatch.lifecycle.*
 *
 * class SomeEspressoTest {
 *
 *   val customDispatcherProvider = IdlingDispatcherProvider()
 *
 *   @JvmField @Rule val idlingRule = IdlingDispatcherProviderRule { customDispatcherProvider }
 *
 *   @Before
 *   fun setUp() {
 *     LifecycleScopeFactory.set { MainImmediateCoroutineScope(customDispatcherProvider) }
 *     ViewModelScopeFactory.set { MainImmediateCoroutineScope(customDispatcherProvider) }
 *
 *     // now all scopes use the same IdlingDispatcherProvider
 *   }
 *
 *   @Test
 *   fun testThings() = runBlocking {
 *
 *     // for the duration of this test
 *
 *   }
 * }
 * ```
 *
 * ### Before the test:
 * * [IdlingDispatcherProvider.registerAllIdlingResources] is called to register all dispatchers with [IdlingRegistry].
 *
 * ### After the test:
 * * [IdlingDispatcherProvider.unregisterAllIdlingResources] is called to unregister all dispatchers with [IdlingRegistry].
 *
 * ### Requires JUnit 4.
 * ```
 * dependencies {
 *   testImplementation "junit:junit:4.12"
 *   -- or --
 *   testImplementation "org.junit.vintage:junit-vintage-engine:5.5.1"
 * }
 * ```
 * @param factory factory for a custom [IdlingDispatcherProvider].
 * This must be the same [DispatcherProvider] which is used to create [CoroutineScope]s in the code being tested.
 *
 * @see TestRule
 * @see TestCoroutineScope
 * @see IdlingRegistry
 */
class IdlingDispatcherProviderRule(
  private val factory: () -> IdlingDispatcherProvider
) : TestWatcher() {

  lateinit var dispatcherProvider: IdlingDispatcherProvider

  override fun starting(description: Description?) {
    dispatcherProvider = factory.invoke()

    dispatcherProvider.registerAllIdlingResources()
  }

  override fun finished(description: Description?) {
    dispatcherProvider.unregisterAllIdlingResources()
  }
}
