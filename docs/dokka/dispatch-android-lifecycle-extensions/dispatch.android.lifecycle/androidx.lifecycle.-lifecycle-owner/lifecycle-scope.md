[dispatch-android-lifecycle-extensions](../../index.md) / [dispatch.android.lifecycle](../index.md) / [androidx.lifecycle.LifecycleOwner](index.md) / [lifecycleScope](./lifecycle-scope.md)

# lifecycleScope

`val `[`LifecycleOwner`](https://developer.android.com/reference/androidx/androidx/lifecycle/LifecycleOwner.html)`.lifecycleScope: LifecycleCoroutineScope` [(source)](https://github.com/RBusarow/Dispatch/tree/master/dispatch-android-lifecycle-extensions/src/main/java/dispatch/android/lifecycle/LifecycleScope.kt#L35)

[CoroutineScope](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/-coroutine-scope/index.html) instance for the [LifecycleOwner](https://developer.android.com/reference/androidx/androidx/lifecycle/LifecycleOwner.html).
By default, it uses the [Dispatchers.Main.immediate](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/-main-coroutine-dispatcher/immediate.html) dispatcher.

The `lifecycleScope` instance is created automatically upon first access,
from the factory set in [LifecycleScopeFactory](../-lifecycle-scope-factory/index.md).

The type of [CoroutineScope](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/-coroutine-scope/index.html) created is configurable via [LifecycleScopeFactory.set](../-lifecycle-scope-factory/set.md).

The `viewModelScope` is automatically cancelled when the [LifecycleOwner](https://developer.android.com/reference/androidx/androidx/lifecycle/LifecycleOwner.html)'s [lifecycle](https://developer.android.com/reference/androidx/androidx/lifecycle/LifecycleOwner.html#getLifecycle())'s [Lifecycle.State](https://developer.android.com/reference/androidx/androidx/lifecycle/Lifecycle/State.html) drops to [Lifecycle.State.DESTROYED](https://developer.android.com/reference/androidx/androidx/lifecycle/Lifecycle/State.html#DESTROYED).

``` kotlin
//Unresolved: samples.LifecycleCoroutineScopeSample.lifecycleCoroutineScopeSample
```

