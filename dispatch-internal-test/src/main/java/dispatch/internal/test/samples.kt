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

package dispatch.internal.test

import dispatch.core.DispatcherProvider
import io.kotest.matchers.shouldBe
import kotlinx.coroutines.ExecutorCoroutineDispatcher
import kotlinx.coroutines.ObsoleteCoroutinesApi
import kotlinx.coroutines.newSingleThreadContext
import java.io.PrintStream
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext
import kotlinx.knit.test.captureOutput as knitCaptureOutput
import org.junit.Test as TestJunit4
import org.junit.jupiter.api.Test as TestJunit5

public typealias Sample4 = TestJunit4
public typealias Sample5 = TestJunit5

public infix fun Any?.shouldPrint(expected: String) {
  toString() shouldBe expected
}

public fun Any?.shouldPrint(vararg expected: String) {
  toString() shouldBe expected
}

public fun dispatcherName(): String = " @coroutine.*".toRegex()
  .replace(Thread.currentThread().name, "")

@OptIn(ObsoleteCoroutinesApi::class)
public val blocking: ExecutorCoroutineDispatcher = newSingleThreadContext("runBlocking thread")

@OptIn(ObsoleteCoroutinesApi::class)
public val someDispatcherProvider: CoroutineContext = object : DispatcherProvider {
  override val default = newSingleThreadContext("default")
  override val io = newSingleThreadContext("io")
  override val main = newSingleThreadContext("main")
  override val mainImmediate = newSingleThreadContext("main immediate")
  override val unconfined = newSingleThreadContext("unconfined")
} + blocking

public fun MyCustomElement(): CoroutineContext {
  return EmptyCoroutineContext
}

public interface Application {
  public fun onCreate()
}

public fun captureOutput(
  block: (out: PrintStream) -> Unit
): List<String> = knitCaptureOutput(name = "", main = block)
