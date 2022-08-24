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

import org.gradle.api.Project
import org.gradle.api.artifacts.MinimalExternalModuleDependency
import org.gradle.api.artifacts.VersionCatalog
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.api.artifacts.VersionConstraint
import org.gradle.api.provider.Provider
import org.gradle.kotlin.dsl.getByType

val Project.catalogs: VersionCatalogsExtension
  get() = extensions.getByType(VersionCatalogsExtension::class)

val Project.libsCatalog: VersionCatalog
  get() = catalogs.named("libs")

fun VersionCatalog.dependency(alias: String): Provider<MinimalExternalModuleDependency> {
  return findLibrary(alias).get()
}

fun VersionCatalog.version(alias: String): VersionConstraint {
  return findVersion(alias).get()
}
