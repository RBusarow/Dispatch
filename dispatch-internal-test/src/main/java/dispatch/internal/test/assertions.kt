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
import io.kotest.assertions.shouldFail
import io.kotest.matchers.collections.shouldContain
import io.kotest.matchers.shouldBe
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineName
import kotlinx.coroutines.Job
import kotlin.coroutines.ContinuationInterceptor
import kotlin.coroutines.CoroutineContext

public fun Job.shouldBeSupervisorJob() {

  this::class.simpleName shouldBe "SupervisorJobImpl"
}

public infix fun Job?.shouldBeOrChildOf(other: Job?) {

  if (this == null) return

  if (this === other) {
    return
  } else {
    other?.let { parameter ->
      parameter.children.toList() shouldContain this
    } ?: (this shouldBe null)
  }
}

public infix fun CoroutineContext.shouldEqualFolded(other: CoroutineContext) {
  get(Job) shouldBeOrChildOf other[Job]
  get(ContinuationInterceptor) shouldBe other[ContinuationInterceptor]
  get(CoroutineName) shouldBe other[CoroutineName]
  get(DispatcherProvider) shouldBe other[DispatcherProvider]
}

public infix fun CoroutineContext.shouldNotEqualFolded(other: CoroutineContext) {
  shouldFail {
    get(Job) shouldBeOrChildOf other[Job]
    get(ContinuationInterceptor) shouldBe other[ContinuationInterceptor]
    get(CoroutineExceptionHandler) shouldBe other[CoroutineExceptionHandler]
    get(CoroutineName) shouldBe other[CoroutineName]
    get(DispatcherProvider) shouldBe other[DispatcherProvider]
  }
}
