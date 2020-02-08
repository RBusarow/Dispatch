[android-lifecycle-viewmodel](../../index.md) / [dispatch.android.lifecycle](../index.md) / [CoroutineViewModel](./index.md)

# CoroutineViewModel

`abstract class CoroutineViewModel : `[`ViewModel`](https://developer.android.com/reference/androidx/androidx/lifecycle/ViewModel.html) [(source)](https://github.com/RBusarow/Dispatch/tree/master/android-lifecycle-viewmodel/src/main/java/dispatch/android/lifecycle/CoroutineViewModel.kt#L34)

Base class for [ViewModel](https://developer.android.com/reference/androidx/androidx/lifecycle/ViewModel.html)s which will be using a [viewModelScope](view-model-scope.md).

The `viewModelScope` instance is created automatically upon first access
from the factory set in [ViewModelScopeFactory](../-view-model-scope-factory/index.md).

The type of [CoroutineScope](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/-coroutine-scope/index.html) created is configurable via [ViewModelScopeFactory.set](../-view-model-scope-factory/set.md).

This must be an abstract class since nothing about the [ViewModel.onCleared](https://developer.android.com/reference/androidx/androidx/lifecycle/ViewModel.html#onCleared()) event is exposed.

The `viewModelScope` is automatically cancelled when `onCleared()` is invoked.

### Constructors

| Name | Summary |
|---|---|
| [&lt;init&gt;](-init-.md) | Base class for [ViewModel](https://developer.android.com/reference/androidx/androidx/lifecycle/ViewModel.html)s which will be using a [viewModelScope](view-model-scope.md).`CoroutineViewModel()` |

### Properties

| Name | Summary |
|---|---|
| [viewModelScope](view-model-scope.md) | [CoroutineScope](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/-coroutine-scope/index.html) instance for the [CoroutineViewModel](./index.md). By default, it uses the [Dispatchers.Main.immediate](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/-main-coroutine-dispatcher/immediate.html) dispatcher`val viewModelScope: `[`CoroutineScope`](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/-coroutine-scope/index.html) |

### Functions

| Name | Summary |
|---|---|
| [onClear](on-clear.md) | This method will be called when this ViewModel is no longer used and will be destroyed.`open fun onClear(): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html) |
| [onCleared](on-cleared.md) | `fun onCleared(): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html) |