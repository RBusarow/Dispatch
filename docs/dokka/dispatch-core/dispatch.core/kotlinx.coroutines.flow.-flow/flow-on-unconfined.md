[dispatch-core](../../index.md) / [dispatch.core](../index.md) / [kotlinx.coroutines.flow.Flow](index.md) / [flowOnUnconfined](./flow-on-unconfined.md)

# flowOnUnconfined

`@ExperimentalCoroutinesApi fun <T> `[`Flow`](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines.flow/-flow/index.html)`<T>.flowOnUnconfined(): `[`Flow`](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines.flow/-flow/index.html)`<T>` [(source)](https://github.com/RBusarow/Dispatch/tree/master/dispatch-core/src/main/java/dispatch/core/Flow.kt#L87)

Extracts the [DispatcherProvider](../-dispatcher-provider/index.md) from the `coroutineContext` of the *collector* coroutine,
then uses its [DispatcherProvider.unconfined](../-dispatcher-provider/unconfined.md) property to call `flowOn(theDispatcher)`,
and returns the result.

``` kotlin
runBlocking(someDispatcherProvider) {

    dispatcherName() shouldBe "runBlocking thread"

    flow {

      dispatcherName() shouldBe "unconfined"

      emit(Unit)
    }.flowOnUnconfined()  // switch to the "unconfined" dispatcher for everything upstream
      .collect()          // collect the flow from the "main" dispatcher

  }
```

**See Also**

[flowOn](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines.flow/flow-on.html)

