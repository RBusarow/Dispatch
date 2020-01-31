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

package dispatch.android.lint

import com.android.tools.lint.detector.api.*
import com.android.tools.lint.detector.api.Location
import org.jetbrains.uast.*
import org.w3c.dom.*
import java.util.*
import java.util.regex.*

val ISSUE_TODO = Issue.create(
  id = "Todo",
  briefDescription = "Marks todos in any given file.",
  explanation = "Marks todo in any given file since they should be resolved.",
  category = Category.USABILITY,
  priority = 6,
  severity = Severity.WARNING,
  implementation = Implementation(
    TodoDetector::class.java,
    EnumSet.of(
      Scope.JAVA_FILE,
      Scope.GRADLE_FILE, Scope.PROGUARD_FILE, Scope.MANIFEST, Scope.RESOURCE_FILE
    ),
    EnumSet.of(
      Scope.JAVA_FILE,
      Scope.GRADLE_FILE,
      Scope.PROGUARD_FILE,
      Scope.MANIFEST,
      Scope.RESOURCE_FILE
    )
  )
)

private const val COMMENT = "TODO:"
private val pattern = Pattern.compile("[\\t]*$COMMENT.*")

class TodoDetector : Detector(),
  Detector.UastScanner,
  Detector.GradleScanner,
  Detector.OtherFileScanner,
  Detector.XmlScanner {
  override fun getApplicableUastTypes() = listOf(UClass::class.java)

  override fun visitDocument(context: XmlContext, document: Document) {
    // Needs to be overridden but we we'll do the work in afterCheckFile.
  }

  override fun afterCheckFile(context: Context) {
    val source = context.getContents()
      .toString()
    val matcher = pattern.matcher(source)

    while (matcher.find()) {
      val start = matcher.start()
      val end = matcher.end()

      val location =
        Location.create(
          context.file,
          source,
          start,
          end
        )
      context.report(ISSUE_TODO, location, "Contains todo.")
    }
  }
}
