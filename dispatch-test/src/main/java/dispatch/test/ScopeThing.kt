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

package dispatch.test

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.runTest

@ExperimentalCoroutinesApi
public abstract class HermitCoroutines<E : Any>(
  private val factory: (TestScope) -> E
) {

  // /**
  //  * the base class for the environment... It can't implement `TestScope`, so instead it just
  //  * decorates. If you need an actual `TestScope`-typed instance to pass into something, it's still
  //  * available.
  //  */
  // public interface TestEnvironmentScope

  /**
   * Uses `runTest { ... }` to create and manage a TestScope, creating a new instance of the
   * environment for each test.
   */
  public fun test(
    action: suspend context(E) TestScope.() -> Unit
  ) {

    runTest scope@{

      val instance = factory(this@scope)

      action.invoke(instance, this@scope)
    }
  }
}
