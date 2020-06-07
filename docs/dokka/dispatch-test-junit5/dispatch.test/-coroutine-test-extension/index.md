[dispatch-test-junit5](../../index.md) / [dispatch.test](../index.md) / [CoroutineTestExtension](./index.md)

# CoroutineTestExtension

`@ExperimentalCoroutinesApi class CoroutineTestExtension : `[`TypeBasedParameterResolver`](https://junit.org/junit5/docs/current/api/org/junit/jupiter/api/extension/support/TypeBasedParameterResolver.html)`<`[`TestProvidedCoroutineScope`](https://rbusarow.github.io/Dispatch/dispatch-test/dispatch.test/-test-provided-coroutine-scope/index.md)`>, `[`BeforeEachCallback`](https://junit.org/junit5/docs/current/api/org/junit/jupiter/api/extension/BeforeEachCallback.html)`, `[`AfterEachCallback`](https://junit.org/junit5/docs/current/api/org/junit/jupiter/api/extension/AfterEachCallback.html) [(source)](https://github.com/RBusarow/Dispatch/tree/master/dispatch-test-junit5/src/main/java/dispatch/test/CoroutineTestExtension.kt#L56)

JUnit 5 [ParameterResolver](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.io/java.io.-file/extension.html)[Extension](https://junit.org/junit5/docs/current/api/org/junit/jupiter/api/extension/Extension.html) for injecting and managing a [TestProvidedCoroutineScope](https://rbusarow.github.io/Dispatch/dispatch-test/dispatch.test/-test-provided-coroutine-scope/index.md) in a test instance.
This creates a new instance of [TestProvidedCoroutineScope](https://rbusarow.github.io/Dispatch/dispatch-test/dispatch.test/-test-provided-coroutine-scope/index.md) each time the scope is injected, optionally using a custom [ScopeFactory](https://rbusarow.github.io/Dispatch/dispatch-test/dispatch.test/-coroutine-test-extension/-scope-factory/index.md).

If this extension is initialized via [RegisterExtension](https://junit.org/junit5/docs/current/api/org/junit/jupiter/api/extension/RegisterExtension.html), there is also a [scope](https://rbusarow.github.io/Dispatch/dispatch-test/dispatch.test/-coroutine-test-extension/scope.md) property which is automatically managed.

### Before Each:

[Dispatchers.Main](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/-dispatchers/-main.html) is set to the [TestCoroutineDispatcher](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-test/kotlinx.coroutines.test/-test-coroutine-dispatcher/index.html) used by the [CoroutineContext](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.coroutines/-coroutine-context/index.html).

### After Each:

* [cleanupTestCoroutines](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-test/kotlinx.coroutines.test/-test-coroutine-scope/cleanup-test-coroutines.html) is called to ensure there are no leaking coroutines.  Any unfinished coroutine
will throw an [UncompletedCoroutinesError](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-test/kotlinx.coroutines.test/-uncompleted-coroutines-error/index.html).
* [Dispatchers.Main](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/-dispatchers/-main.html) is reset via [Dispatchers.resetMain](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-test/kotlinx.coroutines.test/kotlinx.coroutines.-dispatchers/reset-main.html).

### Requires JUnit 5.

``` groovy
dependencies {
  testImplementation "org.junit.jupiter:junit-jupiter:5.6.2"
}
```

``` kotlin
class RegisterSample {

  @JvmField
  @RegisterExtension
  val extension = coroutineTestExtension()

  @Test
  fun `extension should be a TestProvidedCoroutineScope`() = runBlocking<Unit> {

    extension.scope.shouldBeInstanceOf<TestProvidedCoroutineScope>()
  }

  @Test
  fun `extension should automatically inject into functions`(scope: TestProvidedCoroutineScope) =
    runBlocking {

      val subject = SomeClass(scope)

      val resultDeferred = subject.someFunction()

      scope.advanceUntilIdle()

      resultDeferred.await() shouldBe someValue
    }
}
```

``` kotlin
class RegisterWithFactorySample {

  @JvmField
  @RegisterExtension
  val extension = coroutineTestExtension {
    TestProvidedCoroutineScope(context = CoroutineName("custom name"))
  }

  @Test
  fun `extension should provide a scope from the custom factory`() = runBlocking {

    extension.scope.coroutineContext[CoroutineName] shouldBe CoroutineName("custom name")
  }
}
```

``` kotlin
@ExtendWith(CoroutineTestExtension::class)
class CoroutineTestExtensionExtendWithSample(
  val testScope: TestProvidedCoroutineScope
) {

  @Test
  fun `injected scope should be injected`() {

    testScope shouldNotBe null
  }

}
```

### Parameters

`scopeFactory` - *optional* factory for a custom [TestProvidedCoroutineScope](https://rbusarow.github.io/Dispatch/dispatch-test/dispatch.test/-test-provided-coroutine-scope/index.md).  If a factory is not provided,
the resultant scope uses the same [TestCoroutineDispatcher](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-test/kotlinx.coroutines.test/-test-coroutine-dispatcher/index.html) for each property in its [TestDispatcherProvider](https://rbusarow.github.io/Dispatch/dispatch-test/dispatch.test/-test-dispatcher-provider/index.md)

**See Also**

[CoroutineTestExtension](https://rbusarow.github.io/Dispatch/dispatch-test/dispatch.test/-coroutine-test-extension/index.md)

[CoroutineTest](https://rbusarow.github.io/Dispatch/dispatch-test/dispatch.test/-coroutine-test/index.md)

### Types

| Name | Summary |
|---|---|
| [ScopeFactory](-scope-factory/index.md) | Class used to create the [TestProvidedCoroutineScope](https://rbusarow.github.io/Dispatch/dispatch-test/dispatch.test/-test-provided-coroutine-scope/index.md) used in [CoroutineTestExtension](https://rbusarow.github.io/Dispatch/dispatch-test/dispatch.test/-coroutine-test-extension/index.md).`class ScopeFactory` |

### Constructors

| Name | Summary |
|---|---|
| [&lt;init&gt;](-init-.md) | JUnit 5 [ParameterResolver](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.io/java.io.-file/extension.html)[Extension](https://junit.org/junit5/docs/current/api/org/junit/jupiter/api/extension/Extension.html) for injecting and managing a [TestProvidedCoroutineScope](https://rbusarow.github.io/Dispatch/dispatch-test/dispatch.test/-test-provided-coroutine-scope/index.md) in a test instance. This creates a new instance of [TestProvidedCoroutineScope](https://rbusarow.github.io/Dispatch/dispatch-test/dispatch.test/-test-provided-coroutine-scope/index.md) each time the scope is injected, optionally using a custom [ScopeFactory](https://rbusarow.github.io/Dispatch/dispatch-test/dispatch.test/-coroutine-test-extension/-scope-factory/index.md).`CoroutineTestExtension(scopeFactory: `[`ScopeFactory`](https://rbusarow.github.io/Dispatch/dispatch-test/dispatch.test/-coroutine-test-extension/-scope-factory/index.md)` = ScopeFactory())` |

### Properties

| Name | Summary |
|---|---|
| [scope](scope.md) | A single `TestProvidedCoroutineScope` instance which is reset via [cleanUpTestCoroutines](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-test/kotlinx.coroutines.test/-test-coroutine-scope/cleanup-test-coroutines.html) after each test.`val scope: `[`TestProvidedCoroutineScope`](https://rbusarow.github.io/Dispatch/dispatch-test/dispatch.test/-test-provided-coroutine-scope/index.md) |
