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
@file:Suppress("MaxLineLength")

package dispatch.android.espresso

import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.IdlingResource
import dispatch.core.DefaultCoroutineScope
import dispatch.core.DispatcherProvider
import dispatch.core.IOCoroutineScope
import dispatch.core.MainCoroutineScope
import dispatch.core.MainImmediateCoroutineScope
import dispatch.core.UnconfinedCoroutineScope
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob
import kotlin.coroutines.CoroutineContext

/**
 * Special [CoroutineScope] with a [DispatcherProvider] which is an [IdlingDispatcherProvider].
 *
 * @see IdlingDispatcherProvider
 * @see IdlingResource
 */
public interface IdlingCoroutineScope : CoroutineScope {

  /**
   * Any [IdlingCoroutineScope] has an [idlingDispatcherProvider] property which can be registered
   * in the [IdlingRegistry].
   */
  public val idlingDispatcherProvider: IdlingDispatcherProvider
}

/**
 * Marker interface for an [IdlingCoroutineScope] which indicates that its [CoroutineDispatcher] is
 * [DispatcherProvider.default]
 *
 * @see IdlingDispatcherProvider
 * @see IdlingResource
 * @see IdlingCoroutineScope
 */
public interface DefaultIdlingCoroutineScope :
  IdlingCoroutineScope,
  DefaultCoroutineScope

/**
 * Marker interface for an [IdlingCoroutineScope] which indicates that its [CoroutineDispatcher] is
 * [DispatcherProvider.io]
 *
 * @see IdlingDispatcherProvider
 * @see IdlingResource
 * @see IdlingCoroutineScope
 */
public interface IOIdlingCoroutineScope :
  IdlingCoroutineScope,
  IOCoroutineScope

/**
 * Marker interface for an [IdlingCoroutineScope] which indicates that its [CoroutineDispatcher] is
 * [DispatcherProvider.main]
 *
 * @see IdlingDispatcherProvider
 * @see IdlingResource
 * @see IdlingCoroutineScope
 */
public interface MainIdlingCoroutineScope :
  IdlingCoroutineScope,
  MainCoroutineScope

/**
 * Marker interface for an [IdlingCoroutineScope] which indicates that its [CoroutineDispatcher] is
 * [DispatcherProvider.mainImmediate]
 *
 * @see IdlingDispatcherProvider
 * @see IdlingResource
 * @see IdlingCoroutineScope
 */
public interface MainImmediateIdlingCoroutineScope :
  IdlingCoroutineScope,
  MainImmediateCoroutineScope

/**
 * Marker interface for an [IdlingCoroutineScope] which indicates that its [CoroutineDispatcher] is
 * [DispatcherProvider.unconfined]
 *
 * @see IdlingDispatcherProvider
 * @see IdlingResource
 * @see IdlingCoroutineScope
 */
public interface UnconfinedIdlingCoroutineScope :
  IdlingCoroutineScope,
  UnconfinedCoroutineScope

/**
 * Factory function for an [IdlingCoroutineScope].
 *
 * @param job *optional* The [Job] used in creation of the [CoroutineContext]. Uses [SupervisorJob]
 *     by default.
 * @param dispatcherProvider The [IdlingDispatcherProvider] used in creation of the
 *     [CoroutineContext]. Uses the default [IdlingDispatcherProvider] factory by default.
 * @sample dispatch.android.espresso.samples.IdlingCoroutineScope.createNoArg
 * @sample dispatch.android.espresso.samples.IdlingCoroutineScope.createCustom
 * @see IdlingDispatcherProvider
 * @see IdlingResource
 * @see IdlingCoroutineScope
 */
public fun IdlingCoroutineScope(
  job: Job = SupervisorJob(),
  dispatcherProvider: IdlingDispatcherProvider = IdlingDispatcherProvider()
): IdlingCoroutineScope = object : IdlingCoroutineScope {
  override val coroutineContext = job + dispatcherProvider.default + dispatcherProvider
  override val idlingDispatcherProvider: IdlingDispatcherProvider = dispatcherProvider
}

/**
 * Factory function for a [DefaultIdlingCoroutineScope].
 *
 * @param job *optional* The [Job] used in creation of the [CoroutineContext]. Uses [SupervisorJob]
 *     by default.
 * @param dispatcherProvider The [IdlingDispatcherProvider] used in creation of the
 *     [CoroutineContext]. Uses the default [IdlingDispatcherProvider] factory by default.
 * @sample dispatch.android.espresso.samples.DefaultIdlingCoroutineScope.createNoArgDefault
 * @sample dispatch.android.espresso.samples.DefaultIdlingCoroutineScope.createCustomDefault
 * @see IdlingDispatcherProvider
 * @see IdlingResource
 * @see IdlingCoroutineScope
 */
