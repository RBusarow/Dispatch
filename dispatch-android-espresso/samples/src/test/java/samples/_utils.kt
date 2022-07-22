/*
 * Copyright (C) 2022 Rick Busarow
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

@file:Suppress("EXPERIMENTAL_API_USAGE")

package samples

import dispatch.core.*
import io.kotest.matchers.*
import kotlinx.coroutines.*
import org.junit.*

typealias Sample = Test

infix fun Any?.shouldPrint(
  expected: String
) = toString() shouldBe expected

fun dispatcherName() = " @coroutine.*".toRegex()
  .replace(Thread.currentThread().name, "")

val blocking = StandardTestDispatcher(name = "runBlocking thread")

val someDispatcherProvider = object : DispatcherProvider {
  override val default = StandardTestDispatcher(name = "default")
  override val io = StandardTestDispatcher(name = "io")
  override val main = StandardTestDispatcher(name = "main")
  override val mainImmediate = StandardTestDispatcher(name = "main immediate")
  override val unconfined = StandardTestDispatcher(name = "unconfined")
} + blocking
