[android-lifecycle](../../index.md) / [dispatch.android.lifecycle](../index.md) / [kotlinx.coroutines.flow.Flow](index.md) / [launchOnCreate](./launch-on-create.md)

# launchOnCreate

`fun <T> `[`Flow`](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines.flow/-flow/index.html)`<T>.launchOnCreate(scope: `[`LifecycleCoroutineScope`](../-lifecycle-coroutine-scope/index.md)`, minimumStatePolicy: MinimumStatePolicy = MinimumStatePolicy.RESTART_EVERY): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html) [(source)](https://github.com/RBusarow/Dispatch/tree/master/android-lifecycle/src/main/java/dispatch/android/lifecycle/FlowExt.kt#L37)

Lifecycle-aware function for collecting a [Flow](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines.flow/-flow/index.html) while time the [Lifecycle.State](https://developer.android.com/reference/androidx/androidx/lifecycle/Lifecycle/State.html)
is at least [Lifecycle.State.CREATED](https://developer.android.com/reference/androidx/androidx/lifecycle/Lifecycle/State.html#CREATED).

[Collection](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines.flow/collect.html) is performed using this [LifecycleCoroutineScope](../-lifecycle-coroutine-scope/index.md) to create a coroutine.

Collection is cancelled when this [LifecycleCoroutineScope](../-lifecycle-coroutine-scope/index.md) is cancelled,
or when the [LifecycleCoroutineScope.lifecycle](../-lifecycle-coroutine-scope/lifecycle.md)'s [Lifecycle.State](https://developer.android.com/reference/androidx/androidx/lifecycle/Lifecycle/State.html) drops below [Lifecycle.State.CREATED](https://developer.android.com/reference/androidx/androidx/lifecycle/Lifecycle/State.html#CREATED).

``` kotlin
runBlocking {

    class SomeViewModel : ViewModel() {
      val someFlow = flow {
        repeat(100) {
          emit(it)
        }
      }
    }

    class SomeFragment : ComponentActivity() {

      val viewModel = SomeViewModel()

      init {

        // "observe" the viewModel's Flow from the first ON_CREATE event until the first ON_DESTROY event
        viewModel.someFlow.onEach {
          print("collect $it")
        }
          // A coroutine will be created when the Lifecycle's state reaches CREATED,
          // and will be cancelled when the state reaches DESTROYED.
          // The coroutine will never be recreated.
          .launchOnCreate(lifecycleScope, minimumStatePolicy = CANCEL)
      }
    }
  }
```

``` kotlin
runBlocking {

    class SomeViewModel : ViewModel() {
      val someFlow = flow {
        repeat(100) {
          emit(it)
        }
      }
    }

    class SomeFragment : ComponentActivity() {

      val viewModel = SomeViewModel()

      init {

        // "observe" the viewModel's Flow from *every* ON_CREATE event until the following ON_DESTROY event
        viewModel.someFlow.onEach {
          print("collect $it")
        }
          // A coroutine will be created when the Lifecycle's state reaches CREATED,
          // and will be cancelled when the state reaches DESTROYED.
          // If this lifecycle were to somehow reach CREATED again
          // (which is technically possible in a custom LifecycleOwner implementation),
          // the coroutine would be recreated.
          .launchOnCreate(lifecycleScope)
      }
    }
  }
```

### Parameters

`minimumStatePolicy` - *optional* - the way this [Job](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/-job/index.html) will behave when passing below the minimum
state or re-entering.  Uses [MinimumStatePolicy.RESTART_EVERY](../-lifecycle-coroutine-scope/-minimum-state-policy/-r-e-s-t-a-r-t_-e-v-e-r-y.md) by default.