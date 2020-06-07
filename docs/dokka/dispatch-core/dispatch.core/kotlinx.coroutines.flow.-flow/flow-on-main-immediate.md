[dispatch-core](../../index.md) / [dispatch.core](../index.md) / [kotlinx.coroutines.flow.Flow](index.md) / [flowOnMainImmediate](./flow-on-main-immediate.md)

# flowOnMainImmediate

`@ExperimentalCoroutinesApi fun <T> `[`Flow`](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines.flow/-flow/index.html)`<T>.flowOnMainImmediate(): `[`Flow`](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines.flow/-flow/index.html)`<T>` [(source)](https://github.com/RBusarow/Dispatch/tree/master/dispatch-core/src/main/java/dispatch/core/Flow.kt#L73)

Extracts the [DispatcherProvider](../-dispatcher-provider/index.md) from the `coroutineContext` of the *collector* coroutine,
then uses its [DispatcherProvider.mainImmediate](../-dispatcher-provider/main-immediate.md) property to call `flowOn(theDispatcher)`,
and returns the result.

``` kotlin
runBlocking(someDispatcherProvider) {

      dispatcherName() shouldBe "runBlocking thread"

      flow {

        dispatcherName() shouldBe "main immediate"

        emit(Unit)
      }.flowOnMainImmediate()  // switch to the "mainImmediate" dispatcher for everything upstream
        .collect()             // collect the flow from the "main" dispatcher

    }
```

**See Also**

[flowOn](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines.flow/flow-on.html)

