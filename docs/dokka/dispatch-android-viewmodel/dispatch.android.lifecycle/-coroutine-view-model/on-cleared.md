[dispatch-android-viewmodel](../../index.md) / [dispatch.android.lifecycle](../index.md) / [CoroutineViewModel](index.md) / [onCleared](./on-cleared.md)

# onCleared

`protected fun onCleared(): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html) [(source)](https://github.com/RBusarow/Dispatch/tree/master/dispatch-android-viewmodel/src/main/java/dispatch/android/lifecycle/CoroutineViewModel.kt#L73)

It is necessary to do a final override of [ViewModel.onCleared](https://developer.android.com/reference/androidx/androidx/lifecycle/ViewModel.html#onCleared()) to ensure that [viewModelScope](view-model-scope.md) is cancelled.

Use [onClear](on-clear.md) to perform logic after this event.

