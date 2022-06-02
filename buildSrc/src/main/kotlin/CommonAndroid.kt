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
import com.android.build.gradle.*
import org.gradle.api.*
import org.gradle.api.JavaVersion.*
import org.gradle.kotlin.dsl.*
import java.io.*

@Suppress("MagicNumber", "LongMethod", "UnstableApiUsage")
fun CommonExtension<*, *, *, *>.commonAndroid() {

  compileSdk = 31

  defaultConfig {

    minSdk = 21
    // `targetSdk` doesn't have a single base interface, as of AGP 7.1.0
    when (this@defaultConfig) {
      is LibraryBaseFlavor -> targetSdk = 31
      is ApplicationBaseFlavor -> targetSdk = 31
    }

    testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
  }

  buildTypes {
    getByName("release") {
      isMinifyEnabled = false
      proguardFiles(
        getDefaultProguardFile("proguard-android-optimize.txt"),
        "proguard-rules.pro"
      )
    }
  }

  compileOptions {
    sourceCompatibility = VERSION_11
    targetCompatibility = VERSION_11
  }

  lint {
    disable.addAll(setOf("ObsoleteLintCustomCheck", "MissingTranslation"))
    enable.addAll(setOf("InvalidPackage", "Interoperability"))
    abortOnError = true
    checkDependencies = true
    checkAllWarnings = true
  }

  testOptions {
    unitTests.isIncludeAndroidResources = true
    unitTests.isReturnDefaultValues = true
    animationsDisabled = true
  }
}
