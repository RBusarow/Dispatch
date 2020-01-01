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
package com.rickbusarow.dispatcherprovidersample

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import com.rickbusarow.dispatcherprovider.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onEach
import timber.log.Timber

@ExperimentalCoroutinesApi
@SuppressLint("SetTextI18n")
class MainActivity : AppCompatActivity() {

  val scope = MainCoroutineScope()

  private val factory = viewModelFactory {
    MainViewModel(DefaultCoroutineScope(), SomeRepository(IOCoroutineScope()))
  }

  val viewModel: MainViewModel by lazy {
    ViewModelProviders.of(this, factory)[factory.viewModelClass]
  }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)

    Timber.plant(Timber.DebugTree())

    viewModel.message
      .onEach { Timber.v("I'm using the default dispatcher!") }
      // extract the default dispatcher from the CoroutineScope and apply it upstream
      .flowOn(scope.defaultDispatcher)
      .onEach { message ->
        Timber.v("I'm using the main dispatcher!")
        tvMessage.text = message
      }
      .onCompletion { tvMessage.text = "All done!" }
      // extract the main dispatcher from the CoroutineScope and apply it upstream for the UI updates
      .flowOn(scope.mainDispatcher)
      .launchIn(scope)

  }
}

