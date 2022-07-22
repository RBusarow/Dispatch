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

package dispatch.internal.test

import dispatch.core.DispatcherProvider
import io.kotest.matchers.shouldBe
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestDispatcher
import org.junit.Test
import java.io.PrintStream
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext
import kotlinx.knit.test.captureOutput as knitCaptureOutput

public typealias Sample = Test

public infix fun Any?.shouldPrint(expected: String) {
  toString() shouldBe expected
}

public fun Any?.shouldPrint(vararg expected: String) {
  toString() shouldBe expected
}

public fun dispatcherName(): String = " @coroutine.*".toRegex()
  .replace(Thread.currentThread().name, "")

@OptIn(ExperimentalCoroutinesApi::class)
public val blocking: TestDispatcher = StandardTestDispatcher(name = "runBlocking thread")

@OptIn(ExperimentalCoroutinesApi::class)
public val someDispatcherProvider: CoroutineContext = object : DispatcherProvider {
  override val default = StandardTestDispatcher(name = "default")
  override val io = StandardTestDispatcher(name = "io")
  override val main = StandardTestDispatcher(name = "main")
  override val mainImmediate = StandardTestDispatcher(name = "main immediate")
  override val unconfined = StandardTestDispatcher(name = "unconfined")
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
