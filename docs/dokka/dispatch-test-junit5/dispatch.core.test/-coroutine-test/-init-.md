[dispatch-test-junit5](../../index.md) / [dispatch.core.test](../index.md) / [CoroutineTest](index.md) / [&lt;init&gt;](./-init-.md)

# &lt;init&gt;

`CoroutineTest(scopeFactory: `[`KClass`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.reflect/-k-class/index.html)`<*> = CoroutineTestExtension.ScopeFactory::class)`

Annotation for specifying a custom [CoroutineTestExtension.ScopeFactory](https://rbusarow.github.io/Dispatch/dispatch-test/dispatch.core.test/-coroutine-test-extension/-scope-factory/index.md) while
extending a test class or function with [CoroutineTestExtension](https://rbusarow.github.io/Dispatch/dispatch-test/dispatch.core.test/-coroutine-test-extension/index.md).

``` kotlin
@CoroutineTest
class CoroutineTestDefaultFactorySample(
  val testScope: TestProvidedCoroutineScope
) {

  @Test
  fun `extension should automatically inject into test class`() = runBlocking {

    val subject = SomeClass(testScope)

    val resultDeferred = subject.someFunction()

    testScope.advanceUntilIdle()

    resultDeferred.await() shouldBe someValue
  }

}
```

``` kotlin
class CoroutineTestNamedFactorySample {

  class TestCoroutineScopeWithJobFactory : CoroutineTestExtension.ScopeFactory() {

    override fun create(): TestProvidedCoroutineScope {
      return TestProvidedCoroutineScope(context = Job())
    }
  }

  @CoroutineTest(TestCoroutineScopeWithJobFactory::class)
  class CustomFactorySample(val testScope: TestProvidedCoroutineScope) {

    @Test
    fun `injected scope should have a Job context`() = runBlocking {

      testScope.coroutineContext[Job] shouldNotBe null
    }

  }
}
```

### Parameters

`scopeFactory` - *optional* KClass which extends [CoroutineTestExtension.ScopeFactory](https://rbusarow.github.io/Dispatch/dispatch-test/dispatch.core.test/-coroutine-test-extension/-scope-factory/index.md).
**This class must have a default constructor**
An instance will be automatically initialized inside the [CoroutineTestExtension](https://rbusarow.github.io/Dispatch/dispatch-test/dispatch.core.test/-coroutine-test-extension/index.md) and used to create custom [TestProvidedCoroutineScope](https://rbusarow.github.io/Dispatch/dispatch-test/dispatch.core.test/-test-provided-coroutine-scope/index.md) instances.

**See Also**

[CoroutineTestExtension](https://rbusarow.github.io/Dispatch/dispatch-test/dispatch.core.test/-coroutine-test-extension/index.md)

