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

package samples

import dispatch.core.test.*
import io.kotest.matchers.*
import kotlinx.coroutines.*
import org.junit.jupiter.api.*

@ExperimentalCoroutinesApi
class CoroutineTestSample : CoroutineTest {

  override lateinit var testScope: TestProvidedCoroutineScope

  lateinit var someClass: SomeClass

  @BeforeEach
  fun beforeEach() {
    someClass = SomeClass(testScope)
  }

  @Test
  fun someSample() = runBlocking {

    someClass.someFunction()
      .await() shouldBe someValue

  }

}

val someValue = true

class SomeClass(val coroutineScope: CoroutineScope) {

  fun someFunction() = coroutineScope.async { true }
}

