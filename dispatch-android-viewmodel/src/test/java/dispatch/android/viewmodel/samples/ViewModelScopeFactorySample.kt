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

package dispatch.android.viewmodel.samples

import dispatch.android.espresso.MainImmediateIdlingCoroutineScope
import dispatch.android.viewmodel.ViewModelScopeFactory
import dispatch.core.MainImmediateCoroutineScope
import dispatch.internal.test.Sample5
import dispatch.test.TestProvidedCoroutineScope
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
class ViewModelScopeFactorySample {

  @Sample5
  fun productionSample() {

    class MyApplication : Application {

      override fun onCreate() {
        ViewModelScopeFactory.set { MainImmediateCoroutineScope() }
      }
    }
  }

  @Sample5
  fun espressoSample() {

    class MyEspressoTest {

      @Before
      fun setUp() {
        ViewModelScopeFactory.set { MainImmediateIdlingCoroutineScope() }
      }
    }
  }

  @Sample5
  fun resetSample() {

    class MyEspressoTest {

      @Before
      fun setUp() {
        ViewModelScopeFactory.set { MainImmediateIdlingCoroutineScope() }
      }

      @After
      fun tearDown() {
        ViewModelScopeFactory.reset()
      }
    }
  }

  @Sample5
  fun jvmSample() {

    class MyJvmTest {

      @Before
      fun setUp() {
        ViewModelScopeFactory.set { TestProvidedCoroutineScope() }
      }
    }
  }

  private annotation class Before
  private annotation class After
}
