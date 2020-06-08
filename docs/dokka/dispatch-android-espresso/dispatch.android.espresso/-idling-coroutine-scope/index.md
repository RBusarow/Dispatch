[dispatch-android-espresso](../../index.md) / [dispatch.android.espresso](../index.md) / [IdlingCoroutineScope](./index.md)

# IdlingCoroutineScope

`interface IdlingCoroutineScope : `[`CoroutineScope`](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/-coroutine-scope/index.html) [(source)](https://github.com/RBusarow/Dispatch/tree/master/dispatch-android-espresso/src/main/java/dispatch/android/espresso/IdlingCoroutineScope.kt#L29)

Special [CoroutineScope](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/-coroutine-scope/index.html) with a [DispatcherProvider](https://rbusarow.github.io/Dispatch/dispatch-core/dispatch.core/-dispatcher-provider/index.md) which is an [IdlingDispatcherProvider](../-idling-dispatcher-provider/index.md).

**See Also**

[IdlingDispatcherProvider](../-idling-dispatcher-provider/index.md)

[IdlingResource](https://developer.android.com/reference/androidx/test/androidx/test/espresso/IdlingResource.html)

### Properties

| Name | Summary |
|---|---|
| [idlingDispatcherProvider](idling-dispatcher-provider.md) | Any [IdlingCoroutineScope](./index.md) has an [idlingDispatcherProvider](idling-dispatcher-provider.md) property which can be registered in the [IdlingRegistry](https://developer.android.com/reference/androidx/test/androidx/test/espresso/IdlingRegistry.html).`abstract val idlingDispatcherProvider: `[`IdlingDispatcherProvider`](../-idling-dispatcher-provider/index.md) |

### Inheritors

| Name | Summary |
|---|---|
| [DefaultIdlingCoroutineScope](../-default-idling-coroutine-scope.md) | Marker interface for an [IdlingCoroutineScope](./index.md) which indicates that its [CoroutineDispatcher](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/-coroutine-dispatcher/index.html) is [DispatcherProvider.default](https://rbusarow.github.io/Dispatch/dispatch-core/dispatch.core/-dispatcher-provider/default.md)`interface DefaultIdlingCoroutineScope : `[`IdlingCoroutineScope`](./index.md)`, `[`DefaultCoroutineScope`](https://rbusarow.github.io/Dispatch/dispatch-core/dispatch.core/-default-coroutine-scope/index.md) |
| [IOIdlingCoroutineScope](../-i-o-idling-coroutine-scope.md) | Marker interface for an [IdlingCoroutineScope](./index.md) which indicates that its [CoroutineDispatcher](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/-coroutine-dispatcher/index.html) is [DispatcherProvider.io](https://rbusarow.github.io/Dispatch/dispatch-core/dispatch.core/-dispatcher-provider/io.md)`interface IOIdlingCoroutineScope : `[`IdlingCoroutineScope`](./index.md)`, `[`IOCoroutineScope`](https://rbusarow.github.io/Dispatch/dispatch-core/dispatch.core/-i-o-coroutine-scope/index.md) |
| [MainIdlingCoroutineScope](../-main-idling-coroutine-scope.md) | Marker interface for an [IdlingCoroutineScope](./index.md) which indicates that its [CoroutineDispatcher](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/-coroutine-dispatcher/index.html) is [DispatcherProvider.main](https://rbusarow.github.io/Dispatch/dispatch-core/dispatch.core/-dispatcher-provider/main.md)`interface MainIdlingCoroutineScope : `[`IdlingCoroutineScope`](./index.md)`, `[`MainCoroutineScope`](https://rbusarow.github.io/Dispatch/dispatch-core/dispatch.core/-main-coroutine-scope/index.md) |
| [MainImmediateIdlingCoroutineScope](../-main-immediate-idling-coroutine-scope.md) | Marker interface for an [IdlingCoroutineScope](./index.md) which indicates that its [CoroutineDispatcher](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/-coroutine-dispatcher/index.html) is [DispatcherProvider.mainImmediate](https://rbusarow.github.io/Dispatch/dispatch-core/dispatch.core/-dispatcher-provider/main-immediate.md)`interface MainImmediateIdlingCoroutineScope : `[`IdlingCoroutineScope`](./index.md)`, `[`MainImmediateCoroutineScope`](https://rbusarow.github.io/Dispatch/dispatch-core/dispatch.core/-main-immediate-coroutine-scope/index.md) |
| [UnconfinedIdlingCoroutineScope](../-unconfined-idling-coroutine-scope.md) | Marker interface for an [IdlingCoroutineScope](./index.md) which indicates that its [CoroutineDispatcher](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/-coroutine-dispatcher/index.html) is [DispatcherProvider.unconfined](https://rbusarow.github.io/Dispatch/dispatch-core/dispatch.core/-dispatcher-provider/unconfined.md)`interface UnconfinedIdlingCoroutineScope : `[`IdlingCoroutineScope`](./index.md)`, `[`UnconfinedCoroutineScope`](https://rbusarow.github.io/Dispatch/dispatch-core/dispatch.core/-unconfined-coroutine-scope/index.md) |
