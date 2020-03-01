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
import dispatch.core.*
import dispatch.core.test.*
import dispatch.internal.test.android.*
import kotlinx.coroutines.*
import org.junit.jupiter.api.*

@ExperimentalCoroutinesApi
class LifecycleCoroutineScopeSample : CoroutineTest {

  override lateinit var testScope: TestProvidedCoroutineScope

  @BeforeEach
  fun beforeEach() {

    LifecycleScopeFactory.set { testScope }
  }

  @Sample
  fun lifecycleCoroutineScopeSample() = runBlocking {

    // This could be any LifecycleOwner -- Fragments, Activities, Services...
    class SomeFragment : Fragment() {

      init {

        // auto-created MainImmediateCoroutineScope which is lifecycle-aware
        lifecycleScope //...

        // active only when "resumed".  starts a fresh coroutine each time
        // this is a rough proxy for LiveData behavior
        lifecycleScope.launchOnResume { }

        // active only when "started".  starts a fresh coroutine each time
        lifecycleScope.launchOnStart { }

        // launch when created, automatically stop on destroy
        lifecycleScope.launchOnCreate { }

        // it works as a normal CoroutineScope as well (because it is)
        lifecycleScope.launchMain { }

      }
    }
  }

}
