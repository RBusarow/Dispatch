[dispatch-android-lifecycle](../index.md) / [dispatch.android.lifecycle](./index.md)

## Package dispatch.android.lifecycle

### Types

| Name | Summary |
|---|---|
| [LifecycleCoroutineScope](-lifecycle-coroutine-scope/index.md) | [MainImmediateCoroutineScope](https://rbusarow.github.io/Dispatch/dispatch-core/dispatch.core/-main-immediate-coroutine-scope/index.md) which is tied to a [Lifecycle](https://developer.android.com/reference/androidx/androidx/lifecycle/Lifecycle.html).`open class LifecycleCoroutineScope : `[`MainImmediateCoroutineScope`](https://rbusarow.github.io/Dispatch/dispatch-core/dispatch.core/-main-immediate-coroutine-scope/index.md) |
| [LifecycleCoroutineScopeFactory](-lifecycle-coroutine-scope-factory/index.md) | Factory for [LifecycleCoroutineScope](-lifecycle-coroutine-scope/index.md)s.  This may be injected into a lifecycle-aware class to provide custom [CoroutineContexts](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.coroutines/-coroutine-context/index.html).`class LifecycleCoroutineScopeFactory` |
| [ViewLifecycleCoroutineScope](-view-lifecycle-coroutine-scope/index.md) | [LifecycleCoroutineScope](-lifecycle-coroutine-scope/index.md) instance which is tied to a [Fragment's](https://developer.android.com/reference/androidx/androidx/fragment/app/Fragment.html) View [lifecycle](https://developer.android.com/reference/androidx/androidx/lifecycle/Lifecycle.html).`class ViewLifecycleCoroutineScope : `[`LifecycleCoroutineScope`](-lifecycle-coroutine-scope/index.md) |

### Extensions for External Classes

| Name | Summary |
|---|---|
| [androidx.lifecycle.Lifecycle](androidx.lifecycle.-lifecycle/index.md) |  |
| [androidx.lifecycle.LifecycleOwner](androidx.lifecycle.-lifecycle-owner/index.md) |  |
| [kotlinx.coroutines.CoroutineScope](kotlinx.coroutines.-coroutine-scope/index.md) |  |

### Functions

| Name | Summary |
|---|---|
| [MainImmediateContext](-main-immediate-context.md) | Default implementation of a [CoroutineContext](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.coroutines/-coroutine-context/index.html) as seen in a `MainImmediateCoroutineScope`.`fun MainImmediateContext(): `[`CoroutineContext`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.coroutines/-coroutine-context/index.html) |
