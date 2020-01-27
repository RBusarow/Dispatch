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

buildscript {

  repositories {
    mavenCentral()
  }
  dependencies {
    classpath(BuildPlugins.kotlinGradlePlugin)
  }

}

plugins {
  id(Plugins.atomicFu)
  id(Plugins.javaLibrary)
  id(Plugins.kotlin)
  id(Plugins.dokka).version(Versions.dokka)
}

tasks.test {
  useJUnitPlatform()
}

dependencies {

  implementation(Libs.Kotlin.stdlib)

  implementation(Libs.Kotlinx.Coroutines.core)

  implementation(project(":core"))

  testImplementation(project(":core-test"))
  testImplementation(project(":internal-test"))

  testImplementation(Libs.JUnit.jUnit5)
  testImplementation(Libs.KotlinTest.junit4runner)
  testImplementation(Libs.Kotlinx.Coroutines.test)

  testImplementation(Libs.Androidx.testRunner)
  testImplementation(Libs.Androidx.espresso)
}