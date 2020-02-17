[android-lifecycle-runtime](../../index.md) / [dispatch.android.lifecycle](../index.md) / [LifecycleCoroutineScope](./index.md)

# LifecycleCoroutineScope

`class LifecycleCoroutineScope : `[`MainImmediateCoroutineScope`](https://rbusarow.github.io/Dispatch/core/dispatch.core/-main-immediate-coroutine-scope/index.md) [(source)](https://github.com/RBusarow/Dispatch/tree/master/android-lifecycle-runtime/src/main/java/dispatch/android/lifecycle/LifecycleCoroutineScope.kt#L49)

[MainImmediateCoroutineScope](https://rbusarow.github.io/Dispatch/core/dispatch.core/-main-immediate-coroutine-scope/index.md) instance which is tied to a [Lifecycle](https://developer.android.com/reference/androidx/androidx/lifecycle/Lifecycle.html).

The [CoroutineScope](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/-coroutine-scope/index.html) provides lifecycle-aware [launch](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/launch.html) functions
which will automatically start upon reaching their associated [Lifecycle.Event](https://developer.android.com/reference/androidx/androidx/lifecycle/Lifecycle/Event.html),
then automatically cancel upon the [lifecycle](#) dropping below that state.  Reaching
that state again will start a new [Job](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/-job/index.html).

``` kotlin
runBlocking {

    // This could be any LifecycleOwner -- Fragments, Activities, Services...
    class SomeFragment : Fragment() {

      init {

        // auto-created MainImmediateCoroutineScope which is lifecycle-aware
        lifecycleScope //...

        // active only when "resumed".  starts a fresh coroutine each time
        // this is a rough proxy for LiveData behavior
        lifecycleScope.launchEveryResume { }

        // active only when "started".  starts a fresh coroutine each time
        lifecycleScope.launchEveryStart { }

        // launch when created, automatically stop on destroy
        lifecycleScope.launchEveryCreate { }

        // it works as a normal CoroutineScope as well (because it is)
        lifecycleScope.launchMain { }

      }
    }
  }
```

### Constructors

| Name | Summary |
|---|---|
| [&lt;init&gt;](-init-.md) | [MainImmediateCoroutineScope](https://rbusarow.github.io/Dispatch/core/dispatch.core/-main-immediate-coroutine-scope/index.md) instance which is tied to a [Lifecycle](https://developer.android.com/reference/androidx/androidx/lifecycle/Lifecycle.html).`LifecycleCoroutineScope(lifecycle: `[`Lifecycle`](https://developer.android.com/reference/androidx/androidx/lifecycle/Lifecycle.html)`, coroutineScope: `[`MainImmediateCoroutineScope`](https://rbusarow.github.io/Dispatch/core/dispatch.core/-main-immediate-coroutine-scope/index.md)`)` |

### Functions

| Name | Summary |
|---|---|
| [launchEveryCreate](launch-every-create.md) | Lifecycle-aware function for launching a coroutine any time the [Lifecycle.State](https://developer.android.com/reference/androidx/androidx/lifecycle/Lifecycle/State.html) is at least [Lifecycle.State.CREATED](https://developer.android.com/reference/androidx/androidx/lifecycle/Lifecycle/State.html#CREATED).`fun launchEveryCreate(block: suspend `[`CoroutineScope`](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/-coroutine-scope/index.html)`.() -> `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)`): `[`Job`](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/-job/index.html) |
| [launchEveryResume](launch-every-resume.md) | Lifecycle-aware function for launching a coroutine any time the [Lifecycle.State](https://developer.android.com/reference/androidx/androidx/lifecycle/Lifecycle/State.html) is at least [Lifecycle.State.RESUMED](https://developer.android.com/reference/androidx/androidx/lifecycle/Lifecycle/State.html#RESUMED).`fun launchEveryResume(block: suspend `[`CoroutineScope`](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/-coroutine-scope/index.html)`.() -> `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)`): `[`Job`](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/-job/index.html) |
| [launchEveryStart](launch-every-start.md) | Lifecycle-aware function for launching a coroutine any time the [Lifecycle.State](https://developer.android.com/reference/androidx/androidx/lifecycle/Lifecycle/State.html) is at least [Lifecycle.State.STARTED](https://developer.android.com/reference/androidx/androidx/lifecycle/Lifecycle/State.html#STARTED).`fun launchEveryStart(block: suspend `[`CoroutineScope`](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/-coroutine-scope/index.html)`.() -> `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)`): `[`Job`](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/-job/index.html) |
