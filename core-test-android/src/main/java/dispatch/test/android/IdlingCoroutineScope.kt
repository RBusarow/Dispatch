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

import dispatch.core.*
import kotlinx.coroutines.*

interface IdlingCoroutineScope : CoroutineScope {

  val countingDispatcherProvider: IdlingDispatcherProvider
}

interface DefaultIdlingCoroutineScope : IdlingCoroutineScope, DefaultCoroutineScope
interface IOIdlingCoroutineScope : IdlingCoroutineScope, IOCoroutineScope
interface MainIdlingCoroutineScope : IdlingCoroutineScope, MainCoroutineScope
interface MainImmediateIdlingCoroutineScope : IdlingCoroutineScope, MainImmediateCoroutineScope
interface UnconfinedIdlingCoroutineScope : IdlingCoroutineScope, UnconfinedCoroutineScope

fun IdlingCoroutineScope(
  job: Job = SupervisorJob(),
  dispatcherProvider: IdlingDispatcherProvider = IdlingDispatcherProvider()
): IdlingCoroutineScope = object : IdlingCoroutineScope {
  override val coroutineContext = job + dispatcherProvider.default + dispatcherProvider
  override val countingDispatcherProvider: IdlingDispatcherProvider = dispatcherProvider
}

fun DefaultIdlingCoroutineScope(
  job: Job = SupervisorJob(),
  dispatcherProvider: IdlingDispatcherProvider = IdlingDispatcherProvider()
): DefaultIdlingCoroutineScope = object : DefaultIdlingCoroutineScope {
  override val coroutineContext = job + dispatcherProvider.default + dispatcherProvider
  override val countingDispatcherProvider: IdlingDispatcherProvider = dispatcherProvider
}

fun IOIdlingCoroutineScope(
  job: Job = SupervisorJob(),
  dispatcherProvider: IdlingDispatcherProvider = IdlingDispatcherProvider()
): IOIdlingCoroutineScope = object : IOIdlingCoroutineScope {
  override val coroutineContext = job + dispatcherProvider.io + dispatcherProvider
  override val countingDispatcherProvider: IdlingDispatcherProvider = dispatcherProvider
}

fun MainIdlingCoroutineScope(
  job: Job = SupervisorJob(),
  dispatcherProvider: IdlingDispatcherProvider = IdlingDispatcherProvider()
): MainIdlingCoroutineScope = object : MainIdlingCoroutineScope {
  override val coroutineContext = job + dispatcherProvider.main + dispatcherProvider
  override val countingDispatcherProvider: IdlingDispatcherProvider = dispatcherProvider
}

fun MainImmediateIdlingCoroutineScope(
  job: Job = SupervisorJob(),
  dispatcherProvider: IdlingDispatcherProvider = IdlingDispatcherProvider()
): MainImmediateIdlingCoroutineScope = object : MainImmediateIdlingCoroutineScope {
  override val coroutineContext = job + dispatcherProvider.mainImmediate +
      dispatcherProvider
  override val countingDispatcherProvider: IdlingDispatcherProvider = dispatcherProvider
}

fun UnconfinedIdlingCoroutineScope(
  job: Job = SupervisorJob(),
  dispatcherProvider: IdlingDispatcherProvider = IdlingDispatcherProvider()
): UnconfinedIdlingCoroutineScope = object : UnconfinedIdlingCoroutineScope {
  override val coroutineContext = job + dispatcherProvider.unconfined + dispatcherProvider
  override val countingDispatcherProvider: IdlingDispatcherProvider = dispatcherProvider
}
