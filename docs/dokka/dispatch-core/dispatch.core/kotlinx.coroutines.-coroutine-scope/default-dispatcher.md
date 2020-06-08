[dispatch-core](../../index.md) / [dispatch.core](../index.md) / [kotlinx.coroutines.CoroutineScope](index.md) / [defaultDispatcher](./default-dispatcher.md)

# defaultDispatcher

`val `[`CoroutineScope`](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/-coroutine-scope/index.html)`.defaultDispatcher: `[`CoroutineDispatcher`](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/-coroutine-dispatcher/index.html) [(source)](https://github.com/RBusarow/Dispatch/tree/master/dispatch-core/src/main/java/dispatch/core/CoroutineScopeExt.kt#L28)

Extracts the **default** [CoroutineDispatcher](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/-coroutine-dispatcher/index.html) out of the [CoroutineScope](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/-coroutine-scope/index.html),
creating a new instance of a [DefaultDispatcherProvider](../-default-dispatcher-provider/index.md) to provide one if necessary.

Note that `CoroutineContext` is immutable, so if a new `DefaultDispatcherProvider` is needed,
a new instance will be created each time.

