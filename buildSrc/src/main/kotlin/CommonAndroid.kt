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

import com.android.build.api.dsl.ApplicationBaseFlavor
import com.android.build.api.dsl.CommonExtension
import com.android.build.api.dsl.LibraryBaseFlavor
import org.gradle.api.JavaVersion.VERSION_1_8
import org.gradle.api.Project
import org.gradle.kotlin.dsl.withType
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

@Suppress("UnstableApiUsage", "MagicNumber")
fun CommonExtension<*, *, *, *>.commonAndroid(target: Project) {

  compileSdk = 32

  defaultConfig {
    minSdk = 21

    // `targetSdk` doesn't have a single base interface, as of AGP 7.1.0
    when (this@defaultConfig) {
      is LibraryBaseFlavor -> targetSdk = 32
      is ApplicationBaseFlavor -> targetSdk = 32
    }

    vectorDrawables {
      useSupportLibrary = true
    }
  }

  buildTypes.configureEach {
    isMinifyEnabled = false
  }

  compileOptions {
    sourceCompatibility = VERSION_1_8
    targetCompatibility = VERSION_1_8
  }

  sourceSets {
    findByName("androidTest")?.java?.srcDirs("src/androidTest/kotlin")
    findByName("main")?.java?.srcDirs("src/main/kotlin")
    findByName("test")?.java?.srcDirs("src/test/kotlin")
  }

  testOptions {
    unitTests.isIncludeAndroidResources = true
    unitTests.isReturnDefaultValues = true
    animationsDisabled = true
  }

  target.tasks.create("lintMain") {
    doFirst {
      target.tasks.withType<KotlinCompile>()
        .configureEach {
          kotlinOptions {
            allWarningsAsErrors = true
          }
        }
    }

    finalizedBy("lintDebug")
  }

  target.tasks.create("testJvm") {

    dependsOn("testDebugUnitTest")
  }

  target.tasks.create("buildTests") {

    dependsOn("assembleDebugUnitTest")
  }

  lint {
    disable.addAll(setOf("ObsoleteLintCustomCheck", "MissingTranslation", "InvalidPackage"))
    enable.addAll(setOf("Interoperability"))
    abortOnError = true
    checkDependencies = true
    checkAllWarnings = true
  }

  target.pluginManager.withPlugin("com.vanniktech.maven.publish.base") {

    // explicit API mode doesn't work in the IDE for Android projects
    // https://youtrack.jetbrains.com/issue/KT-37652
    // disabling this bandaid because it also complains about exlicit API things in test sources
    target.tasks
      .matching { it is KotlinCompile }
      .configureEach {
        val task = this
        val shouldEnable = !task.name.contains("test", ignoreCase = true)
        val kotlinCompile = task as KotlinCompile

        if (shouldEnable && !project.hasProperty("kotlin.optOutExplicitApi")) {
          if ("-Xexplicit-api=strict" !in kotlinCompile.kotlinOptions.freeCompilerArgs) {
            kotlinCompile.kotlinOptions.freeCompilerArgs += "-Xexplicit-api=strict"
          }
        } else {
          kotlinCompile.kotlinOptions.freeCompilerArgs = kotlinCompile.kotlinOptions
            .freeCompilerArgs
            .filterNot { it == "-Xexplicit-api=strict" }
        }
      }
  }
}
