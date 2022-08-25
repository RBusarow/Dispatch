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

import dispatch.test.CanaryTest.MyTestEnvironment
import kotlinx.coroutines.CoroutineName
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.advanceUntilIdle
import org.junit.jupiter.api.Test

@OptIn(ExperimentalCoroutinesApi::class)
class CanaryTest : HermitCoroutines<MyTestEnvironment>(::MyTestEnvironment) {

  class MyTestEnvironment(testScope: TestScope) {
    val name = testScope.coroutineContext[CoroutineName] ?: CoroutineName("hi")
  }

  @Test
  fun `canary things`() = test {

    name
    advanceUntilIdle()
  }
}
