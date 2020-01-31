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
  id(Plugins.javaLibrary)
  id(Plugins.kotlin)
}

dependencies {

  compileOnly(Libs.Lint.api)
  compileOnly(Libs.Kotlin.stdlib)

  testImplementation(Libs.Kotlin.stdlib)
  testImplementation(Libs.Lint.core)
  testImplementation(Libs.Lint.tests)
}

tasks {
  "jar"(Jar::class) {
    manifest {
      attributes("Lint-Registry-v2" to "dispatch.android.lint.LifecycleCoroutineScopeIssueRegistry")
    }
//    archiveName = "foo.jar"
//    into("META-INF") {
//      from("bar")
//    }
  }

  "test" {
    doLast { println("test completed") }
  }
}
java {
  sourceCompatibility = JavaVersion.VERSION_1_7
  targetCompatibility = JavaVersion.VERSION_1_7
}
