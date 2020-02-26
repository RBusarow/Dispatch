[core-test-junit5](../../index.md) / [dispatch.core.test](../index.md) / [TestCoroutineExtension](index.md) / [&lt;init&gt;](./-init-.md)

# &lt;init&gt;

`TestCoroutineExtension(factory: () -> `[`TestProvidedCoroutineScope`](https://rbusarow.github.io/Dispatch/core-test/dispatch.core.test/-test-provided-coroutine-scope/index.md)` = { TestProvidedCoroutineScope() })`

Convenience interface for tagging a test class with a JUnit 5 extension.  This creates a new instance
of [testScope](https://rbusarow.github.io/Dispatch/core-test/dispatch.core.test/-test-coroutine-extension/test-scope.md) before each test, optionally using a custom [factory](https://rbusarow.github.io/Dispatch/core-test/dispatch.core.test/-test-coroutine-extension/factory.md).

### Before Each:

* A new [TestProvidedCoroutineScope](https://rbusarow.github.io/Dispatch/core-test/dispatch.core.test/-test-provided-coroutine-scope/index.md) is created using [factory](https://rbusarow.github.io/Dispatch/core-test/dispatch.core.test/-test-coroutine-extension/factory.md).
* [Dispatchers.Main](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/-dispatchers/-main.html) is set to the [TestCoroutineDispatcher](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-test/kotlinx.coroutines.test/-test-coroutine-dispatcher/index.html) used by the [CoroutineContext](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.coroutines/-coroutine-context/index.html).

### After Each:

* [cleanupTestCoroutines](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-test/kotlinx.coroutines.test/-test-coroutine-scope/cleanup-test-coroutines.html) is called to ensure there are no leaking coroutines.  Any unfinished coroutine
will throw an [UncompletedCoroutinesError](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-test/kotlinx.coroutines.test/-uncompleted-coroutines-error/index.html).
* [Dispatchers.Main](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/-dispatchers/-main.html) is reset via [Dispatchers.resetMain](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-test/kotlinx.coroutines.test/kotlinx.coroutines.-dispatchers/reset-main.html).

### Requires JUnit 5.

``` groovy
dependencies {
  testImplementation "org.junit.jupiter:junit-jupiter:5.5.1"
}
```

``` kotlin
@ExperimentalCoroutinesApi
class TestCoroutineExtensionSample {

  @JvmField
  @RegisterExtension
  val extension = TestCoroutineExtension()

  @Test
  fun `extension should be a TestProvidedCoroutineScope`() = runBlocking {

    extension.testScope.shouldBeInstanceOf<TestProvidedCoroutineScope>()
  }

  @Test
  fun `the testScope and dispatcher have normal test functionality`() = runBlocking {

    val subject = SomeClass(extension.testScope)

    val resultDeferred = subject.someFunction()

    extension.dispatcher.advanceUntilIdle()

    resultDeferred.await() shouldBe someValue
  }
}
```

``` kotlin
@ExperimentalCoroutinesApi
class TestCoroutineExtensionWithFactorySample {

  val customScope = TestProvidedCoroutineScope(
    context = CoroutineName("custom name")
  )

  @JvmField
  @RegisterExtension
  val extension = TestCoroutineExtension { customScope }

  @Test
  fun `extension should provide a scope from the custom factory`() = runBlocking {

    extension.testScope shouldBe customScope
  }
}
```

### Parameters

`factory` - *optional* factory for a custom [TestProvidedCoroutineScope](https://rbusarow.github.io/Dispatch/core-test/dispatch.core.test/-test-provided-coroutine-scope/index.md).  If a factory is not provided,
the resultant scope uses the same [TestCoroutineDispatcher](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-test/kotlinx.coroutines.test/-test-coroutine-dispatcher/index.html) for each property in its [TestDispatcherProvider](https://rbusarow.github.io/Dispatch/core-test/dispatch.core.test/-test-dispatcher-provider/index.md)