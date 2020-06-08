[dispatch-android-lifecycle-extensions](../../index.md) / [dispatch.android.lifecycle](../index.md) / [androidx.lifecycle.LifecycleOwner](index.md) / [lifecycleScope](./lifecycle-scope.md)

# lifecycleScope

`val `[`LifecycleOwner`](https://developer.android.com/reference/androidx/androidx/lifecycle/LifecycleOwner.html)`.lifecycleScope: `[`LifecycleCoroutineScope`](https://rbusarow.github.io/Dispatch/dispatch-android-lifecycle/dispatch.android.lifecycle/-lifecycle-coroutine-scope/index.md) [(source)](https://github.com/RBusarow/Dispatch/tree/master/dispatch-android-lifecycle-extensions/src/main/java/dispatch/android/lifecycle/LifecycleScope.kt#L35)

[CoroutineScope](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/-coroutine-scope/index.html) instance for the [LifecycleOwner](https://developer.android.com/reference/androidx/androidx/lifecycle/LifecycleOwner.html).
By default, it uses the [Dispatchers.Main.immediate](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/-main-coroutine-dispatcher/immediate.html) dispatcher.

The `lifecycleScope` instance is created automatically upon first access,
from the factory set in [LifecycleScopeFactory](https://rbusarow.github.io/Dispatch/dispatch-android-lifecycle/dispatch.android.lifecycle/-lifecycle-scope-factory/index.md).

The type of [CoroutineScope](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/-coroutine-scope/index.html) created is configurable via [LifecycleScopeFactory.set](https://rbusarow.github.io/Dispatch/dispatch-android-lifecycle/dispatch.android.lifecycle/-lifecycle-scope-factory/set.md).

The `viewModelScope` is automatically cancelled when the [LifecycleOwner](https://developer.android.com/reference/androidx/androidx/lifecycle/LifecycleOwner.html)'s [lifecycle](https://developer.android.com/reference/androidx/androidx/lifecycle/LifecycleOwner.html#getLifecycle())'s [Lifecycle.State](https://developer.android.com/reference/androidx/androidx/lifecycle/Lifecycle/State.html) drops to [Lifecycle.State.DESTROYED](https://developer.android.com/reference/androidx/androidx/lifecycle/Lifecycle/State.html#DESTROYED).

``` kotlin
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
```

