[dispatch-core](../../index.md) / [dispatch.core](../index.md) / [kotlin.coroutines.CoroutineContext](index.md) / [dispatcherProvider](./dispatcher-provider.md)

# dispatcherProvider

`val `[`CoroutineContext`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.coroutines/-coroutine-context/index.html)`.dispatcherProvider: `[`DispatcherProvider`](../-dispatcher-provider/index.md) [(source)](https://github.com/RBusarow/Dispatch/tree/master/dispatch-core/src/main/java/dispatch/core/CoroutineScopeExt.kt#L90)

Extracts the [DispatcherProvider](../-dispatcher-provider/index.md) out of the [CoroutineContext](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.coroutines/-coroutine-context/index.html),
or returns a new instance of a [DefaultDispatcherProvider](../-default-dispatcher-provider/index.md) if the `CoroutineContext`
does not have one specified.

Note that `CoroutineContext` is immutable, so if a new `DefaultDispatcherProvider` is needed,
a new instance will be created each time.

