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

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext
import kotlin.coroutines.coroutineContext

@ExperimentalCoroutinesApi
suspend inline fun <T> Flow<T>.flowOnDefault(context: CoroutineContext = EmptyCoroutineContext)
    : Flow<T> = flowOn(coroutineContext.dispatcherProvider.default + context)

@ExperimentalCoroutinesApi
suspend inline fun <T> Flow<T>.flowOnIO(context: CoroutineContext = EmptyCoroutineContext)
    : Flow<T> = flowOn(coroutineContext.dispatcherProvider.io + context)

@ExperimentalCoroutinesApi
suspend inline fun <T> Flow<T>.flowOnMain(context: CoroutineContext = EmptyCoroutineContext)
    : Flow<T> = flowOn(coroutineContext.dispatcherProvider.main + context)

@ExperimentalCoroutinesApi
suspend inline fun <T> Flow<T>.flowOnMainImmediate(context: CoroutineContext = EmptyCoroutineContext)
    : Flow<T> = flowOn(coroutineContext.dispatcherProvider.mainImmediate + context)

@ExperimentalCoroutinesApi
suspend inline fun <T> Flow<T>.flowOnUnconfined(context: CoroutineContext = EmptyCoroutineContext)
    : Flow<T> = flowOn(coroutineContext.dispatcherProvider.unconfined + context)


