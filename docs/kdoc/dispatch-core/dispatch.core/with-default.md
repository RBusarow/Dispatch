[dispatch-core](../index.md) / [dispatch.core](index.md) / [withDefault](./with-default.md)

# withDefault

`suspend fun <T> withDefault(context: `[`CoroutineContext`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.coroutines/-coroutine-context/index.html)` = EmptyCoroutineContext, block: suspend `[`CoroutineScope`](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/-coroutine-scope/index.html)`.() -> T): T` [(source)](https://github.com/RBusarow/Dispatch/tree/master/dispatch-core/src/main/java/dispatch/core/Suspend.kt#L34)

Calls the specified suspending block with a given coroutine context, suspends until it completes, and returns
the result.

Extracts the [DispatcherProvider](-dispatcher-provider/index.md) from the `coroutineContext` of the current coroutine,
then uses its **default** [CoroutineDispatcher](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/-coroutine-dispatcher/index.html) property to call `withContext(theDispatcher)`,
and returns the result.

The *default* property always corresponds to the `DispatcherProvider` of the current coroutine.

``` kotlin
runBlocking(someDispatcherProvider) {

    dispatcherName() shouldBe "runBlocking thread"

    withDefault {

      dispatcherName() shouldBe "default"

    }
  }
```

**See Also**

[withContext](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/with-context.html)

