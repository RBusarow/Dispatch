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

package dispatch.detekt.rules

import io.gitlab.arturbosch.detekt.test.lint
import io.kotest.core.spec.style.FreeSpec
import io.kotest.matchers.shouldBe

internal class AndroidxLifecycleScopeUsageTest : FreeSpec(
  {

    "importing androidx lifecycleScope and using it should report Finding" - {

      val text = """
      |package a.b.c
      |
      |import androidx.lifecycle.lifecycleScope
      |
      |class MyActivity : Activity() {
      |
      |  init {
      |    lifecycleScope.launch { println(1) }
      |  }
      |  
      |}
      |
    """.trimMargin()

      val rule = AndroidxLifecycleScopeUsage()

      val findings = rule.lint(text)

      findings.messages shouldBe listOf(
        "Using androidx.lifecycle.lifecycleScope instead of dispatch.lifecycle.lifecycleScope."
      )
    }

    "importing androidx lifecycleScope without using it should NOT report Finding" - {

      val text = """
      |package a.b.c
      |
      |import androidx.lifecycle.lifecycleScope
      |
      |class MyActivity : Activity() {
      |  
      |}
      |
    """.trimMargin()

      val rule = AndroidxLifecycleScopeUsage()

      val findings = rule.lint(text)

      findings shouldBe listOf()
    }

    "issue id should be AndroidxLifecycleScopeUsage" - {

      val rule = AndroidxLifecycleScopeUsage()

      rule.issue.id shouldBe "AndroidxLifecycleScopeUsage"
    }

    "issue should not be reported if suppressing AndroidxLifecycleScopeUsage" - {

      val text = """
      |@file:Suppress("AndroidxLifecycleScopeUsage")
      |
      |package a.b.c
      |
      |import androidx.lifecycle.lifecycleScope
      |
      |class MyActivity : Activity() {
      |
      |  init {
      |    lifecycleScope.launch { println(1) }
      |  }
      |  
      |}
    """.trimMargin()

      val rule = AndroidxLifecycleScopeUsage()

      val findings = rule.lint(text)

      findings shouldBe listOf()
    }

    "using Dispatch lifecycleScope should not report Finding" - {

      val text = """
      |package a.b.c
      |
      |import dispatch.android.lifecycle.lifecycleScope
      |
      |class MyActivity : Activity() {
      |
      |  init {
      |    lifecycleScope.launch { println(1) }
      |  }
      |  
      |}
      |
    """.trimMargin()

      val rule = AndroidxLifecycleScopeUsage()

      val findings = rule.lint(text)

      findings shouldBe emptyList()
    }
  })



