[android-lifecycle-runtime](../../index.md) / [dispatch.android.lifecycle](../index.md) / [androidx.lifecycle.Lifecycle](index.md) / [onNextCreate](./on-next-create.md)

# onNextCreate

`suspend fun <T> `[`Lifecycle`](https://developer.android.com/reference/androidx/androidx/lifecycle/Lifecycle.html)`.onNextCreate(block: suspend `[`CoroutineScope`](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/-coroutine-scope/index.html)`.() -> T): T?` [(source)](https://github.com/RBusarow/Dispatch/tree/master/android-lifecycle-runtime/src/main/java/dispatch/android/lifecycle/LifecycleSuspendExt.kt#L40)

Executes [block](on-next-create.md#dispatch.android.lifecycle$onNextCreate(androidx.lifecycle.Lifecycle, kotlin.coroutines.SuspendFunction1((kotlinx.coroutines.CoroutineScope, dispatch.android.lifecycle.onNextCreate.T)))/block) one time, the next time the [Lifecycle](https://developer.android.com/reference/androidx/androidx/lifecycle/Lifecycle.html)'s state is at least [Lifecycle.State.CREATED](https://developer.android.com/reference/androidx/androidx/lifecycle/Lifecycle/State.html#CREATED).

If the lifecycle is already in this state, [block](on-next-create.md#dispatch.android.lifecycle$onNextCreate(androidx.lifecycle.Lifecycle, kotlin.coroutines.SuspendFunction1((kotlinx.coroutines.CoroutineScope, dispatch.android.lifecycle.onNextCreate.T)))/block) will be executed immediately.

**See Also**

[LifecycleCoroutineScope.launchEveryCreate](../-lifecycle-coroutine-scope/launch-every-create.md)

