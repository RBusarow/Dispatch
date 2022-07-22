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

import com.android.build.gradle.tasks.CompileLibraryResourcesTask
import kotlinx.atomicfu.plugin.gradle.AtomicFUTransformTask
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import org.jlleitschuh.gradle.ktlint.tasks.KtLintCheckTask
import org.jlleitschuh.gradle.ktlint.tasks.KtLintFormatTask

apply(plugin = "org.jetbrains.dokka")

subprojects {

  afterEvaluate {

    val proj = this

    val includeSubproject = when {
      path.endsWith("tests") -> false
      path.endsWith("compiler") -> false
      proj.parent?.path == ":sample" -> false
      else -> File("${proj.projectDir}/src").exists()
    }

    if (includeSubproject) {
      apply(plugin = "org.jetbrains.dokka")

      proj.tasks
        .withType<org.jetbrains.dokka.gradle.AbstractDokkaLeafTask>()
        .configureEach {

          // Dokka uses their outputs but doesn't explicitly depend upon them.
          mustRunAfter(tasks.withType(KotlinCompile::class.java))
          mustRunAfter(tasks.withType(KtLintCheckTask::class.java))
          mustRunAfter(tasks.withType(KtLintFormatTask::class.java))
          mustRunAfter(tasks.withType(CompileLibraryResourcesTask::class.java))
          mustRunAfter(tasks.withType(AtomicFUTransformTask::class.java))

          dependsOn(allprojects.mapNotNull { it.tasks.findByName("compileKotlin") })

          dokkaSourceSets {

            getByName("main") {

              samples.setFrom(
                fileTree(proj.projectDir) {
                  include("**/samples/**")
                }
              )

              if (File("${proj.projectDir}/README.md").exists()) {
                includes.from(files("${proj.projectDir}/README.md"))
              }

              sourceLink {
                localDirectory.set(file("src/main"))

                val modulePath = proj.path.replace(":", "/").replaceFirst("/", "")

                // URL showing where the source code can be accessed through the web browser
                remoteUrl.set(
                  uri("https://github.com/RBusarow/Dispatch/blob/main/$modulePath/src/main").toURL()
                )
                // Suffix which is used to append the line number to the URL. Use #L for GitHub
                remoteLineSuffix.set("#L")
              }
            }
          }
        }
    }
  }
}
