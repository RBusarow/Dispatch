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
    .maxDepth(1)
    .filter { file ->

      when {
        file.parentFile != root                   -> false
        file.path.startsWith(prefix = "docs/css") -> false
        else                                      -> true
      }
    }
    .forEach {
      it.deleteRecursively()
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
        "docs/index.md"
      } else {
        markdown.replace(file.path) { match ->
          "docs/${match.destructured.component1()}.md"
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

      val newName = "$rootDir/docs/modules/${projectDir.name}.md"

      file.copyTo(File(newName))
    }
}
