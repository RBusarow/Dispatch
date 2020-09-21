[dispatch-android-lifecycle](../../index.md) / [dispatch.android.lifecycle](../index.md) / [androidx.lifecycle.LifecycleOwner](index.md) / [onNextStart](./on-next-start.md)

# onNextStart

`suspend fun <T> `[`LifecycleOwner`](https://developer.android.com/reference/androidx/androidx/lifecycle/LifecycleOwner.html)`.onNextStart(context: `[`CoroutineContext`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.coroutines/-coroutine-context/index.html)` = EmptyCoroutineContext, block: suspend `[`CoroutineScope`](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/-coroutine-scope/index.html)`.() -> T): T?` [(source)](https://github.com/RBusarow/Dispatch/tree/master/dispatch-android-lifecycle/src/main/java/dispatch/android/lifecycle/suspend.kt#L60)

Executes `block` one time, the next time the [Lifecycle](https://developer.android.com/reference/androidx/androidx/lifecycle/Lifecycle.html)'s state is at least [Lifecycle.State.STARTED](https://developer.android.com/reference/androidx/androidx/lifecycle/Lifecycle/State.html#STARTED).

If the lifecycle is already in this state, `block` will be executed immediately.

``` kotlin
runBlocking {

    class SomeFragment : Fragment() {

      var invocations = 0

      init {
        lifecycleScope.launchMainImmediate {
          onNextStart { invocations++ }
        }
      }
    }

    // current view lifecycle state is INITIALIZED
    val fragment = SomeFragment()

    // nothing is invoked yet
    fragment.invocations shouldBe 0

    fragment.start()
    fragment.invocations shouldBe 1

    // crossing the threshold doesn't invoke the lambda again
    fragment.stop()
    fragment.start()
    fragment.invocations shouldBe 1
  }
```

### Parameters

`context` - *optional* - additional to [CoroutineScope.coroutineContext](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/-coroutine-scope/coroutine-context.html) context of the coroutine.

**See Also**

[LifecycleCoroutineScope.launchOnStart](../-lifecycle-coroutine-scope/launch-on-start.md)

