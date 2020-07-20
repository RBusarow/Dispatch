[dispatch-android-lifecycle](../../index.md) / [dispatch.android.lifecycle](../index.md) / [kotlinx.coroutines.CoroutineScope](index.md) / [withViewLifecycle](./with-view-lifecycle.md)

# withViewLifecycle

`@ExperimentalCoroutinesApi fun `[`CoroutineScope`](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/-coroutine-scope/index.html)`.withViewLifecycle(fragment: `[`Fragment`](https://developer.android.com/reference/androidx/androidx/fragment/app/Fragment.html)`, block: `[`ViewLifecycleCoroutineScope`](../-view-lifecycle-coroutine-scope/index.md)`.() -> `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)`): `[`Job`](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/-job/index.html) [(source)](https://github.com/RBusarow/Dispatch/tree/master/dispatch-android-lifecycle/src/main/java/dispatch/android/lifecycle/ViewLifecycleCoroutineScope.kt#L70)

[CoroutineScope](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/-coroutine-scope/index.html) helper for a [Fragment](https://developer.android.com/reference/androidx/androidx/fragment/app/Fragment.html)'s [ViewLifecycleOwner](https://developer.android.com/reference/androidx/androidx/fragment/app/FragmentViewLifecycleOwner.html).

This function observes a `Fragment`'s [viewLifecycleOwnerLiveData](https://developer.android.com/reference/androidx/androidx/fragment/app/Fragment.html#getViewLifecycleOwnerLiveData()),
and invokes [block](with-view-lifecycle.md#dispatch.android.lifecycle$withViewLifecycle(kotlinx.coroutines.CoroutineScope, androidx.fragment.app.Fragment, kotlin.Function1((dispatch.android.lifecycle.ViewLifecycleCoroutineScope, kotlin.Unit)))/block).

``` kotlin
class MyFragment @Inject constructor(
  scope: MainImmediateCoroutineScope
) : Fragment() {

  val myViewModel by viewModels<MyViewModel>()

  val observerJob = scope.withViewLifecycle(this) {
    // this lambda is invoked every time the View lifecycle is set
    myViewModel.dataFlow.onEach { data ->
      // ...
    }.launchOnCreate()
  }
}
```

