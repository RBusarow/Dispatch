[dispatcher-provider](../index.md) / [com.rickbusarow.dispatcherprovider](index.md) / [withIO](./with-i-o.md)

# withIO

`suspend fun <T> withIO(context: `[`CoroutineContext`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.coroutines/-coroutine-context/index.html)` = EmptyCoroutineContext, block: suspend `[`CoroutineScope`](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/-coroutine-scope/index.html)`.() -> T): T` [(source)](https://github.com/RBusarow/Dispatch/tree/master/dispatcher-provider/src/main/java/com/rickbusarow/dispatcherprovider/Builders.kt#L55)

Calls the specified suspending block with a given coroutine context, suspends until it completes, and returns
the result.

Extracts the [DispatcherProvider](-dispatcher-provider/index.md) from the `coroutineContext` of the current coroutine,
then uses its **io** [CoroutineDispatcher](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/-coroutine-dispatcher/index.html) property to call `withContext(theDispatcher)`,
and returns the result.

The `io` property always corresponds to the `DispatcherProvider` of the current coroutine.

**See Also**

[withContext](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/with-context.html)

