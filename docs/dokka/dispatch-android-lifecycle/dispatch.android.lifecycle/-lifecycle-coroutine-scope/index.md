[dispatch-android-lifecycle](../../index.md) / [dispatch.android.lifecycle](../index.md) / [LifecycleCoroutineScope](./index.md)

# LifecycleCoroutineScope

`class LifecycleCoroutineScope : `[`MainImmediateCoroutineScope`](https://rbusarow.github.io/Dispatch/dispatch-core/dispatch.core/-main-immediate-coroutine-scope/index.md) [(source)](https://github.com/RBusarow/Dispatch/tree/master/dispatch-android-lifecycle/src/main/java/dispatch/android/lifecycle/LifecycleCoroutineScope.kt#L38)

[MainImmediateCoroutineScope](https://rbusarow.github.io/Dispatch/dispatch-core/dispatch.core/-main-immediate-coroutine-scope/index.md) instance which is tied to a [Lifecycle](https://developer.android.com/reference/androidx/androidx/lifecycle/Lifecycle.html).

The [CoroutineScope](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/-coroutine-scope/index.html) provides lifecycle-aware [launch](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/launch.html) functions
which will automatically start upon reaching their associated [Lifecycle.Event](https://developer.android.com/reference/androidx/androidx/lifecycle/Lifecycle/Event.html),
then automatically cancel upon the [lifecycle](lifecycle.md) dropping below that state.  Reaching
that state again will start a new [Job](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/-job/index.html).

If this `CoroutineScope` has a [Job](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/-job/index.html), it will be cancelled automatically
as soon as the [lifecycle](lifecycle.md) reaches [DESTROYED](https://developer.android.com/reference/androidx/androidx/lifecycle/Lifecycle/State.html#DESTROYED).

``` kotlin
runBlocking {

    // This could be any LifecycleOwner -- Fragments, Activities, Services...
    class SomeFragment : Fragment() {

      init {

        // auto-created MainImmediateCoroutineScope which is lifecycle-aware
        lifecycleScope //...

        // active only when "resumed".  starts a fresh coroutine each time
        // this is a rough proxy for LiveData behavior
        lifecycleScope.launchOnResume { }

        // active only when "started".  starts a fresh coroutine each time
        lifecycleScope.launchOnStart { }

        // launch when created, automatically stop on destroy
        lifecycleScope.launchOnCreate { }

        // it works as a normal CoroutineScope as well (because it is)
        lifecycleScope.launchMain { }

      }
    }
  }
```

### Parameters

`lifecycle` - the lifecycle to which this [MainImmediateCoroutineScope](https://rbusarow.github.io/Dispatch/dispatch-core/dispatch.core/-main-immediate-coroutine-scope/index.md) is linked.

### Types

| Name | Summary |
|---|---|
| [MinimumStatePolicy](-minimum-state-policy/index.md) | Describes the way a particular [Job](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/-job/index.html) will behave if the [lifecycle](lifecycle.md) passes below the minimum state before said [Job](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/-job/index.html) has completed.`enum class MinimumStatePolicy` |

### Constructors

| Name | Summary |
|---|---|
| [&lt;init&gt;](-init-.md) | [MainImmediateCoroutineScope](https://rbusarow.github.io/Dispatch/dispatch-core/dispatch.core/-main-immediate-coroutine-scope/index.md) instance which is tied to a [Lifecycle](https://developer.android.com/reference/androidx/androidx/lifecycle/Lifecycle.html).`LifecycleCoroutineScope(lifecycle: `[`Lifecycle`](https://developer.android.com/reference/androidx/androidx/lifecycle/Lifecycle.html)`, coroutineScope: `[`MainImmediateCoroutineScope`](https://rbusarow.github.io/Dispatch/dispatch-core/dispatch.core/-main-immediate-coroutine-scope/index.md)`)` |

### Properties

| Name | Summary |
|---|---|
| [lifecycle](lifecycle.md) | the lifecycle to which this [MainImmediateCoroutineScope](https://rbusarow.github.io/Dispatch/dispatch-core/dispatch.core/-main-immediate-coroutine-scope/index.md) is linked.`val lifecycle: `[`Lifecycle`](https://developer.android.com/reference/androidx/androidx/lifecycle/Lifecycle.html) |

### Functions

| Name | Summary |
|---|---|
| [launchOnCreate](launch-on-create.md) | Lifecycle-aware function for launching a coroutine any time the [Lifecycle.State](https://developer.android.com/reference/androidx/androidx/lifecycle/Lifecycle/State.html) is at least [Lifecycle.State.CREATED](https://developer.android.com/reference/androidx/androidx/lifecycle/Lifecycle/State.html#CREATED).`fun launchOnCreate(minimumStatePolicy: MinimumStatePolicy = MinimumStatePolicy.RESTART_EVERY, block: suspend `[`CoroutineScope`](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/-coroutine-scope/index.html)`.() -> `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)`): `[`Job`](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/-job/index.html) |
| [launchOnResume](launch-on-resume.md) | Lifecycle-aware function for launching a coroutine any time the [Lifecycle.State](https://developer.android.com/reference/androidx/androidx/lifecycle/Lifecycle/State.html) is at least [Lifecycle.State.RESUMED](https://developer.android.com/reference/androidx/androidx/lifecycle/Lifecycle/State.html#RESUMED).`fun launchOnResume(minimumStatePolicy: MinimumStatePolicy = MinimumStatePolicy.RESTART_EVERY, block: suspend `[`CoroutineScope`](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/-coroutine-scope/index.html)`.() -> `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)`): `[`Job`](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/-job/index.html) |
| [launchOnStart](launch-on-start.md) | Lifecycle-aware function for launching a coroutine any time the [Lifecycle.State](https://developer.android.com/reference/androidx/androidx/lifecycle/Lifecycle/State.html) is at least [Lifecycle.State.STARTED](https://developer.android.com/reference/androidx/androidx/lifecycle/Lifecycle/State.html#STARTED).`fun launchOnStart(minimumStatePolicy: MinimumStatePolicy = MinimumStatePolicy.RESTART_EVERY, block: suspend `[`CoroutineScope`](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/-coroutine-scope/index.html)`.() -> `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)`): `[`Job`](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/-job/index.html) |
