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

import io.gitlab.arturbosch.detekt.Detekt
import io.gitlab.arturbosch.detekt.report.ReportMergeTask
import org.gradle.internal.impldep.org.junit.experimental.categories.Categories.CategoryFilter.exclude

plugins {
  id("io.gitlab.arturbosch.detekt")
}

val reportMerge by tasks.registering(ReportMergeTask::class) {
  output.set(rootProject.buildDir.resolve("reports/detekt/merged.sarif"))
}

val detektExcludes = listOf(
  "**/resources/**",
  "**/buildSrc/**",
  "**/build/**"
)

tasks.withType<Detekt> {

  parallel = true
  config.from(files("$rootDir/detekt/detekt-config.yml"))
  buildUponDefaultConfig = true

  // If in CI, merge sarif reports.  Skip this locally because it's not worth looking at
  // and the task is unnecessarily chatty.
  if (!System.getenv("CI").isNullOrBlank()) {
    finalizedBy(reportMerge)
    reportMerge.configure {
      input.from(sarifReportFile)
    }
  }

  reports {
    xml.required.set(true)
    html.required.set(true)
    txt.required.set(false)
    sarif.required.set(true)
  }

  setSource(files(projectDir))

  include("**/*.kt", "**/*.kts")
  exclude(detektExcludes)
  subprojects.forEach { sub ->
    exclude("**/${sub.projectDir.relativeTo(rootDir)}/**")
  }
}
