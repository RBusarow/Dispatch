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

package dispatch.test.internal

import dispatch.test.*
import kotlinx.coroutines.*

internal class TestJobImpl(private val parent: Job) : Job by parent,
                                                      TestJob {

  override val isActive: Boolean
    get() = children.any { it.isActive }

  override fun cancel(cause: CancellationException?) {
    cancelChildren(cause)
  }

  override fun cancelFinal(cause: CancellationException?) {
    parent.cancel(cause)
  }
}
