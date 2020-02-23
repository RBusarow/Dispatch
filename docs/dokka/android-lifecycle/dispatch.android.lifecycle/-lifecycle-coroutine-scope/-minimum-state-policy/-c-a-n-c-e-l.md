[android-lifecycle](../../../index.md) / [dispatch.android.lifecycle](../../index.md) / [LifecycleCoroutineScope](../index.md) / [MinimumStatePolicy](index.md) / [CANCEL](./-c-a-n-c-e-l.md)

# CANCEL

`CANCEL` [(source)](https://github.com/RBusarow/Dispatch/tree/master/android-lifecycle/src/main/java/dispatch/android/lifecycle/LifecycleCoroutineScope.kt#L115)

When using `CANCEL`, a coroutine will be created the first time the [lifecycle](../lifecycle.md) meets the minimum state,
and cancelled upon dropping below it.
Subsequently meeting the minimum state again will not resume the coroutine or create a new one.

**See Also**

[launchOnCreate](../launch-on-create.md)

[launchOnStart](../launch-on-start.md)

[launchOnResume](../launch-on-resume.md)

