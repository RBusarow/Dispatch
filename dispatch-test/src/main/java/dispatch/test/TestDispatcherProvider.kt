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

package dispatch.test

import dispatch.core.DispatcherProvider
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.MainCoroutineDispatcher
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestDispatcher

/**
 * [DispatcherProvider] implementation for testing, where each property defaults to a
 * [StandardTestDispatcher].
 *
 * A default version will create a different `StandardTestDispatcher` for each property.
 */
@ExperimentalCoroutinesApi
public class TestDispatcherProvider(
  /**
   * [TestDispatcher] generally intended for cpu-bound tasks.
   *
   * Corresponds to the [Dispatchers.Default] property in a default implementation.
   *
   * @see Dispatchers.Default
   */
  override val default: TestDispatcher = StandardTestDispatcher(),
  /**
   * [TestDispatcher] generally intended for blocking I/O tasks.
   *
   * Corresponds to the [Dispatchers.IO] property in a default implementation.
   *
   * @see Dispatchers.IO
   */
  override val io: TestDispatcher = StandardTestDispatcher(),
  /**
   * [TestDispatcher] which is confined to the "main" thread.
   *
   * Corresponds to the [Dispatchers.Main] property in a default implementation.
   *
   * @see Dispatchers.Main
   */
  override val main: TestDispatcher = StandardTestDispatcher(),
  /**
   * [TestDispatcher] which is confined to the "main" thread with immediate dispatch.
   *
   * Corresponds to the
   * [Dispatchers.Main.immediate][kotlinx.coroutines.MainCoroutineDispatcher.immediate] property in
   * a default implementation.
   *
   * @see [MainCoroutineDispatcher.immediate]
   */
  override val mainImmediate: TestDispatcher = StandardTestDispatcher(),
  /**
   * Corresponds to the [Dispatchers.Unconfined] property in a default implementation.
   *
   * @see [Dispatchers.Unconfined]
   */
  override val unconfined: TestDispatcher = StandardTestDispatcher()
) : DispatcherProvider {
  /** @suppress */
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
 * Convenience factory function for [TestDispatcherProvider], creating an implementation where all
 * properties point to the same underlying [CoroutineDispatcher].
 */
@ExperimentalCoroutinesApi
public fun TestDispatcherProvider(
  dispatcher: TestDispatcher = StandardTestDispatcher()
): TestDispatcherProvider = TestDispatcherProvider(
  default = dispatcher,
  io = dispatcher,
  main = dispatcher,
  mainImmediate = dispatcher,
  unconfined = dispatcher
)

// /**
//  * "Basic" [TestDispatcherProvider] which mimics production behavior, without
//  * the automatic time control of [TestDispatcher] and without the need for
//  * [Dispatchers.setMain][kotlinx.coroutines.test.setMain]
//  *
//  * The `default`, `io`, and `unconfined` properties just delegate to their counterparts in
//  * [Dispatchers].
//  *
//  * The `main` and `mainImmediate` properties share a single dispatcher and thread as they do with
//  * the `Dispatchers.setMain(...)` implementation from `kotlinx-coroutines-test`.
//  */
// @DelicateCoroutinesApi
// @ExperimentalCoroutinesApi
// @Suppress("HardCodedDispatcher")
// public fun TestBasicDispatcherProvider(
//   scheduler: TestCoroutineScheduler? = null,
//   name: String? = null
// ): TestDispatcherProvider {
//   val mainThread = StandardTestDispatcher(scheduler = scheduler, name = name)
//
//   return TestDispatcherProvider(
//     default = Dispatchers.Default,
//     io = Dispatchers.IO,
//     main = mainThread,
//     mainImmediate = mainThread,
//     unconfined = Dispatchers.Unconfined
//   )
// }
//
// /**
//  * "Basic" [TestDispatcherProvider] which mimics production behavior, without
//  * the automatic time control of [TestDispatcher] and without the need for
//  * [Dispatchers.setMain][kotlinx.coroutines.test.setMain]
//  *
//  * The `default`, `io`, and `unconfined` properties just delegate to their counterparts in
//  * [Dispatchers].
//  *
//  * The `main` and `mainImmediate` properties share a single dispatcher and thread as they do with
//  * the `Dispatchers.setMain(...)` implementation from `kotlinx-coroutines-test`.
//  */
// @DelicateCoroutinesApi
// @ExperimentalCoroutinesApi
// @Suppress("HardCodedDispatcher")
// public fun TestBasicDispatcherProvider(
//   mainThread: TestDispatcher = StandardTestDispatcher()
// ): TestDispatcherProvider {
//   return TestDispatcherProvider(
//     default = Dispatchers.Default,
//     io = Dispatchers.IO,
//     main = mainThread,
//     mainImmediate = mainThread,
//     unconfined = Dispatchers.Unconfined
//   )
// }
