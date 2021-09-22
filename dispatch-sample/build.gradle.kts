/*
 * Copyright (C) 2021 Rick Busarow
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
  id("com.android.application")
  kotlin("android")
  id("kotlin-parcelize")
}

commonAndroid()

android {
  buildFeatures.viewBinding = true
}

dependencies {

  androidTestImplementation(libs.androidx.test.espresso.core)
  androidTestImplementation(libs.androidx.test.runner)

  androidTestImplementation(projects.dispatchAndroidEspresso)

  implementation(libs.androidx.activity.ktx)
  implementation(libs.androidx.appcompat)
  implementation(libs.androidx.constraintLayout)
  implementation(libs.androidx.coreKtx)
  implementation(libs.androidx.fragment.ktx)
  implementation(libs.androidx.lifecycle.common)
  implementation(libs.timber)
  implementation(libs.kotlinx.coroutines.android)
  implementation(libs.kotlinx.coroutines.core)

  implementation(projects.dispatchAndroidLifecycle)
  implementation(projects.dispatchAndroidLifecycleExtensions)
  implementation(projects.dispatchAndroidViewmodel)
  implementation(projects.dispatchCore)

  testImplementation(libs.junit.junit4)
  testImplementation(libs.junit.jupiter)
  testImplementation(libs.kotest.assertions)
  testImplementation(libs.kotest.properties)
  testImplementation(libs.kotest.runner)
  testImplementation(libs.kotlinx.coroutines.test)

  testImplementation(projects.dispatchTestJunit4)
  testImplementation(projects.dispatchTestJunit5)
}
