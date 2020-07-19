[dispatch-core](../../../index.md) / [dispatch.core](../../index.md) / [DispatcherProvider](../index.md) / [Key](./index.md)

# Key

`companion object Key : `[`Key`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.coroutines/-coroutine-context/-key/index.html)`<`[`DispatcherProvider`](../index.md)`>` [(source)](https://github.com/RBusarow/Dispatch/tree/master/dispatch-core/src/main/java/dispatch/core/DispatcherProvider.kt#L85)

Unique [Key](./index.md) definition which allows the `DispatcherProvider` to be stored in the [CoroutineContext](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.coroutines/-coroutine-context/index.html).

### Companion Object Functions

| Name | Summary |
|---|---|
| [invoke](invoke.md) | Default implementation of [DispatcherProvider](../index.md) which simply delegates to the corresponding properties in the [Dispatchers](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/-dispatchers/index.html) singleton.`operator fun invoke(): `[`DispatcherProvider`](../index.md) |
