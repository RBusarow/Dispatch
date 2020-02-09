[dispatcher-provider](../../index.md) / [com.rickbusarow.dispatcherprovider](../index.md) / [kotlinx.coroutines.CoroutineScope](index.md) / [dispatcherProvider](./dispatcher-provider.md)

# dispatcherProvider

`val `[`CoroutineScope`](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/-coroutine-scope/index.html)`.dispatcherProvider: `[`DispatcherProvider`](../-dispatcher-provider/index.md) [(source)](https://github.com/RBusarow/Dispatch/tree/master/dispatcher-provider/src/main/java/com/rickbusarow/dispatcherprovider/CoroutineScopeExt.kt#L79)

Extracts the [DispatcherProvider](../-dispatcher-provider/index.md) out of the [CoroutineScope](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/-coroutine-scope/index.html),
or returns a new instance of a [DefaultDispatcherProvider](../-default-dispatcher-provider/index.md) if the `coroutineContext`
does not have one specified.

Note that `CoroutineContext` is immutable, so if a new `DefaultDispatcherProvider` is needed,
a new instance will be created each time.

