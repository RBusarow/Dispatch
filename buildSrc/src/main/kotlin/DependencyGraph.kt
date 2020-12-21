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

import org.codehaus.groovy.runtime.DefaultGroovyMethods.execute
import org.gradle.api.*
import org.gradle.api.artifacts.*
import java.io.*
import java.util.*
import kotlin.collections.LinkedHashMap
import kotlin.collections.LinkedHashSet
import kotlin.collections.component1
import kotlin.collections.component2
import kotlin.collections.set
import kotlin.io.println

@Suppress("TooGenericExceptionThrown", "NestedBlockDepth", "ComplexMethod", "LongMethod")
fun Project.createDependencyGraph() {

  val dot = File(buildDir, "reports/dependency-graph/project.dot")

  dot.parentFile.mkdirs()

  dot.delete()

  var dotText = """
    digraph {
      graph [label="$name",labelloc=t,fontsize=30,ranksep=1.4];
      node [style=filled, fillcolor="#bbbbbb"];
      rankdir=TB;

  """.trimIndent()

  val rootProjects = mutableListOf<Project>()
  var queue = mutableListOf(rootProject)

  while (queue.isNotEmpty()) {
    val project = queue.removeAt(0)

    rootProjects.add(project)
    queue.addAll(project.childProjects.values)
  }

  val projects = LinkedHashSet<Project>()
  val dependencies = LinkedHashMap<Pair<Project, Project>, List<String>>()

  val multiPlatformProjects = mutableListOf<Project>()
  val jsProjects = mutableListOf<Project>()
  val androidProjects = mutableListOf<Project>()
  val javaProjects = mutableListOf<Project>()

  queue = mutableListOf(rootProject)

  while (queue.isNotEmpty()) {

    val project = queue.removeAt(0)
    queue.addAll(project.childProjects.values)

    if (project.plugins.hasPlugin("org.jetbrains.kotlin.multiplatform")) {
      multiPlatformProjects.add(project)
    }
    if (project.plugins.hasPlugin("org.jetbrains.kotlin.js")) {
      jsProjects.add(project)
    }
    if (project.plugins.hasPlugin("com.android.library") || project.plugins.hasPlugin("com.android.application")) {
      androidProjects.add(project)
    }
    if (project.plugins.hasPlugin("java-library") || project.plugins.hasPlugin("java")) {
      javaProjects.add(project)
    }

    project.configurations.forEach { config ->

      config.dependencies
        .withType(ProjectDependency::class.java)
        .map { it.dependencyProject }
        .forEach { dependency ->
          projects.add(project)
          projects.add(dependency)
          rootProjects.remove(dependency)

          val graphKey = project to dependency
          val traits = dependencies.computeIfAbsent(graphKey) { listOf<String>() }
            .toMutableList()

          if (config.name.toLowerCase(Locale.getDefault())
            .endsWith("implementation")
          ) {
            traits.add("style=dotted")
          }

          dependencies[graphKey] = traits
        }
    }
  }

  val sortedProjects = projects.sortedBy { it.path }

  dotText += "\n  # Projects\n\n"

  sortedProjects.forEach { project ->

    val traits = mutableListOf<String>()

    if (rootProjects.contains(project)) {
      traits.add("shape=box")
    }

    val color = when {
      multiPlatformProjects.contains(project) -> "#ffd2b3"
      jsProjects.contains(project) -> "#ffffba"
      androidProjects.contains(project) -> "#baffc9"
      javaProjects.contains(project) -> "#ffb3ba"
      else -> "#eeeeee"
    }

    traits.add("fillcolor=\"$color\"")

    dotText += "  \"${project.path}\" [${traits.joinToString(", ")}];\n"
  }

  dotText += "\n  {rank = same;"

  sortedProjects.forEach { project ->

    if (rootProjects.contains(project)) {
      dotText += " \"${project.path}\";"
    }
  }

  dotText += "}\n"

  dotText += "\n  # Dependencies\n\n"

  dependencies.forEach { (key, traits) ->

    dotText += "  \"${key.first.path}\" -> \"${key.second.path}\""

    if (traits.isNotEmpty()) {
      dotText += " [${traits.joinToString(", ")}]"
    }
    dotText += "\n"
  }

  dotText += "}\n"

  dot.writeText(dotText)

  @Suppress("DEPRECATION")
  val process = execute("dot -Tpng -O project.dot", arrayOf(""), dot.parentFile)
  process.waitFor()
  if (process.exitValue() != 0) {
    throw RuntimeException(process.errorStream.toString())
  }

  println("file at ${File("$dot.png")}")
}
