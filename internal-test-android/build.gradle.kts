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

  implementation(Libs.Androidx.Lifecycle.common)
  implementation(Libs.Androidx.Lifecycle.liveData)
  implementation(Libs.Androidx.Lifecycle.runtime)

  implementation(Libs.JUnit.jUnit5)

  implementation(Libs.Kotlin.stdlib)
  implementation(Libs.Kotlin.test)
  implementation(Libs.Kotlin.testCommon)

  implementation(Libs.KotlinTest.junit5runner)

  implementation(Libs.Kotlinx.Coroutines.core)
  implementation(Libs.Kotlinx.Coroutines.test)
  implementation(Libs.Kotlinx.Knit.test)

  implementation(project(":android-espresso"))
  implementation(project(":android-lifecycle"))
  implementation(project(":android-lifecycle-extensions"))

  implementation(project(":core"))
  implementation(project(":core-test"))
  implementation(project(":core-test-junit5"))
  implementation(project(":extensions"))

}
