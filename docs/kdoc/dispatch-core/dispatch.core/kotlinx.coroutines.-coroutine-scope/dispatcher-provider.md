[dispatch-core](../../index.md) / [dispatch.core](../index.md) / [kotlinx.coroutines.CoroutineScope](index.md) / [dispatcherProvider](./dispatcher-provider.md)

# dispatcherProvider

`val `[`CoroutineScope`](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/-coroutine-scope/index.html)`.dispatcherProvider: `[`DispatcherProvider`](../-dispatcher-provider/index.md) [(source)](https://github.com/RBusarow/Dispatch/tree/master/dispatch-core/src/main/java/dispatch/core/CoroutineScopeExt.kt#L91)

Extracts the [DispatcherProvider](../-dispatcher-provider/index.md) out of the [CoroutineScope](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/-coroutine-scope/index.html),
or returns a new instance of [DefaultDispatcherProvider.get](../-default-dispatcher-provider/get.md) if the `coroutineContext`
does not have one specified.

Note that `CoroutineContext` is immutable, so if a new `DefaultDispatcherProvider` is needed,
a new instance will be created each time.

**See Also**

[CoroutineContext.dispatcherProvider](../kotlin.coroutines.-coroutine-context/dispatcher-provider.md)

