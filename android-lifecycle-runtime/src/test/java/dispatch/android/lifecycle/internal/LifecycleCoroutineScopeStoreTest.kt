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

import androidx.lifecycle.*
import dispatch.android.lifecycle.LifecycleCoroutineScope
import dispatch.core.test.*
import io.kotlintest.*
import io.kotlintest.matchers.types.*
import kotlinx.coroutines.*
import org.junit.jupiter.api.*

private typealias Store = LifecycleCoroutineScopeStore

@ExperimentalCoroutinesApi
internal class LifecycleCoroutineScopeStoreTest : CoroutineTest {

  override lateinit var testScope: TestProvidedCoroutineScope

  lateinit var lifecycleOwner: LifecycleOwner
  lateinit var lifecycle: LifecycleRegistry

  lateinit var scope: LifecycleCoroutineScope

  @BeforeEach
  fun beforeEach() {

    lifecycleOwner = LifecycleOwner { lifecycle }
    lifecycle = LifecycleRegistry(lifecycleOwner)

    scope =
      LifecycleCoroutineScope(lifecycle, testScope)

    lifecycle.currentState shouldBe Lifecycle.State.INITIALIZED
  }

  @Test
  fun `calling get repeatedly for the same lifecycle should return the same scope`() = runBlocking {

    val fresh = Store.get(lifecycle)

    val cached = Store.get(lifecycle)

    fresh shouldBeSameInstanceAs cached
  }

  @Test
  fun `calling get for a unique lifecycle should return a unique scope`() = runBlocking {

    val fresh = Store.get(lifecycle)

    val newLifecycle = LifecycleRegistry(lifecycleOwner)

    lifecycle shouldNotBe newLifecycle

    val alsoFresh = Store.get(newLifecycle)

    fresh shouldNotBe alsoFresh
  }

  @Test
  fun `scopes should be returned cancelled if their lifecycle state is destroyed`() = runBlocking {

    lifecycle.handleLifecycleEvent(Lifecycle.Event.ON_RESUME)
    lifecycle.handleLifecycleEvent(Lifecycle.Event.ON_DESTROY)

    val fresh = Store.get(lifecycle)

    fresh.isActive shouldBe false
  }

  @Test
  fun `provided scopes should be cancelled when their lifecycle is destroyed`() = runBlocking {

    val fresh = Store.get(lifecycle)

    lifecycle.handleLifecycleEvent(Lifecycle.Event.ON_RESUME)
    lifecycle.handleLifecycleEvent(Lifecycle.Event.ON_DESTROY)

    fresh.isActive shouldBe false
  }

}
