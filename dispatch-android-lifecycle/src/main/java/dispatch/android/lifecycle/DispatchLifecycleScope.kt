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

package dispatch.android.lifecycle

import androidx.lifecycle.Lifecycle
import dispatch.android.lifecycle.internal.DispatchLifecycleScopeBinding
import dispatch.android.lifecycle.internal.launchOn
import dispatch.core.DefaultDispatcherProvider
import dispatch.core.DispatcherProvider
import dispatch.core.MainImmediateCoroutineScope
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext

/**
 * Default implementation of a [CoroutineContext] as seen in a `MainImmediateCoroutineScope`.
 *
 * @see MainImmediateCoroutineScope
 */
public fun MainImmediateContext(): CoroutineContext {
  val dispatcherProvider = DispatcherProvider()

  return SupervisorJob() + dispatcherProvider + dispatcherProvider.mainImmediate
}

/**
 * **Deprecated** in favor of [DispatchLifecycleScope] in order to avoid name collisions with
 * Androidx.
 *
 * @see DispatchLifecycleScope
 */
@Deprecated(
  "Use DispatchLifecycleScope to avoid collisions with the Androidx library",
  replaceWith = ReplaceWith("DispatchLifecycleScope")
)
@Suppress("UNUSED")
public typealias LifecycleCoroutineScope = DispatchLifecycleScope

/**
 * [MainImmediateCoroutineScope] which is tied to a [Lifecycle].
 *
 * The [CoroutineScope] provides lifecycle-aware [launch] functions which will automatically start
 * upon reaching their associated [Lifecycle.Event], then automatically cancel upon the lifecycle
 * dropping below that state. Reaching that state again will start a new [Job].
 *
 * This `CoroutineScope`'s [Job] will be cancelled automatically as soon as the `lifecycle` reaches
 * [DESTROYED][Lifecycle.State.DESTROYED].
 * 1. If a [DispatcherProvider] element isn't present, [DefaultDispatcherProvider.get] will be
 *    added.
 * 2. If a [Job] element isn't present, a [SupervisorJob] will be added.
 * 3. If the [ContinuationInterceptor][kotlin.coroutines.ContinuationInterceptor] does not match the
 *    one referenced by the [possibly new] [DispatcherProvider.mainImmediate] property, it will be
 *    updated to match.
 *
 * @property lifecycle the lifecycle to which this [MainImmediateCoroutineScope] is linked.
 * @param coroutineContext *optional* the source CoroutineContext which will be converted to a
 *     [MainImmediateCoroutineScope]. Its [Elements][CoroutineContext.Element] will be re-used.
 * @sample dispatch.android.lifecycle.samples.DispatchLifecycleScope.defaultSample
 * @sample dispatch.android.lifecycle.samples.DispatchLifecycleScope.scopeFromContext
 */
