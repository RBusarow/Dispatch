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

import com.github.benmanes.gradle.versions.updates.DependencyUpdatesTask
import formatting.sortDependencies
import io.gitlab.arturbosch.detekt.Detekt
import io.gitlab.arturbosch.detekt.DetektCreateBaselineTask
import kotlinx.validation.ApiValidationExtension
import org.jlleitschuh.gradle.ktlint.KtlintExtension

buildscript {
  dependencies {
    classpath(libs.android.gradle)
    classpath(libs.square.anvil.gradle)
    classpath(libs.google.ksp)
    classpath(libs.kotlin.gradle.plug)
    classpath(libs.kotlinx.atomicfu)
    classpath(libs.kotlinx.metadata.jvm)
    classpath(libs.ktlint.gradle)
    classpath(libs.ktlint.gradle)
  }
}

// `alias(libs.______)` inside the plugins block throws a false positive warning
// https://youtrack.jetbrains.com/issue/KTIJ-19369
// There's also an IntelliJ plugin to disable this warning globally:
// https://plugins.jetbrains.com/plugin/18949-gradle-libs-error-suppressor
@Suppress("DSL_SCOPE_VIOLATION")
plugins {
  kotlin("jvm") apply false
  alias(libs.plugins.kotlinx.binaryCompatibility)
  alias(libs.plugins.dependencyAnalysis)
  alias(libs.plugins.benManes)
  alias(libs.plugins.gradleDoctor)
  alias(libs.plugins.taskTree)
  alias(libs.plugins.moduleCheck)
  base
  detekt
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

detekt {

  parallel = true
  config = files("$rootDir/detekt/detekt-config.yml")
}

dependencies {

  detekt(libs.detekt.cli)

  detektPlugins(projects.dispatchDetekt)
}

moduleCheck {
  deleteUnused = true
  checks.sortDependencies = true
}

tasks.withType<DetektCreateBaselineTask> {

  setSource(files(rootDir))

  include("**/*.kt", "**/*.kts")
  exclude("**/resources/**", "**/build/**", "**/src/test/java**")

  // Target version of the generated JVM bytecode. It is used for type resolution.
  this.jvmTarget = "1.8"
}

tasks.withType<Detekt> {

  reports {
    xml.required.set(true)
    html.required.set(true)
    txt.required.set(false)
  }

  setSource(files(rootDir))

  include("**/*.kt", "**/*.kts")
  exclude("**/resources/**", "**/build/**", "**/src/test/java**")

  // Target version of the generated JVM bytecode. It is used for type resolution.
  this.jvmTarget = "1.8"
}

apply(plugin = "binary-compatibility-validator")

extensions.configure<ApiValidationExtension> {

  /** Packages that are excluded from public API dumps even if they contain public API. */
  ignoredPackages = mutableSetOf("sample", "samples")

  /** Sub-projects that are excluded from API validation. */
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

@Suppress("UndocumentedPublicFunction")
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

  extensions.configure(KtlintExtension::class.java) {
    val libVersion = "0.45.2"
    version.set(libVersion)
    debug.set(false)
    outputToConsole.set(true)
    enableExperimentalRules.set(true)
    filter {
      exclude("**/generated/**")
      exclude("**/build/**")
    }
    disabledRules.set(
      setOf(
        // manually formatting still does this, and KTLint will still wrap long chains when possible
        "max-line-length",
        // same as Detekt's MatchingDeclarationName,
        // but Detekt's version can be suppressed and this can't
        "filename",
        "experimental:argument-list-wrapping", // doesn't work half the time
        "experimental:no-empty-first-line-in-method-block", // code golf...
        // This can be re-enabled once 0.46.0 is released
        // https://github.com/pinterest/ktlint/issues/1435
        "experimental:type-parameter-list-spacing",
        // added in 0.46.0
        "experimental:function-signature"
      )
    )

    extensions.configure(KtlintExtension::class.java) {

      require(libVersion < "0.46.0") {
        """
      when updating to 0.46.0:
      - Re-enable `experimental:type-parameter-list-spacing`
      - remove 'experimental' from 'argument-list-wrapping'
      - remove 'experimental' from 'no-empty-first-line-in-method-block'
        """.trimIndent()
      }
    }
  }

  tasks.withType<org.jlleitschuh.gradle.ktlint.tasks.BaseKtLintCheckTask> {
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
