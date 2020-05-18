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

package dispatch.extensions.flow.internal

/**
 * CircularArray implementation which will hold the latest of up to `size` elements.
 *
 * After the cache has been filled, all further additions will overwrite the least recent value.
 *
 * @param size the maximum number of elements to store in the array
 */
internal class CircularArray<T>(size: Int) : Iterable<T> {

  private val array: Array<Any?> = arrayOfNulls(size)
  private var count: Int = 0
  private var tail: Int = -1

  fun add(item: T) {
    tail = (tail + 1) % array.size
    array[tail] = item
    if (count < array.size) count++
  }

  override fun iterator(): Iterator<T> = object : Iterator<T> {
    private val arraySnapshot = array.copyOf()
    private val tailSnapshot = tail

    private var _index = 0

    private val head: Int
      get() = when (count) {
        arraySnapshot.size -> (tailSnapshot + 1) % count
        else               -> 0
      }

    @Suppress("UNCHECKED_CAST")
    private fun get(index: Int): T = when (count) {
      arraySnapshot.size -> arraySnapshot[(head + index) % arraySnapshot.size] as T
      else               -> arraySnapshot[index] as T
    }

    override fun hasNext(): Boolean = _index < count
    override fun next(): T = get(_index++)

  }

  override fun toString(): String = "${javaClass.simpleName}[array=${contentToString()}]"

  private fun contentToString(): String = joinToString { "$it" }
}
