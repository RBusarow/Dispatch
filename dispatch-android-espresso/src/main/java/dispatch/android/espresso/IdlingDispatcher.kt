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

package dispatch.android.espresso

import androidx.test.espresso.IdlingResource
import androidx.test.espresso.idling.CountingIdlingResource
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Runnable
import kotlin.coroutines.CoroutineContext

/**
 * [IdlingResource] helper for coroutines. This class simply wraps a delegate [CoroutineDispatcher]
 * and keeps a running count of all coroutines it creates, decrementing the count when they
 * complete.
 *
 * @property delegate The [CoroutineDispatcher] which will be used for actual dispatch.
 * @see IdlingResource
 * @see CoroutineDispatcher
 */
public class IdlingDispatcher(
  private val delegate: CoroutineDispatcher
) : CoroutineDispatcher() {

  /** The [CountingIdlingResource] which is responsible for Espresso functionality. */
  public val counter: CountingIdlingResource = CountingIdlingResource("IdlingResource for $this")

  /**
   * * true if the [counter]'s count is zero
   * * false if the [counter]'s count is non-zero
   *
   * @return
   */
  @Suppress("UNUSED")
  public fun isIdle(): Boolean = counter.isIdleNow

  /**
   * Counting implementation of the [dispatch][CoroutineDispatcher.dispatch] function.
   *
   * The count is incremented for every dispatch, and decremented for every completion, including
   * suspension.
   */
  override fun dispatch(context: CoroutineContext, block: Runnable) {

    val runnable = Runnable {
      counter.increment()
      try {
        block.run()
      } finally {
        counter.decrement()
      }
    }
    delegate.dispatch(context, runnable)
  }

  /** @suppress */
  override fun toString(): String = "CountingDispatcher delegating to $delegate"
}
