[dispatcher-provider](../../index.md) / [com.rickbusarow.dispatcherprovider](../index.md) / [kotlinx.coroutines.flow.Flow](index.md) / [flowOnDefault](./flow-on-default.md)

# flowOnDefault

`@ExperimentalCoroutinesApi fun <T> `[`Flow`](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines.flow/-flow/index.html)`<T>.flowOnDefault(): `[`Flow`](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines.flow/-flow/index.html)`<T>` [(source)](https://github.com/RBusarow/Dispatch/tree/master/dispatcher-provider/src/main/java/com/rickbusarow/dispatcherprovider/Flow.kt#L34)

Extracts the [DispatcherProvider](../-dispatcher-provider/index.md) from the `coroutineContext` of the **collector** coroutine,
then uses its **default** [CoroutineDispatcher](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/-coroutine-dispatcher/index.html) property to call `flowOn(theDispatcher)`,
and returns the result.

**See Also**

[flowOn](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines.flow/flow-on.html)

