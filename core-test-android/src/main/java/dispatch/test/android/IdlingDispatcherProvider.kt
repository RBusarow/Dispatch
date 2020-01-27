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

package dispatch.test.android

import androidx.test.espresso.*
import dispatch.core.*

class IdlingDispatcherProvider(
  override val default: IdlingDispatcher,
  override val io: IdlingDispatcher,
  override val main: IdlingDispatcher,
  override val mainImmediate: IdlingDispatcher,
  override val unconfined: IdlingDispatcher
) : DispatcherProvider

fun IdlingDispatcherProvider(
  delegate: DispatcherProvider = DefaultDispatcherProvider()
): IdlingDispatcherProvider = IdlingDispatcherProvider(
  default = IdlingDispatcher(delegate.default),
  io = IdlingDispatcher(delegate.io),
  main = IdlingDispatcher(delegate.main),
  mainImmediate = IdlingDispatcher(delegate.mainImmediate),
  unconfined = IdlingDispatcher(delegate.unconfined)
)

fun IdlingDispatcherProvider.registerAllIdlingResources() {

  listOf(
    default,
    io,
    main,
    mainImmediate,
    unconfined
  )
    .forEach {
      IdlingRegistry.getInstance()
        .register(it.counter)
    }

}

fun IdlingDispatcherProvider.unregisterAllIdlingResources() {

  listOf(
    default,
    io,
    main,
    mainImmediate,
    unconfined
  )
    .forEach {
      IdlingRegistry.getInstance()
        .unregister(it.counter)
    }

}
