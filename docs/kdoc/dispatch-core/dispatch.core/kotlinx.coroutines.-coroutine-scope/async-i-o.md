[dispatch-core](../../index.md) / [dispatch.core](../index.md) / [kotlinx.coroutines.CoroutineScope](index.md) / [asyncIO](./async-i-o.md)

# asyncIO

`fun <T> `[`CoroutineScope`](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/-coroutine-scope/index.html)`.asyncIO(context: `[`CoroutineContext`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.coroutines/-coroutine-context/index.html)` = EmptyCoroutineContext, start: `[`CoroutineStart`](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/-coroutine-start/index.html)` = CoroutineStart.DEFAULT, block: suspend `[`CoroutineScope`](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/-coroutine-scope/index.html)`.() -> T): `[`Deferred`](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/-deferred/index.html)`<T>` [(source)](https://github.com/RBusarow/Dispatch/tree/master/dispatch-core/src/main/java/dispatch/core/Async.kt#L51)

Creates a coroutine and returns its future result as an implementation of [Deferred](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/-deferred/index.html).

Extracts the [DispatcherProvider](../-dispatcher-provider/index.md) from the [CoroutineScope](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/-coroutine-scope/index.html) receiver, then uses its **io** [CoroutineDispatcher](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/-coroutine-dispatcher/index.html)
property (`coroutineContext.dispatcherProvider.io`) to call `async(...)`.

The `io` property always corresponds to the `DispatcherProvider` of the current `CoroutineScope`.

``` kotlin
runBlocking(someDispatcherProvider) {

    dispatcherName() shouldBe "runBlocking thread"

    asyncIO {

      dispatcherName() shouldBe "io"

    }.join()

  }
```

**See Also**

[async](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/async.html)

