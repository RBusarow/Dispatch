[core](../../index.md) / [dispatch.core](../index.md) / [DispatcherProvider](./index.md)

# DispatcherProvider

`interface DispatcherProvider : `[`Element`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.coroutines/-coroutine-context/-element/index.html) [(source)](https://github.com/RBusarow/Dispatch/tree/master/core/src/main/java/dispatch/core/DispatcherProvider.kt#L31)

Interface corresponding to the different [CoroutineDispatcher](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/-coroutine-dispatcher/index.html)'s offered by [Dispatchers](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/-dispatchers/index.html).

Implements the [CoroutineContext.Element](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.coroutines/-coroutine-context/-element/index.html) interface
so that it can be embedded into the [CoroutineContext](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.coroutines/-coroutine-context/index.html) map,
meaning that a `CoroutineContext` can be composed with a set of pre-set dispatchers,
thereby eliminating the need for singleton references or dependency injecting this interface.

### Types

| Name | Summary |
|---|---|
| [Key](-key.md) | Unique [Key](-key.md) definition which allows the `DispatcherProvider` to be stored in the [CoroutineContext](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.coroutines/-coroutine-context/index.html).`companion object Key : `[`Key`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.coroutines/-coroutine-context/-key/index.html)`<`[`DispatcherProvider`](./index.md)`>` |

### Properties

| Name | Summary |
|---|---|
| [default](default.md) | [CoroutineDispatcher](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/-coroutine-dispatcher/index.html) generally intended for cpu-bound tasks.`abstract val default: `[`CoroutineDispatcher`](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/-coroutine-dispatcher/index.html) |
| [io](io.md) | [CoroutineDispatcher](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/-coroutine-dispatcher/index.html) generally intended for blocking I/O tasks.`abstract val io: `[`CoroutineDispatcher`](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/-coroutine-dispatcher/index.html) |
| [key](key.md) | This unique [Key](-key.md) property is what allows the `DispatcherProvider` to be stored in the [CoroutineContext](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.coroutines/-coroutine-context/index.html).`open val key: `[`Key`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.coroutines/-coroutine-context/-key/index.html)`<*>` |
| [main](main.md) | [CoroutineDispatcher](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/-coroutine-dispatcher/index.html) which is confined to the "main" thread.`abstract val main: `[`CoroutineDispatcher`](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/-coroutine-dispatcher/index.html) |
| [mainImmediate](main-immediate.md) | [CoroutineDispatcher](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/-coroutine-dispatcher/index.html) which is confined to the "main" thread with immediate dispatch.`abstract val mainImmediate: `[`CoroutineDispatcher`](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/-coroutine-dispatcher/index.html) |
| [unconfined](unconfined.md) | [CoroutineDispatcher](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/-coroutine-dispatcher/index.html) which is unconfined.`abstract val unconfined: `[`CoroutineDispatcher`](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/-coroutine-dispatcher/index.html) |

### Inheritors

| Name | Summary |
|---|---|
| [DefaultDispatcherProvider](../-default-dispatcher-provider/index.md) | Default implementation of [DispatcherProvider](./index.md) which simply delegates to the corresponding properties in the [Dispatchers](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/-dispatchers/index.html) singleton.`class DefaultDispatcherProvider : `[`DispatcherProvider`](./index.md) |
