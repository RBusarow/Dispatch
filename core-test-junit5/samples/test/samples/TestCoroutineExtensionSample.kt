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
import io.kotest.matchers.types.*
import kotlinx.coroutines.*
import org.junit.jupiter.api.*
import org.junit.jupiter.api.extension.*

@ExperimentalCoroutinesApi
class TestCoroutineExtensionSample {

  @JvmField
  @RegisterExtension
  val extension = TestCoroutineExtension()

  @Test
  fun `extension should be a TestProvidedCoroutineScope`() = runBlocking<Unit> {

    extension.testScope.shouldBeInstanceOf<TestProvidedCoroutineScope>()
  }

  @Test
  fun `the testScope and dispatcher have normal test functionality`() = runBlocking {

    val subject = SomeClass(extension.testScope)

    val resultDeferred = subject.someFunction()

    extension.dispatcher.advanceUntilIdle()

    resultDeferred.await() shouldBe someValue
  }
}
