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
@file:Suppress("UndocumentedPublicProperty")

import com.vanniktech.maven.publish.AndroidSingleVariantLibrary
import com.vanniktech.maven.publish.JavadocJar.Dokka
import com.vanniktech.maven.publish.KotlinJvm
import com.vanniktech.maven.publish.MavenPublishBaseExtension
import com.vanniktech.maven.publish.SonatypeHost.DEFAULT
import com.vanniktech.maven.publish.tasks.JavadocJar
import com.vanniktech.maven.publish.tasks.SourcesJar
import org.jetbrains.dokka.gradle.AbstractDokkaLeafTask
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

plugins {
  id("com.vanniktech.maven.publish.base")
  id("dokka")
  id("dependency-guard")
}

version = project.property("VERSION_NAME") as String

pluginManager.withPlugin("com.android.library") {
  configurePublish(android = true)
}

pluginManager.withPlugin("org.jetbrains.kotlin.jvm") {
  configurePublish(android = false)
}

fun configurePublish(android: Boolean) {
  configure<MavenPublishBaseExtension> {
    publishToMavenCentral(DEFAULT)
    signAllPublications()

    // import all settings from root project and project-specific gradle.properties files
    pomFromGradleProperties()

    val artifactId = project.property("POM_ARTIFACT_ID") as String
    val pomDescription = project.property("POM_NAME") as String

    if (android) {
      configure(AndroidSingleVariantLibrary(sourcesJar = true))
      setPublicationProperties(pomDescription, artifactId)
    } else {
      configure(KotlinJvm(javadocJar = Dokka(taskName = "dokkaHtml"), sourcesJar = true))
      setPublicationProperties(pomDescription, artifactId)
    }
  }
}

fun setPublicationProperties(
  pomDescription: String,
  artifactIdOrNull: String?
) {
  afterEvaluate {
    configure<PublishingExtension> {
      publications.filterIsInstance<MavenPublication>()
        .forEach { publication ->

          if (artifactIdOrNull != null) {
            publication.artifactId = artifactIdOrNull
          }
          publication.pom.description.set(pomDescription)
          publication.groupId = project.property("GROUP") as String
        }
    }
  }
}

tasks.withType(PublishToMavenRepository::class.java).configureEach {
  notCompatibleWithConfigurationCache("See https://github.com/gradle/gradle/issues/13468")
}

tasks.register("checkVersionIsSnapshot") {
  doLast {
    val expected = "-SNAPSHOT"
    val versionString = version as String
    require(versionString.endsWith(expected)) {
      "The project's version name must be suffixed with `$expected` when checked in" +
        " to the main branch, but instead it's `$versionString`."
    }
  }
}

tasks.withType(PublishToMavenRepository::class.java).configureEach {
  notCompatibleWithConfigurationCache("See https://github.com/gradle/gradle/issues/13468")
}

tasks.withType(Jar::class.java).configureEach {
  notCompatibleWithConfigurationCache("")
}
tasks.withType(SourcesJar::class.java).configureEach {
  notCompatibleWithConfigurationCache("")
}
tasks.withType(JavadocJar::class.java).configureEach {
  notCompatibleWithConfigurationCache("")
}
tasks.withType(Sign::class.java).configureEach {
  notCompatibleWithConfigurationCache("")
  // skip signing for -LOCAL and -SNAPSHOT publishing
  onlyIf {

    !(version as String).endsWith("SNAPSHOT") && !(version as String).endsWith("LOCAL")
  }
}

var skipDokka = false

tasks.matching { it.name == "javaDocReleaseGeneration" }.configureEach {
  onlyIf { !skipDokka }
}
tasks.withType(AbstractDokkaLeafTask::class.java) {
  onlyIf { !skipDokka }
}

// Integration tests require `publishToMavenLocal`, but they definitely don't need Dokka output,
// and generating kdoc for everything takes forever -- especially on a GitHub Actions server.
// So for integration tests, skip Dokka tasks.
val publishToMavenLocalNoDokka = tasks.register("publishToMavenLocalNoDokka") {

  doFirst { skipDokka = true }

  finalizedBy(rootProject.tasks.matching { it.name == "publishToMavenLocal" })
}

tasks.matching { it.name == "publishToMavenLocal" }.configureEach {
  mustRunAfter(publishToMavenLocalNoDokka)
}

inline fun <reified T : Any> propertyDelegate(name: String): ReadWriteProperty<Any, T> {
  return object : ReadWriteProperty<Any, T> {

    override fun getValue(thisRef: Any, property: KProperty<*>): T {
      return project.property(name) as T
    }

    override fun setValue(thisRef: Any, property: KProperty<*>, value: T) {
      project.setProperty(name, value)
    }
  }
}
