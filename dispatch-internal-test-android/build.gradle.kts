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
}

android {
  namespace = "dispatch.internal.test.android"
}

dependencies {

  api(libs.androidx.fragment.core)
  api(libs.androidx.lifecycle.common)
  api(libs.androidx.lifecycle.liveData)
  api(libs.androidx.lifecycle.runtime)
  api(libs.junit.api)
  api(libs.kotlinx.coroutines.core)
  api(libs.kotlinx.coroutines.jvm)

  implementation(libs.androidx.lifecycle.runtimeKtx)
  implementation(libs.kotlin.reflect)
  implementation(libs.kotlinx.coroutines.android)
}
