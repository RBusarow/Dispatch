[android-lifecycle](../../index.md) / [dispatch.android.lifecycle](../index.md) / [androidx.lifecycle.LifecycleOwner](index.md) / [onNextCreate](./on-next-create.md)

# onNextCreate

`suspend fun <T> `[`LifecycleOwner`](https://developer.android.com/reference/androidx/androidx/lifecycle/LifecycleOwner.html)`.onNextCreate(block: suspend `[`CoroutineScope`](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/-coroutine-scope/index.html)`.() -> T): T?` [(source)](https://github.com/RBusarow/Dispatch/tree/master/android-lifecycle/src/main/java/dispatch/android/lifecycle/LifecycleSuspendExt.kt#L30)

Executes [block](on-next-create.md#dispatch.android.lifecycle$onNextCreate(androidx.lifecycle.LifecycleOwner, kotlin.coroutines.SuspendFunction1((kotlinx.coroutines.CoroutineScope, dispatch.android.lifecycle.onNextCreate.T)))/block) one time, the next time the [Lifecycle](https://developer.android.com/reference/androidx/androidx/lifecycle/Lifecycle.html)'s state is at least [Lifecycle.State.CREATED](https://developer.android.com/reference/androidx/androidx/lifecycle/Lifecycle/State.html#CREATED).

If the lifecycle is already in this state, [block](on-next-create.md#dispatch.android.lifecycle$onNextCreate(androidx.lifecycle.LifecycleOwner, kotlin.coroutines.SuspendFunction1((kotlinx.coroutines.CoroutineScope, dispatch.android.lifecycle.onNextCreate.T)))/block) will be executed immediately.

``` kotlin
runBlocking {

    class SomeFragment : Fragment() {

      var invocations = 0

      init {
        lifecycleScope.launchMainImmediate {
          onNextCreate { invocations++ }
        }
      }
    }

    // current view lifecycle state is INITIALIZED
    val fragment = SomeFragment()

    // nothing is invoked yet
    fragment.invocations shouldBe 0

    fragment.create()
    fragment.invocations shouldBe 1
  }
```

**See Also**

[LifecycleCoroutineScope.launchOnCreate](../-lifecycle-coroutine-scope/launch-on-create.md)

