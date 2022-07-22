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

@file:OptIn(ExperimentalCoroutinesApi::class)
@file:Suppress("UNUSED_ANONYMOUS_PARAMETER")

package dispatch.android.lifecycle.samples

import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import dispatch.android.lifecycle.withViewLifecycle
import dispatch.core.MainImmediateCoroutineScope
import dispatch.internal.test.android.LiveDataTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onEach
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@Suppress("MemberNameEqualsClassName")
@RunWith(RobolectricTestRunner::class)
@ExperimentalCoroutinesApi
@Config(manifest = Config.NONE)
class WithViewLifecycleScopeSample : LiveDataTest {

  @Test
  fun withViewLifecycleScopeSample() {

    class MyFragment @Inject constructor(
      scope: MainImmediateCoroutineScope
    ) : Fragment() {

      val myViewModel by viewModels<MyViewModel>()

      val observerJob = scope.withViewLifecycle(this) {
        // this lambda is invoked every time the View lifecycle is set
        myViewModel.dataFlow.onEach { data ->
          // ...
        }.launchOnCreate()
      }
    }
  }
}

interface Data

class MyViewModel : ViewModel() {

  val dataFlow = flow<Data> {
    // ...
  }
}
