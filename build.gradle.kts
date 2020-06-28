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

@file:Suppress("MagicNumber")

import io.gitlab.arturbosch.detekt.Detekt
import kotlinx.knit.*
import kotlinx.validation.*
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
    classpath(BuildPlugins.binaryCompatibility)
    classpath(BuildPlugins.kotlinGradlePlugin)
    classpath(BuildPlugins.gradleMavenPublish)
    classpath(BuildPlugins.dokka)
    classpath(BuildPlugins.knit)
  }
}

plugins {
  id("io.gitlab.arturbosch.detekt") version Libs.Detekt.version
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
          url = URL("https://developer.android.com/reference/androidx/test/")
          packageListUrl = URL(
            "https://developer.android.com/reference/androidx/test/package-list"
          )
        }
        externalDocumentationLink {
          url = URL("https://developer.android.com/reference/")
          packageListUrl = URL(
            "https://developer.android.com/reference/android/support/package-list"
          )
        }
        externalDocumentationLink { url = URL("https://junit.org/junit4/javadoc/latest/") }
        externalDocumentationLink { url = URL("https://junit.org/junit5/docs/current/api/") }
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
        externalDocumentationLink {
          url = URL("https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-test/")
          packageListUrl = URL(
            "https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-test/package-list"
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

      if (this@proj.name != "dispatch-core" && this@proj.name != "dispatch-detekt") {

        linkModuleDocs(
          matchingModules = emptyList(), // all
          currentProject = this@proj,
          currentTask = this@dokkaTask,
          dependencyModule = "dispatch-core"
        )

        linkModuleDocs(
          matchingModules = listOf("dispatch-android-lifecycle-extensions"),
          currentProject = this@proj,
          currentTask = this@dokkaTask,
          dependencyModule = "dispatch-android-lifecycle"
        )

        linkModuleDocs(
          matchingModules = listOf("dispatch-test-junit4", "dispatch-test-junit5"),
          currentProject = this@proj,
          currentTask = this@dokkaTask,
          dependencyModule = "dispatch-test"
        )
      }

      @Suppress("UNUSED_VARIABLE")
      val buildDocs by tasks.registering {

        description = "recreates all documentation for the /docs directory"
        group = "documentation"

        doFirst {
          updateReadMeArtifactVersions()
        }

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

val clean by tasks.registering {
  doLast {
    delete("build")
  }
}

subprojects {
  tasks.withType<KotlinCompile>()
    .configureEach {

      kotlinOptions {
        allWarningsAsErrors = true

        jvmTarget = "1.8"

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

val cleanDocs by tasks.registering {

  description = "cleans /docs"
  group = "documentation"

  doLast {
    cleanDocs()
  }
}

val copyRootFiles by tasks.registering {

  description = "copies documentation files from the project root into /docs"
  group = "documentation"

  dependsOn("cleanDocs")

  doLast {
    copySite()
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

subprojects {

  apply {
    plugin("io.gitlab.arturbosch.detekt")
  }

  detekt {
    parallel = true
    config = files("$rootDir/detekt/detekt-config.yml")

    val unique = "${rootProject.relativePath(projectDir)}/${project.name}"

    idea {
      path = "$rootDir/.idea"
      codeStyleScheme = "$rootDir/.idea/Project.xml"
      inspectionsProfile = "$rootDir/.idea/Project-Default.xml"
      report = "${project.projectDir}/reports/build/detekt-reports"
      mask = "*.kt"
    }

    reports {
      xml {
        enabled = false
        destination = file("$rootDir/build/detekt-reports/$unique-detekt.xml")
      }
      html {
        enabled = true
        destination = file("$rootDir/build/detekt-reports/$unique-detekt.html")
      }
      txt {
        enabled = false
        destination = file("$rootDir/build/detekt-reports/$unique-detekt.txt")
      }
    }
  }
}

allprojects {
  dependencies {
    detekt(Libs.Detekt.cli)
    detektPlugins(project(path = ":dispatch-detekt"))
  }
}

val analysisDir = file(projectDir)
val baselineFile = file("$rootDir/detekt/project-baseline.xml")
val configFile = file("$rootDir/detekt/detekt-config.yml")
val formatConfigFile = file("$rootDir/config/detekt/format.yml")
val statisticsConfigFile = file("$rootDir/config/detekt/statistics.yml")

val kotlinFiles = "**/*.kt"
val kotlinScriptFiles = "**/*.kts"
val resourceFiles = "**/resources/**"
val buildFiles = "**/build/**"
val testFiles = "**/src/test/**"

val detektAll by tasks.registering(Detekt::class) {

  description = "Runs the whole project at once."
  parallel = true
  buildUponDefaultConfig = true
  setSource(files(rootDir))
  config.setFrom(files(configFile))
  include(kotlinFiles, kotlinScriptFiles)
  exclude(resourceFiles, buildFiles, testFiles)
  reports {
    xml.enabled = false
    html.enabled = false
    txt.enabled = false
  }
}

tasks.findByName("detekt")
  ?.finalizedBy(detektAll)

apply(plugin = Plugins.binaryCompatilibity)

extensions.configure<ApiValidationExtension> {

  /**
   * Packages that are excluded from public API dumps even if they
   * contain public API.
   */
  ignoredPackages = mutableSetOf("sample", "samples")

  /**
   * Sub-projects that are excluded from API validation
   */
  ignoredProjects = mutableSetOf(
    "dispatch-internal-test",
    "dispatch-internal-test-android",
    "dispatch-sample",
    "samples"
  )
}
