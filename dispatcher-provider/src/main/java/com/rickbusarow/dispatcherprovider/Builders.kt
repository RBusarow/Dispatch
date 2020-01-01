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

suspend fun <T> withDefault(
  context: CoroutineContext = EmptyCoroutineContext,
  block: suspend CoroutineScope.() -> T
): T {
  val newContext = coroutineContext.dispatcherProvider.default + context
  return withContext(newContext, block)
}

suspend fun <T> withIO(
  context: CoroutineContext = EmptyCoroutineContext,
  block: suspend CoroutineScope.() -> T
): T {
  val newContext = coroutineContext.dispatcherProvider.io + context
  return withContext(newContext, block)
}

suspend fun <T> withMain(
  context: CoroutineContext = EmptyCoroutineContext,
  block: suspend CoroutineScope.() -> T
): T {
  val newContext = coroutineContext.dispatcherProvider.main + context
  return withContext(newContext, block)
}

suspend fun <T> withMainImmediate(
  context: CoroutineContext = EmptyCoroutineContext, block: suspend CoroutineScope.() -> T
): T {
  val newContext = coroutineContext.dispatcherProvider.mainImmediate + context
  return withContext(newContext, block)
}

suspend fun <T> withUnconfined(
  context: CoroutineContext = EmptyCoroutineContext,
  block: suspend CoroutineScope.() -> T
): T {
  val newContext = coroutineContext.dispatcherProvider.unconfined + context
  return withContext(newContext, block)
}

fun CoroutineScope.launchDefault(
  context: CoroutineContext = EmptyCoroutineContext,
  start: CoroutineStart = CoroutineStart.DEFAULT,
  block: suspend CoroutineScope.() -> Unit
): Job = launch(coroutineContext.dispatcherProvider.default + context, start, block)

fun CoroutineScope.launchIO(
  context: CoroutineContext = EmptyCoroutineContext,
  start: CoroutineStart = CoroutineStart.DEFAULT,
  block: suspend CoroutineScope.() -> Unit
): Job = launch(coroutineContext.dispatcherProvider.io + context, start, block)

fun CoroutineScope.launchMain(
  context: CoroutineContext = EmptyCoroutineContext,
  start: CoroutineStart = CoroutineStart.DEFAULT,
  block: suspend CoroutineScope.() -> Unit
): Job = launch(coroutineContext.dispatcherProvider.main + context, start, block)

fun CoroutineScope.launchMainImmediate(
  context: CoroutineContext = EmptyCoroutineContext,
  start: CoroutineStart = CoroutineStart.DEFAULT,
  block: suspend CoroutineScope.() -> Unit
): Job = launch(coroutineContext.dispatcherProvider.mainImmediate + context, start, block)

fun CoroutineScope.launchUnconfined(
  context: CoroutineContext = EmptyCoroutineContext,
  start: CoroutineStart = CoroutineStart.DEFAULT,
  block: suspend CoroutineScope.() -> Unit
): Job = launch(coroutineContext.dispatcherProvider.unconfined + context, start, block)

fun <T> CoroutineScope.asyncDefault(
  context: CoroutineContext = EmptyCoroutineContext,
  start: CoroutineStart = CoroutineStart.DEFAULT,
  block: suspend CoroutineScope.() -> T
): Deferred<T> = async(coroutineContext.dispatcherProvider.default + context, start, block)

fun <T> CoroutineScope.asyncIO(
  context: CoroutineContext = EmptyCoroutineContext,
  start: CoroutineStart = CoroutineStart.DEFAULT,
  block: suspend CoroutineScope.() -> T
): Deferred<T> = async(coroutineContext.dispatcherProvider.io + context, start, block)

fun <T> CoroutineScope.asyncMain(
  context: CoroutineContext = EmptyCoroutineContext,
  start: CoroutineStart = CoroutineStart.DEFAULT,
  block: suspend CoroutineScope.() -> T
): Deferred<T> = async(coroutineContext.dispatcherProvider.main + context, start, block)

fun <T> CoroutineScope.asyncMainImmediate(
  context: CoroutineContext = EmptyCoroutineContext,
  start: CoroutineStart = CoroutineStart.DEFAULT,
  block: suspend CoroutineScope.() -> T
): Deferred<T> = async(coroutineContext.dispatcherProvider.mainImmediate + context, start, block)

fun <T> CoroutineScope.asyncUnconfined(
  context: CoroutineContext = EmptyCoroutineContext,
  start: CoroutineStart = CoroutineStart.DEFAULT,
  block: suspend CoroutineScope.() -> T
): Deferred<T> = async(coroutineContext.dispatcherProvider.unconfined + context, start, block)

