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
  androidLibrary
  published
}

android {
  namespace = "dispatch.android.lifecycle.extensions"
}

dependencies {

  api(libs.androidx.fragment.core)
  api(libs.androidx.lifecycle.common)
  api(libs.kotlinx.coroutines.core)
  api(libs.kotlinx.coroutines.jvm)

  api(projects.dispatchAndroidLifecycle)

  implementation(libs.kotlinx.coroutines.android)

  implementation(projects.dispatchCore)

  testImplementation(libs.androidx.arch.test.core)
  testImplementation(libs.androidx.lifecycle.runtime)
  testImplementation(libs.androidx.test.espresso.core)
  testImplementation(libs.androidx.test.runner)
  testImplementation(libs.hermit.jUnit5)
  testImplementation(libs.junit.jupiter)
  testImplementation(libs.kotest.assertions)
  testImplementation(libs.kotest.properties)
  testImplementation(libs.kotest.runner)
  testImplementation(libs.kotlinx.coroutines.test)
  testImplementation(libs.robolectric)

  testImplementation(projects.dispatchAndroidEspresso)
  testImplementation(projects.dispatchInternalTest)
  testImplementation(projects.dispatchInternalTestAndroid)
  testImplementation(projects.dispatchTest)
  testImplementation(projects.dispatchTestJunit4)
}
