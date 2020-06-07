[dispatch-android-espresso](../index.md) / [dispatch.android.espresso](index.md) / [IdlingDispatcherProvider](./-idling-dispatcher-provider.md)

# IdlingDispatcherProvider

`fun IdlingDispatcherProvider(delegate: `[`DispatcherProvider`](https://rbusarow.github.io/Dispatch/dispatch-core/dispatch.core/-dispatcher-provider/index.md)` = DefaultDispatcherProvider()): `[`IdlingDispatcherProvider`](-idling-dispatcher-provider/index.md) [(source)](https://github.com/RBusarow/Dispatch/tree/master/dispatch-android-espresso/src/main/java/dispatch/android/espresso/IdlingDispatcherProvider.kt#L71)

[IdlingDispatcherProvider](-idling-dispatcher-provider/index.md) factory function, which creates an instance using an existing [DispatcherProvider](https://rbusarow.github.io/Dispatch/dispatch-core/dispatch.core/-dispatcher-provider/index.md).

### Parameters

`delegate` - *optional* Use this [DispatcherProvider](https://rbusarow.github.io/Dispatch/dispatch-core/dispatch.core/-dispatcher-provider/index.md) to create a single [IdlingDispatcher](-idling-dispatcher/index.md)
which is used as all properties for the [IdlingDispatcherProvider](-idling-dispatcher-provider/index.md).
Uses a [DefaultDispatcherProvider](https://rbusarow.github.io/Dispatch/dispatch-core/dispatch.core/-default-dispatcher-provider/index.md) if no instance provided.

**See Also**

[IdlingResource](https://developer.android.com/reference/androidx/test/androidx/test/espresso/IdlingResource.html)

[DispatcherProvider](https://rbusarow.github.io/Dispatch/dispatch-core/dispatch.core/-dispatcher-provider/index.md)

[IdlingDispatcher](-idling-dispatcher/index.md)

[CoroutineDispatcher](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/-coroutine-dispatcher/index.html)

