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

package dispatch.detekt

import dispatch.detekt.rules.AndroidXLifecycleScope
import dispatch.detekt.rules.HardCodedDispatcher
import io.gitlab.arturbosch.detekt.api.Config
import io.gitlab.arturbosch.detekt.api.RuleSet
import io.gitlab.arturbosch.detekt.api.RuleSetProvider

/** @suppress */
public class DispatchRuleSetProvider : RuleSetProvider {

  override val ruleSetId: String = "dispatch"

  override fun instance(config: Config): RuleSet = RuleSet(
    id = ruleSetId,
    rules = listOf(
      AndroidXLifecycleScope(config),
      HardCodedDispatcher(config)
    )
  )
}
