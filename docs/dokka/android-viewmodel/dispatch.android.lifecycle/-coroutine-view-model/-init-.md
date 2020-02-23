[android-viewmodel](../../index.md) / [dispatch.android.lifecycle](../index.md) / [CoroutineViewModel](index.md) / [&lt;init&gt;](./-init-.md)

# &lt;init&gt;

`CoroutineViewModel()`

Base class for [ViewModel](https://developer.android.com/reference/androidx/androidx/lifecycle/ViewModel.html)s which will be using a [viewModelScope](view-model-scope.md).

The `viewModelScope` instance is created automatically upon first access
from the factory set in [ViewModelScopeFactory](../-view-model-scope-factory/index.md).

The type of [CoroutineScope](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/-coroutine-scope/index.html) created is configurable via [ViewModelScopeFactory.set](../-view-model-scope-factory/set.md).

This must be an abstract class since nothing about the [ViewModel.onCleared](https://developer.android.com/reference/androidx/androidx/lifecycle/ViewModel.html#onCleared()) event is exposed.

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

