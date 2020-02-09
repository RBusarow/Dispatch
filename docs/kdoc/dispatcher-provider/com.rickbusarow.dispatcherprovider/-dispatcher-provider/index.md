[dispatcher-provider](../../index.md) / [com.rickbusarow.dispatcherprovider](../index.md) / [DispatcherProvider](./index.md)

# DispatcherProvider

`interface DispatcherProvider : `[`Element`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.coroutines/-coroutine-context/-element/index.html) [(source)](https://github.com/RBusarow/Dispatch/tree/master/dispatcher-provider/src/main/java/com/rickbusarow/dispatcherprovider/DispatcherProvider.kt#L29)

Interface corresponding to the different [CoroutineDispatcher](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/-coroutine-dispatcher/index.html)'s offered by [Dispatchers](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/-dispatchers/index.html).

Implements the [CoroutineContext.Element](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.coroutines/-coroutine-context/-element/index.html) interface
so that it can be embedded into the [CoroutineContext](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.coroutines/-coroutine-context/index.html) map,
meaning that a `CoroutineContext` can be composed with a set of pre-set dispatchers,
thereby eliminating the need for singleton references or dependency injecting this interface.

### Types

| Name | Summary |
|---|---|
| [Key](-key.md) | `companion object Key : `[`Key`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.coroutines/-coroutine-context/-key/index.html)`<`[`DispatcherProvider`](./index.md)`>` |

### Properties

| Name | Summary |
|---|---|
| [default](default.md) | `abstract val default: `[`CoroutineDispatcher`](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/-coroutine-dispatcher/index.html) |
| [io](io.md) | `abstract val io: `[`CoroutineDispatcher`](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/-coroutine-dispatcher/index.html) |
| [key](key.md) | `open val key: `[`Key`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.coroutines/-coroutine-context/-key/index.html)`<*>` |
| [main](main.md) | `abstract val main: `[`CoroutineDispatcher`](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/-coroutine-dispatcher/index.html) |
| [mainImmediate](main-immediate.md) | `abstract val mainImmediate: `[`CoroutineDispatcher`](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/-coroutine-dispatcher/index.html) |
| [unconfined](unconfined.md) | `abstract val unconfined: `[`CoroutineDispatcher`](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/-coroutine-dispatcher/index.html) |

### Inheritors

| Name | Summary |
|---|---|
| [DefaultDispatcherProvider](../-default-dispatcher-provider/index.md) | Default implementation of [DispatcherProvider](./index.md) which simply delegates to the corresponding properties in the [Dispatchers](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/-dispatchers/index.html) singleton.`class DefaultDispatcherProvider : `[`DispatcherProvider`](./index.md) |
