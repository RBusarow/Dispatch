[dispatch-core](../../index.md) / [dispatch.core](../index.md) / [kotlinx.coroutines.CoroutineScope](index.md) / [mainDispatcher](./main-dispatcher.md)

# mainDispatcher

`val `[`CoroutineScope`](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/-coroutine-scope/index.html)`.mainDispatcher: `[`CoroutineDispatcher`](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/-coroutine-dispatcher/index.html) [(source)](https://github.com/RBusarow/Dispatch/tree/master/dispatch-core/src/main/java/dispatch/core/CoroutineScopeExt.kt#L54)

Extracts the **main** [CoroutineDispatcher](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/-coroutine-dispatcher/index.html) out of the [CoroutineScope](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/-coroutine-scope/index.html),
using [DefaultDispatcherProvider.get](../-default-dispatcher-provider/get.md) to provide one if necessary.

Note that `CoroutineContext` is immutable, so if a new `DefaultDispatcherProvider` is needed,
a new instance will be created each time.

**See Also**

[CoroutineScope.dispatcherProvider](dispatcher-provider.md)

