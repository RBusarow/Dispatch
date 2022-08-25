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

import io.kotest.matchers.shouldNotBe
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestScope
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.RegisterExtension

@ExperimentalCoroutinesApi
class CoroutineTestExtensionInjectionTest {

  @JvmField @RegisterExtension
  val extension = CoroutineTestExtension()

  @Nested
  inner class `nested classes` {

    @Test
    fun `function arguments should be automatically injected`(scope: TestScope) {

      scope shouldNotBe null
    }
  }

  @Nested
  inner class `lifecycle callback functions` {

    var beforeAllInjectedScope: TestScope? = null
    var beforeEachInjectedScope: TestScope? = null

    @BeforeAll
    fun beforeAll(scope: TestScope) {
      beforeAllInjectedScope = scope
    }

    @BeforeEach
    fun beforeEach(scope: TestScope) {
      beforeEachInjectedScope = scope
    }

    @Test
    fun `beforeAll should be injected before a function is called`() {

      beforeAllInjectedScope shouldNotBe null
    }

    @Test
    fun `beforeEach should be injected before a function is called`() {

      beforeEachInjectedScope shouldNotBe null
    }
  }
}
