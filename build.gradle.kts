/*
 * Copyright (C) 2019-2020 Rick Busarow
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

import org.jetbrains.kotlin.gradle.tasks.*

/*
 * Copyright (C) 2019-2020 Rick Busarow
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

buildscript {

  repositories {
    mavenCentral()
    maven("https://oss.sonatype.org/content/repositories/snapshots")
    google()
    jcenter()
    gradlePluginPortal()
  }
  dependencies {
    classpath(BuildPlugins.androidGradlePlugin)
    classpath("com.github.ben-manes:gradle-versions-plugin:0.27.0")
    classpath(BuildPlugins.kotlinGradlePlugin)
    classpath("com.github.dcendents:android-maven-gradle-plugin:2.1")
    classpath("io.codearte.gradle.nexus:gradle-nexus-staging-plugin:0.21.2")
  }
}

//apply("io.codearte.nexus-staging")

allprojects {
  repositories {
    mavenCentral()
    google()
    jcenter()
  }
}

tasks.register("clean").configure {
  delete("build")
}

subprojects {
  tasks.withType<KotlinCompile>()
    .configureEach {
      kotlinOptions.jvmTarget = "1.6"
      // https://youtrack.jetbrains.com/issue/KT-24946
      // kotlinOptions.freeCompilerArgs = listOf(
      //     "-progressive",
      //     "-Xskip-runtime-version-check",
      //     "-Xdisable-default-scripting-plugin",
      //     "-Xuse-experimental=kotlin.Experimental"
      // )
    }
}
