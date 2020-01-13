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

package com.rickbusarow.dispatcherprovider

import kotlinx.coroutines.*
import kotlin.coroutines.*

/**
 * Interface corresponding to the different [CoroutineDispatcher]'s offered by [Dispatchers].
 *
 * Implements the [CoroutineContext.Element] interface
 * so that it can be embedded into the [CoroutineContext] map,
 * meaning that a `CoroutineContext` can be composed with a set of pre-set dispatchers,
 * thereby eliminating the need for singleton references or dependency injecting this interface.
 */
public interface DispatcherProvider : CoroutineContext.Element {

  override val key: CoroutineContext.Key<*> get() = Key

  val default: CoroutineDispatcher
  val io: CoroutineDispatcher
  val main: CoroutineDispatcher
  val mainImmediate: CoroutineDispatcher
  val unconfined: CoroutineDispatcher

  companion object Key : CoroutineContext.Key<DispatcherProvider>
}

public fun DispatcherProvider(): DispatcherProvider = DefaultDispatcherProvider()

/**
 * Default implementation of [DispatcherProvider] which simply delegates to the corresponding
 * properties in the [Dispatchers] singleton.
 *
 * This should be suitable for most production code.
 */
public class DefaultDispatcherProvider : DispatcherProvider {

  override val default: CoroutineDispatcher = Dispatchers.Default
  override val io: CoroutineDispatcher = Dispatchers.IO
  override val main: CoroutineDispatcher get() = Dispatchers.Main
  override val mainImmediate: CoroutineDispatcher get() = Dispatchers.Main.immediate
  override val unconfined: CoroutineDispatcher = Dispatchers.Unconfined
}
