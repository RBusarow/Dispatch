[android-espresso](../index.md) / [dispatch.android.espresso](index.md) / [unregisterAllIdlingResources](./unregister-all-idling-resources.md)

# unregisterAllIdlingResources

`fun `[`IdlingDispatcherProvider`](-idling-dispatcher-provider/index.md)`.unregisterAllIdlingResources(): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html) [(source)](https://github.com/RBusarow/Dispatch/tree/master/android-espresso/src/main/java/dispatch/android/espresso/IdlingDispatcherProvider.kt#L116)

Unregister all [IdlingDispatcher](-idling-dispatcher/index.md) properties of the receiver [IdlingDispatcherProvider](-idling-dispatcher-provider/index.md) with Espresso's [IdlingRegistry](#).

This should be done after executing a test.

Before test execution, be sure to call the companion [IdlingDispatcherProvider.registerAllIdlingResources](register-all-idling-resources.md)
or this function will have no effect.

**See Also**

[IdlingDispatcherProvider.registerAllIdlingResources](register-all-idling-resources.md)

