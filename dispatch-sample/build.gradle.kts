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

import Libs.Androidx.Lifecycle

plugins {
  id(Plugins.androidApplication)
  id(Plugins.kotlinAndroid)
  id(Plugins.kotlinAndroidExtensions)
}

android {
  compileSdkVersion(Versions.compileSdk)

  defaultConfig {
    minSdkVersion(Versions.minSdk)
    targetSdkVersion(Versions.targetSdk)
    versionCode = 1
    versionName = Versions.versionName

    testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
  }

  viewBinding {
    isEnabled = true
  }

  buildTypes {
    getByName("release") {
      isMinifyEnabled = false
      proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
    }
  }
}

dependencies {

  implementation(Libs.Androidx.activity)
  implementation(Libs.Androidx.appcompat)
  implementation(Libs.Androidx.constraintLayout)
  implementation(Libs.Androidx.coreKtx)
  implementation(Libs.Androidx.Fragment.ktx)
  implementation(Lifecycle.common)
  implementation(Lifecycle.extensions)

  implementation(Libs.JakeWharton.timber)

  implementation(Libs.Kotlin.stdlib)

  implementation(Libs.Kotlinx.Coroutines.android)
  implementation(Libs.Kotlinx.Coroutines.core)

  implementation(project(":dispatch-core"))
  implementation(project(":dispatch-android-lifecycle"))
  implementation(project(":dispatch-android-lifecycle-extensions"))
  implementation(project(":dispatch-android-viewmodel"))

  testImplementation(Libs.JUnit.jUnit4)
  testImplementation(Libs.JUnit.jUnit5)
  testImplementation(Libs.Kotest.assertions)
  testImplementation(Libs.Kotest.properties)
  testImplementation(Libs.Kotest.runner)
  testImplementation(Libs.Kotlinx.Coroutines.test)

  testImplementation(project(":dispatch-test-junit4"))
  testImplementation(project(":dispatch-test-junit5"))

  androidTestImplementation(project(":dispatch-android-espresso"))

  androidTestImplementation(Libs.Androidx.Test.runner)
  androidTestImplementation(Libs.Androidx.Test.Espresso.core)
}
