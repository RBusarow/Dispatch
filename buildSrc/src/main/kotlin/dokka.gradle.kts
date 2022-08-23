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

plugins {
  id("org.jetbrains.dokka")
}

tasks
  .withType<org.jetbrains.dokka.gradle.AbstractDokkaLeafTask>()
  .configureEach {

    // Dokka doesn't support configuration caching
    notCompatibleWithConfigurationCache("Dokka doesn't support configuration caching")

    // Dokka uses their outputs but doesn't explicitly depend upon them.
    mustRunAfter(tasks.withType(KotlinCompile::class.java))
    mustRunAfter(tasks.withType(KtLintCheckTask::class.java))
    mustRunAfter(tasks.withType(KtLintFormatTask::class.java))
    mustRunAfter(tasks.withType(CompileLibraryResourcesTask::class.java))
    mustRunAfter(tasks.withType(AtomicFUTransformTask::class.java))

    val fullModuleName = project.path.removePrefix(":")
    moduleName.set(fullModuleName)

    dokkaSourceSets {

      getByName("main") {

        samples.setFrom(
          fileTree(projectDir) {
            include("**/samples/**")
          }
        )

        val readmeFile = file("$projectDir/README.md")

        if (readmeFile.exists()) {
          includes.from(readmeFile)
        }

        sourceLink {
          localDirectory.set(file("src/main"))

          val modulePath = path.replace(":", "/").replaceFirst("/", "")

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

tasks.withType<Javadoc> {
  dependsOn("dokkaJavadoc")
}
