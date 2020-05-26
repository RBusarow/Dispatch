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

package dispatch.extensions

import dispatch.core.test.*
import dispatch.extensions.flow.*
import io.kotest.assertions.throwables.*
import io.kotest.matchers.*
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import org.junit.jupiter.api.*

@FlowPreview
@ExperimentalCoroutinesApi
internal class CacheTest {

  @Test
  fun illegalHistorySizes_throwException() = testProvided {

    shouldThrow<IllegalArgumentException> { flowOf("a").cache(-3) }
    shouldThrow<IllegalArgumentException> { flowOf("a").cache(-2) }
    shouldThrow<IllegalArgumentException> { flowOf("a").cache(-1) }
    shouldThrow<IllegalArgumentException> { flowOf("a").cache(0) }
  }

  @Test
  fun firstCollection_doesNotEmitCache() = testProvided {

    val list = listOf(1, 2, 3, 4, 5)

    val flow = list.asFlow()
      .cache(2)

    val collected = flow.toList()

    collected shouldBe list
  }

  @Test
  fun secondCollection_receivesCacheFirst() = testProvided {

    val list = listOf(1, 2, 3, 4, 5)

    val flow = list.asFlow()
      .cache(2)

    flow.collect() // collect [1, 2, 3, 4, 5], cache = [4, 5]

    val collect2 = flow.toList()

    collect2 shouldBe listOf(4, 5, 1, 2, 3, 4, 5)
  }

  @Test
  fun thirdCollection_getsUpdatedCache() = testProvided {

    val list = listOf(1, 2, 3, 4, 5)

    val flow = list.asFlow()
      .cache(2)

    flow.take(4)
      .collect() // collect [1, 2, 3, 4], cache = [3, 4]
    flow.take(3)
      .collect() // collect [3, 4, 1], cache = [4, 1]

    val collect3 = flow.toList()

    collect3 shouldBe listOf(4, 1, 1, 2, 3, 4, 5)
  }

  @Test
  fun largeCache_buildsOverMultipleCollectors() = testProvided {

    val list = listOf(1, 2, 3, 4, 5)

    val flow = list.asFlow()
      .cache(8)

    // collect twice to generate the cache
    flow.take(4)
      .collect()                    // collect [1, 2, 3, 4], cache = [1, 2, 3, 4]
    flow.collect()                  // collect [1, 2, 3, 4, 1, 2, 3, 4, 5], cache = [2, 3, 4, 1, 2, 3, 4, 5]

    val collect3 = flow.toList()

    val expected = listOf(2, 3, 4, 1, 2, 3, 4, 5, 1, 2, 3, 4, 5)

    collect3 shouldBe expected
  }

}
