[android-viewmodel](../../index.md) / [dispatch.android.lifecycle](../index.md) / [CoroutineViewModel](index.md) / [viewModelScope](./view-model-scope.md)

# viewModelScope

`val viewModelScope: `[`CoroutineScope`](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/-coroutine-scope/index.html) [(source)](https://github.com/RBusarow/Dispatch/tree/master/android-viewmodel/src/main/java/dispatch/android/lifecycle/CoroutineViewModel.kt#L53)

[CoroutineScope](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/-coroutine-scope/index.html) instance for the [CoroutineViewModel](index.md).
By default, it uses the [Dispatchers.Main.immediate](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/-main-coroutine-dispatcher/immediate.html) dispatcher

The `viewModelScope` instance is created automatically upon first access
from the factory set in [ViewModelScopeFactory](../-view-model-scope-factory/index.md).

The type of [CoroutineScope](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/-coroutine-scope/index.html) created is configurable via [ViewModelScopeFactory.set](../-view-model-scope-factory/set.md).

`viewModelScope` is automatically cancelled when `onCleared()` is invoked.

``` kotlin
class SomeViewModel : CoroutineViewModel() {

  init {

    // auto-created MainImmediateCoroutineScope which is auto-cancelled in onClear()
    viewModelScope //...

    // it works as a normal CoroutineScope (because it is)
    viewModelScope.launchMain { }

    // this is the same CoroutineScope instance each time
    viewModelScope.launchMain { }

  }

  override fun onClear() {
    viewModelScope.isActive shouldBe false
  }
}
```

