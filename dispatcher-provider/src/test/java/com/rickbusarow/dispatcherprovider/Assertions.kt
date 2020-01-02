/*
 * Copyright (C) 2019-2020 Rick Busarow
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

package com.rickbusarow.dispatcherprovider

import kotlinx.coroutines.*
import org.amshove.kluent.*
import kotlin.coroutines.*

fun Job.shouldBeSupervisorJob() {

  this::class.simpleName shouldEqual "SupervisorJobImpl"
}

infix fun Job?.shouldBeOrChildOf(other: Job?) {

  if (this == null) return

  if (this === other) {
    return
  } else {
    other?.let { parameter ->
      this shouldBeIn parameter.children.toList()
    } ?: this shouldEqual other
  }
}

infix fun CoroutineContext.shouldEqualFolded(other: CoroutineContext) {
  get(Job) shouldBeOrChildOf other[Job]
  get(ContinuationInterceptor) shouldEqual other[ContinuationInterceptor]
  get(CoroutineExceptionHandler) shouldEqual other[CoroutineExceptionHandler]
  get(CoroutineName) shouldEqual other[CoroutineName]
  get(DispatcherProvider) shouldEqual other[DispatcherProvider]
}

inline fun <reified T> Any?.shouldBeTypeOf() {
  if (this !is T) throw AssertionError(
    "Expected $this to be an instance or subclass of ${T::class.simpleName}"
  )
}
