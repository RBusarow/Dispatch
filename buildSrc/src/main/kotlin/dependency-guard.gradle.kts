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

plugins {
  id("com.dropbox.dependency-guard")
}

if (project == rootProject) {
  // record the root project's *build* classpath
  configureClasspath("classpath")
}

pluginManager.withPlugin("java") {
  // If we got here, we're either in an empty "parent" module without a build plugin
  // (and no configurations), or we're in a vanilla Kotlin module.  In this case, we can just look
  // at configuration names.
  configurations
    .matching {
      it.name.endsWith("runtimeClasspath", ignoreCase = true) &&
        !it.name.endsWith("testRuntimeClasspath", ignoreCase = true)
    }
    .all { configureClasspath(name) }
}

pluginManager.withPlugin("com.android.library") {
  // For Android modules, just hard-code `releaseRuntimeClasspath` for the release variant.
  // This is actually pretty robust, since if this configuration ever changes, dependency-guard
  // will fail when trying to look it up.
  extensions.configure< com.android.build.gradle.LibraryExtension> {
    configureClasspath("releaseRuntimeClasspath")
  }
}

fun configureClasspath(classpathName: String) {
  configure<com.dropbox.gradle.plugins.dependencyguard.DependencyGuardPluginExtension> {
    // Tell dependency-guard to check the `$classpathName` configuration's dependencies.
    configuration(classpathName)
  }
}

// Delete any existing dependency files/directories before recreating with a new baseline task.
val dependencyGuardDeleteBaselines by tasks.registering(Delete::class) {
  delete("dependencies")
}
tasks.matching { it.name == "dependencyGuardBaseline" }
  .configureEach { dependsOn(dependencyGuardDeleteBaselines) }
