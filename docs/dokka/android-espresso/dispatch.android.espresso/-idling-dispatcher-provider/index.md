[android-espresso](../../index.md) / [dispatch.android.espresso](../index.md) / [IdlingDispatcherProvider](./index.md)

# IdlingDispatcherProvider

`class IdlingDispatcherProvider : `[`DispatcherProvider`](https://rbusarow.github.io/Dispatch/core/dispatch.core/-dispatcher-provider/index.md) [(source)](https://github.com/RBusarow/Dispatch/tree/master/android-espresso/src/main/java/dispatch/android/espresso/IdlingDispatcherProvider.kt#L31)

[IdlingResource](https://developer.android.com/reference/androidx/test/androidx/test/espresso/IdlingResource.html) helper for coroutines.  This [DispatcherProvider](https://rbusarow.github.io/Dispatch/core/dispatch.core/-dispatcher-provider/index.md) implementation
utilizes an [IdlingDispatcher](../-idling-dispatcher/index.md) for each [CoroutineDispatcher](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/-coroutine-dispatcher/index.html).

**See Also**

[IdlingResource](https://developer.android.com/reference/androidx/test/androidx/test/espresso/IdlingResource.html)

[DispatcherProvider](https://rbusarow.github.io/Dispatch/core/dispatch.core/-dispatcher-provider/index.md)

[IdlingDispatcher](../-idling-dispatcher/index.md)

[CoroutineDispatcher](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/-coroutine-dispatcher/index.html)

### Constructors

| Name | Summary |
|---|---|
| [&lt;init&gt;](-init-.md) | [IdlingResource](https://developer.android.com/reference/androidx/test/androidx/test/espresso/IdlingResource.html) helper for coroutines.  This [DispatcherProvider](https://rbusarow.github.io/Dispatch/core/dispatch.core/-dispatcher-provider/index.md) implementation utilizes an [IdlingDispatcher](../-idling-dispatcher/index.md) for each [CoroutineDispatcher](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/-coroutine-dispatcher/index.html).`IdlingDispatcherProvider(default: `[`IdlingDispatcher`](../-idling-dispatcher/index.md)`, io: `[`IdlingDispatcher`](../-idling-dispatcher/index.md)`, main: `[`IdlingDispatcher`](../-idling-dispatcher/index.md)`, mainImmediate: `[`IdlingDispatcher`](../-idling-dispatcher/index.md)`, unconfined: `[`IdlingDispatcher`](../-idling-dispatcher/index.md)`)` |

### Properties

| Name | Summary |
|---|---|
| [default](default.md) | [IdlingDispatcher](../-idling-dispatcher/index.md) implementation of [DispatcherProvider.default](https://rbusarow.github.io/Dispatch/core/dispatch.core/-dispatcher-provider/default.md), which typically corresponds to the [Dispatchers.Default](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/-coroutine-dispatcher/index.html).`val default: `[`IdlingDispatcher`](../-idling-dispatcher/index.md) |
| [io](io.md) | [IdlingDispatcher](../-idling-dispatcher/index.md) implementation of [DispatcherProvider.io](https://rbusarow.github.io/Dispatch/core/dispatch.core/-dispatcher-provider/io.md), which typically corresponds to the [Dispatchers.IO](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/-coroutine-dispatcher/index.html).`val io: `[`IdlingDispatcher`](../-idling-dispatcher/index.md) |
| [main](main.md) | [IdlingDispatcher](../-idling-dispatcher/index.md) implementation of [DispatcherProvider.main](https://rbusarow.github.io/Dispatch/core/dispatch.core/-dispatcher-provider/main.md), which typically corresponds to the [Dispatchers.Main](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/-coroutine-dispatcher/index.html).`val main: `[`IdlingDispatcher`](../-idling-dispatcher/index.md) |
| [mainImmediate](main-immediate.md) | [IdlingDispatcher](../-idling-dispatcher/index.md) implementation of [DispatcherProvider.mainImmediate](https://rbusarow.github.io/Dispatch/core/dispatch.core/-dispatcher-provider/main-immediate.md), which typically corresponds to the [Dispatchers.Main.immediate](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/-main-coroutine-dispatcher/immediate.html) [CoroutineDispatcher](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/-coroutine-dispatcher/index.html).`val mainImmediate: `[`IdlingDispatcher`](../-idling-dispatcher/index.md) |
| [unconfined](unconfined.md) | [IdlingDispatcher](../-idling-dispatcher/index.md) implementation of [DispatcherProvider.unconfined](https://rbusarow.github.io/Dispatch/core/dispatch.core/-dispatcher-provider/unconfined.md), which typically corresponds to the [Dispatchers.Unconfined](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/-coroutine-dispatcher/index.html).`val unconfined: `[`IdlingDispatcher`](../-idling-dispatcher/index.md) |

### Extension Functions

| Name | Summary |
|---|---|
| [registerAllIdlingResources](../register-all-idling-resources.md) | Register all [IdlingDispatcher](../-idling-dispatcher/index.md) properties of the receiver [IdlingDispatcherProvider](./index.md) with Espresso's [IdlingRegistry](https://developer.android.com/reference/androidx/test/androidx/test/espresso/IdlingRegistry.html).`fun `[`IdlingDispatcherProvider`](./index.md)`.registerAllIdlingResources(): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html) |
| [unregisterAllIdlingResources](../unregister-all-idling-resources.md) | Unregister all [IdlingDispatcher](../-idling-dispatcher/index.md) properties of the receiver [IdlingDispatcherProvider](./index.md) with Espresso's [IdlingRegistry](https://developer.android.com/reference/androidx/test/androidx/test/espresso/IdlingRegistry.html).`fun `[`IdlingDispatcherProvider`](./index.md)`.unregisterAllIdlingResources(): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html) |
