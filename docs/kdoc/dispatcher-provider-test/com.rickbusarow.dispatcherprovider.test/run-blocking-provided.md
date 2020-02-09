[dispatcher-provider-test](../index.md) / [com.rickbusarow.dispatcherprovider.test](index.md) / [runBlockingProvided](./run-blocking-provided.md)

# runBlockingProvided

`@ExperimentalCoroutinesApi fun runBlockingProvided(context: `[`CoroutineContext`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.coroutines/-coroutine-context/index.html)` = EmptyCoroutineContext, block: suspend `[`CoroutineScope`](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/-coroutine-scope/index.html)`.() -> `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)`): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html) [(source)](https://github.com/RBusarow/Dispatch/tree/master/dispatcher-provider-test/src/main/java/com/rickbusarow/dispatcherprovider/test/Builders.kt#L39)

Delegates to [runBlocking](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/run-blocking.html), but injects a [DispatcherProvider](#) into the created [CoroutineScope](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/-coroutine-scope/index.html).

The resultant [CoroutineContext](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.coroutines/-coroutine-context/index.html) will use a [BlockingEventLoop](kotlinx.coroutines.BlockingEventLoop)
as its default [ContinuationInterceptor](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.coroutines/-continuation-interceptor/index.html).

If the `context` does not contain a `DispatcherProvider`,
a [TestDispatcherProvider](-test-dispatcher-provider/index.md) will be created using the `BlockingEventLoop` interceptor.

### Parameters

`context` - The base `CoroutineContext` which will be modified
to use a `TestCoroutineDispatcher` and `TestDispatcherProvider`.
[EmptyCoroutineContext](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.coroutines/-empty-coroutine-context/index.html) is used if one is not provided.

**See Also**

[runBlocking](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/run-blocking.html)

