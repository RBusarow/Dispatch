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

package dispatch.android.lifecycle.internal

import android.os.Build
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import dispatch.android.lifecycle.DispatchLifecycleScope
import dispatch.android.lifecycle.LifecycleScopeFactory
import dispatch.core.launchMainImmediate
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.ConcurrentMap

@Suppress("MagicNumber")
internal object DispatchLifecycleScopeStore : LifecycleEventObserver {

  // ConcurrentHashMap can miss "put___" operations on API 21/22 https://issuetracker.google.com/issues/37042460
  private val map: MutableMap<Lifecycle, DispatchLifecycleScope> =
    if (Build.VERSION.SDK_INT < 23) {
      mutableMapOf()
    } else {
      ConcurrentHashMap()
    }

  override fun onStateChanged(source: LifecycleOwner, event: Lifecycle.Event) {
    if (source.lifecycle.currentState <= Lifecycle.State.DESTROYED) {
      map.remove(source.lifecycle)
    }
  }

  fun get(lifecycle: Lifecycle): DispatchLifecycleScope {

    if (lifecycle.currentState <= Lifecycle.State.DESTROYED) {
      return LifecycleScopeFactory.create(lifecycle)
    }

    return when {
      Build.VERSION.SDK_INT >= 24 -> {
        map.computeIfAbsent(lifecycle) { bindLifecycle(lifecycle) }
      }

      Build.VERSION.SDK_INT == 23 -> {
        /*
        `getOrPut` by itself isn't atomic.  It is guaranteed to only ever return one instance
         for a given key, but the lambda argument may be invoked unnecessarily.
         */
        (map as ConcurrentMap).atomicGetOrPut(lifecycle) { bindLifecycle(lifecycle) }
      }

      else -> {
        synchronized(map) {
          map.getOrPut(lifecycle) { bindLifecycle(lifecycle) }
        }
      }
    }
  }

  private fun bindLifecycle(lifecycle: Lifecycle): DispatchLifecycleScope {

    val scope = LifecycleScopeFactory.create(lifecycle)

    scope.launchMainImmediate {
      if (lifecycle.currentState >= Lifecycle.State.INITIALIZED) {
        lifecycle.addObserver(this@DispatchLifecycleScopeStore)
      }
    }

    return scope
  }
}
