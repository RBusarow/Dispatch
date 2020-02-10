[android-espresso](../index.md) / [dispatch.android.espresso](index.md) / [IdlingDispatcherProvider](./-idling-dispatcher-provider.md)

# IdlingDispatcherProvider

`fun IdlingDispatcherProvider(delegate: DispatcherProvider = DefaultDispatcherProvider()): `[`IdlingDispatcherProvider`](-idling-dispatcher-provider/index.md) [(source)](https://github.com/RBusarow/Dispatch/tree/master/android-espresso/src/main/java/dispatch/android/espresso/IdlingDispatcherProvider.kt#L71)

[IdlingDispatcherProvider](-idling-dispatcher-provider/index.md) factory function, which creates an instance using an existing [DispatcherProvider](#).

### Parameters

`delegate` - *optional* Use this [DispatcherProvider](#) to create a single [IdlingDispatcher](-idling-dispatcher/index.md)
which is used as all properties for the [IdlingDispatcherProvider](-idling-dispatcher-provider/index.md).
Uses a [DefaultDispatcherProvider](#) if no instance provided.

**See Also**

[IdlingResource](#)

[DispatcherProvider](#)

[IdlingDispatcher](-idling-dispatcher/index.md)

[CoroutineDispatcher](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/-coroutine-dispatcher/index.html)

