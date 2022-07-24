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

plugins {
  id("kotlinx-atomicfu")
  javaLibrary
}

dependencies {

  api(libs.hermit.core)
  api(libs.hermit.jUnit5)
  api(libs.junit.api)
  api(libs.junit.junit4)
  api(libs.junit.jupiter)
  api(libs.junit.vintage)
  api(libs.kotest.assertions)
  api(libs.kotest.assertionsShared)
  api(libs.kotest.common.jvm)
  api(libs.kotest.runner)
  api(libs.kotlin.reflect)
  api(libs.kotlin.test.common)
  api(libs.kotlin.test.core)
  api(libs.kotlinx.coroutines.core)
  api(libs.kotlinx.coroutines.jvm)
  api(libs.kotlinx.coroutines.test)
  api(libs.kotlinx.knit.test)

  api(projects.dispatchCore)
}
