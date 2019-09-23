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

package com.rickbusarow.dispatcherprovider

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlin.coroutines.CoroutineContext

val CoroutineScope.defaultDispatcher: CoroutineDispatcher
  get() = dispatcherProvider.default

val CoroutineScope.ioDispatcher: CoroutineDispatcher
  get() = dispatcherProvider.io

val CoroutineScope.mainDispatcher: CoroutineDispatcher
  get() = dispatcherProvider.main

val CoroutineScope.mainImmediateDispatcher: CoroutineDispatcher
  get() = dispatcherProvider.mainImmediate

val CoroutineScope.unconfinedDispatcher: CoroutineDispatcher
  get() = dispatcherProvider.unconfined

val CoroutineScope.dispatcherProvider: DispatcherProvider
  get() = coroutineContext.dispatcherProvider

val CoroutineContext.dispatcherProvider: DispatcherProvider
  get() = get(DispatcherProvider) ?: DefaultDispatcherProvider()
