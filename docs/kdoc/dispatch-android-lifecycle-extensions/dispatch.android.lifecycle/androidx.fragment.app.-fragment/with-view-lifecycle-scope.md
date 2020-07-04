[dispatch-android-lifecycle-extensions](../../index.md) / [dispatch.android.lifecycle](../index.md) / [androidx.fragment.app.Fragment](index.md) / [withViewLifecycleScope](./with-view-lifecycle-scope.md)

# withViewLifecycleScope

`@ExperimentalCoroutinesApi fun `[`Fragment`](https://developer.android.com/reference/androidx/androidx/fragment/app/Fragment.html)`.withViewLifecycleScope(block: `[`ViewLifecycleCoroutineScope`](https://rbusarow.github.io/Dispatch/dispatch-android-lifecycle/dispatch.android.lifecycle/-view-lifecycle-coroutine-scope/index.md)`.() -> `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)`): `[`Job`](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/-job/index.html) [(source)](https://github.com/RBusarow/Dispatch/tree/master/dispatch-android-lifecycle-extensions/src/main/java/dispatch/android/lifecycle/withViewLifecycleScope.kt#L30)

[CoroutineScope](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/-coroutine-scope/index.html) helper for a [Fragment](https://developer.android.com/reference/androidx/androidx/fragment/app/Fragment.html)'s [ViewLifecycleOwner](https://developer.android.com/reference/androidx/androidx/fragment/app/FragmentViewLifecycleOwner.html).

This function observes a `Fragment`'s [viewLifecycleOwnerLiveData](https://developer.android.com/reference/androidx/androidx/fragment/app/Fragment.html#getViewLifecycleOwnerLiveData()),
and invokes [block](https://rbusarow.github.io/Dispatch/dispatch-android-lifecycle/dispatch.android.lifecycle/with-view-lifecycle-scope/block.md).

``` kotlin
class MyViewModel {

  val dataFlow = flow<Data> {
    // ...
  }
}

class MyFragment : Fragment() {

  val myViewModel = MyViewModel()

  val observerJob = withViewLifecycleScope {
    myViewModel.dataFlow.onEach { data ->
      // ...
    }.launchOnCreate()
  }
}
```

