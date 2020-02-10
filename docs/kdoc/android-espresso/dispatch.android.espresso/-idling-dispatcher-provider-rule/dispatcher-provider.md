[android-espresso](../../index.md) / [dispatch.android.espresso](../index.md) / [IdlingDispatcherProviderRule](index.md) / [dispatcherProvider](./dispatcher-provider.md)

# dispatcherProvider

`lateinit var dispatcherProvider: `[`IdlingDispatcherProvider`](../-idling-dispatcher-provider/index.md) [(source)](https://github.com/RBusarow/Dispatch/tree/master/android-espresso/src/main/java/dispatch/android/espresso/IdlingCoroutineScopeRule.kt#L68)

The [IdlingDispatcherProvider](../-idling-dispatcher-provider/index.md) which is automatically registered with [IdlingRegistry](#).

This `dispatcherProvider` should be used in all other [CoroutineScope](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/-coroutine-scope/index.html)s for the duration of the test.

