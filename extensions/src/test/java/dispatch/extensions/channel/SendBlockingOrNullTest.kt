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

package dispatch.extensions.channel

import io.kotest.assertions.throwables.*
import io.kotest.matchers.*
import kotlinx.coroutines.channels.*
import org.junit.jupiter.api.*
import java.util.concurrent.*

internal class SendBlockingOrNullTest {

  @Test
  fun `sendBlockingOrNull should not be a suspend function`() {

    val channel = Channel<Int>(10)

    channel.sendBlockingOrNull(1)
  }

  @Test
  fun `sendBlockingOrNull should return unit when successful`() {

    val channel = Channel<Int>(10)

    channel.sendBlockingOrNull(1) shouldBe Unit
  }

  @Test
  fun `sendBlockingOrNull should return null when channel is closed`() {

    val channel = Channel<Int>()

    channel.close()
    channel.sendBlockingOrNull(2) shouldBe null
  }

  @Test
  fun `sendBlockingOrNull should throw CancellationException when channel is cancelled`() {

    val channel = Channel<Int>()

    channel.cancel()

    shouldThrow<CancellationException> {
      channel.sendBlockingOrNull(2)
    }
  }
}
