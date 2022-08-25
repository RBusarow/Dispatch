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

import org.gradle.api.Project
import org.gradle.api.tasks.testing.Test
import org.gradle.kotlin.dsl.systemProperties
import org.gradle.kotlin.dsl.withType
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

fun Project.common() {

  tasks.withType<Test> {

    useJUnitPlatform {
      includeEngines("junit-vintage", "junit-jupiter")
    }

    systemProperties(
      // auto-discover and apply any Junit5 extensions in the classpath
      // "junit.jupiter.extensions.autodetection.enabled" to true,

      // remove parentheses from test display names
      "junit.jupiter.displayname.generator.default" to "org.junit.jupiter.api.DisplayNameGenerator\$Simple",

      // single class instance for all tests
      "junit.jupiter.testinstance.lifecycle.default" to "per_class",

      // https://junit.org/junit5/docs/snapshot/user-guide/#writing-tests-parallel-execution-config-properties
      // run all tests concurrently
      // this might wind up creating flakes thanks to Dispatchers.setMain
      "junit.jupiter.execution.parallel.enabled" to true,
      "junit.jupiter.execution.parallel.mode.default" to "concurrent",
      "junit.jupiter.execution.parallel.mode.classes.default" to "concurrent",

      "junit.jupiter.execution.parallel.config.strategy" to "dynamic",
      "junit.jupiter.execution.parallel.config.dynamic.factor" to 1.0
    )

    // Allow Junit 4 tests to run in parallel
    maxParallelForks = Runtime.getRuntime().availableProcessors()
  }

  tasks.withType<KotlinCompile>()
    .configureEach {

      kotlinOptions {

        allWarningsAsErrors = false
        // allWarningsAsErrors = true

        jvmTarget = "1.8"

        freeCompilerArgs = freeCompilerArgs + listOf(
          "-opt-in=kotlin.RequiresOptIn",
          "-Xcontext-receivers"
        )
      }
    }
}
