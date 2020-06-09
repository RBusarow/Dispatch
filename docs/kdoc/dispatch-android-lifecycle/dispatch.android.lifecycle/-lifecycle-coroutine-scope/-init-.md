[dispatch-android-lifecycle](../../index.md) / [dispatch.android.lifecycle](../index.md) / [LifecycleCoroutineScope](index.md) / [&lt;init&gt;](./-init-.md)

# &lt;init&gt;

`LifecycleCoroutineScope(lifecycle: `[`Lifecycle`](https://developer.android.com/reference/androidx/androidx/lifecycle/Lifecycle.html)`, coroutineScope: `[`MainImmediateCoroutineScope`](https://rbusarow.github.io/Dispatch/dispatch-core/dispatch.core/-main-immediate-coroutine-scope/index.md)`)`

[MainImmediateCoroutineScope](https://rbusarow.github.io/Dispatch/dispatch-core/dispatch.core/-main-immediate-coroutine-scope/index.md) instance which is tied to a [Lifecycle](https://developer.android.com/reference/androidx/androidx/lifecycle/Lifecycle.html).

The [CoroutineScope](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/-coroutine-scope/index.html) provides lifecycle-aware [launch](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/launch.html) functions
which will automatically start upon reaching their associated [Lifecycle.Event](https://developer.android.com/reference/androidx/androidx/lifecycle/Lifecycle/Event.html),
then automatically cancel upon the [lifecycle](lifecycle.md) dropping below that state.  Reaching
that state again will start a new [Job](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/-job/index.html).

``` kotlin
runBlocking {

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
  }
```

### Parameters

`lifecycle` - the lifecycle to which this [MainImmediateCoroutineScope](https://rbusarow.github.io/Dispatch/dispatch-core/dispatch.core/-main-immediate-coroutine-scope/index.md) is linked.