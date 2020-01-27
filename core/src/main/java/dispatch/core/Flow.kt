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

package dispatch.core

import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import kotlin.coroutines.*

/**
 * Extracts the [DispatcherProvider] from the `coroutineContext` of the **collector** coroutine,
 * then uses its **default** [CoroutineDispatcher] property to call `flowOn(theDispatcher)`,
 * and returns the result.
 *
 * @see flowOn
 */
@ExperimentalCoroutinesApi
public fun <T> Flow<T>.flowOnDefault(): Flow<T> = flow {
  flowOn(coroutineContext.dispatcherProvider.default)
    .collect { emit(it) }
}

/**
 * Extracts the [DispatcherProvider] from the `coroutineContext` of the **collector** coroutine,
 * then uses its **io** [CoroutineDispatcher] property to call `flowOn(theDispatcher)`,
 * and returns the result.
 *
 * @see flowOn
 */
@ExperimentalCoroutinesApi
public fun <T> Flow<T>.flowOnIO(): Flow<T> = flow {
  flowOn(coroutineContext.dispatcherProvider.io)
    .collect { emit(it) }
}

/**
 * Extracts the [DispatcherProvider] from the `coroutineContext` of the **collector** coroutine,
 * then uses its **main** [CoroutineDispatcher] property to call `flowOn(theDispatcher)`,
 * and returns the result.
 *
 * @see flowOn
 */
@ExperimentalCoroutinesApi
public fun <T> Flow<T>.flowOnMain(): Flow<T> = flow {
  flowOn(coroutineContext.dispatcherProvider.main)
    .collect { emit(it) }
}

/**
 * Extracts the [DispatcherProvider] from the `coroutineContext` of the **collector** coroutine,
 * then uses its **mainImmediate** [CoroutineDispatcher] property to call `flowOn(theDispatcher)`,
 * and returns the result.
 *
 * @see flowOn
 */
@ExperimentalCoroutinesApi
public fun <T> Flow<T>.flowOnMainImmediate(): Flow<T> = flow {
  flowOn(coroutineContext.dispatcherProvider.mainImmediate)
    .collect { emit(it) }
}

/**
 * Extracts the [DispatcherProvider] from the `coroutineContext` of the **collector** coroutine,
 * then uses its **unconfined** [CoroutineDispatcher] property to call `flowOn(theDispatcher)`,
 * and returns the result.
 *
 * @see flowOn
 */
@ExperimentalCoroutinesApi
public fun <T> Flow<T>.flowOnUnconfined(): Flow<T> = flow {
  flowOn(coroutineContext.dispatcherProvider.unconfined)
    .collect { emit(it) }
}
