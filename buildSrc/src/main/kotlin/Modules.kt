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

object Modules {

  private val internalRegex = ".*internal.*".toRegex()
  private val sampleRegex = ".*sample.*".toRegex()

  val allPaths = listOf(
    ":android-espresso",
    ":android-espresso:samples",
    ":android-lifecycle",
    ":android-lifecycle:samples",
    ":android-lifecycle-extensions",
    ":android-lifecycle-extensions:samples",
    ":android-viewmodel",
    ":android-viewmodel:samples",
    ":core",
    ":core-test",
    ":core-test:samples",
    ":core-test-junit4",
    ":core-test-junit4:samples",
    ":core-test-junit5",
    ":core-test-junit5:samples",
    ":core-test:samples",
    ":core:samples",
    ":internal-test",
    ":sample"
  )
  val allInternalPaths = allPaths.filter { it.matches(internalRegex) }
  val allProductionPaths = allPaths.filter { !it.matches(internalRegex, sampleRegex) }
  val allSamplesPaths = allPaths.filter { it.matches(sampleRegex) }

  val all = allPaths.map { it.removePrefix(":") }
  val allInternal = all.filter { it.matches(internalRegex) }
  val allProduction = all.filter { !it.matches(internalRegex, sampleRegex) }
  val allSamples = all.filter { it.matches(sampleRegex) }
}
