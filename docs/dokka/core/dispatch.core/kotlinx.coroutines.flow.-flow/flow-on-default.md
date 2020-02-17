[core](../../index.md) / [dispatch.core](../index.md) / [kotlinx.coroutines.flow.Flow](index.md) / [flowOnDefault](./flow-on-default.md)

# flowOnDefault

`@ExperimentalCoroutinesApi fun <T> `[`Flow`](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines.flow/-flow/index.html)`<T>.flowOnDefault(): `[`Flow`](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines.flow/-flow/index.html)`<T>` [(source)](https://github.com/RBusarow/Dispatch/tree/master/core/src/main/java/dispatch/core/Flow.kt#L31)

Extracts the [DispatcherProvider](../-dispatcher-provider/index.md) from the `coroutineContext` of the *collector* coroutine,
then uses its [DispatcherProvider.default](../-dispatcher-provider/default.md) property to call `flowOn(theDispatcher)`,
and returns the result.

``` kotlin
runBlocking(someDispatcherProvider) {

    dispatcherName() shouldBe "runBlocking thread"

    flow {

      dispatcherName() shouldBe "default"

      emit(Unit)
    }.flowOnDefault() // switch to the "default" dispatcher for everything upstream
      .collect()      // collect the flow from the "main" dispatcher

  }
```

**See Also**

[flowOn](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines.flow/flow-on.html)

