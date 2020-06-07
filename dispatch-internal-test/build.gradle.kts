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

plugins {
  id(Plugins.atomicFu)
  id(Plugins.javaLibrary)
  id(Plugins.kotlin)
}

dependencies {
  implementation(Libs.Kotlin.stdlib)

  implementation(Libs.Kotlinx.Coroutines.core)

  implementation(Libs.JUnit.jUnit5)
  implementation(Libs.JUnit.jUnit5Vintage)
  implementation(Libs.Kotest.assertions)
  implementation(Libs.Kotest.properties)
  implementation(Libs.Kotest.runner)

  implementation(Libs.Kotlin.test)
  implementation(Libs.Kotlin.testCommon)

  implementation(Libs.Kotlinx.Coroutines.test)

  api(project(":dispatch-core"))

}
