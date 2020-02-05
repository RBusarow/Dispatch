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

package dispatch.android.lifecycle

import dispatch.android.lifecycle.ViewModelScopeFactory.create
import dispatch.android.lifecycle.ViewModelScopeFactory.reset
import dispatch.core.*
import kotlinx.coroutines.*

/**
 * Factory holder for [viewModelScope][dispatch.android.CoroutineViewModel.viewModelScope]'s.
 *
 * By default, [create] returns a [MainImmediateCoroutineScope], but may return any [CoroutineScope].
 *
 * This factory can be overridden for testing or to include a custom [CoroutineContext][kotlin.coroutines.CoroutineContext]
 * in production code.  This may be done in [Application.onCreate][android.app.Application.onCreate].
 *
 * example:
 *
 * ```
 * class MyApplication : Application() {
 *
 *   override fun onCreate() {
 *     super.onCreate()
 *     LifecycleScopeFactory.set { MainImmediateCoroutineScope() + MyCustomElement() }
 *   }
 * }
 * ```
 *
 * ```
 * class MyEspressoTest {
 *
 *   @Before fun setUp() {
 *     LifecycleCoroutineScopeFactory.set { MainImmediateIdlingCoroutineScope() }
 *   }
 * }
 * ```
 *
 * [reset] may be used to reset the factory to default at any time.
 */
public object ViewModelScopeFactory {

  private var _factory: () -> CoroutineScope = { MainImmediateCoroutineScope() }
  /**
   * example:
   *
   * ```
   * class MyApplication : Application() {
   *
   *   override fun onCreate() {
   *     super.onCreate()
   *     ViewModelScopeFactory.set { MainImmediateCoroutineScope() + MyCustomElement() }
   *   }
   * }
   * ```
   *
   * ```
   * class MyEspressoTest {
   *
   *   @Before fun setUp() {
   *     ViewModelScopeFactory.set { MainImmediateIdlingCoroutineScope() }
   *   }
   * }
   * ```
   *
   * @param factory sets a custom [CoroutineScope] factory to be used for all new instance creations until reset.
   */
  public fun set(factory: () -> CoroutineScope) {
    _factory = factory
  }

  internal fun create(): CoroutineScope = _factory.invoke()

  /**
   * Immediately resets the factory function to its default.
   */
  public fun reset() {
    _factory = { MainCoroutineScope() }
  }

}
