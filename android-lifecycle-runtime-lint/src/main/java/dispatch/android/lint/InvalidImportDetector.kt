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
import com.android.tools.lint.detector.api.Category.Companion.CORRECTNESS
import com.android.tools.lint.detector.api.Scope.*
import com.android.tools.lint.detector.api.Severity.*
import org.jetbrains.uast.*
import java.util.*

val ISSUE_INVALID_IMPORT = Issue.create(
  id = "InvalidImport",
  briefDescription = "Flags invalid imports.",
  explanation = "Flags invalid imports. One example is com.foo.bar.R.drawable. Instead just the generated class R should be imported and not R.drawable. Also you should never import anything that's in an internal package.",
  category = CORRECTNESS,
  priority = 10,
  severity = WARNING,
  implementation = Implementation(InvalidImportDetector::class.java, EnumSet.of(JAVA_FILE))
)

private val disallowedImports = listOf(".R.", "internal.", "internaI.", "dispatch.", "androidx.")

class InvalidImportDetector : Detector(), Detector.UastScanner {

  override fun getApplicableUastTypes() = listOf(UImportStatement::class.java)

  override fun createUastHandler(context: JavaContext) = InvalidImportHandler(context)

  class InvalidImportHandler(private val context: JavaContext) : UElementHandler() {
    override fun visitImportStatement(node: UImportStatement) {
      node.importReference?.let { importReference ->
        if (disallowedImports.any {
            importReference.asSourceString()
              .contains(it)
          }) {
          context.report(
            ISSUE_INVALID_IMPORT,
            node,
            context.getLocation(importReference),
            "Forbidden import"
          )
        }
      }
    }
  }
}
