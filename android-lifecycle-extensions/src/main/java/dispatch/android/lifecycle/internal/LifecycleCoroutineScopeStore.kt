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

package dispatch.android.lifecycle.internal

import android.os.Build
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import dispatch.android.lifecycle.LifecycleCoroutineScope
import dispatch.android.lifecycle.LifecycleScopeFactory
import dispatch.core.MainImmediateCoroutineScope
import dispatch.core.launchMainImmediate
import kotlinx.coroutines.cancel
import java.util.*
import java.util.concurrent.ConcurrentHashMap
import kotlin.collections.set

internal object LifecycleCoroutineScopeStore {

  // ConcurrentHashMap can miss "put___" operations on API 21/22 https://issuetracker.google.com/issues/37042460
  @Suppress("MagicNumber")
  private val map = if (Build.VERSION.SDK_INT < 23) {
    Collections.synchronizedMap<Lifecycle, LifecycleCoroutineScope>(mutableMapOf<Lifecycle, LifecycleCoroutineScope>())
  } else {
    ConcurrentHashMap<Lifecycle, LifecycleCoroutineScope>()
  }

  fun get(lifecycle: Lifecycle): LifecycleCoroutineScope {

    return map[lifecycle] ?: bindLifecycle(lifecycle, LifecycleScopeFactory.create())
  }

  private fun bindLifecycle(
    lifecycle: Lifecycle, coroutineScope: MainImmediateCoroutineScope
  ): LifecycleCoroutineScope {

    val scope = LifecycleCoroutineScope(lifecycle, coroutineScope)

    map[lifecycle] = scope

    if (!lifecycle.currentState.isAtLeast(Lifecycle.State.INITIALIZED)) {
      scope.coroutineContext.cancel()
      return scope
    }

    val observer = object : LifecycleEventObserver {

      override fun onStateChanged(source: LifecycleOwner, event: Lifecycle.Event) {

        if (!lifecycle.currentState.isAtLeast(Lifecycle.State.INITIALIZED)) {

          scope.coroutineContext.cancel()
          lifecycle.removeObserver(this)
        }
      }
    }

    scope.launchMainImmediate {

      if (lifecycle.currentState.isAtLeast(Lifecycle.State.INITIALIZED)) {
        lifecycle.addObserver(observer)
      } else {
        scope.coroutineContext.cancel()
      }
    }

    return scope
  }

}

