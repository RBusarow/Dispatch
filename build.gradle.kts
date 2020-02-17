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

import kotlinx.knit.*
import org.gradle.kotlin.dsl.*
import org.jetbrains.dokka.gradle.*
import org.jetbrains.kotlin.gradle.tasks.*
import java.net.*

buildscript {
  repositories {
    mavenLocal()
    mavenCentral()
    maven("https://oss.sonatype.org/content/repositories/snapshots")
    google()
    jcenter()
    gradlePluginPortal()
    maven("https://dl.bintray.com/kotlin/kotlinx")
  }
  dependencies {
    classpath(BuildPlugins.androidGradlePlugin)
    classpath(BuildPlugins.atomicFu)
    classpath(BuildPlugins.benManesVersions)
    classpath(BuildPlugins.kotlinGradlePlugin)
    classpath(BuildPlugins.gradleMavenPublish)
    classpath(BuildPlugins.dokka)
    classpath(BuildPlugins.knit)
  }
}

allprojects {

  repositories {
    mavenCentral()
    google()
    jcenter()
  }

  tasks.withType<Test> {
    useJUnitPlatform()
  }

  afterEvaluate proj@{
    tasks.withType<DokkaTask> dokkaTask@{

      /*
      Basic Dokka config
       */

      outputFormat = "gfm"
      outputDirectory = "${project.buildDir}/dokka"

      subProjects = Modules.allProduction

      configuration {

        jdkVersion = 6
        reportUndocumented = true
        skipDeprecated = true
        skipEmptyPackages = true

        samples = listOf("samples")
        includes = listOf("README.md")

        externalDocumentationLink {
          url = URL("https://developer.android.com/reference/androidx/")
          packageListUrl = URL(
            "https://developer.android.com/reference/androidx/package-list"
          )
        }
        externalDocumentationLink {
          url = URL("https://developer.android.com/reference/")
          packageListUrl = URL(
            "https://developer.android.com/reference/android/support/package-list"
          )
        }
        externalDocumentationLink {
          url = URL(
            "https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-android/"
          )
          packageListUrl = URL(
            "https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-android/package-list"
          )
        }
        externalDocumentationLink {
          url = URL("https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/")
          packageListUrl = URL(
            "https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/package-list"
          )
        }

        sourceLink {
          // Unix based directory relative path to the root of the project (where you execute gradle respectively).
          path = "./"

          // URL showing where the source code can be accessed through the web browser
          url = "https://github.com/RBusarow/Dispatch/tree/master"

          // Suffix which is used to append the line number to the URL. Use #L for GitHub
          lineSuffix = "#L"
        }
      }

      /*
       Module-specific linking fixes.  This makes it so that links in moduleB's kdoc
       which point to something in moduleA's kdoc will actually work.
       */

      if (this@proj.name != "core") {

        linkModuleDocs(
          matchingModules = emptyList(), // all
          currentProject = this@proj,
          currentTask = this@dokkaTask,
          dependencyModule = "core"
        )

        linkModuleDocs(
          matchingModules = listOf("android-lifecycle-runtime", "android-lifecycle-viewmodel"),
          currentProject = this@proj,
          currentTask = this@dokkaTask,
          dependencyModule = "extensions"
        )

        linkModuleDocs(
          matchingModules = listOf("core-test-junit4", "core-test-junit5"),
          currentProject = this@proj,
          currentTask = this@dokkaTask,
          dependencyModule = "core-test"
        )
      }

      tasks.register("buildDocs")
        .configure {

          description = "recreates all documentation for the /docs directory"
          group = "documentation"

          dependsOn(
            rootProject.tasks.findByName("cleanDocs"),
            rootProject.tasks.findByName("copyRootFiles"),
            rootProject.tasks.findByName("knit"),
            this@dokkaTask
          )

          doLast {
            copyKdoc()
            copyReadMe()
          }
        }
    }
  }
}

fun linkModuleDocs(
  matchingModules: List<String>,
  currentProject: Project,
  currentTask: DokkaTask,
  dependencyModule: String
) {

  if (matchingModules.contains(currentProject.name) || matchingModules.isEmpty()) {

    currentTask.dependsOn(project(":$dependencyModule").tasks.withType<DokkaTask>())

    currentTask.configuration {
      externalDocumentationLink {
        url = URL("https://rbusarow.github.io/Dispatch/$dependencyModule/")
        packageListUrl = URL(
          "file://$projectDir/$dependencyModule/build/dokka/$dependencyModule/package-list"
        )
      }
    }
  }
}

tasks.register("clean").configure {
  delete("build")
}

subprojects {
  tasks.withType<KotlinCompile>()
    .configureEach {

      kotlinOptions {
        allWarningsAsErrors = true

        jvmTarget = "1.6"

        // https://youtrack.jetbrains.com/issue/KT-24946
        // freeCompilerArgs = listOf(
        //     "-progressive",
        //     "-Xskip-runtime-version-check",
        //     "-Xdisable-default-scripting-plugin",
        //     "-Xuse-experimental=kotlin.Experimental"
        // )
      }
    }
}

tasks.register("cleanDocs")
  .configure {

    description = "cleans /docs"
    group = "documentation"

    cleanDocs()

  }

tasks.register("copyRootFiles")
  .configure {

    description = "copies documentation files from the project root into /docs"
    group = "documentation"

    dependsOn("cleanDocs")

    doLast {
      copyRootFiles()
    }
  }

apply(plugin = Plugins.knit)

extensions.configure<KnitPluginExtension> {

  //  rootDir = File(".")
  //  moduleRoots = listOf( )

  moduleDocs = "build/dokka"
  moduleMarkers = listOf("build.gradle", "build.gradle.kts")
  siteRoot = "https://rbusarow.github.io/Dispatch"
}

// Build API docs for all modules with dokka before running Knit
tasks.getByName("knitPrepare") {
  dependsOn(subprojects.mapNotNull { it.tasks.findByName("dokka") })
}
