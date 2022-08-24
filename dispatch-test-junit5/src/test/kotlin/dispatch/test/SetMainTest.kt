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

package dispatch.test

import dispatch.core.dispatcherProvider
import io.kotest.matchers.shouldBe
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.setMain
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.RegisterExtension

@Suppress("HardCodedDispatcher")
@ExperimentalCoroutinesApi
class SetMainTest {

  @RegisterExtension @JvmField
  val ext = CoroutineTestExtension()

  @Test
  fun `extension's internal scope's dispatcher should be set as Main by default`() {

    val beforeSet = Dispatchers.Main

    Dispatchers.setMain(ext.scope.dispatcherProvider.main)

    beforeSet shouldBe Dispatchers.Main
  }

  @Test
  fun `function injected scope's dispatcher should be set as Main by default`(
    scope: TestScope
  ) {

    val beforeSet = Dispatchers.Main

    Dispatchers.setMain(scope.dispatcherProvider.main)

    beforeSet shouldBe Dispatchers.Main
  }

  @Nested
  inner class `constructor injection`(
    val scope: TestScope
  ) {

    @Test
    fun `constructor injected scope's dispatcher should be set as Main by default`() {

      val beforeSet = Dispatchers.Main

      Dispatchers.setMain(scope.dispatcherProvider.main)

      beforeSet shouldBe Dispatchers.Main
    }
  }
}
