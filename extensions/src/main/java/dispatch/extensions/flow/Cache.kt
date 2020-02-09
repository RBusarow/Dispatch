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

@file:JvmMultifileClass
@file:JvmName("FlowKt")

package dispatch.extensions.flow

import dispatch.extensions.flow.internal.*
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*

/**
 * A "cached" [Flow] which will record the last [history] collected values.
 *
 * When a collector begins collecting after values have already been recorded,
 * those values will be collected *before* values from the receiver [Flow] are collected.
 *
 * example:
 * ```Kotlin
 * val ints = flowOf(1, 2, 3, 4).cache(2)   // cache the last 2 values
 *
 * ints.take(4).collect {  }             // 4 values are emitted, but also recorded.  The last 2 remain.
 *
 * ints.collect {  }                     // collects [3, 4, 1, 2, 3, 4]
 * ```
 *
 * Throws [IllegalArgumentException] if size parameter is not greater than 0
 *
 * @param history the number of items to keep in the [Flow]'s history -- must be greater than 0
 */
@ExperimentalCoroutinesApi
@FlowPreview
fun <T> Flow<T>.cache(history: Int): Flow<T> = asCachedFlow(history)
