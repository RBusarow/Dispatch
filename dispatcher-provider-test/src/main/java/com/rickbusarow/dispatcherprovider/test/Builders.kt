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

package com.rickbusarow.dispatcherprovider.test

import com.rickbusarow.dispatcherprovider.*
import kotlinx.coroutines.*
import kotlinx.coroutines.test.*
import kotlin.coroutines.*

@ExperimentalCoroutinesApi
fun runBlockingProvided(
  context: CoroutineContext = EmptyCoroutineContext,
  dispatcherProvider: DispatcherProvider = TestDispatcherProvider(TestCoroutineDispatcher()),
  block: suspend CoroutineScope.() -> Unit
): Unit = runBlocking(context = dispatcherProvider + context, block = block)

@ExperimentalCoroutinesApi
fun runBlockingTestProvided(
  context: CoroutineContext = EmptyCoroutineContext,
  dispatcherProvider: DispatcherProvider = TestDispatcherProvider(TestCoroutineDispatcher()),
  testBody: suspend TestCoroutineScope.() -> Unit
): Unit = runBlockingTest(context = dispatcherProvider + context, testBody = testBody)
