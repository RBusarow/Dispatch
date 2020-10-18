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
  kotlin("android")
  id(Plugins.mavenPublish)
  id(Plugins.dokka)
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
      proguardFiles(
        getDefaultProguardFile("proguard-android-optimize.txt"),
        "proguard-rules.pro"
      )
    }
  }
}

dependencies {

  api(Libs.AndroidX.Lifecycle.common)
  api(Libs.Kotlinx.Coroutines.core)
  api(Libs.Kotlinx.Coroutines.coreJvm)

  api(project(":dispatch-core"))

  implementation(Libs.AndroidX.Fragment.core)
  implementation(Libs.AndroidX.Lifecycle.liveData)
  implementation(Libs.AndroidX.Lifecycle.liveDataKtx)
  implementation(Libs.AndroidX.Lifecycle.runtime)
  implementation(Libs.Kotlinx.Coroutines.android)

  testImplementation(Libs.AndroidX.Lifecycle.runtime)
  testImplementation(Libs.AndroidX.Test.Arch.core)
  testImplementation(Libs.AndroidX.Test.Espresso.core)
  testImplementation(Libs.AndroidX.Test.runner)
  testImplementation(Libs.JUnit.jUnit5)
  testImplementation(Libs.Kotest.assertions)
  testImplementation(Libs.Kotest.properties)
  testImplementation(Libs.Kotest.runner)
  testImplementation(Libs.Kotlinx.Coroutines.test)
  testImplementation(Libs.RickBusarow.Hermit.coroutines)
  testImplementation(Libs.RickBusarow.Hermit.junit5)
  testImplementation(Libs.Robolectric.core)

  testImplementation(project(":dispatch-internal-test"))
  testImplementation(project(":dispatch-internal-test-android"))
  testImplementation(project(":dispatch-test"))
  testImplementation(project(":dispatch-test-junit4"))
  testImplementation(project(":dispatch-test-junit5"))
}
