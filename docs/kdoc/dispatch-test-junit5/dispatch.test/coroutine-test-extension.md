[dispatch-test-junit5](../index.md) / [dispatch.test](index.md) / [coroutineTestExtension](./coroutine-test-extension.md)

# coroutineTestExtension

`@ExperimentalCoroutinesApi inline fun coroutineTestExtension(crossinline scopeFactory: () -> `[`TestProvidedCoroutineScope`](https://rbusarow.github.io/Dispatch/dispatch-test/dispatch.test/-test-provided-coroutine-scope/index.md)` = { TestProvidedCoroutineScope() }): `[`CoroutineTestExtension`](https://rbusarow.github.io/Dispatch/dispatch-test/dispatch.test/-coroutine-test-extension/index.md) [(source)](https://github.com/RBusarow/Dispatch/tree/master/dispatch-test-junit5/src/main/java/dispatch/test/CoroutineTestExtension.kt#L166)

Factory function for creating a [CoroutineTestExtension](https://rbusarow.github.io/Dispatch/dispatch-test/dispatch.test/-coroutine-test-extension/index.md).

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

### Parameters

`scopeFactory` - *optional* factory for a custom [TestProvidedCoroutineScope](https://rbusarow.github.io/Dispatch/dispatch-test/dispatch.test/-test-provided-coroutine-scope/index.md).  If a factory is not provided,
the resultant scope uses the same [TestCoroutineDispatcher](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-test/kotlinx.coroutines.test/-test-coroutine-dispatcher/index.html) for each property in its [TestDispatcherProvider](https://rbusarow.github.io/Dispatch/dispatch-test/dispatch.test/-test-dispatcher-provider/index.md)

**See Also**

[CoroutineTestExtension](https://rbusarow.github.io/Dispatch/dispatch-test/dispatch.test/-coroutine-test-extension/index.md)

[CoroutineTest](https://rbusarow.github.io/Dispatch/dispatch-test/dispatch.test/-coroutine-test/index.md)

