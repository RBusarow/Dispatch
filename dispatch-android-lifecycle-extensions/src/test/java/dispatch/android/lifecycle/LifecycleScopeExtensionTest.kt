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

package dispatch.android.lifecycle

import androidx.lifecycle.*
import dispatch.android.lifecycle.internal.*
import dispatch.internal.test.*
import dispatch.internal.test.android.*
import io.kotest.core.spec.*
import io.kotest.core.spec.style.*
import io.kotest.matchers.*
import kotlinx.coroutines.*
import kotlinx.coroutines.test.*
import java.util.concurrent.*

@ObsoleteCoroutinesApi
@ExperimentalCoroutinesApi
internal class LifecycleScopeExtensionTest : BehaviorSpec() {

  override fun isolationMode() = IsolationMode.InstancePerLeaf

  init {

    val storeMap: MutableMap<Lifecycle, LifecycleCoroutineScope> =
      LifecycleCoroutineScopeStore.getPrivateObjectFieldByName("map")

    val main = newSingleThreadContext("main")

    beforeTest { Dispatchers.setMain(main) }

    afterTest { Dispatchers.resetMain() }

    given("lifecycle is already destroyed") {

      val lifecycleOwner = FakeLifecycleOwner(initialState = Lifecycle.State.DESTROYED)

      `when`("scope is created") {

        val scope = lifecycleOwner.lifecycleScope

        then("scope and job should already be cancelled") {

          scope.isActive shouldBe false
          scope.coroutineContext[Job]!!.isActive shouldBe false
        }

        then("scope should not be cached") {

          storeMap[lifecycleOwner.lifecycle] shouldBe null
        }

        // This is a change detector.
        // This isn't actually desired, but the precondition should be impossible.
        then("additional scopes should be unique") {

          lifecycleOwner.lifecycleScope shouldNotBe scope
        }
      }
    }

    include(activeTests(Lifecycle.State.INITIALIZED, storeMap))
    include(activeTests(Lifecycle.State.CREATED, storeMap))
    include(activeTests(Lifecycle.State.STARTED, storeMap))
    include(activeTests(Lifecycle.State.RESUMED, storeMap))
  }
}

fun activeTests(
  initialState: Lifecycle.State,
  storeMap: MutableMap<Lifecycle, LifecycleCoroutineScope>
) = behaviorSpec {

  given("initial lifecycle of $initialState") {

    val lifecycleOwner = FakeLifecycleOwner(initialState = initialState)

    `when`("scope is created") {

      val scope = lifecycleOwner.lifecycleScope

      then("scope and job should be active") {

        scope.isActive shouldBe true
      }

      then("scope should be cached") {

        storeMap[lifecycleOwner.lifecycle] shouldBe scope
      }

      then("repeated access should return the same scope") {

        lifecycleOwner.lifecycleScope shouldBe scope
      }

      and("lifecycle passes to destroyed") {

        // special case for Initialized state
        if (lifecycleOwner.lifecycle.currentState == Lifecycle.State.INITIALIZED) {
          lifecycleOwner.create()
        }

        lifecycleOwner.destroy()

        then("scope should be cancelled") {

          scope.isActive shouldBe false
        }

        then("scope should be removed from the cache") {

          storeMap[lifecycleOwner.lifecycle] shouldBe null
        }
      }
    }

    `when`("multiple threads access lifecycleScope at once") {

      then("all threads should get the same instance") {

        val hugeExecutor = ThreadPoolExecutor(
          50, 50, 5000, TimeUnit.MILLISECONDS, LinkedBlockingQueue()
        )

        val dispatcher = hugeExecutor.asCoroutineDispatcher()

        val lock = CompletableDeferred<Unit>()

        val all = List(50) {
          async(dispatcher) {
            lock.await()
            lifecycleOwner.lifecycleScope
          }
        }

        yield()
        lock.complete(Unit)

        all.awaitAll().distinct() shouldBe listOf(lifecycleOwner.lifecycleScope)
      }
    }
  }
}
