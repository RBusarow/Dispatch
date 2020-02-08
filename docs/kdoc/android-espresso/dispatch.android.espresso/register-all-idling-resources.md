[android-espresso](../index.md) / [dispatch.android.espresso](index.md) / [registerAllIdlingResources](./register-all-idling-resources.md)

# registerAllIdlingResources

`fun `[`IdlingDispatcherProvider`](-idling-dispatcher-provider/index.md)`.registerAllIdlingResources(): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html) [(source)](https://github.com/RBusarow/Dispatch/tree/master/android-espresso/src/main/java/dispatch/android/espresso/IdlingDispatcherProvider.kt#L71)

Register all [IdlingDispatcher](-idling-dispatcher/index.md) properties of the receiver [IdlingDispatcherProvider](-idling-dispatcher-provider/index.md) with Espresso's [IdlingRegistry](#).

This should be done before executing a test.

After test execution, be sure to call the companion [IdlingDispatcherProvider.unregisterAllIdlingResources](unregister-all-idling-resources.md).

**See Also**

[IdlingDispatcherProvider.unregisterAllIdlingResources](unregister-all-idling-resources.md)

