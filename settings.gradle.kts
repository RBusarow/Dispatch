/*
 * Copyright (C) 2021 Rick Busarow
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

pluginManagement {
  repositories {
    gradlePluginPortal()
    mavenCentral()
    google()
  }
  resolutionStrategy {
    eachPlugin {
      when {
        requested.id.id.startsWith("org.jetbrains.kotlin") -> useVersion("1.5.20")
      }
    }
  }
}

dependencyResolutionManagement {
  @Suppress("UnstableApiUsage")
  repositories {
    google()
    mavenCentral()
    jcenter {
      content {
        // dokka
        includeGroup("org.jetbrains.dokka")
        includeModule("org.jetbrains", "markdown")
        // Detekt && dokka
        includeModule("org.jetbrains.kotlinx", "kotlinx-html-jvm")
        // https://youtrack.jetbrains.com/issue/IDEA-261387
        includeModule("org.jetbrains.trove4j", "trove4j")
      }
    }
  }
}

plugins {
  id("com.gradle.enterprise").version("3.5.2")
}

gradleEnterprise {
  buildScan {
    termsOfServiceUrl = "https://gradle.com/terms-of-service"
    termsOfServiceAgree = "yes"
    publishAlwaysIf(System.getenv("GITHUB_ACTIONS")?.toBoolean() == true)
  }
}

rootProject.name = "Dispatch"
enableFeaturePreview("VERSION_CATALOGS")
enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

include(":dependency-sync")
include(":dispatch-android-espresso")
include(":dispatch-android-espresso:samples")
include(":dispatch-android-lifecycle")
include(":dispatch-android-lifecycle-extensions")
include(":dispatch-android-lifecycle-extensions:samples")
include(":dispatch-android-lifecycle:samples")
include(":dispatch-android-viewmodel")
include(":dispatch-android-viewmodel:samples")
include(":dispatch-bom")
include(":dispatch-core")
include(":dispatch-test")
include(":dispatch-test-junit4")
include(":dispatch-test-junit4:samples")
include(":dispatch-test-junit5")
include(":dispatch-test-junit5:samples")
include(":dispatch-test:samples")
include(":dispatch-core:samples")
include(":dispatch-detekt")
include(":dispatch-detekt:samples")
include(":dispatch-internal-test")
include(":dispatch-internal-test-android")
include(":dispatch-sample")
