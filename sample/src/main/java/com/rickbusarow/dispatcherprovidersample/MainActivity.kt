/*
 * Copyright (C) 2019-2020 Rick Busarow
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

@file:SuppressLint("SetTextI18n")

package com.rickbusarow.dispatcherprovidersample

import android.annotation.*
import android.os.*
import androidx.activity.*
import androidx.appcompat.app.*
import com.rickbusarow.dispatcherprovider.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import timber.log.*

@ExperimentalCoroutinesApi
class MainActivity : AppCompatActivity() {

  val scope = MainCoroutineScope()

  private val factory = viewModelFactory {
    MainViewModel(DefaultCoroutineScope(), SomeRepository(IOCoroutineScope()))
  }

  val viewModel: MainViewModel by viewModels { factory }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)

    Timber.plant(Timber.DebugTree())

    // share the same Job as the `scope` property above, but use the IO dispatcher as default
    val ioDefaultScope = scope + Dispatchers.IO

    viewModel.message
      .onEach { Timber.v("I'm using the default dispatcher!") }
      // extract the default dispatcher from the CoroutineScope and apply it upstream
      .flowOn(scope.defaultDispatcher)
      .onEach { message ->
        Timber.v("I'm using the main dispatcher!")
        tvMessage.text = message
      }
      .onCompletion { tvMessage.text = "All done!" }
      // the .flowOn____() operator pulls the desired dispatcher out of the CoroutineScope
      // and applies it.  So in this case .flowOnMain() is pulling
      // the dispatcher assigned to "main" out of `ioDefaultScope`
      // and dispatching upstream execution
      .flowOnMain()
      .onEach { Timber.v("I'm using the IO dispatcher!") }
      // the default dispatcher in this scope is now Dispatchers.IO
      .launchIn(ioDefaultScope)

  }
}

