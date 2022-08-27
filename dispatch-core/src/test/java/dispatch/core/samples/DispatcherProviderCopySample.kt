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

package dispatch.core.samples

import dispatch.core.dispatcherProvider
import dispatch.internal.test.Sample5
import io.kotest.matchers.shouldBe
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.Runnable
import kotlinx.coroutines.currentCoroutineContext
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import kotlinx.coroutines.withContext
import kotlin.coroutines.CoroutineContext

@Suppress("HardCodedDispatcher")
@OptIn(
  ExperimentalStdlibApi::class,
  ExperimentalCoroutinesApi::class
)
class DispatcherProviderCopySample {

  @Sample5
  fun testCopySample() = runBlocking {

    Dispatchers.setMain(coroutineContext[CoroutineDispatcher.Key]!!)

    copySample()

    Dispatchers.resetMain()
  }

  suspend fun copySample() {
    val originalDispatcherProvider = currentCoroutineContext().dispatcherProvider
    originalDispatcherProvider.main shouldBe Dispatchers.Main

    val newMain = MyCustomBlockingQueueDispatcher()
    // create a copy of the existing DispatcherProvider, except the new main dispatcher
    val bleDispatchers = originalDispatcherProvider.copy(main = newMain)

    withContext(bleDispatchers) {
      // any coroutine downstream of this `withContext { }` will use the redefined main dispatcher
      dispatcherProvider.main shouldBe newMain
    }
  }

  private class MyCustomBlockingQueueDispatcher : CoroutineDispatcher() {
    override fun dispatch(context: CoroutineContext, block: Runnable) {
      block.run()
    }
  }
}
