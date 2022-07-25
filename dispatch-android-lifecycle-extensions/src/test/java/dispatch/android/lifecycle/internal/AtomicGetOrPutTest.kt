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

import androidx.lifecycle.Lifecycle
import dispatch.android.lifecycle.DispatchLifecycleScope
import dispatch.core.MainImmediateCoroutineScope
import dispatch.internal.test.android.FakeLifecycleOwner
import dispatch.internal.test.android.InstantTaskExecutorExtension
import dispatch.test.TestDispatcherProvider
import hermit.test.junit.HermitJUnit5
import io.kotest.matchers.shouldBe
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.Job
import kotlinx.coroutines.ObsoleteCoroutinesApi
import kotlinx.coroutines.asCoroutineDispatcher
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.delay
import kotlinx.coroutines.newSingleThreadContext
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import kotlinx.coroutines.yield
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.LinkedBlockingQueue
import java.util.concurrent.ThreadPoolExecutor
import java.util.concurrent.TimeUnit

@ExtendWith(InstantTaskExecutorExtension::class)
@ObsoleteCoroutinesApi
@ExperimentalCoroutinesApi
internal class AtomicGetOrPutTest : HermitJUnit5() {

  @Nested
  inner class `API level 23` {

    @Nested
    inner class `multiple threads access lifecycleScope at once` {
      @Test
      fun `all threads should get the same instance`() = runBlocking {

        val main = newSingleThreadContext("main")

        val storeMap = ConcurrentHashMap<Lifecycle, DispatchLifecycleScope>()

        val lifecycleOwner = FakeLifecycleOwner(Lifecycle.State.INITIALIZED)

        val androidLifecycle = lifecycleOwner.lifecycle

        val hugeExecutor = ThreadPoolExecutor(
          200, 200, 5000, TimeUnit.MILLISECONDS, LinkedBlockingQueue()
        ).asCoroutineDispatcher()

        val lock = CompletableDeferred<Unit>()

        val all = List(200) {
          async(hugeExecutor) {

            lock.await()

            storeMap.atomicGetOrPut(androidLifecycle) {
              val scope = DispatchLifecycleScope(
                lifecycle = androidLifecycle,
                coroutineScope = MainImmediateCoroutineScope(Job(), TestDispatcherProvider(main))
              )
              withContext(main) {

                androidLifecycle.addObserver(DispatchLifecycleScopeStore)
              }
              scope
            }
          }
        }

        yield()
        lock.complete(Unit)

        all.awaitAll().distinct().size shouldBe 1

        delay(100)

        androidLifecycle.observerCount shouldBe 2
      }
    }
  }
}
