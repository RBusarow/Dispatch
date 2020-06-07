[dispatch-test](../index.md) / [dispatch.core.test](index.md) / [runBlockingProvided](./run-blocking-provided.md)

# runBlockingProvided

`@ExperimentalCoroutinesApi fun runBlockingProvided(context: `[`CoroutineContext`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.coroutines/-coroutine-context/index.html)` = EmptyCoroutineContext, block: suspend `[`CoroutineScope`](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/-coroutine-scope/index.html)`.() -> `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)`): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html) [(source)](https://github.com/RBusarow/Dispatch/tree/master/dispatch-test/src/main/java/dispatch/core/test/Builders.kt#L41)

Delegates to [runBlocking](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/run-blocking.html), but injects a [DispatcherProvider](https://rbusarow.github.io/Dispatch/dispatch-core/dispatch.core/-dispatcher-provider/index.md) into the created [CoroutineScope](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/-coroutine-scope/index.html).

The resultant [CoroutineContext](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.coroutines/-coroutine-context/index.html) will use a [BlockingEventLoop](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/-blocking-event-loop/index.html)
as its default [ContinuationInterceptor](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.coroutines/-continuation-interceptor/index.html).

If the `context` does not contain a `DispatcherProvider`,
a [TestDispatcherProvider](-test-dispatcher-provider/index.md) will be created using the [BlockingEventLoop](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/-blocking-event-loop/index.html) interceptor.

``` kotlin
@Test
fun someTest() = runBlockingProvided {

  val subject = SomeClass(this)

  val myData = Data()

  subject.dataDeferred()
    .await() shouldBe myData

}
```

### Parameters

`context` - The base `CoroutineContext` which will be modified
to use a [TestCoroutineDispatcher](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-test/kotlinx.coroutines.test/-test-coroutine-dispatcher/index.html) and [TestDispatcherProvider](-test-dispatcher-provider/index.md).
[EmptyCoroutineContext](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.coroutines/-empty-coroutine-context/index.html) is used if one is not provided.

**See Also**

[runBlocking](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/run-blocking.html)

[testProvided](test-provided.md)

