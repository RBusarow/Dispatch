[android-lifecycle-runtime](../../index.md) / [dispatch.android.lifecycle](../index.md) / [androidx.lifecycle.LifecycleOwner](index.md) / [lifecycleScope](./lifecycle-scope.md)

# lifecycleScope

`val `[`LifecycleOwner`](https://developer.android.com/reference/androidx/androidx/lifecycle/LifecycleOwner.html)`.lifecycleScope: `[`LifecycleCoroutineScope`](../-lifecycle-coroutine-scope/index.md) [(source)](https://github.com/RBusarow/Dispatch/tree/master/android-lifecycle-runtime/src/main/java/dispatch/android/lifecycle/LifecycleCoroutineScope.kt#L36)

[CoroutineScope](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/-coroutine-scope/index.html) instance for the [LifecycleOwner](https://developer.android.com/reference/androidx/androidx/lifecycle/LifecycleOwner.html).
By default, it uses the [Dispatchers.Main.immediate](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/-main-coroutine-dispatcher/immediate.html) dispatcher.

The `lifecycleScope` instance is created automatically upon first access,
from the factory set in [LifecycleScopeFactory](../-lifecycle-scope-factory/index.md).

The type of [CoroutineScope](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/-coroutine-scope/index.html) created is configurable via [LifecycleScopeFactory.set](../-lifecycle-scope-factory/set.md).

The `viewModelScope` is automatically cancelled when the [LifecycleOwner](https://developer.android.com/reference/androidx/androidx/lifecycle/LifecycleOwner.html)'s [lifecycle](https://developer.android.com/reference/androidx/androidx/lifecycle/LifecycleOwner.html#getLifecycle())'s [Lifecycle.State](https://developer.android.com/reference/androidx/androidx/lifecycle/Lifecycle/State.html) drops to [Lifecycle.State.DESTROYED](https://developer.android.com/reference/androidx/androidx/lifecycle/Lifecycle/State.html#DESTROYED).

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

