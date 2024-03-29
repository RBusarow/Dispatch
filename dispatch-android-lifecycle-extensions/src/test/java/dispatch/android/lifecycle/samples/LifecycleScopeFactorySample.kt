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

@file:Suppress("EXPERIMENTAL_API_USAGE")

package dispatch.android.lifecycle.samples

import dispatch.android.espresso.IdlingDispatcherProvider
import dispatch.android.lifecycle.LifecycleScopeFactory
import dispatch.android.lifecycle.MainImmediateContext
import dispatch.core.DispatcherProvider
import dispatch.internal.test.Application
import dispatch.internal.test.MyCustomElement
import dispatch.internal.test.Sample5
import kotlinx.coroutines.SupervisorJob

class LifecycleScopeFactorySample {

  @Sample5
  fun productionSample() {

    class MyApplication : Application {

      override fun onCreate() {

        LifecycleScopeFactory.set { MyCustomElement() + MainImmediateContext() }
      }
    }
  }

  @Sample5
  fun espressoSample() {

    class MyEspressoTest {

      @Before
      fun setUp() {

        val dispatcherProvider = IdlingDispatcherProvider()

        LifecycleScopeFactory.set {
          SupervisorJob() + dispatcherProvider + dispatcherProvider.mainImmediate
        }
      }
    }
  }

  @Sample5
  fun resetSample() {

    class MyEspressoTest {

      @Before
      fun setUp() {

        val dispatcherProvider = DispatcherProvider()

        LifecycleScopeFactory.set {
          SupervisorJob() + dispatcherProvider + dispatcherProvider.mainImmediate
        }
      }

      @After
      fun tearDown() {
        LifecycleScopeFactory.reset()
      }
    }
  }

  private annotation class Before
  private annotation class After
}
