[dispatch-android-lifecycle](../../index.md) / [dispatch.android.lifecycle](../index.md) / [LifecycleCoroutineScope](index.md) / [&lt;init&gt;](./-init-.md)

# &lt;init&gt;

`LifecycleCoroutineScope(lifecycle: `[`Lifecycle`](https://developer.android.com/reference/androidx/androidx/lifecycle/Lifecycle.html)`, coroutineContext: `[`CoroutineContext`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.coroutines/-coroutine-context/index.html)` = MainImmediateContext())`

[MainImmediateCoroutineScope](https://rbusarow.github.io/Dispatch/dispatch-core/dispatch.core/-main-immediate-coroutine-scope/index.md) which is tied to a [Lifecycle](https://developer.android.com/reference/androidx/androidx/lifecycle/Lifecycle.html).

The [CoroutineScope](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/-coroutine-scope/index.html) provides lifecycle-aware [launch](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/launch.html) functions
which will automatically start upon reaching their associated [Lifecycle.Event](https://developer.android.com/reference/androidx/androidx/lifecycle/Lifecycle/Event.html),
then automatically cancel upon the lifecycle dropping below that state.  Reaching
that state again will start a new [Job](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/-job/index.html).

This `CoroutineScope`'s [Job](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/-job/index.html) will be cancelled automatically
as soon as the `lifecycle` reaches [DESTROYED](https://developer.android.com/reference/androidx/androidx/lifecycle/Lifecycle/State.html#DESTROYED).

``` kotlin
runBlocking {

    // This could be any LifecycleOwner -- Fragments, Activities, Services...
    class SomeFragment : Fragment() {

      val lifecycleScope = LifecycleCoroutineScope(lifecycle)

      init {

        // active only when "resumed".  starts a fresh coroutine each time
        lifecycleScope.launchOnResume { }

        // active only when "started".  starts a fresh coroutine each time
        // this is a rough proxy for LiveData behavior
        lifecycleScope.launchOnStart { }

        // active after only the first "started" event, and never re-started
        lifecycleScope.launchOnStart(minimumStatePolicy = CANCEL) { }

        // launch when created, automatically stop on destroy
        lifecycleScope.launchOnCreate { }

        // it works as a normal CoroutineScope as well (because it is)
        lifecycleScope.launchMain { }
      }
    }
  }
```

``` kotlin
runBlocking {

    // This could be any LifecycleOwner -- Fragments, Activities, Services...
    class SomeFragment : Fragment() {

      val context = Job() + DispatcherProvider()

      val lifecycleScope = LifecycleCoroutineScope(lifecycle, context)

      init {

        // active only when "resumed".  starts a fresh coroutine each time
        lifecycleScope.launchOnResume { }

        // active only when "started".  starts a fresh coroutine each time
        // this is a rough proxy for LiveData behavior
        lifecycleScope.launchOnStart { }

        // active after only the first "started" event, and never re-started
        lifecycleScope.launchOnStart(minimumStatePolicy = CANCEL) { }

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

`coroutineContext` - the source CoroutineContext which will be converted to a [MainImmediateCoroutineScope](https://rbusarow.github.io/Dispatch/dispatch-core/dispatch.core/-main-immediate-coroutine-scope/index.md).
Its [Elements](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.coroutines/-coroutine-context/-element/index.html) will be re-used, except: