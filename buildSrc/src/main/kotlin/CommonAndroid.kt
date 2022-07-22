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

import com.android.build.gradle.TestedExtension
import org.gradle.api.JavaVersion.VERSION_1_8
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure

@Suppress("MagicNumber", "LongMethod")
fun Project.commonAndroid() {

  configure<TestedExtension> {
    compileSdkVersion(32)

    defaultConfig {
      minSdk = 21
      targetSdk = 32
      versionName = "1.0.0-beta10"

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
      sourceCompatibility = VERSION_1_8
      targetCompatibility = VERSION_1_8
    }

    lintOptions {
      disable("ObsoleteLintCustomCheck")
      disable("MissingTranslation")
      enable("InvalidPackage")
      enable("Interoperability")
      isAbortOnError = true
    }

    testOptions {
      unitTests.isIncludeAndroidResources = true
      unitTests.isReturnDefaultValues = true
      animationsDisabled = true
    }
  }
}
