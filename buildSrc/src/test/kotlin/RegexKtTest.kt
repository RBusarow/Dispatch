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

import io.kotlintest.*
import io.kotlintest.data.*
import io.kotlintest.tables.*
import org.junit.jupiter.api.*

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


internal class RegexKtTest {

  @Test
  fun `string matches with multiple regexes`() {

    val numbers = ".*[0-9].*".toRegex()
    val lowercase = ".*[a-z].*".toRegex()
    val uppercase = ".*[A-Z].*".toRegex()

    forall(
      row("1lowercaseUPPERCASE", arrayOf(numbers, lowercase, uppercase), true),
      row("1lowercase", arrayOf(numbers, lowercase, uppercase), true),
      row("1UPPERCASE", arrayOf(numbers, lowercase, uppercase), true),
      row("1", arrayOf(numbers, lowercase, uppercase), true),
      row("lowercaseUPPERCASE", arrayOf(numbers, lowercase, uppercase), true),
      row("lowercase", arrayOf(numbers, lowercase, uppercase), true),
      row("UPPERCASE", arrayOf(numbers, lowercase, uppercase), true),
      row("", arrayOf(numbers, lowercase, uppercase), false),
      row("1lowercaseUPPERCASE", arrayOf(numbers, lowercase), true),
      row("1lowercase", arrayOf(numbers, lowercase), true),
      row("1UPPERCASE", arrayOf(numbers, lowercase), true),
      row("1", arrayOf(numbers, lowercase), true),
      row("lowercaseUPPERCASE", arrayOf(numbers, lowercase), true),
      row("lowercase", arrayOf(numbers, lowercase), true),
      row("UPPERCASE", arrayOf(numbers, lowercase), false),
      row("", arrayOf(numbers, lowercase), false),
      row("1lowercaseUPPERCASE", arrayOf(numbers, uppercase), true),
      row("1lowercase", arrayOf(numbers, uppercase), true),
      row("1UPPERCASE", arrayOf(numbers, uppercase), true),
      row("1", arrayOf(numbers, uppercase), true),
      row("lowercaseUPPERCASE", arrayOf(numbers, uppercase), true),
      row("lowercase", arrayOf(numbers, uppercase), false),
      row("UPPERCASE", arrayOf(numbers, uppercase), true),
      row("", arrayOf(numbers, uppercase), false),
      row("1lowercaseUPPERCASE", arrayOf(numbers), true),
      row("1lowercase", arrayOf(numbers), true),
      row("1UPPERCASE", arrayOf(numbers), true),
      row("1", arrayOf(numbers), true),
      row("lowercaseUPPERCASE", arrayOf(numbers), false),
      row("lowercase", arrayOf(numbers), false),
      row("UPPERCASE", arrayOf(numbers), false),
      row("", arrayOf(numbers), false),
      row("1lowercaseUPPERCASE", arrayOf(lowercase, uppercase), true),
      row("1lowercase", arrayOf(lowercase, uppercase), true),
      row("1UPPERCASE", arrayOf(lowercase, uppercase), true),
      row("1", arrayOf(lowercase, uppercase), false),
      row("lowercaseUPPERCASE", arrayOf(lowercase, uppercase), true),
      row("lowercase", arrayOf(lowercase, uppercase), true),
      row("UPPERCASE", arrayOf(lowercase, uppercase), true),
      row("", arrayOf(lowercase, uppercase), false),
      row("1lowercaseUPPERCASE", arrayOf(lowercase), true),
      row("1lowercase", arrayOf(lowercase), true),
      row("1UPPERCASE", arrayOf(lowercase), false),
      row("1", arrayOf(lowercase), false),
      row("lowercaseUPPERCASE", arrayOf(lowercase), true),
      row("lowercase", arrayOf(lowercase), true),
      row("UPPERCASE", arrayOf(lowercase), false),
      row("", arrayOf(lowercase), false),
      row("1lowercaseUPPERCASE", arrayOf(uppercase), true),
      row("1lowercase", arrayOf(uppercase), false),
      row("1UPPERCASE", arrayOf(uppercase), true),
      row("1", arrayOf(uppercase), false),
      row("lowercaseUPPERCASE", arrayOf(uppercase), true),
      row("lowercase", arrayOf(uppercase), false),
      row("UPPERCASE", arrayOf(uppercase), true),
      row("", arrayOf(uppercase), false),
      row("1lowercaseUPPERCASE", arrayOf(), false),
      row("1lowercase", arrayOf(), false),
      row("1UPPERCASE", arrayOf(), false),
      row("1", arrayOf(), false),
      row("lowercaseUPPERCASE", arrayOf(), false),
      row("lowercase", arrayOf(), false),
      row("UPPERCASE", arrayOf(), false),
      row("", arrayOf(), false)
    ) { str, regexes, expected ->

      str.matches(*regexes) shouldBe expected
      str.reversed().matches(*regexes) shouldBe expected
    }

  }

}
