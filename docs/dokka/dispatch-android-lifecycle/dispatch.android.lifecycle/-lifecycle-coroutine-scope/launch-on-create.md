[dispatch-android-lifecycle](../../index.md) / [dispatch.android.lifecycle](../index.md) / [LifecycleCoroutineScope](index.md) / [launchOnCreate](./launch-on-create.md)

# launchOnCreate

`fun launchOnCreate(minimumStatePolicy: MinimumStatePolicy = MinimumStatePolicy.RESTART_EVERY, block: suspend `[`CoroutineScope`](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/-coroutine-scope/index.html)`.() -> `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)`): `[`Job`](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/-job/index.html) [(source)](https://github.com/RBusarow/Dispatch/tree/master/dispatch-android-lifecycle/src/main/java/dispatch/android/lifecycle/LifecycleCoroutineScope.kt#L57)

Lifecycle-aware function for launching a coroutine any time the [Lifecycle.State](https://developer.android.com/reference/androidx/androidx/lifecycle/Lifecycle/State.html)
is at least [Lifecycle.State.CREATED](https://developer.android.com/reference/androidx/androidx/lifecycle/Lifecycle/State.html#CREATED).

[block](launch-on-create.md#dispatch.android.lifecycle.LifecycleCoroutineScope$launchOnCreate(dispatch.android.lifecycle.LifecycleCoroutineScope.MinimumStatePolicy, kotlin.coroutines.SuspendFunction1((kotlinx.coroutines.CoroutineScope, kotlin.Unit)))/block) is executed using the receiver [CoroutineScope](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/-coroutine-scope/index.html)'s [Job](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/-job/index.html) as a parent,
but always executes using [Dispatchers.Main](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/-dispatchers/-main.html) as its [CoroutineDispatcher](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/-coroutine-dispatcher/index.html).

Execution of [block](launch-on-create.md#dispatch.android.lifecycle.LifecycleCoroutineScope$launchOnCreate(dispatch.android.lifecycle.LifecycleCoroutineScope.MinimumStatePolicy, kotlin.coroutines.SuspendFunction1((kotlinx.coroutines.CoroutineScope, kotlin.Unit)))/block) is cancelled when the receiver [CoroutineScope](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/-coroutine-scope/index.html) is cancelled,
or when [lifecycle](lifecycle.md)'s [Lifecycle.State](https://developer.android.com/reference/androidx/androidx/lifecycle/Lifecycle/State.html) drops below [Lifecycle.State.CREATED](https://developer.android.com/reference/androidx/androidx/lifecycle/Lifecycle/State.html#CREATED).

``` kotlin
runBlocking {

    val channel = Channel<String>()
    val history = mutableListOf<String>()

    class SomeViewModel {
      val someFlow = flow {
        repeat(100) {
          emit(it)
        }
      }
    }

    class SomeFragment : Fragment() {

      val viewModel = SomeViewModel()

      init {

        lifecycleScope.launchOnCreate(minimumStatePolicy = CANCEL) {
          viewModel.someFlow.collect {
            channel.send("$it")
          }
        }
      }
    }

    val fragment = SomeFragment()

    history.add("creating")
    fragment.create()

    repeat(3) {
      history.add(channel.receive())
    }

    // destroying the lifecycle cancels the lifecycleScope
    history.add("destroying")
    fragment.destroy()

    history shouldBe listOf(
      "creating",
      "0",
      "1",
      "2",
      "destroying"
    )
  }
```

``` kotlin
runBlocking {

    val channel = Channel<String>()
    val history = mutableListOf<String>()

    class SomeViewModel {
      val someFlow = flow {
        repeat(100) {
          emit(it)
        }
      }
    }

    class SomeFragment : Fragment() {

      val viewModel = SomeViewModel()

      init {

        lifecycleScope.launchOnCreate {
          viewModel.someFlow.collect {
            channel.send("$it")
          }
        }
      }
    }

    val fragment = SomeFragment()

    history.add("creating")
    fragment.create()

    repeat(3) {
      history.add(channel.receive())
    }

    // destroying the lifecycle cancels the lifecycleScope
    history.add("destroying")
    fragment.destroy()

    history shouldBe listOf(
      "creating",
      "0",
      "1",
      "2",
      "destroying"
    )
  }
```

### Parameters

`minimumStatePolicy` - *optional* - the way this [Job](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/-job/index.html) will behave when passing below the minimum
state or re-entering.  Uses [MinimumStatePolicy.RESTART_EVERY](-minimum-state-policy/-r-e-s-t-a-r-t_-e-v-e-r-y.md) by default.  Note that for a normal Lifecycle,
there is no returning from below a [CREATED](https://developer.android.com/reference/androidx/androidx/lifecycle/Lifecycle/State.html#CREATED) state,
so the [minimumStatePolicy](-minimum-state-policy/index.md) is largely irrelevant.