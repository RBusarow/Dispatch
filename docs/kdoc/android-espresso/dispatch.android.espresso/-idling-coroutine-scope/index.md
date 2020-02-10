[android-espresso](../../index.md) / [dispatch.android.espresso](../index.md) / [IdlingCoroutineScope](./index.md)

# IdlingCoroutineScope

`interface IdlingCoroutineScope : `[`CoroutineScope`](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/-coroutine-scope/index.html) [(source)](https://github.com/RBusarow/Dispatch/tree/master/android-espresso/src/main/java/dispatch/android/espresso/IdlingCoroutineScope.kt#L29)

Marker interface for a [CoroutineScope](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/-coroutine-scope/index.html) which indicates that its [DispatcherProvider](#) is an [IdlingDispatcherProvider](../-idling-dispatcher-provider/index.md).

**See Also**

[IdlingDispatcherProvider](../-idling-dispatcher-provider/index.md)

[IdlingResource](#)

### Properties

| Name | Summary |
|---|---|
| [idlingDispatcherProvider](idling-dispatcher-provider.md) | Any [IdlingCoroutineScope](./index.md) has an [idlingDispatcherProvider](idling-dispatcher-provider.md) property which can be registered in the [IdlingRegistry](#).`abstract val idlingDispatcherProvider: `[`IdlingDispatcherProvider`](../-idling-dispatcher-provider/index.md) |

### Inheritors

| Name | Summary |
|---|---|
| [DefaultIdlingCoroutineScope](../-default-idling-coroutine-scope.md) | Marker interface for an [IdlingCoroutineScope](./index.md) which indicates that its [CoroutineDispatcher](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/-coroutine-dispatcher/index.html) is [DispatcherProvider.default](#)`interface DefaultIdlingCoroutineScope : `[`IdlingCoroutineScope`](./index.md)`, DefaultCoroutineScope` |
| [IOIdlingCoroutineScope](../-i-o-idling-coroutine-scope.md) | Marker interface for an [IdlingCoroutineScope](./index.md) which indicates that its [CoroutineDispatcher](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/-coroutine-dispatcher/index.html) is [DispatcherProvider.io](#)`interface IOIdlingCoroutineScope : `[`IdlingCoroutineScope`](./index.md)`, IOCoroutineScope` |
| [MainIdlingCoroutineScope](../-main-idling-coroutine-scope.md) | Marker interface for an [IdlingCoroutineScope](./index.md) which indicates that its [CoroutineDispatcher](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/-coroutine-dispatcher/index.html) is [DispatcherProvider.main](#)`interface MainIdlingCoroutineScope : `[`IdlingCoroutineScope`](./index.md)`, MainCoroutineScope` |
| [MainImmediateIdlingCoroutineScope](../-main-immediate-idling-coroutine-scope.md) | Marker interface for an [IdlingCoroutineScope](./index.md) which indicates that its [CoroutineDispatcher](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/-coroutine-dispatcher/index.html) is [DispatcherProvider.mainImmediate](#)`interface MainImmediateIdlingCoroutineScope : `[`IdlingCoroutineScope`](./index.md)`, MainImmediateCoroutineScope` |
| [UnconfinedIdlingCoroutineScope](../-unconfined-idling-coroutine-scope.md) | Marker interface for an [IdlingCoroutineScope](./index.md) which indicates that its [CoroutineDispatcher](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/-coroutine-dispatcher/index.html) is [DispatcherProvider.unconfined](#)`interface UnconfinedIdlingCoroutineScope : `[`IdlingCoroutineScope`](./index.md)`, UnconfinedCoroutineScope` |