public open class DispatchLifecycleScope(
  public val lifecycle: Lifecycle,
  coroutineContext: CoroutineContext = MainImmediateContext()
) : MainImmediateCoroutineScope by MainImmediateCoroutineScope(coroutineContext) {

  init {
    DispatchLifecycleScopeBinding(lifecycle, coroutineContext).bind()
  }

  /**
   * Lifecycle-aware function for launching a coroutine any time the [Lifecycle.State] is at least
   * [Lifecycle.State.CREATED].
   *
   * `block` is executed using the receiver [CoroutineScope]'s [Job] as a parent, but always
   * executes using [Dispatchers.Main] as its [CoroutineDispatcher].
   *
   * Execution of `block` is cancelled when the receiver [CoroutineScope] is cancelled, or when
   * [lifecycle]'s [Lifecycle.State] drops below [Lifecycle.State.CREATED].
   *
   * @param context *optional* - additional to [CoroutineScope.coroutineContext] context of the
   *     coroutine.
   * @param minimumStatePolicy *optional* - the way this [Job] will behave when passing below the
   *     minimum state or re-entering. Uses [MinimumStatePolicy.RESTART_EVERY] by default. Note that
   *     for a normal Lifecycle, there is no returning from below a
   *     [CREATED][Lifecycle.State.CREATED] state, so the [minimumStatePolicy][MinimumStatePolicy]
   *     is largely irrelevant.
   * @param block the action to be performed
   * @sample dispatch.android.lifecycle.samples.DispatchLifecycleScope.launchOnCreateOnce
   * @sample dispatch.android.lifecycle.samples.DispatchLifecycleScope.onCreateRestarting
   */
  public fun launchOnCreate(
    context: CoroutineContext = EmptyCoroutineContext,
    minimumStatePolicy: MinimumStatePolicy = MinimumStatePolicy.RESTART_EVERY,
    block: suspend CoroutineScope.() -> Unit
  ): Job = launchOn(context, Lifecycle.State.CREATED, minimumStatePolicy, block)

  /**
   * Lifecycle-aware function for launching a coroutine any time the [Lifecycle.State] is at least
   * [Lifecycle.State.STARTED].
   *
   * `block` is executed using the receiver [CoroutineScope]'s [Job] as a parent, but always
   * executes using [Dispatchers.Main] as its [CoroutineDispatcher].
   *
   * Execution of `block` is cancelled when the receiver [CoroutineScope] is cancelled, or when
   * [lifecycle]'s [Lifecycle.State] drops below [Lifecycle.State.STARTED].
   *
   * @param context *optional* - additional to [CoroutineScope.coroutineContext] context of the
   *     coroutine.
   * @param minimumStatePolicy *optional* - the way this [Job] will behave when passing below the
   *     minimum state or re-entering. Uses [MinimumStatePolicy.RESTART_EVERY] by default.
   * @param block the action to be performed
   * @sample dispatch.android.lifecycle.samples.DispatchLifecycleScope.launchOnStartOnce
   * @sample dispatch.android.lifecycle.samples.DispatchLifecycleScope.launchOnStartRestarting
   */
  public fun launchOnStart(
    context: CoroutineContext = EmptyCoroutineContext,
    minimumStatePolicy: MinimumStatePolicy = MinimumStatePolicy.RESTART_EVERY,
    block: suspend CoroutineScope.() -> Unit
  ): Job = launchOn(context, Lifecycle.State.STARTED, minimumStatePolicy, block)

  /**
   * Lifecycle-aware function for launching a coroutine any time the [Lifecycle.State] is at least
   * [Lifecycle.State.RESUMED].
   *
   * `block` is executed using the receiver [CoroutineScope]'s [Job] as a parent, but always
   * executes using [Dispatchers.Main] as its [CoroutineDispatcher].
   *
   * Execution of `block` is cancelled when the receiver [CoroutineScope] is cancelled, or when
   * [lifecycle]'s [Lifecycle.State] drops below [Lifecycle.State.RESUMED].
   *
   * @param context *optional* - additional to [CoroutineScope.coroutineContext] context of the
   *     coroutine.
   * @param minimumStatePolicy *optional* - the way this [Job] will behave when passing below the
   *     minimum state or re-entering. Uses [MinimumStatePolicy.RESTART_EVERY] by default.
   * @param block the action to be performed
   * @sample dispatch.android.lifecycle.samples.DispatchLifecycleScope.launchOnResumeOnce
   * @sample dispatch.android.lifecycle.samples.DispatchLifecycleScope.launchOnResumeRestarting
   */
  public fun launchOnResume(
    context: CoroutineContext = EmptyCoroutineContext,
    minimumStatePolicy: MinimumStatePolicy = MinimumStatePolicy.RESTART_EVERY,
    block: suspend CoroutineScope.() -> Unit
  ): Job = launchOn(context, Lifecycle.State.RESUMED, minimumStatePolicy, block)

  /**
   * Describes the way a particular [Job] will behave if the [lifecycle] passes below the minimum
   * state before said [Job] has completed.
   */
  public enum class MinimumStatePolicy {
    /**
     * When using `CANCEL`, a coroutine will be created the first time the [lifecycle] meets the
     * minimum state, and cancelled upon dropping below it. Subsequently meeting the minimum state
     * again will not resume the coroutine or create a new one.
     *
     * @see launchOnCreate
     * @see launchOnStart
     * @see launchOnResume
     */
    CANCEL,

    /**
     * When using `RESTART_EVERY`, a coroutine will be created every time the [lifecycle] meets the
     * minimum state, and will be cancelled upon dropping below it. Subsequently meeting the minimum
     * state again will create a new coroutine.
     *
     * Note that **state is not retained** when dropping below a minimum state.
     *
     * @see launchOnCreate
     * @see launchOnStart
     * @see launchOnResume
     */
    RESTART_EVERY
  }

  /** @suppress */
  public companion object {

    /**
     * [MainImmediateCoroutineScope] which is tied to a [Lifecycle].
     *
     * The [CoroutineScope] provides lifecycle-aware [launch] functions which will automatically
     * start upon reaching their associated [Lifecycle.Event], then automatically cancel upon the
     * lifecycle dropping below that state. Reaching that state again will start a new [Job].
     *
     * If this `CoroutineScope` has a [Job], it will be cancelled automatically as soon as the
     * `lifecycle` reaches [DESTROYED][Lifecycle.State.DESTROYED].
     * 1. If a [DispatcherProvider] element isn't present, [DefaultDispatcherProvider.get] will be
     *    added.
     * 2. If a [Job] element isn't present, a [SupervisorJob] will be added.
     * 3. If the [ContinuationInterceptor][kotlin.coroutines.ContinuationInterceptor] does not match
     *    the one referenced by the [possibly new] [DispatcherProvider.mainImmediate] property, it
     *    will be updated to match.
     *
     * @param lifecycle the lifecycle to which this [MainImmediateCoroutineScope] is linked.
     * @param coroutineScope the source CoroutineScope which will be converted to a
     *     [MainImmediateCoroutineScope]. Its [CoroutineContext][kotlin.coroutines.CoroutineContext]
     *     will be re-used, except:
     * @sample dispatch.android.lifecycle.samples.DispatchLifecycleScope.scopeFromScope
     */
    public operator fun invoke(
      lifecycle: Lifecycle,
      coroutineScope: CoroutineScope
    ): DispatchLifecycleScope = DispatchLifecycleScope(
      lifecycle, coroutineScope.coroutineContext
    )
  }
}
