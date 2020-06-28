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
  id(Plugins.androidLibrary)
  id(Plugins.kotlinAndroid)
  id(Plugins.kotlinAndroidExtensions)
}

android {
  compileSdkVersion(Versions.compileSdk)

  defaultConfig {
    minSdkVersion(Versions.minSdk)
    targetSdkVersion(Versions.targetSdk)
    versionName = Versions.versionName

    testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
  }

  buildTypes {
    getByName("release") {
      isMinifyEnabled = false
      proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
    }
  }
}

dependencies {

  implementation(Libs.AndroidX.Lifecycle.common)
  implementation(Libs.AndroidX.Lifecycle.liveData)
  testImplementation(Libs.AndroidX.Lifecycle.runtime)

  implementation(Libs.Kotlin.stdlib)

  implementation(Libs.Kotlinx.Coroutines.core)

  implementation(project(":dispatch-android-espresso"))
  implementation(project(":dispatch-android-lifecycle"))
  implementation(project(":dispatch-android-lifecycle-extensions"))
  implementation(project(":dispatch-core"))
  implementation(project(":dispatch-test"))
  implementation(project(":dispatch-test-junit5"))
  testImplementation(Libs.JUnit.jUnit5)
  testImplementation(Libs.Kotest.assertions)
  testImplementation(Libs.Kotest.consoleRunner)
  testImplementation(Libs.Kotest.properties)
  testImplementation(Libs.Kotest.runner)

  testImplementation(Libs.Kotlin.test)
  testImplementation(Libs.Kotlin.testCommon)

  testImplementation(Libs.Kotlinx.Coroutines.test)
  testImplementation(Libs.Kotlinx.Knit.test)

}