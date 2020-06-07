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

package dispatch.core.test

import dispatch.core.test.CoroutineTestExtension.ScopeFactory
import dispatch.core.test.internal.getAnnotationRecursive
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.*
import org.junit.jupiter.api.extension.*
import org.junit.jupiter.api.extension.support.TypeBasedParameterResolver
import kotlin.coroutines.CoroutineContext

/**
 * JUnit 5 [ParameterResolver] [extension][Extension] for injecting and managing a [TestProvidedCoroutineScope] in a test instance.
 * This creates a new instance of [TestProvidedCoroutineScope] each time the scope is injected, optionally using a custom [ScopeFactory].
 *
 * If this extension is initialized via [RegisterExtension], there is also a [scope] property which is automatically managed.
 *
 * ### Before Each:
 * [Dispatchers.Main] is set to the [TestCoroutineDispatcher] used by the [CoroutineContext].
 *
 * ### After Each:
 * * [cleanupTestCoroutines][TestCoroutineScope.cleanupTestCoroutines] is called to ensure there are no leaking coroutines.  Any unfinished coroutine
 * will throw an [UncompletedCoroutinesError].
 * * [Dispatchers.Main] is reset via [Dispatchers.resetMain].
 *
 * ### Requires JUnit 5.
 * ``` groovy
 * dependencies {
 *   testImplementation "org.junit.jupiter:junit-jupiter:5.6.2"
 * }
 * ```
 *
 * @see CoroutineTestExtension
 * @see CoroutineTest
 * @param scopeFactory *optional* factory for a custom [TestProvidedCoroutineScope].  If a factory is not provided,
 * the resultant scope uses the same [TestCoroutineDispatcher] for each property in its [TestDispatcherProvider]
 * @sample samples.RegisterSample
 * @sample samples.RegisterWithFactorySample
 * @sample samples.CoroutineTestExtensionExtendWithSample
 */
@ExperimentalCoroutinesApi
public class CoroutineTestExtension(
  private val scopeFactory: ScopeFactory = ScopeFactory()
) : TypeBasedParameterResolver<TestProvidedCoroutineScope>(),
    BeforeEachCallback,
    AfterEachCallback {

  private val lazyScope = lazy { scopeFactory.create() }
  val scope: TestProvidedCoroutineScope
    get() = lazyScope.value

  private val contextScopeMap = mutableMapOf<ExtensionContext, TestProvidedCoroutineScope>()

  override fun resolveParameter(
    parameterContext: ParameterContext,
    extensionContext: ExtensionContext
  ): TestProvidedCoroutineScope {

    val scope = contextScopeMap[extensionContext]

    // if a scope was already created for this context (not sure how), return it
    if (scope != null) return scope

    val annotation = extensionContext.getAnnotationRecursive<CoroutineTest>()

    // if the extension is registered via @RegisterExtension,
    // then its factory would be passed at instance creation and is already set
    val newScope = if (annotation == null) {
      scopeFactory.create()
    } else {
      @Suppress("UNCHECKED_CAST")
      val factoryClass = annotation.scopeFactory

      val factory = factoryClass.java.getConstructor().newInstance() as? ScopeFactory
        ?: throw ParameterResolutionException(
          """A ${CoroutineTest::class.simpleName} annotation was found with an incompatible factory type.
            |
            |The specified factory must be <${ScopeFactory::class.qualifiedName}> or a subtype.
            |
            |The provided factory type was:  <${factoryClass.qualifiedName}>
            |""".trimMargin()
        )

      factory.create()
    }

    contextScopeMap[extensionContext] = newScope

    return newScope
  }

  /**
   * @suppress
   */
  override fun beforeEach(context: ExtensionContext) {
    contextScopeMap[context]?.dispatcherProvider?.main?.let {
      Dispatchers.setMain(it)
    }
  }

  /**
   * @suppress
   */
  override fun afterEach(context: ExtensionContext) {

    if (lazyScope.isInitialized()) {
      scope.cleanupTestCoroutines()
    }

    contextScopeMap[context]?.cleanupTestCoroutines()
    Dispatchers.resetMain()
  }

  @ExperimentalCoroutinesApi
  open class ScopeFactory {

    open fun create(): TestProvidedCoroutineScope = TestProvidedCoroutineScope()
  }
}

/**
 * Factory function for creating a [CoroutineTestExtension].
 *
 * @see CoroutineTestExtension
 * @see CoroutineTest
 * @param scopeFactory *optional* factory for a custom [TestProvidedCoroutineScope].  If a factory is not provided,
 * the resultant scope uses the same [TestCoroutineDispatcher] for each property in its [TestDispatcherProvider]
 * @sample samples.RegisterSample
 * @sample samples.RegisterWithFactorySample
 */
@ExperimentalCoroutinesApi
public inline fun coroutineTestExtension(
  crossinline scopeFactory: () -> TestProvidedCoroutineScope = { TestProvidedCoroutineScope() }
) =
  CoroutineTestExtension(object : CoroutineTestExtension.ScopeFactory() {
    override fun create(): TestProvidedCoroutineScope = scopeFactory.invoke()
  })
