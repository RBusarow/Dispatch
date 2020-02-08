[android-lifecycle-runtime](../../index.md) / [dispatch.android.lifecycle](../index.md) / [LifecycleCoroutineScope](index.md) / [launchEveryCreate](./launch-every-create.md)

# launchEveryCreate

`fun launchEveryCreate(block: suspend `[`CoroutineScope`](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/-coroutine-scope/index.html)`.() -> `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)`): `[`Job`](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/-job/index.html) [(source)](https://github.com/RBusarow/Dispatch/tree/master/android-lifecycle-runtime/src/main/java/dispatch/android/lifecycle/LifecycleCoroutineScope.kt#L75)

Lifecycle-aware function for launching a coroutine any time the [Lifecycle.State](https://developer.android.com/reference/androidx/androidx/lifecycle/Lifecycle/State.html)
is at least [Lifecycle.State.CREATED](https://developer.android.com/reference/androidx/androidx/lifecycle/Lifecycle/State.html#CREATED).

[block](launch-every-create.md#dispatch.android.lifecycle.LifecycleCoroutineScope$launchEveryCreate(kotlin.coroutines.SuspendFunction1((kotlinx.coroutines.CoroutineScope, kotlin.Unit)))/block) is executed using the receiver [CoroutineScope](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/-coroutine-scope/index.html)'s [Job](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/-job/index.html) as a parent,
but always executes using [Dispatchers.Main](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/-dispatchers/-main.html) as its [CoroutineDispatcher](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/-coroutine-dispatcher/index.html).

Execution of [block](launch-every-create.md#dispatch.android.lifecycle.LifecycleCoroutineScope$launchEveryCreate(kotlin.coroutines.SuspendFunction1((kotlinx.coroutines.CoroutineScope, kotlin.Unit)))/block) is cancelled when the receiver [CoroutineScope](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/-coroutine-scope/index.html) is cancelled,
or when [lifecycle](#)'s [Lifecycle.State](https://developer.android.com/reference/androidx/androidx/lifecycle/Lifecycle/State.html) drops below [Lifecycle.State.CREATED](https://developer.android.com/reference/androidx/androidx/lifecycle/Lifecycle/State.html#CREATED).

example:

```
class SomeFragment : Fragment {

  init {
    lifecycleScope.launchEveryCreate {
      viewModel.someFlow.collect {
        printLn("new value --> $it")
      }
    }
  }
}
```

