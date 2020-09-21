[dispatch-core](../../index.md) / [dispatch.core](../index.md) / [kotlinx.coroutines.CoroutineScope](index.md) / [mainImmediateDispatcher](./main-immediate-dispatcher.md)

# mainImmediateDispatcher

`val `[`CoroutineScope`](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/-coroutine-scope/index.html)`.mainImmediateDispatcher: `[`CoroutineDispatcher`](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/-coroutine-dispatcher/index.html) [(source)](https://github.com/RBusarow/Dispatch/tree/master/dispatch-core/src/main/java/dispatch/core/CoroutineScopeExt.kt#L66)

Extracts the **mainImmediate** [CoroutineDispatcher](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/-coroutine-dispatcher/index.html) out of the [CoroutineScope](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/-coroutine-scope/index.html),
using [DefaultDispatcherProvider.get](../-default-dispatcher-provider/get.md) to provide one if necessary.

Note that `CoroutineContext` is immutable, so if a new `DefaultDispatcherProvider` is needed,
a new instance will be created each time.

**See Also**

[CoroutineScope.dispatcherProvider](dispatcher-provider.md)

