[dispatch-android-lifecycle](../../index.md) / [dispatch.android.lifecycle](../index.md) / [ViewLifecycleCoroutineScope](./index.md)

# ViewLifecycleCoroutineScope

`class ViewLifecycleCoroutineScope : `[`LifecycleCoroutineScope`](../-lifecycle-coroutine-scope/index.md) [(source)](https://github.com/RBusarow/Dispatch/tree/master/dispatch-android-lifecycle/src/main/java/dispatch/android/lifecycle/ViewLifecycleCoroutineScope.kt#L28)

[LifecycleCoroutineScope](../-lifecycle-coroutine-scope/index.md) instance which is tied to a [Fragment's](https://developer.android.com/reference/androidx/androidx/fragment/app/Fragment.html) View [lifecycle](https://developer.android.com/reference/androidx/androidx/lifecycle/Lifecycle.html).

### Functions

| Name | Summary |
|---|---|
| [launchOnCreate](launch-on-create.md) | Every time the View [Lifecycle State](https://developer.android.com/reference/androidx/androidx/lifecycle/Lifecycle/State.html) reaches [CREATED](https://developer.android.com/reference/androidx/androidx/lifecycle/Lifecycle/State.html#CREATED), create a new coroutine and collect this [Flow](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines.flow/-flow/index.html).`fun <T> `[`Flow`](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines.flow/-flow/index.html)`<T>.launchOnCreate(): `[`Job`](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/-job/index.html) |
| [launchOnResume](launch-on-resume.md) | Every time the View [Lifecycle State](https://developer.android.com/reference/androidx/androidx/lifecycle/Lifecycle/State.html) reaches [RESUMED](https://developer.android.com/reference/androidx/androidx/lifecycle/Lifecycle/State.html#RESUMED), create a new coroutine and collect this [Flow](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines.flow/-flow/index.html).`fun <T> `[`Flow`](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines.flow/-flow/index.html)`<T>.launchOnResume(): `[`Job`](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/-job/index.html) |
| [launchOnStart](launch-on-start.md) | Every time the View [Lifecycle State](https://developer.android.com/reference/androidx/androidx/lifecycle/Lifecycle/State.html) reaches [STARTED](https://developer.android.com/reference/androidx/androidx/lifecycle/Lifecycle/State.html#STARTED), create a new coroutine and collect this [Flow](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines.flow/-flow/index.html).`fun <T> `[`Flow`](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines.flow/-flow/index.html)`<T>.launchOnStart(): `[`Job`](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/-job/index.html) |
