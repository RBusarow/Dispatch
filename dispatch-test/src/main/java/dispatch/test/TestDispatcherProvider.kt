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
import kotlinx.coroutines.test.setMain

/**
 * [DispatcherProvider] implementation for testing, where each property defaults to a
 * [StandardTestDispatcher].
 *
 * A default version will create a different `StandardTestDispatcher` for each property.
 *
 * @property default generally intended for cpu-bound tasks. Corresponds to the
 *     [Dispatchers.Default] property in a default implementation.
 * @property io generally intended for blocking I/O tasks. Corresponds to the [Dispatchers.IO]
 *     property in a default implementation.
 * @property unconfined corresponds to the [Dispatchers.Unconfined] property in a default
 *     implementation.
 * @param main
 * @param mainImmediate
 */
@ExperimentalCoroutinesApi
public class TestDispatcherProvider(
  override val default: CoroutineDispatcher = StandardTestDispatcher(),
  override val io: CoroutineDispatcher = StandardTestDispatcher(),
  main: CoroutineDispatcher = StandardTestDispatcher(),
  mainImmediate: CoroutineDispatcher = main,
  override val unconfined: CoroutineDispatcher = StandardTestDispatcher()
) : DispatcherProvider {

  /**
   * [CoroutineDispatcher] which is confined to the "main" thread.
   *
   * Corresponds to the [Dispatchers.Main] property in a default implementation.
   *
   * @see Dispatchers.Main
   */
  override val main: CoroutineDispatcher by setMain { main }

  /**
   * [CoroutineDispatcher] which is confined to the "main" thread with immediate dispatch.
   *
   * Corresponds to the
   * [Dispatchers.Main.immediate][kotlinx.coroutines.MainCoroutineDispatcher.immediate]
   * property in a default implementation.
   *
   * @see [MainCoroutineDispatcher.immediate]
   */
  override val mainImmediate: CoroutineDispatcher by setMain { mainImmediate }

  private val _setMain = lazy { Dispatchers.setMain(main) }
  private inline fun <T> setMain(crossinline factory: () -> T) = lazy {
    _setMain.value
    factory.invoke()
  }

  /**
   * @return a copy of this DispatcherProvider, retaining the properties of the original if they're
   *     not specified as arguments.
   * @sample dispatch.core.samples.DispatcherProviderCopySample.copySample
   */
  override fun copy(
    default: CoroutineDispatcher,
    io: CoroutineDispatcher,
    main: CoroutineDispatcher,
    mainImmediate: CoroutineDispatcher,
    unconfined: CoroutineDispatcher
  ): TestDispatcherProvider {
    return TestDispatcherProvider(
      default = default,
      io = io,
      main = main,
      mainImmediate = mainImmediate,
      unconfined = unconfined
    )
  }

  /** @suppress */
  override fun toString(): String {
    return """${this::class.java.simpleName}:
      |default       -> $default
      |io            -> $io
      |main          -> $main
      |mainImmediate -> $mainImmediate
      |unconfined    -> $unconfined
    """.replaceIndentByMargin(" ".repeat(this::class.java.simpleName.length + 2))
  }
}

/**
 * Convenience factory function for [TestDispatcherProvider], creating an implementation where all
 * properties point to the same underlying [TestDispatcher].
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
