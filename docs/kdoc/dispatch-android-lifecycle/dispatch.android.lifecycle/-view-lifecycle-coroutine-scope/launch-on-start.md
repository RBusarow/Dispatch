[dispatch-android-lifecycle](../../index.md) / [dispatch.android.lifecycle](../index.md) / [ViewLifecycleCoroutineScope](index.md) / [launchOnStart](./launch-on-start.md)

# launchOnStart

`fun <T> `[`Flow`](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines.flow/-flow/index.html)`<T>.launchOnStart(): `[`Job`](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/-job/index.html) [(source)](https://github.com/RBusarow/Dispatch/tree/master/dispatch-android-lifecycle/src/main/java/dispatch/android/lifecycle/ViewLifecycleCoroutineScope.kt#L45)

Every time the View [Lifecycle State](https://developer.android.com/reference/androidx/androidx/lifecycle/Lifecycle/State.html) reaches [STARTED](https://developer.android.com/reference/androidx/androidx/lifecycle/Lifecycle/State.html#STARTED), create a new coroutine and collect this [Flow](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines.flow/-flow/index.html).

**See Also**

[kotlinx.coroutines.flow.launchIn](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines.flow/launch-in.html)

