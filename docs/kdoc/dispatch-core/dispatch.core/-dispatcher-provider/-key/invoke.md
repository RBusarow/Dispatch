[dispatch-core](../../../index.md) / [dispatch.core](../../index.md) / [DispatcherProvider](../index.md) / [Key](index.md) / [invoke](./invoke.md)

# invoke

`operator fun invoke(): `[`DispatcherProvider`](../index.md) [(source)](https://github.com/RBusarow/Dispatch/tree/master/dispatch-core/src/main/java/dispatch/core/DispatcherProvider.kt#L95)

Default implementation of [DispatcherProvider](../index.md) which simply delegates to the corresponding
properties in the [Dispatchers](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/-dispatchers/index.html) singleton.

This should be suitable for most production code.

**See Also**

[DefaultDispatcherProvider](../../-default-dispatcher-provider/index.md)

