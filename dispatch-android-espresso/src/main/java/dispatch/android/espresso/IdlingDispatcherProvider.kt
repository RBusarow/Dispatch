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

package dispatch.android.espresso

import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.IdlingResource
import dispatch.core.DefaultDispatcherProvider
import dispatch.core.DispatcherProvider
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainCoroutineDispatcher

/**
 * [IdlingResource] helper for coroutines. This [DispatcherProvider] implementation utilizes an
 * [IdlingDispatcher] for each [CoroutineDispatcher].
 *
 * @property default
 * @property io
 * @property main
 * @property mainImmediate
 * @property unconfined
 * @see IdlingResource
 * @see DispatcherProvider
 * @see IdlingDispatcher
 * @see CoroutineDispatcher
 */
public class IdlingDispatcherProvider(
  /**
   * [IdlingDispatcher] implementation of [DispatcherProvider.default], which typically corresponds
   * to the [Dispatchers.Default] [CoroutineDispatcher].
   */
  override val default: IdlingDispatcher,
  /**
   * [IdlingDispatcher] implementation of [DispatcherProvider.io], which typically corresponds to
   * the [Dispatchers.IO] [CoroutineDispatcher].
   */
  override val io: IdlingDispatcher,
  /**
   * [IdlingDispatcher] implementation of [DispatcherProvider.main], which typically corresponds to
   * the [Dispatchers.Main] [CoroutineDispatcher].
   */
  override val main: IdlingDispatcher,
  /**
   * [IdlingDispatcher] implementation of [DispatcherProvider.mainImmediate], which typically
   * corresponds to the [Dispatchers.Main.immediate][MainCoroutineDispatcher.immediate]
   * [CoroutineDispatcher].
   */
  override val mainImmediate: IdlingDispatcher,
  /**
   * [IdlingDispatcher] implementation of [DispatcherProvider.unconfined], which typically
   * corresponds to the [Dispatchers.Unconfined] [CoroutineDispatcher].
   */
  override val unconfined: IdlingDispatcher
) : DispatcherProvider {

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
  ): IdlingDispatcherProvider {

    return IdlingDispatcherProvider(
      default = default as IdlingDispatcher,
      io = io as IdlingDispatcher,
      main = main as IdlingDispatcher,
      mainImmediate = mainImmediate as IdlingDispatcher,
      unconfined = unconfined as IdlingDispatcher
    )
  }

  /**
   * @return a copy of this DispatcherProvider, retaining the properties of the original if they're
   *     not specified as arguments.
   * @sample dispatch.core.samples.DispatcherProviderCopySample.copySample
   */
  public fun copy(
    default: IdlingDispatcher,
    io: IdlingDispatcher,
    main: IdlingDispatcher,
    mainImmediate: IdlingDispatcher,
    unconfined: IdlingDispatcher
  ): IdlingDispatcherProvider {

    return IdlingDispatcherProvider(
      default = default,
      io = io,
      main = main,
      mainImmediate = mainImmediate,
      unconfined = unconfined
    )
  }
}

/**
 * [IdlingDispatcherProvider] factory function, which creates an instance using an existing
 * [DispatcherProvider].
 *
 * @param delegate *optional* Use this [DispatcherProvider] to create a single [IdlingDispatcher]
 *     which is used as all properties for the [IdlingDispatcherProvider]. Uses
 *     [DefaultDispatcherProvider.get] if no instance provided.
 * @see IdlingResource
 * @see DispatcherProvider
 * @see IdlingDispatcher
 * @see CoroutineDispatcher
 */
public fun IdlingDispatcherProvider(
  delegate: DispatcherProvider = DefaultDispatcherProvider.get()
): IdlingDispatcherProvider = IdlingDispatcherProvider(
  default = IdlingDispatcher(delegate.default),
  io = IdlingDispatcher(delegate.io),
  main = IdlingDispatcher(delegate.main),
  mainImmediate = IdlingDispatcher(delegate.mainImmediate),
  unconfined = IdlingDispatcher(delegate.unconfined)
)

/**
 * Register all [IdlingDispatcher] properties of the receiver [IdlingDispatcherProvider] with
 * Espresso's [IdlingRegistry].
 *
 * This should be done before executing a test.
 *
 * After test execution, be sure to call the companion
 * [IdlingDispatcherProvider.unregisterAllIdlingResources].
 *
 * @see IdlingDispatcherProvider.unregisterAllIdlingResources
 */
public fun IdlingDispatcherProvider.registerAllIdlingResources() {

  listOf(
    default,
    io,
    main,
    mainImmediate,
    unconfined
  ).forEach {
    IdlingRegistry.getInstance()
      .register(it.counter)
  }
}

/**
 * Unregister all [IdlingDispatcher] properties of the receiver [IdlingDispatcherProvider] with
 * Espresso's [IdlingRegistry].
 *
 * This should be done after executing a test.
 *
 * Before test execution, be sure to call the companion
 * [IdlingDispatcherProvider.registerAllIdlingResources] or this function will have no effect.
 *
 * @see IdlingDispatcherProvider.registerAllIdlingResources
 */
public fun IdlingDispatcherProvider.unregisterAllIdlingResources() {

  listOf(
    default,
    io,
    main,
    mainImmediate,
    unconfined
  ).forEach {
    IdlingRegistry.getInstance()
      .unregister(it.counter)
  }
}
