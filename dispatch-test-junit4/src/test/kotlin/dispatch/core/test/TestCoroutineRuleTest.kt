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

package dispatch.core.test

import dispatch.core.DispatcherProvider
import dispatch.internal.test.ExpectedFailureRule
import dispatch.test.TestCoroutineRule
import dispatch.test.TestProvidedCoroutineScope
import io.kotest.matchers.shouldBe
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Rule
import org.junit.Test
import kotlin.coroutines.ContinuationInterceptor

@ExperimentalCoroutinesApi
class TestCoroutineRuleTest {

  val customScope = TestProvidedCoroutineScope()

  @JvmField @Rule
  val customFactoryRule = TestCoroutineRule { customScope }

  @JvmField @Rule
  val defaultRule = TestCoroutineRule()

  @JvmField @Rule
  val failureRule = ExpectedFailureRule()

  @Test
  fun `a no-arg rule should use a default TestProvidedCoroutineScope`() {
    val dispatcherProvider = defaultRule.coroutineContext[DispatcherProvider]!!

    val allDispatchers = setOf(
      dispatcherProvider.default,
      dispatcherProvider.io,
      dispatcherProvider.main,
      dispatcherProvider.mainImmediate,
      dispatcherProvider.unconfined,
      defaultRule.dispatcher,
      defaultRule.coroutineContext[ContinuationInterceptor]!!
    )

    allDispatchers.size shouldBe 1
  }

  @Test
  fun `a custom factory rule should use use the custom factory`() {
    val context = customFactoryRule.coroutineContext

    context shouldBe customScope.coroutineContext
  }
}
