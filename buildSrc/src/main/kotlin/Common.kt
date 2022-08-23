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
import org.gradle.kotlin.dsl.apply
import org.gradle.kotlin.dsl.withType
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

fun Project.common() {

  apply(plugin = "dokka")

  // check for runtime classpath changes in any published modul/artifact
  pluginManager.withPlugin("com.vanniktech.maven.publish") {
    apply(plugin = "dependency-guard")
  }

  tasks.withType<Test> {

    useJUnitPlatform {
      includeEngines("junit-vintage", "junit-jupiter")
    }
  }

  tasks.withType<KotlinCompile>()
    .configureEach {

      kotlinOptions {

        allWarningsAsErrors = true

        jvmTarget = "1.8"

        freeCompilerArgs = freeCompilerArgs + listOf(
          "-opt-in=kotlin.RequiresOptIn"
        )
      }
    }
}
