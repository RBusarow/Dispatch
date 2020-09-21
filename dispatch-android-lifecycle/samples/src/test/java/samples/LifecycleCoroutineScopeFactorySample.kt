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

package samples

import dispatch.android.lifecycle.*

class LifecycleCoroutineScopeFactorySample {

  @Sample
  fun factorySample() {

    @Provides
    fun provideFactory(): LifecycleCoroutineScopeFactory = LifecycleCoroutineScopeFactory {
      // other elements are added automatically
      MyCustomElement()
    }

    class MyFragment @Inject constructor(
      factory: LifecycleCoroutineScopeFactory
    ) : Fragment() {

      val lifecycleScope = factory.create(lifecycle)

      init {
        lifecycleScope.launchOnStart {
          // ...
        }
      }
    }
  }
}

internal annotation class Before
internal annotation class After
internal annotation class Module
internal annotation class Provides
internal annotation class Inject
