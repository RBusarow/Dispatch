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

import kotlinx.coroutines.*
import kotlinx.coroutines.channels.BroadcastChannel
import kotlinx.coroutines.channels.ReceiveChannel

fun <T> ReceiveChannel<T>.observe(
  coroutineScope: CoroutineScope, observer: suspend CoroutineScope.
    (t: T) -> Unit
): Job =
  coroutineScope.launch {
    for (t in this@observe) {
      observer(t)
    }
  }

fun <T> ReceiveChannel<T>.distinct(
  coroutineScope: CoroutineScope,
  observer: suspend CoroutineScope.(t: T) -> Unit
): Job =
  coroutineScope.launch {
    var last: T? = null
    for (t in this@distinct) {
      if (last != t) {
        last = t
        observer(t)
      }
    }
  }

fun <T> BroadcastChannel<T>.observe(
  coroutineScope: CoroutineScope,
  observer: suspend CoroutineScope.(t: T) -> Unit
): Job =
  openSubscription().observe(coroutineScope, observer)

fun <T> BroadcastChannel<T>.distinct(
  coroutineScope: CoroutineScope, observer: suspend
  CoroutineScope.(t: T) -> Unit
): Job =
  openSubscription().distinct(coroutineScope, observer)
