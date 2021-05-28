/*
 * Copyright (C) 2021 Rick Busarow
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

package dispatch.core

import kotlinx.coroutines.*
import kotlin.coroutines.*


/**
 * Extracts the **io** [CoroutineDispatcher] out of the [CoroutineScope],
 * using [DefaultDispatcherProvider.get] to provide one if necessary.
 *
 * Note that `CoroutineContext` is immutable, so if a new `DefaultDispatcherProvider` is needed,
 * a new instance will be created each time.
 *
 * @see CoroutineScope.dispatcherProvider
 */
public val CoroutineScope.ioDispatcher: CoroutineDispatcher
  get() = dispatcherProvider.io
