[dispatch-test-junit5](../../../index.md) / [dispatch.core.test](../../index.md) / [CoroutineTestExtension](../index.md) / [ScopeFactory](index.md) / [&lt;init&gt;](./-init-.md)

# &lt;init&gt;

`ScopeFactory()`

Class used to create the [TestProvidedCoroutineScope](https://rbusarow.github.io/Dispatch/dispatch-test/dispatch.core.test/-test-provided-coroutine-scope/index.md) used in [CoroutineTestExtension](https://rbusarow.github.io/Dispatch/dispatch-test/dispatch.core.test/-coroutine-test-extension/index.md).

In order to provide a custom implementation of [TestProvidedCoroutineScope](https://rbusarow.github.io/Dispatch/dispatch-test/dispatch.core.test/-test-provided-coroutine-scope/index.md):

1. Create a custom factory which has a default constructor and extends this `ScopeFactory`
2. Annotate your test class with [CoroutineTest](https://rbusarow.github.io/Dispatch/dispatch-test/dispatch.core.test/-coroutine-test/index.md) and pass your custom factory's `KClass` in as its parameter.

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

