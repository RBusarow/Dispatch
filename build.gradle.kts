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
@file:Suppress("MagicNumber")

import com.github.benmanes.gradle.versions.updates.*
import formatting.*
import io.gitlab.arturbosch.detekt.*
import kotlinx.knit.*
import kotlinx.validation.*
import org.jetbrains.dokka.gradle.*
import org.jetbrains.kotlin.gradle.tasks.*
import org.jlleitschuh.gradle.ktlint.KtlintExtension
import org.jlleitschuh.gradle.ktlint.tasks.BaseKtLintCheckTask

buildscript {
  dependencies {
    classpath(libs.android.gradle)
    classpath(libs.vanniktech.maven.publish)
    classpath(libs.kotlin.gradle.plug)
    classpath(libs.kotlinx.atomicfu)
    classpath(libs.ktlint.gradle)
  }
}

@Suppress("UnstableApiUsage")
plugins {
  kotlin("jvm") apply false
  alias(libs.plugins.kotlinx.binaryCompatibility)
  alias(libs.plugins.dependencyAnalysis)
  alias(libs.plugins.benManes)
  alias(libs.plugins.gradleDoctor)
  alias(libs.plugins.detekt)
  alias(libs.plugins.taskTree)
  alias(libs.plugins.moduleCheck)
  base
  dokka
  knit
  website
}

allprojects {
  configurations.all {
    resolutionStrategy {

      eachDependency {
        when {
          requested.group == "org.jetbrains.kotlin" -> useVersion(libs.versions.kotlin.get())
          requested.name.startsWith("kotlinx-coroutines") -> useVersion(libs.versions.kotlinx.coroutines.get())
        }
      }
    }
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

  detekt(libs.arturbosch.detekt.cli)

  detektPlugins(projects.dispatchDetekt)
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

apply(plugin = "binary-compatibility-validator")

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
    "dispatch-sample"
  )
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

allprojects {
  apply(plugin = "org.jlleitschuh.gradle.ktlint")

  configure<KtlintExtension> {
    debug.set(false)
    version.set("0.45.2")
    disabledRules.set(
      setOf(
        "no-wildcard-imports",
        "max-line-length", // manually formatting still does this, and KTLint will still wrap long chains when possible
        "filename", // same as Detekt's MatchingDeclarationName, but Detekt's version can be suppressed and this can't
        "experimental:argument-list-wrapping" // doesn't work half the time
      )
    )
  }
  tasks.withType<BaseKtLintCheckTask> {
    workerMaxHeapSize.set("512m")
  }
}

configure<com.osacky.doctor.DoctorExtension> {
  negativeAvoidanceThreshold.set(500)
  javaHome {
    // this is throwing a false positive
    // JAVA_HOME is /Users/rbusarow/Library/Java/JavaVirtualMachines/azul-11-ARM64
    // Gradle is using /Users/rbusarow/Library/Java/JavaVirtualMachines/azul-11-ARM64/zulu-11.jdk/Contents/Home
    ensureJavaHomeMatches.set(false)
  }
}
