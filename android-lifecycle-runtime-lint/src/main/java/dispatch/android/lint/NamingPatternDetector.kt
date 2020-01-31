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

import com.android.tools.lint.client.api.*
import com.android.tools.lint.detector.api.*
import org.jetbrains.uast.*
import java.util.*

val ISSUE_NAMING_PATTERN = Issue.create(
  id = "NamingPattern",
  briefDescription = "Names should be well named.",
  explanation = "Some long description about this issue",
  category = Category.CORRECTNESS,
  priority = 5,
  severity = Severity.WARNING,
  implementation = Implementation(
    NamingPatternDetector::class.java,
    EnumSet.of(Scope.JAVA_FILE, Scope.TEST_SOURCES)
  )
)

class NamingPatternDetector : Detector(), Detector.UastScanner {

  override fun getApplicableUastTypes() = listOf(UClass::class.java)
  override fun createUastHandler(context: JavaContext) =
    NamingPatternHandler(context)

  class NamingPatternHandler(private val context: JavaContext) :
    UElementHandler() {
    override fun visitClass(clazz: UClass) {
      if (clazz.name?.isDefinedCamelCase() == false) {
        context.report(
          ISSUE_NAMING_PATTERN, clazz,
          context.getNameLocation(clazz),
          "Not named in defined camel case."
        )
      }
    }
  }
}

private fun String.isDefinedCamelCase(): Boolean {
  val charArray = toCharArray()
  return charArray
    .mapIndexed { index, current ->
      current to charArray.getOrNull(index + 1)
    }
    .none {
      it.first.isUpperCase() && it.second?.isUpperCase() ?: false
    }
}

