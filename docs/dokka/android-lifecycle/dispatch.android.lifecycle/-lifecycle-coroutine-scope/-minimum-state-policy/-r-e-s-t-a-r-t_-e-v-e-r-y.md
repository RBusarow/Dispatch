[android-lifecycle](../../../index.md) / [dispatch.android.lifecycle](../../index.md) / [LifecycleCoroutineScope](../index.md) / [MinimumStatePolicy](index.md) / [RESTART_EVERY](./-r-e-s-t-a-r-t_-e-v-e-r-y.md)

# RESTART_EVERY

`RESTART_EVERY` [(source)](https://github.com/RBusarow/Dispatch/tree/master/android-lifecycle/src/main/java/dispatch/android/lifecycle/LifecycleCoroutineScope.kt#L128)

When using `RESTART_EVERY`, a coroutine will be created every time the [lifecycle](../lifecycle.md) meets the minimum state,
and will be cancelled upon dropping below it.
Subsequently meeting the minimum state again will create a new coroutine.

Note that **state is not retained** when dropping below a minimum state.

**See Also**

[launchOnCreate](../launch-on-create.md)

[launchOnStart](../launch-on-start.md)

[launchOnResume](../launch-on-resume.md)

