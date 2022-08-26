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

package dispatch.internal.test

import dispatch.core.DispatcherProvider
import io.kotest.assertions.assertSoftly
import io.kotest.assertions.shouldFail
import io.kotest.matchers.collections.shouldContain
import io.kotest.matchers.shouldBe
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineName
import kotlinx.coroutines.Job
import kotlin.coroutines.ContinuationInterceptor
import kotlin.coroutines.CoroutineContext

@TrimAssertion
@Suppress("NOTHING_TO_INLINE")
public inline fun Job.shouldBeSupervisorJob() {

  this::class.simpleName shouldBe "SupervisorJobImpl"
}

@TrimAssertion
@Suppress("NOTHING_TO_INLINE")
public inline infix fun Job?.shouldBeOrChildOf(other: Job?) {

  if (this == null) return

  if (this === other) {
    return
  } else {
    other?.let { parameter ->
      parameter.children.toList() shouldContain this
    } ?: (this shouldBe other)
  }
}

@TrimAssertion
@Suppress("NOTHING_TO_INLINE")
public inline infix fun CoroutineContext.shouldEqualFolded(other: CoroutineContext) {
  trimAssertion {
    assertSoftly {
      get(Job) shouldBeOrChildOf other[Job]
      get(ContinuationInterceptor) shouldBe other[ContinuationInterceptor]
      get(CoroutineExceptionHandler) shouldBe other[CoroutineExceptionHandler]
      get(CoroutineName) shouldBe other[CoroutineName]
      get(DispatcherProvider) shouldBe other[DispatcherProvider]
    }
  }
}

@TrimAssertion
public infix fun CoroutineContext.shouldNotEqualFolded(other: CoroutineContext) {
  trimAssertion {
    shouldFail {
      get(Job) shouldBeOrChildOf other[Job]
      get(ContinuationInterceptor) shouldBe other[ContinuationInterceptor]
      get(CoroutineExceptionHandler) shouldBe other[CoroutineExceptionHandler]
      get(CoroutineName) shouldBe other[CoroutineName]
      get(DispatcherProvider) shouldBe other[DispatcherProvider]
    }
  }
}

public annotation class TrimAssertion

public inline fun <T> trimAssertion(testAction: () -> T): T {
  return try {
    testAction()
  } catch (error: Throwable) {
    throw AssertionError(error.message, error)
      .apply {
        stackTrace = error.stackTrace
          .asSequence()
          .filterNot { it.isSuppressed() }
          .filterNot { it.className.startsWith("io.kotest") }
          .filterNot { it.className.startsWith("kotlin.coroutines") }
          .filterNot { it.className.startsWith("kotlinx.coroutines") }
          .toList()
          .toTypedArray()
      }
  }
}

@TrimAssertion
@PublishedApi
internal fun StackTraceElement.isSuppressed(): Boolean {

  val method = Class.forName(className)
    .methods
    .singleOrNull { it.name == methodName }
    ?: return false

  return method.isAnnotationPresent(TrimAssertion::class.java)
}
