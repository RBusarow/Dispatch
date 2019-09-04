/*
 * Copyright (C) 2019 Rick Busarow
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

import androidx.lifecycle.ViewModel
import com.rickbusarow.dispatcherprovider.DefaultCoroutineScope
import com.rickbusarow.dispatcherprovider.launchDefault
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.cancel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.launch

class MainViewModel(val coroutineScope: DefaultCoroutineScope, val repository: SomeRepository) :
  ViewModel() {

  val message = Channel<String>()

  // This property is eagerly evaluated.  The query is made immediately and the deferred will be
  // completed as soon as it is done.
  val expensiveDeferred: Deferred<String> = repository.getSomethingExpensiveUnstructured()

  // This property is lazily evaluated.  The query is made when it is requested the first time,
  // then will be completed as soon as it is done.
  val alsoExpensiveDeferred: Deferred<String> by lazy {
    CompletableDeferred<String>().also { completable ->

      // this no-arg launch will use the default CoroutineDispatcher in the CoroutineScope,
      // which in this case is dispatcherProvider.default
      coroutineScope.launch {
        val expensive = repository.getSomethingExpensive()
        completable.complete(expensive)
      }
    }
  }

  init {

    // this launch will explicitly use the "default" dispatcher,
    // which is also the default for this CoroutineScope (which is confusing)
    coroutineScope.launchDefault {

      message.send(expensiveDeferred.await())

      // this send will not be evaluated until after the first one has completed
      // it will begin the query for "alsoExpensiveDeferred"
      message.send(alsoExpensiveDeferred.await())

    }
  }

  override fun onCleared() {
    super.onCleared()

    coroutineScope.cancel()
  }
}
