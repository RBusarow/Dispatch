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

package dispatch.core.test

import dispatch.core.*
import kotlinx.coroutines.*
import kotlinx.coroutines.test.*

/**
 * [DispatcherProvider] implementation for testing, where each property is a [CoroutineDispatcher].
 *
 * A default version will create a different `TestCoroutineDispatcher` for each property.
 */
@ExperimentalCoroutinesApi
class TestDispatcherProvider(
  override val default: CoroutineDispatcher = TestCoroutineDispatcher(),
  override val io: CoroutineDispatcher = TestCoroutineDispatcher(),
  override val main: CoroutineDispatcher = TestCoroutineDispatcher(),
  override val mainImmediate: CoroutineDispatcher = TestCoroutineDispatcher(),
  override val unconfined: CoroutineDispatcher = TestCoroutineDispatcher()
) : DispatcherProvider {
  override fun toString(): String {
    return """${this::class.java.simpleName}: default       -> $default
      |io            -> $io
      |main          -> $main
      |mainImmediate -> $mainImmediate
      |unconfined    -> $unconfined
    """.replaceIndentByMargin(" ".repeat(this::class.java.simpleName.length + 2))
  }
}

/**
 * Convenience factory function for [TestDispatcherProvider], creating an implementation
 * where all properties point to the same underlying [TestCoroutineDispatcher].
 */
@ExperimentalCoroutinesApi
fun TestDispatcherProvider(
  dispatcher: CoroutineDispatcher
): TestDispatcherProvider = TestDispatcherProvider(
    default = dispatcher,
    io = dispatcher,
    main = dispatcher,
    mainImmediate = dispatcher,
    unconfined = dispatcher
  )

/**
 * "Basic" [TestDispatcherProvider] which mimics production behavior,
 * without the automatic time control of [TestCoroutineDispatcher]
 * and without the need for [Dispatchers.setMain][kotlinx.coroutines.test]
 *
 * The `default`, `io`, and `unconfined` properties just delegate to their counterparts in [Dispatchers].
 *
 * The `main` and `mainImmediate` properties share a single dispatcher and thread
 * as they do with the `Dispatchers.setMain(...)` implementation from `kotlinx-coroutines-test`.
 */
@ExperimentalCoroutinesApi
fun TestBasicDispatcherProvider(): TestDispatcherProvider {

  @Suppress("EXPERIMENTAL_API_USAGE")
  val mainThread = newSingleThreadContext("main thread proxy")

  return TestDispatcherProvider(
    default = Dispatchers.Default,
    io = Dispatchers.IO,
    main = mainThread,
    mainImmediate = mainThread,
    unconfined = Dispatchers.Unconfined
  )

}