public fun DefaultIdlingCoroutineScope(
  job: Job = SupervisorJob(),
  dispatcherProvider: IdlingDispatcherProvider = IdlingDispatcherProvider()
): DefaultIdlingCoroutineScope = object : DefaultIdlingCoroutineScope {
  override val coroutineContext = job + dispatcherProvider.default + dispatcherProvider
  override val idlingDispatcherProvider: IdlingDispatcherProvider = dispatcherProvider
}

/**
 * Factory function for an [IOIdlingCoroutineScope].
 *
 * @param job *optional* The [Job] used in creation of the [CoroutineContext]. Uses [SupervisorJob]
 *     by default.
 * @param dispatcherProvider The [IdlingDispatcherProvider] used in creation of the
 *     [CoroutineContext]. Uses the default [IdlingDispatcherProvider] factory by default.
 * @sample dispatch.android.espresso.samples.IOIdlingCoroutineScope.createNoArgIO
 * @sample dispatch.android.espresso.samples.IOIdlingCoroutineScope.createCustomIO
 * @see IdlingDispatcherProvider
 * @see IdlingResource
 * @see IdlingCoroutineScope
 */
public fun IOIdlingCoroutineScope(
  job: Job = SupervisorJob(),
  dispatcherProvider: IdlingDispatcherProvider = IdlingDispatcherProvider()
): IOIdlingCoroutineScope = object : IOIdlingCoroutineScope {
  override val coroutineContext = job + dispatcherProvider.io + dispatcherProvider
  override val idlingDispatcherProvider: IdlingDispatcherProvider = dispatcherProvider
}

/**
 * Factory function for a [MainIdlingCoroutineScope].
 *
 * @param job *optional* The [Job] used in creation of the [CoroutineContext]. Uses [SupervisorJob]
 *     by default.
 * @param dispatcherProvider The [IdlingDispatcherProvider] used in creation of the
 *     [CoroutineContext]. Uses the default [IdlingDispatcherProvider] factory by default.
 * @sample dispatch.android.espresso.samples.MainIdlingCoroutineScope.createNoArgMain
 * @sample dispatch.android.espresso.samples.MainIdlingCoroutineScope.createCustomMain
 * @see IdlingDispatcherProvider
 * @see IdlingResource
 * @see IdlingCoroutineScope
 */
public fun MainIdlingCoroutineScope(
  job: Job = SupervisorJob(),
  dispatcherProvider: IdlingDispatcherProvider = IdlingDispatcherProvider()
): MainIdlingCoroutineScope = object : MainIdlingCoroutineScope {
  override val coroutineContext = job + dispatcherProvider.main + dispatcherProvider
  override val idlingDispatcherProvider: IdlingDispatcherProvider = dispatcherProvider
}

/**
 * Factory function for a [MainImmediateIdlingCoroutineScope].
 *
 * @param job *optional* The [Job] used in creation of the [CoroutineContext]. Uses [SupervisorJob]
 *     by default.
 * @param dispatcherProvider The [IdlingDispatcherProvider] used in creation of the
 *     [CoroutineContext]. Uses the default [IdlingDispatcherProvider] factory by default.
 * @sample dispatch.android.espresso.samples.MainImmediateIdlingCoroutineScope.createNoArg
 * @sample dispatch.android.espresso.samples.MainImmediateIdlingCoroutineScope.createCustom
 * @see IdlingDispatcherProvider
 * @see IdlingResource
 * @see IdlingCoroutineScope
 */
public fun MainImmediateIdlingCoroutineScope(
  job: Job = SupervisorJob(),
  dispatcherProvider: IdlingDispatcherProvider = IdlingDispatcherProvider()
): MainImmediateIdlingCoroutineScope = object : MainImmediateIdlingCoroutineScope {
  override val coroutineContext = job + dispatcherProvider.mainImmediate +
    dispatcherProvider
  override val idlingDispatcherProvider: IdlingDispatcherProvider = dispatcherProvider
}

/**
 * Factory function for an [UnconfinedIdlingCoroutineScope].
 *
 * @param job *optional* The [Job] used in creation of the [CoroutineContext]. Uses [SupervisorJob]
 *     by default.
 * @param dispatcherProvider The [IdlingDispatcherProvider] used in creation of the
 *     [CoroutineContext]. Uses the default [IdlingDispatcherProvider] factory by default.
 * @sample dispatch.android.espresso.samples.UnconfinedIdlingCoroutineScope.createNoArgUnconfined
 * @sample dispatch.android.espresso.samples.UnconfinedIdlingCoroutineScope.createCustomUnconfined
 * @see IdlingDispatcherProvider
 * @see IdlingResource
 * @see IdlingCoroutineScope
 */
public fun UnconfinedIdlingCoroutineScope(
  job: Job = SupervisorJob(),
  dispatcherProvider: IdlingDispatcherProvider = IdlingDispatcherProvider()
): UnconfinedIdlingCoroutineScope = object : UnconfinedIdlingCoroutineScope {
  override val coroutineContext = job + dispatcherProvider.unconfined + dispatcherProvider
  override val idlingDispatcherProvider: IdlingDispatcherProvider = dispatcherProvider
}
