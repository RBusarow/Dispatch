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

object Plugins {

  const val androidApplication = "com.android.application"
  const val androidLibrary = "com.android.library"

  const val atomicFu = "kotlinx-atomicfu"

  const val dokka = "org.jetbrains.dokka"

  const val javaLibrary = "java-library"

  const val kotlin = "kotlin"
  const val kotlinAndroid = "kotlin-android"
  const val kotlinAndroidExtensions = "kotlin-android-extensions"
}

object Versions {
  const val ktlint = "0.35.0"
  const val dokka = "0.10.0"

  const val compileSdk = 29
  const val minSdk = "21"
  const val targetSdk = 29

  const val gradleWrapper = "3.5.3"
  const val dagger = "2.25.2"
  const val kotlin = "1.3.61"

  const val versionName = "1.0.0-beta03"
}

object BuildPlugins {

  const val atomicFu = "org.jetbrains.kotlinx:atomicfu-gradle-plugin:0.14.1"

  const val androidGradlePlugin = "com.android.tools.build:gradle:${Versions.gradleWrapper}"
  const val kotlinGradlePlugin = "org.jetbrains.kotlin:kotlin-gradle-plugin:${Versions.kotlin}"

  const val androidApplication = "com.android.application"
  const val kotlinAndroid = "kotlin-android"
  const val kotlinAndroidExtensions = "kotlin-android-extensions"

}

object Libs {

  object Androidx {
    const val activity = "androidx.activity:activity-ktx:1.0.0"
    const val appcompat = "androidx.appcompat:appcompat:1.1.0"
    const val constraintLayout = "androidx.constraintlayout:constraintlayout:1.1.3"
    const val coreKtx = "androidx.core:core-ktx:1.1.0"
    const val fragment = "androidx.fragment:fragment:1.2.0"
    const val fragmentKtx = "androidx.fragment:fragment-ktx:1.2.0"
    const val fragmentTesting = "androidx.fragment:fragment-testing:1.2.0"
    const val lifecycle = "androidx.lifecycle:lifecycle-common-java8:2.2.0"
    const val lifecycleExtensions = "androidx.lifecycle:lifecycle-extensions:2.2.0"
    const val lifecycleLiveData = "androidx.lifecycle:lifecycle-livedata-ktx:2.2.0"
    const val lifecycleRuntime = "androidx.lifecycle:lifecycle-runtime-ktx:2.2.0"
    const val lifecycleViewModel = "androidx.lifecycle:lifecycle-viewmodel-ktx:2.2.0"
    const val espresso = "androidx.test.espresso:espresso-core:3.2.0"
    const val testRunner = "androidx.test:runner:1.2.0"
  }

  object Dagger {
    private const val version = "2.25.2"
    const val android = "com.google.dagger:dagger-android:$version"
    const val androidKapt = "com.google.dagger:dagger-android-processor:$version"
    const val androidSupport = "com.google.dagger:dagger-android-support:$version"
    const val compiler = "com.google.dagger:dagger-compiler:$version"
    const val core = "com.google.dagger:dagger:$version"
  }

  object JakeWharton {
    const val timber = "com.jakewharton.timber:timber:4.7.1"
  }

  object JUnit {
    const val jUnit4 = "junit:junit:4.12"

    private const val version = "5.5.1"

    const val jUnit5 = "org.junit.jupiter:junit-jupiter:$version"
    const val jUnit5Api = "org.junit.jupiter:junit-jupiter-api:$version"
    const val jUnit5Params = "org.junit.jupiter:junit-jupiter-params:$version"
    const val jUnit5Runtime = "org.junit.jupiter:junit-jupiter-engine:$version"
    const val jUnit5Vintage = "org.junit.vintage:junit-vintage-engine:$version"
  }

  object Kotlin {
    private const val version = "1.3.61"
    const val stdlib = "org.jetbrains.kotlin:kotlin-stdlib-jdk8:$version"
    const val reflect = "org.jetbrains.kotlin:kotlin-reflect:$version"
    const val gradlePlugin = "org.jetbrains.kotlin:kotlin-gradle-plugin:$version"
    const val extensions = "org.jetbrains.kotlin:kotlin-android-extensions:$version"
    const val test = "org.jetbrains.kotlin:kotlin-test:$version"
    const val testCommon = "org.jetbrains.kotlin:kotlin-test-common:$version"
  }

  object KotlinTest {
    const val junit4runner = "io.kotlintest:kotlintest-runner-junit5:3.3.3"
  }

  object Kotlinx {

    object Coroutines {
      private const val version = "1.3.3"
      const val core = "org.jetbrains.kotlinx:kotlinx-coroutines-core:$version"
      const val android = "org.jetbrains.kotlinx:kotlinx-coroutines-android:$version"
      const val test = "org.jetbrains.kotlinx:kotlinx-coroutines-test:$version"
    }

  }

  object MockK {
    const val core = "io.mockk:mockk:1.9.2"
  }

  object RickBusarow {

    object DispatcherProvider {
      private const val version = "1.+"
      const val core = "com.rickbusarow.dispatcherprovider:dispatcher-provider:$version"
      const val test = "com.rickbusarow.dispatcherprovider:dispatcher-provider-test:$version"
    }
  }

}
