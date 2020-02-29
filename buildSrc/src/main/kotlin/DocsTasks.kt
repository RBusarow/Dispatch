/*
 * Copyright (C) 2020 Rick Busarow
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

import org.gradle.api.*
import java.io.*

fun cleanDocs() {

  val root = File("docs")

  root.walkTopDown()
    .forEach {
      if (it != root) {
        it.deleteRecursively()
      }
    }
}

fun copyRootFiles() {

  val root = File(".")

  val markdown = "./(.*).md".toRegex()
  val readMe = "./README.md".toRegex()

  root.walkTopDown()
    .maxDepth(1)
    .filter { file ->
      file.path.matches(markdown)
    }
    .forEach { file ->

      val newName = if (file.path.matches(readMe)) {

        file.updateLibraryVersions()
        "docs/index.md"
      } else {
        file.path.replace(markdown) { m1 ->
          "docs/$m1.md"
        }
      }

      file.copyTo(File(newName))
    }
}

fun Project.copyKdoc() {

  val root = File("$buildDir/dokka")

  val dirName = "$buildDir/dokka/${projectDir.name}"

  root.walkTopDown()
    .maxDepth(1)
    .filter { file ->

      file.isDirectory && file.path == dirName
    }
    .forEach { file ->

      file.copyRecursively(File("docs/dokka/${projectDir.name}"))
    }
}

fun Project.copyReadMe() {

  val regex = "$projectDir/README.md".toRegex()

  projectDir.walkTopDown()
    // A depth of 3 allows for nested modules to introduce their README's.
    // This probably won't ever be necessary but it's like 10ms.
    .maxDepth(3)
    .filter { file ->

      file.path.matches(regex)
    }
    .forEach { file ->

      val newName = "${rootProject.rootDir}/docs/modules/${projectDir.name}.md"

      file.copyTo(File(newName))
    }
}

fun Project.copySite() {

  val root = File("$rootDir/site")

  root.walkTopDown()
    .maxDepth(1)
    .forEach { file ->

      if (file.name.matches(".*mkdocs.yml".toRegex())) {
        // TOOD this is probably inefficient.
        // Fail the build if mkdocs.yml exists in root and has been changed
        // maybe move the root logic to the "build" dir?

        val rootMkDocs = File("$rootDir/mkdocs.yml")

        val existingFileHasChanges by lazy(LazyThreadSafetyMode.NONE) {
          !file.deepEquals(rootMkDocs)
        }

//        val changingRoot = rootMkDocs.exists() && existingFileHasChanges
        val changingRoot = false

        file.copyTo(
          target = File("$rootDir/mkdocs.yml"),
          overwrite = !changingRoot
        )
      } else if (file != root) {
        root.copyRecursively(File("$rootDir"), true)
      }
    }
}

fun Project.updateReadMeArtifactVersions() {

  val regex = "$projectDir/README.md".toRegex()

  projectDir.walkTopDown()
    // A depth of 3 allows for nested modules to introduce their README's.
    // This probably won't ever be necessary but it's like 10ms.
    .maxDepth(3)
    .filter { file ->

      file.path.matches(regex)
    }
    .forEach { file ->

      file.updateLibraryVersions()
    }
}

fun File.updateLibraryVersions(): File {

  val new = createTempFile()

  new.printWriter()
    .use { writer ->

      val dependencyMatchers = listOf(
        Libs.Androidx.Test.runner.toDependencyMatcher(),
        Libs.Androidx.Test.Espresso.core.toDependencyMatcher(),
        Libs.JUnit.jUnit4.toDependencyMatcher(),
        Libs.JUnit.jUnit5Vintage.toDependencyMatcher(),
        Libs.JUnit.jUnit5Runtime.toDependencyMatcher(),
        Libs.JUnit.jUnit5Params.toDependencyMatcher(),
        Libs.JUnit.jUnit5Api.toDependencyMatcher(),
        Libs.JUnit.jUnit5.toDependencyMatcher(),
        Libs.Kotlinx.Coroutines.core.toDependencyMatcher(),
        Libs.Kotlinx.Coroutines.android.toDependencyMatcher(),
        Libs.Kotlinx.Coroutines.test.toDependencyMatcher(),
        Libs.RickBusarow.Dispatch.espresso.toDependencyMatcher(),
        Libs.RickBusarow.Dispatch.extensions.toDependencyMatcher(),
        Libs.RickBusarow.Dispatch.lifecycleExtensions.toDependencyMatcher(),
        Libs.RickBusarow.Dispatch.lifecycle.toDependencyMatcher(),
        Libs.RickBusarow.Dispatch.viewModel.toDependencyMatcher(),
        Libs.RickBusarow.Dispatch.Test.jUnit4.toDependencyMatcher(),
        Libs.RickBusarow.Dispatch.Test.jUnit5.toDependencyMatcher(),
        Libs.RickBusarow.Dispatch.Test.core.toDependencyMatcher(),
        Libs.RickBusarow.Dispatch.core.toDependencyMatcher()
      )

      forEachLine { originalLine ->

        val newLine = dependencyMatchers.firstOrNull { matcher ->

          matcher.regex.matches(originalLine)
        }
          ?.let { matcher ->

            originalLine.replace(matcher)
          } ?: originalLine

        writer.println(newLine)
      }
    }

  check(delete() && new.renameTo(this)) { "failed to replace file" }

  return new
}

/**
 * examples:
 * ```
 * (.*)(['\"])com.rickbusarow.dispatch:dispatch-core*(.*)(['\"])(.*)
 * (.*)(['\"])androidx.test:runner.*(['"])(.*)
 * (.*)(['\"])androidx.test.espresso:espresso-core.*(['"])(.*)
 * ```
 */
private fun String.toMavenDependencyRegex() =
  "(.*)(['\"])${this.removeVersionSuffix()}.*(['\"])(.*)".toRegex()

private data class DependencyMatcher(val fullCoordinate: String, val regex: Regex)

private fun String.toDependencyMatcher() = DependencyMatcher(
  fullCoordinate = this,
  regex = this.toMavenDependencyRegex()
)

private fun String.replace(
  dependencyMatcher: DependencyMatcher
): String = this.replace(dependencyMatcher.regex) { m1, m2, m3, m4 ->
  "$m1$m2${dependencyMatcher.fullCoordinate}$m3$m4"
}

private fun String.removeVersionSuffix(): String = split(":").subList(0, 2).joinToString(":")

private fun String.replace(
  regex: Regex, block: (String) -> String
): String = regex.replace(this) { match ->
  block(match.destructured.component1())
}

private fun String.replace(
  regex: Regex, block: (String, String) -> String
): String = regex.replace(this) { match ->
  block(match.destructured.component1(), match.destructured.component2())
}

private fun String.replace(
  regex: Regex, block: (String, String, String) -> String
): String = regex.replace(this) { match ->
  block(
    match.destructured.component1(),
    match.destructured.component2(),
    match.destructured.component3()
  )
}

private fun String.replace(
  regex: Regex, block: (String, String, String, String) -> String
): String = regex.replace(this) { match ->
  block(
    match.destructured.component1(),
    match.destructured.component2(),
    match.destructured.component3(),
    match.destructured.component4()
  )
}

internal fun File.deepEquals(other: File): Boolean {
  return this.readText() == other.readText()
}
