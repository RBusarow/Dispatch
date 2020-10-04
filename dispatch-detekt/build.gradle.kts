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
  id(Plugins.kotlin)
  id(Plugins.mavenPublish)
  id(Plugins.dokka)
}

dependencies {

  implementation(Libs.Detekt.api)
  implementation(Libs.Kotlin.stdlib)

  // detekt-test leaks transitive dependencies upon AssertJ and Spek
  // https://github.com/detekt/detekt/issues/3082
  testImplementation("org.assertj:assertj-core:3.17.2")
  testImplementation("org.spekframework.spek2:spek-dsl-jvm:2.0.13")

  testImplementation(Libs.Detekt.api)
  testImplementation(Libs.Detekt.test)
  testImplementation(Libs.JUnit.jUnit5Api)
  testImplementation(Libs.Kotest.assertions)
  testImplementation(Libs.Kotest.properties)
  testImplementation(Libs.Kotest.runner)

  testRuntimeOnly(Libs.JUnit.jUnit5Runtime)
  testRuntimeOnly(Libs.JUnit.jUnit5Vintage)
}
