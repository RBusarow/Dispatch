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

import com.github.benmanes.gradle.versions.updates.*
import formatting.*
import io.gitlab.arturbosch.detekt.*
import kotlinx.knit.*
import kotlinx.validation.*
import org.jetbrains.dokka.gradle.*
import org.jetbrains.kotlin.gradle.tasks.*

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
    classpath(BuildPlugins.binaryCompatibility)
    classpath(BuildPlugins.kotlinGradlePlugin)
    classpath(BuildPlugins.gradleMavenPublish)
    classpath(BuildPlugins.knit)
  }
}

plugins {
  id(Plugins.benManes) version Versions.benManes
  id(Plugins.dependencyAnalysis) version Versions.dependencyAnalysis
  id(Plugins.gradleDoctor) version Versions.gradleDoctor
  id(Plugins.detekt) version Libs.Detekt.version
  kotlin("jvm")
  id(Plugins.dokka) version Versions.dokka
  id(Plugins.taskTree) version Versions.taskTree
  id(Plugins.spotless) version Versions.spotless
  base
}

allprojects {

  repositories {
    mavenLocal()
    mavenCentral()
    google()
    jcenter()
  }

  tasks.withType<Test> {
    useJUnitPlatform()
  }
}

tasks.dokkaHtmlMultiModule.configure {

  outputDirectory.set(buildDir.resolve("dokka"))

  // missing from 1.4.10  https://github.com/Kotlin/dokka/issues/1530
  // documentationFileName.set("README.md")
}

subprojects {

  tasks.withType<DokkaTask>().configureEach {

    dependsOn(allprojects.mapNotNull { it.tasks.findByName("assemble") })

    outputDirectory.set(buildDir.resolve("dokka"))

    dokkaSourceSets.configureEach {

      jdkVersion.set(8)
      reportUndocumented.set(true)
      skipEmptyPackages.set(true)
      noAndroidSdkLink.set(false)

      samples.from(files("samples"))

      if (File("$projectDir/README.md").exists()) {
        includes.from(files("README.md"))
      }

      sourceLink {

        val modulePath = this@subprojects.path.replace(":", "/").replaceFirst("/", "")

        // Unix based directory relative path to the root of the project (where you execute gradle respectively).
        localDirectory.set(file("src/main"))

        // URL showing where the source code can be accessed through the web browser
        remoteUrl.set(uri("https://github.com/RBusarow/Dispatch/blob/main/$modulePath/src/main").toURL())
        // Suffix which is used to append the line number to the URL. Use #L for GitHub
        remoteLineSuffix.set("#L")
      }
    }
  }
}
subprojects {
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
      rootProject.tasks.findByName("knit")
    )

    doLast {
      copyKdoc()
      copyReadMe()
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

@Suppress("DEPRECATION")
detekt {

  parallel = true
  config = files("$rootDir/detekt/detekt-config.yml")

  reports {
    xml.enabled = false
    html.enabled = true
    txt.enabled = false
  }
}

dependencies {

  detekt(Libs.Detekt.cli)
  detektPlugins(project(path = ":dispatch-detekt"))
}

tasks.withType<DetektCreateBaselineTask> {

  setSource(files(rootDir))

  include("**/*.kt", "**/*.kts")
  exclude("**/resources/**", "**/build/**", "**/src/test/java**")

  // Target version of the generated JVM bytecode. It is used for type resolution.
  this.jvmTarget = "1.8"
}

tasks.withType<Detekt> {

  setSource(files(rootDir))

  include("**/*.kt", "**/*.kts")
  exclude("**/resources/**", "**/build/**", "**/src/test/java**")

  // Target version of the generated JVM bytecode. It is used for type resolution.
  this.jvmTarget = "1.8"
}

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

apply(plugin = Plugins.knit)

extensions.configure<KnitPluginExtension> {

  rootDir = rootProject.rootDir
  moduleRoots = listOf(".")

  moduleDocs = "build/dokka"
  moduleMarkers = listOf("build.gradle", "build.gradle.kts")
  siteRoot = "https://rbusarow.github.io/Dispatch/api"
}

// Build API docs for all modules with dokka before running Knit
tasks.withType<KnitTask> {
  dependsOn(allprojects.mapNotNull { it.tasks.findByName("dokkaHtml") })
  doLast {
    fixDocsReferencePaths()
  }
}

val sortDependencies by tasks.registering {

  description = "sort all dependencies in a gradle kts file"
  group = "refactor"

  doLast {
    sortDependencies()
  }
}

dependencyAnalysis {
  issues {
    all {
      ignoreKtx(false) // default is false
    }
  }
}

fun isNonStable(version: String): Boolean {
  val stableKeyword = listOf("RELEASE", "FINAL", "GA").any { version.toUpperCase().contains(it) }
  val regex = "^[0-9,.v-]+(-r)?$".toRegex()
  val isStable = stableKeyword || regex.matches(version)
  return isStable.not()
}

tasks.named("dependencyUpdates", DependencyUpdatesTask::class.java).configure {
  rejectVersionIf {
    isNonStable(candidate.version) && !isNonStable(currentVersion)
  }
}

configure<com.diffplug.gradle.spotless.SpotlessExtension> {
  kotlin {
    target("**/src/**/*.kt")
    ktlint("0.40.0")
      .userData(
        mapOf(
          "indent_size" to "2",
          "continuation_indent_size" to "2",
          "max_line_length" to "off",
          "disabled_rules" to "no-wildcard-imports",
          "ij_kotlin_imports_layout" to "*,java.**,javax.**,kotlin.**,^"
        )
      )
    trimTrailingWhitespace()
    endWithNewline()
  }
  kotlinGradle {
    target("*.gradle.kts")
    ktlint("0.40.0")
      .userData(
        mapOf(
          "indent_size" to "2",
          "continuation_indent_size" to "2",
          "max_line_length" to "off",
          "disabled_rules" to "no-wildcard-imports",
          "ij_kotlin_imports_layout" to "*,java.**,javax.**,kotlin.**,^"
        )
      )
  }
}
