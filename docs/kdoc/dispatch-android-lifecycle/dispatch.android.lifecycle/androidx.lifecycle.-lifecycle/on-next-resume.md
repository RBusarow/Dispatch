[dispatch-android-lifecycle](../../index.md) / [dispatch.android.lifecycle](../index.md) / [androidx.lifecycle.Lifecycle](index.md) / [onNextResume](./on-next-resume.md)

# onNextResume

`suspend fun <T> `[`Lifecycle`](https://developer.android.com/reference/androidx/androidx/lifecycle/Lifecycle.html)`.onNextResume(context: `[`CoroutineContext`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.coroutines/-coroutine-context/index.html)` = EmptyCoroutineContext, block: suspend `[`CoroutineScope`](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/-coroutine-scope/index.html)`.() -> T): T?` [(source)](https://github.com/RBusarow/Dispatch/tree/master/dispatch-android-lifecycle/src/main/java/dispatch/android/lifecycle/suspend.kt#L102)

Executes `block` one time, the next time the [Lifecycle](https://developer.android.com/reference/androidx/androidx/lifecycle/Lifecycle.html)'s state is at least [Lifecycle.State.RESUMED](https://developer.android.com/reference/androidx/androidx/lifecycle/Lifecycle/State.html#RESUMED).

If the lifecycle is already in this state, `block` will be executed immediately.

``` kotlin
runBlocking {

    class SomeFragment : Fragment() {

      var invocations = 0

      init {
        lifecycleScope.launchMainImmediate {
          lifecycle.onNextResume { invocations++ }
        }
      }
    }

    // current lifecycle state is INITIALIZED
    val fragment = SomeFragment()

    // nothing is invoked yet
    fragment.invocations shouldBe 0

    fragment.resume()
    fragment.invocations shouldBe 1

    // crossing the threshold doesn't invoke the lambda again
    fragment.pause()
    fragment.resume()
    fragment.invocations shouldBe 1
  }
```

### Parameters

`context` - *optional* - additional to [CoroutineScope.coroutineContext](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/-coroutine-scope/coroutine-context.html) context of the coroutine.

**See Also**

[LifecycleCoroutineScope.launchOnResume](../-lifecycle-coroutine-scope/launch-on-resume.md)

