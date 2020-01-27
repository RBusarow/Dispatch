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

package dispatch.test.android

import org.junit.rules.*
import org.junit.runner.*

class IdlingDispatcherProviderRule(
  private val factory: () -> IdlingDispatcherProvider
) : TestWatcher() {

  lateinit var dispatcherProvider: IdlingDispatcherProvider

  override fun starting(description: Description?) {
    super.starting(description)

    dispatcherProvider = factory.invoke()

    dispatcherProvider.registerAllIdlingResources()
  }

  override fun finished(description: Description?) {
    super.finished(description)

    dispatcherProvider.unregisterAllIdlingResources()
  }
}
