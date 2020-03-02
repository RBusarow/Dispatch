[android-lifecycle](../../index.md) / [dispatch.android.lifecycle](../index.md) / [LifecycleCoroutineScope](index.md) / [launchOnResume](./launch-on-resume.md)

# launchOnResume

`fun launchOnResume(minimumStatePolicy: MinimumStatePolicy = MinimumStatePolicy.RESTART_EVERY, block: suspend `[`LifecycleCoroutineScope`](index.md)`.() -> `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)`): `[`Job`](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/-job/index.html) [(source)](https://github.com/RBusarow/Dispatch/tree/master/android-lifecycle/src/main/java/dispatch/android/lifecycle/LifecycleCoroutineScope.kt#L97)

Lifecycle-aware function for launching a coroutine any time the [Lifecycle.State](https://developer.android.com/reference/androidx/androidx/lifecycle/Lifecycle/State.html)
is at least [Lifecycle.State.RESUMED](https://developer.android.com/reference/androidx/androidx/lifecycle/Lifecycle/State.html#RESUMED).

[block](launch-on-resume.md#dispatch.android.lifecycle.LifecycleCoroutineScope$launchOnResume(dispatch.android.lifecycle.LifecycleCoroutineScope.MinimumStatePolicy, kotlin.coroutines.SuspendFunction1((dispatch.android.lifecycle.LifecycleCoroutineScope, kotlin.Unit)))/block) is executed using the receiver [LifecycleCoroutineScope](index.md)'s [Job](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/-job/index.html) as a parent,
but always executes using [Dispatchers.Main](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/-dispatchers/-main.html) as its [CoroutineDispatcher](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/-coroutine-dispatcher/index.html).

Execution of [block](launch-on-resume.md#dispatch.android.lifecycle.LifecycleCoroutineScope$launchOnResume(dispatch.android.lifecycle.LifecycleCoroutineScope.MinimumStatePolicy, kotlin.coroutines.SuspendFunction1((dispatch.android.lifecycle.LifecycleCoroutineScope, kotlin.Unit)))/block) is cancelled when the receiver [LifecycleCoroutineScope](index.md) is cancelled,
or when [lifecycle](lifecycle.md)'s [Lifecycle.State](https://developer.android.com/reference/androidx/androidx/lifecycle/Lifecycle/State.html) drops below [Lifecycle.State.RESUMED](https://developer.android.com/reference/androidx/androidx/lifecycle/Lifecycle/State.html#RESUMED).

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
        lifecycleScope.launchOnResume(minimumStatePolicy = CANCEL) {
          viewModel.someFlow.collect {
            channel.send("$it")
          }
        }
      }
    }

    val fragment = SomeFragment()

    history.add("resuming")
    fragment.resume()

    repeat(3) {
      history.add(channel.receive())
    }

    // pausing the lifecycle cancels the existing Job
    history.add("pausing")
    fragment.pause()

    // resuming the lifecycle does not create a new Job

    history shouldBe listOf(
      "resuming",
      "0",
      "1",
      "2",
      "pausing"
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
        lifecycleScope.launchOnResume {
          viewModel.someFlow.collect {
            channel.send("$it")
          }
        }
      }
    }

    val fragment = SomeFragment()

    history.add("resuming")
    fragment.resume()

    repeat(3) {
      history.add(channel.receive())
    }

    // pausing the lifecycle cancels the existing Job
    history.add("pausing")
    fragment.pause()

    // resuming the lifecycle creates a new Job
    history.add("resuming")
    fragment.resume()

    repeat(3) {
      history.add(channel.receive())
    }

    history.add("pausing")
    fragment.pause()

    history shouldBe listOf(
      "resuming",
      "0",
      "1",
      "2",
      "pausing",
      "resuming",
      "0",
      "1",
      "2",
      "pausing"
    )
  }
```

### Parameters

`minimumStatePolicy` - *optional* - the way this [Job](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/-job/index.html) will behave when passing below the minimum
state or re-entering.  Uses [MinimumStatePolicy.RESTART_EVERY](-minimum-state-policy/-r-e-s-t-a-r-t_-e-v-e-r-y.md) by default.