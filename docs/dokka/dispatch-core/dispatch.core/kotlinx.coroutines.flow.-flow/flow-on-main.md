[dispatch-core](../../index.md) / [dispatch.core](../index.md) / [kotlinx.coroutines.flow.Flow](index.md) / [flowOnMain](./flow-on-main.md)

# flowOnMain

`@ExperimentalCoroutinesApi fun <T> `[`Flow`](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines.flow/-flow/index.html)`<T>.flowOnMain(): `[`Flow`](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines.flow/-flow/index.html)`<T>` [(source)](https://github.com/RBusarow/Dispatch/tree/master/dispatch-core/src/main/java/dispatch/core/Flow.kt#L59)

Extracts the [DispatcherProvider](../-dispatcher-provider/index.md) from the `coroutineContext` of the *collector* coroutine,
then uses its [DispatcherProvider.main](../-dispatcher-provider/main.md) property to call `flowOn(theDispatcher)`,
and returns the result.

``` kotlin
runBlocking(someDispatcherProvider) {

    dispatcherName() shouldBe "runBlocking thread"

    flow {

      dispatcherName() shouldBe "main"

      emit(Unit)
    }.flowOnMain()    // switch to the "main" dispatcher for everything upstream
      .collect()      // collect the flow from the "default" dispatcher

  }
```

**See Also**

[flowOn](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines.flow/flow-on.html)

