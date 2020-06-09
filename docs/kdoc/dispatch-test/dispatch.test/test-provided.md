[dispatch-test](../index.md) / [dispatch.test](index.md) / [testProvided](./test-provided.md)

# testProvided

`@ExperimentalCoroutinesApi fun testProvided(context: `[`CoroutineContext`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.coroutines/-coroutine-context/index.html)` = EmptyCoroutineContext, testBody: suspend `[`TestCoroutineScope`](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-test/kotlinx.coroutines.test/-test-coroutine-scope/index.html)`.() -> `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)`): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html) [(source)](https://github.com/RBusarow/Dispatch/tree/master/dispatch-test/src/main/java/dispatch/test/Builders.kt#L73)

Delegates to [runBlockingTest](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-test/kotlinx.coroutines.test/run-blocking-test.html), but injects a [DispatcherProvider](https://rbusarow.github.io/Dispatch/dispatch-core/dispatch.core/-dispatcher-provider/index.md) into the created [TestCoroutineScope](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-test/kotlinx.coroutines.test/-test-coroutine-scope/index.html).

If the `context`'s [ContinuationInterceptor](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.coroutines/-continuation-interceptor/index.html) is not a [TestCoroutineDispatcher](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-test/kotlinx.coroutines.test/-test-coroutine-dispatcher/index.html),
then a new [TestCoroutineDispatcher](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-test/kotlinx.coroutines.test/-test-coroutine-dispatcher/index.html) will be created.

If the `context` does not contain a `DispatcherProvider`,
a [TestDispatcherProvider](-test-dispatcher-provider/index.md) will be created using the `TestCoroutineDispatcher`.

``` kotlin
@Test
fun someTest() = testProvided {

  val subject = SomeClass(this)

  val myData = Data()

  subject.dataDeferred()
    .await() shouldBe myData

}
```

### Parameters

`context` - The base `CoroutineContext` which will be modified
to use a `TestCoroutineDispatcher` and `TestDispatcherProvider`.
[EmptyCoroutineContext](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.coroutines/-empty-coroutine-context/index.html) is used if one is not provided.

**See Also**

[runBlockingTest](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-test/kotlinx.coroutines.test/run-blocking-test.html)

[runBlockingProvided](run-blocking-provided.md)

`@ExperimentalCoroutinesApi fun `[`TestProvidedCoroutineScope`](-test-provided-coroutine-scope/index.md)`.testProvided(testBody: suspend `[`TestCoroutineScope`](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-test/kotlinx.coroutines.test/-test-coroutine-scope/index.html)`.() -> `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)`): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html) [(source)](https://github.com/RBusarow/Dispatch/tree/master/dispatch-test/src/main/java/dispatch/test/Builders.kt#L102)

Delegates to [runBlockingTest](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-test/kotlinx.coroutines.test/run-blocking-test.html), but injects a [DispatcherProvider](https://rbusarow.github.io/Dispatch/dispatch-core/dispatch.core/-dispatcher-provider/index.md) into the created [TestCoroutineScope](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-test/kotlinx.coroutines.test/-test-coroutine-scope/index.html).

If the `context`'s [ContinuationInterceptor](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.coroutines/-continuation-interceptor/index.html) is not a [TestCoroutineDispatcher](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-test/kotlinx.coroutines.test/-test-coroutine-dispatcher/index.html),
then a new [TestCoroutineDispatcher](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-test/kotlinx.coroutines.test/-test-coroutine-dispatcher/index.html) will be created.

If the `context` does not contain a `DispatcherProvider`,
a [TestDispatcherProvider](-test-dispatcher-provider/index.md) will be created using the `TestCoroutineDispatcher`.

``` kotlin
@Test
fun someTest() = testProvided {

  val subject = SomeClass(this)

  val myData = Data()

  subject.dataDeferred()
    .await() shouldBe myData

}
```

``` kotlin
val scope = TestProvidedCoroutineScope()

@Test
fun someTest() = scope.testProvided {

  val subject = SomeClass(this)

  val myData = Data()

  subject.dataDeferred()
    .await() shouldBe myData

}
```

**See Also**

[runBlockingTest](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-test/kotlinx.coroutines.test/run-blocking-test.html)

[runBlockingProvided](run-blocking-provided.md)

