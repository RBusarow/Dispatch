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

plugins {
  id("java")
  id("kotlin-multiplatform")
  id("java-library")
}

common()

kotlin {
  if (!path.endsWith("samples")) {
    explicitApi()
  }

  targets {
    jvm {
      compilations.all {
        kotlinOptions {
          jvmTarget = "1.8"
        }
      }
    }
    js(BOTH) {
      browser()
      nodejs()
    }

    linuxX64()

    mingwX64()

    macosX64()
    tvos()

    watchosArm32()
    watchosArm64()
    watchosX86()
    watchosX64()

    iosX64()
    iosArm64()
    iosArm32()
  }

  sourceSets {

    val commonMain by getting {
      dependencies {
        compileOnly(kotlin("stdlib"))
        api("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.5.0")
      }
    }

    val jvmMain by getting {
      dependsOn(commonMain)
      dependencies {
        api("org.jetbrains.kotlinx:kotlinx-coroutines-core-jvm:1.5.0")
      }
    }

    val desktopMain by creating { dependsOn(commonMain) }
    val macosX64Main by getting { dependsOn(desktopMain) }
    val mingwX64Main by getting { dependsOn(desktopMain) }
    val linuxX64Main by getting { dependsOn(desktopMain) }
    val iosX64Main by getting { dependsOn(desktopMain) }
    val iosArm64Main by getting { dependsOn(desktopMain) }
    val iosArm32Main by getting { dependsOn(desktopMain) }
    val watchosX86Main by getting { dependsOn(desktopMain) }
    val watchosArm32Main by getting { dependsOn(desktopMain) }
    val watchosArm64Main by getting { dependsOn(desktopMain) }
    val watchosX64Main by getting { dependsOn(desktopMain) }
    val tvosMain by getting { dependsOn(desktopMain) }
    val jvmTest by getting {
//      dependencies {
//        implementation(project(Projects.Engine))
//        implementation(project(Projects.JunitRunner))
//      }
    }

    all {
      languageSettings.useExperimentalAnnotation("kotlin.time.ExperimentalTime")
      languageSettings.useExperimentalAnnotation("kotlin.experimental.ExperimentalTypeInference")
      languageSettings.useExperimentalAnnotation("kotlin.RequiresOptIn")
    }
  }
}

java {
  // force Java 8 source when building java-only artifacts.
  // This is different than the Kotlin jvm target.
  sourceCompatibility = JavaVersion.VERSION_1_8
  targetCompatibility = JavaVersion.VERSION_1_8
}

val testJvm by tasks.registering {
  dependsOn("test")
}

val buildTests by tasks.registering {
  dependsOn("testClasses")
}
