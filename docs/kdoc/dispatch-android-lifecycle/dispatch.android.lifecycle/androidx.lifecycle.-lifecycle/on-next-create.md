[dispatch-android-lifecycle](../../index.md) / [dispatch.android.lifecycle](../index.md) / [androidx.lifecycle.Lifecycle](index.md) / [onNextCreate](./on-next-create.md)

# onNextCreate

`suspend fun <T> `[`Lifecycle`](https://developer.android.com/reference/androidx/androidx/lifecycle/Lifecycle.html)`.onNextCreate(context: `[`CoroutineContext`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.coroutines/-coroutine-context/index.html)` = EmptyCoroutineContext, block: suspend `[`CoroutineScope`](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/-coroutine-scope/index.html)`.() -> T): T?` [(source)](https://github.com/RBusarow/Dispatch/tree/master/dispatch-android-lifecycle/src/main/java/dispatch/android/lifecycle/suspend.kt#L46)

Executes `block` one time, the next time the [Lifecycle](https://developer.android.com/reference/androidx/androidx/lifecycle/Lifecycle.html)'s state is at least [Lifecycle.State.CREATED](https://developer.android.com/reference/androidx/androidx/lifecycle/Lifecycle/State.html#CREATED).

If the lifecycle is already in this state, `block` will be executed immediately.

``` kotlin
runBlocking {

    class SomeFragment : Fragment() {

      var invocations = 0

      init {
        lifecycleScope.launchMainImmediate {
          lifecycle.onNextCreate { invocations++ }
        }
      }
    }

    // current lifecycle state is INITIALIZED
    val fragment = SomeFragment()

    // nothing is invoked yet
    fragment.invocations shouldBe 0

    fragment.create()
    fragment.invocations shouldBe 1
  }
```

### Parameters

`context` - *optional* - additional to [CoroutineScope.coroutineContext](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/-coroutine-scope/coroutine-context.html) context of the coroutine.

**See Also**

[LifecycleCoroutineScope.launchOnCreate](../-lifecycle-coroutine-scope/launch-on-create.md)

