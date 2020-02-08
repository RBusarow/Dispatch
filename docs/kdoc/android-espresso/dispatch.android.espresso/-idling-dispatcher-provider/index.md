[android-espresso](../../index.md) / [dispatch.android.espresso](../index.md) / [IdlingDispatcherProvider](./index.md)

# IdlingDispatcherProvider

`class IdlingDispatcherProvider : DispatcherProvider` [(source)](https://github.com/RBusarow/Dispatch/tree/master/android-espresso/src/main/java/dispatch/android/espresso/IdlingDispatcherProvider.kt#L31)

[IdlingResource](#) helper for coroutines.  This [DispatcherProvider](#) implementation
utilizes an [IdlingDispatcher](../-idling-dispatcher/index.md) for each [CoroutineDispatcher](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/-coroutine-dispatcher/index.html).

**See Also**

[IdlingResource](#)

[DispatcherProvider](#)

[IdlingDispatcher](../-idling-dispatcher/index.md)

[CoroutineDispatcher](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/-coroutine-dispatcher/index.html)

### Constructors

| Name | Summary |
|---|---|
| [&lt;init&gt;](-init-.md) | [IdlingResource](#) helper for coroutines.  This [DispatcherProvider](#) implementation utilizes an [IdlingDispatcher](../-idling-dispatcher/index.md) for each [CoroutineDispatcher](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/-coroutine-dispatcher/index.html).`IdlingDispatcherProvider(default: `[`IdlingDispatcher`](../-idling-dispatcher/index.md)`, io: `[`IdlingDispatcher`](../-idling-dispatcher/index.md)`, main: `[`IdlingDispatcher`](../-idling-dispatcher/index.md)`, mainImmediate: `[`IdlingDispatcher`](../-idling-dispatcher/index.md)`, unconfined: `[`IdlingDispatcher`](../-idling-dispatcher/index.md)`)` |

### Properties

| Name | Summary |
|---|---|
| [default](default.md) | `val default: `[`IdlingDispatcher`](../-idling-dispatcher/index.md) |
| [io](io.md) | `val io: `[`IdlingDispatcher`](../-idling-dispatcher/index.md) |
| [main](main.md) | `val main: `[`IdlingDispatcher`](../-idling-dispatcher/index.md) |
| [mainImmediate](main-immediate.md) | `val mainImmediate: `[`IdlingDispatcher`](../-idling-dispatcher/index.md) |
| [unconfined](unconfined.md) | `val unconfined: `[`IdlingDispatcher`](../-idling-dispatcher/index.md) |

### Extension Functions

| Name | Summary |
|---|---|
| [registerAllIdlingResources](../register-all-idling-resources.md) | Register all [IdlingDispatcher](../-idling-dispatcher/index.md) properties of the receiver [IdlingDispatcherProvider](./index.md) with Espresso's [IdlingRegistry](#).`fun `[`IdlingDispatcherProvider`](./index.md)`.registerAllIdlingResources(): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html) |
| [unregisterAllIdlingResources](../unregister-all-idling-resources.md) | Unregister all [IdlingDispatcher](../-idling-dispatcher/index.md) properties of the receiver [IdlingDispatcherProvider](./index.md) with Espresso's [IdlingRegistry](#).`fun `[`IdlingDispatcherProvider`](./index.md)`.unregisterAllIdlingResources(): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html) |
