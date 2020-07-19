[dispatch-android-lifecycle](../../index.md) / [dispatch.android.lifecycle](../index.md) / [ViewLifecycleCoroutineScope](index.md) / [launchOnResume](./launch-on-resume.md)

# launchOnResume

`fun <T> `[`Flow`](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines.flow/-flow/index.html)`<T>.launchOnResume(): `[`Job`](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/-job/index.html) [(source)](https://github.com/RBusarow/Dispatch/tree/master/dispatch-android-lifecycle/src/main/java/dispatch/android/lifecycle/ViewLifecycleCoroutineScope.kt#L52)

Every time the View [Lifecycle State](https://developer.android.com/reference/androidx/androidx/lifecycle/Lifecycle/State.html) reaches [RESUMED](https://developer.android.com/reference/androidx/androidx/lifecycle/Lifecycle/State.html#RESUMED), create a new coroutine and collect this [Flow](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines.flow/-flow/index.html).

**See Also**

[kotlinx.coroutines.flow.launchIn](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines.flow/launch-in.html)

