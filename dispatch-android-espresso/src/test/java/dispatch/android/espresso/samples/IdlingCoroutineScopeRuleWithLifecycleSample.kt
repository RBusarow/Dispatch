/*
 * Copyright (C) 2021 Rick Busarow
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
package dispatch.android.espresso.samples

import dispatch.android.espresso.IdlingDispatcherProvider
import dispatch.android.espresso.IdlingDispatcherProviderRule
import dispatch.android.lifecycle.LifecycleScopeFactory
import dispatch.android.viewmodel.ViewModelScopeFactory
import dispatch.core.MainImmediateCoroutineScope
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class IdlingCoroutineScopeRuleWithLifecycleSample {

  // Retrieve the DispatcherProvider from a dependency graph,
  // so that the same one is used throughout the codebase.
  val customDispatcherProvider = testAppComponent.customDispatcherProvider

  @JvmField
  @Rule
  val idlingRule = IdlingDispatcherProviderRule {
    IdlingDispatcherProvider(customDispatcherProvider)
  }

  @Before
  fun setUp() {
    LifecycleScopeFactory.set { customDispatcherProvider }
    ViewModelScopeFactory.set {
      MainImmediateCoroutineScope(customDispatcherProvider)
    }
    // now all scopes use the same IdlingDispatcherProvider
  }

  @Test
  fun testThings() = runBlocking {

    // Now any CoroutineScope which uses the DispatcherProvider
    // in TestAppComponent will sync its "idle" state with Espresso
  }
}
