[dispatch-android-lifecycle](../index.md) / [dispatch.android.lifecycle](./index.md)

## Package dispatch.android.lifecycle

### Types

| Name | Summary |
|---|---|
| [LifecycleCoroutineScope](-lifecycle-coroutine-scope/index.md) | [MainImmediateCoroutineScope](https://rbusarow.github.io/Dispatch/dispatch-core/dispatch.core/-main-immediate-coroutine-scope/index.md) which is tied to a [Lifecycle](https://developer.android.com/reference/androidx/androidx/lifecycle/Lifecycle.html).`open class LifecycleCoroutineScope : `[`MainImmediateCoroutineScope`](https://rbusarow.github.io/Dispatch/dispatch-core/dispatch.core/-main-immediate-coroutine-scope/index.md) |
| [LifecycleCoroutineScopeFactory](-lifecycle-coroutine-scope-factory/index.md) | Factory for [LifecycleCoroutineScope](-lifecycle-coroutine-scope/index.md)s.  This may be injected into a lifecycle-aware class to provide custom [CoroutineContexts](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.coroutines/-coroutine-context/index.html).`class LifecycleCoroutineScopeFactory` |

### Extensions for External Classes

| Name | Summary |
|---|---|
| [androidx.lifecycle.Lifecycle](androidx.lifecycle.-lifecycle/index.md) |  |
| [androidx.lifecycle.LifecycleOwner](androidx.lifecycle.-lifecycle-owner/index.md) |  |

### Functions

| Name | Summary |
|---|---|
| [MainImmediateContext](-main-immediate-context.md) | Default implementation of a [CoroutineContext](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.coroutines/-coroutine-context/index.html) as seen in a `MainImmediateCoroutineScope`.`fun MainImmediateContext(): `[`CoroutineContext`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.coroutines/-coroutine-context/index.html) |
