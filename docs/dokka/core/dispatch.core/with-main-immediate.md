[core](../index.md) / [dispatch.core](index.md) / [withMainImmediate](./with-main-immediate.md)

# withMainImmediate

`suspend fun <T> withMainImmediate(context: `[`CoroutineContext`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.coroutines/-coroutine-context/index.html)` = EmptyCoroutineContext, block: suspend `[`CoroutineScope`](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/-coroutine-scope/index.html)`.() -> T): T` [(source)](https://github.com/RBusarow/Dispatch/tree/master/core/src/main/java/dispatch/core/Suspend.kt#L97)

Calls the specified suspending block with a given coroutine context, suspends until it completes, and returns
the result.

Extracts the [DispatcherProvider](-dispatcher-provider/index.md) from the `coroutineContext` of the current coroutine,
then uses its **mainImmediate** [CoroutineDispatcher](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/-coroutine-dispatcher/index.html) property to call `withContext(theDispatcher)`,
and returns the result.

The `mainImmediate` property always corresponds to the `DispatcherProvider` of the current coroutine.

``` kotlin
runBlocking(someDispatcherProvider) {

    dispatcherName() shouldBe "runBlocking thread"

    withMainImmediate {

      dispatcherName() shouldBe "main immediate"

    }
  }
```

**See Also**

[withContext](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/with-context.html)

