[dispatch-core](../../index.md) / [dispatch.core](../index.md) / [DispatcherProvider](./index.md)

# DispatcherProvider

`interface DispatcherProvider : `[`Element`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.coroutines/-coroutine-context/-element/index.html) [(source)](https://github.com/RBusarow/Dispatch/tree/master/dispatch-core/src/main/java/dispatch/core/DispatcherProvider.kt#L30)

Interface corresponding to the different [CoroutineDispatcher](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/-coroutine-dispatcher/index.html)'s offered by [Dispatchers](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/-dispatchers/index.html).

Implements the [CoroutineContext.Element](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.coroutines/-coroutine-context/-element/index.html) interface
so that it can be embedded into the [CoroutineContext](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.coroutines/-coroutine-context/index.html) map,
meaning that a `CoroutineContext` can be composed with a set of pre-set dispatchers,
thereby eliminating the need for singleton references or dependency injecting this interface.

### Types

| Name | Summary |
|---|---|
| [Key](-key/index.md) | Unique [Key](-key/index.md) definition which allows the `DispatcherProvider` to be stored in the [CoroutineContext](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.coroutines/-coroutine-context/index.html).`companion object Key : `[`Key`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.coroutines/-coroutine-context/-key/index.html)`<`[`DispatcherProvider`](./index.md)`>` |

### Properties

| Name | Summary |
|---|---|
| [default](default.md) | [CoroutineDispatcher](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/-coroutine-dispatcher/index.html) generally intended for cpu-bound tasks.`open val default: `[`CoroutineDispatcher`](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/-coroutine-dispatcher/index.html) |
| [io](io.md) | [CoroutineDispatcher](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/-coroutine-dispatcher/index.html) generally intended for blocking I/O tasks.`open val io: `[`CoroutineDispatcher`](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/-coroutine-dispatcher/index.html) |
| [key](key.md) | This unique [Key](-key/index.md) property is what allows the `DispatcherProvider` to be stored in the [CoroutineContext](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.coroutines/-coroutine-context/index.html).`open val key: `[`Key`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.coroutines/-coroutine-context/-key/index.html)`<*>` |
| [main](main.md) | [CoroutineDispatcher](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/-coroutine-dispatcher/index.html) which is confined to the "main" thread.`open val main: `[`CoroutineDispatcher`](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/-coroutine-dispatcher/index.html) |
| [mainImmediate](main-immediate.md) | [CoroutineDispatcher](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/-coroutine-dispatcher/index.html) which is confined to the "main" thread with immediate dispatch.`open val mainImmediate: `[`CoroutineDispatcher`](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/-coroutine-dispatcher/index.html) |
| [unconfined](unconfined.md) | [CoroutineDispatcher](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/-coroutine-dispatcher/index.html) which is unconfined.`open val unconfined: `[`CoroutineDispatcher`](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/-coroutine-dispatcher/index.html) |

### Companion Object Functions

| Name | Summary |
|---|---|
| [invoke](invoke.md) | Default implementation of [DispatcherProvider](./index.md) which simply delegates to the corresponding properties in the [Dispatchers](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/-dispatchers/index.html) singleton.`operator fun invoke(): `[`DispatcherProvider`](./index.md) |
