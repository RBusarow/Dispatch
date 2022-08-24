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

/** Indicates that a test **function** is expected to fail with the given exception type. */
@Target(AnnotationTarget.FUNCTION)
public annotation class Fails(
  /**
   * The fully qualified name string of the expected Throwable.
   *
   * NB This can't just be a KClass<*> since `UncompletedCoroutinesError` is now internal in
   * `kotlinx-coroutines-test`.
   */
  val expectedExceptionFqName: String
)
