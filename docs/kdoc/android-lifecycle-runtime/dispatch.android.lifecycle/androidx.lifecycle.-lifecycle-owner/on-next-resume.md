[android-lifecycle-runtime](../../index.md) / [dispatch.android.lifecycle](../index.md) / [androidx.lifecycle.LifecycleOwner](index.md) / [onNextResume](./on-next-resume.md)

# onNextResume

`suspend fun <T> `[`LifecycleOwner`](https://developer.android.com/reference/androidx/androidx/lifecycle/LifecycleOwner.html)`.onNextResume(block: suspend `[`CoroutineScope`](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/-coroutine-scope/index.html)`.() -> T): T?` [(source)](https://github.com/RBusarow/Dispatch/tree/master/android-lifecycle-runtime/src/main/java/dispatch/android/lifecycle/LifecycleSuspendExt.kt#L73)

Executes [block](on-next-resume.md#dispatch.android.lifecycle$onNextResume(androidx.lifecycle.LifecycleOwner, kotlin.coroutines.SuspendFunction1((kotlinx.coroutines.CoroutineScope, dispatch.android.lifecycle.onNextResume.T)))/block) one time, the next time the [Lifecycle](https://developer.android.com/reference/androidx/androidx/lifecycle/Lifecycle.html)'s state is at least [Lifecycle.State.RESUMED](https://developer.android.com/reference/androidx/androidx/lifecycle/Lifecycle/State.html#RESUMED).

If the lifecycle is already in this state, [block](on-next-resume.md#dispatch.android.lifecycle$onNextResume(androidx.lifecycle.LifecycleOwner, kotlin.coroutines.SuspendFunction1((kotlinx.coroutines.CoroutineScope, dispatch.android.lifecycle.onNextResume.T)))/block) will be executed immediately.

**See Also**

[LifecycleCoroutineScope.launchEveryResume](../-lifecycle-coroutine-scope/launch-every-resume.md)

