[dispatch-android-lifecycle](../../../index.md) / [dispatch.android.lifecycle](../../index.md) / [LifecycleCoroutineScope](../index.md) / [MinimumStatePolicy](./index.md)

# MinimumStatePolicy

`enum class MinimumStatePolicy` [(source)](https://github.com/RBusarow/Dispatch/tree/master/dispatch-android-lifecycle/src/main/java/dispatch/android/lifecycle/LifecycleCoroutineScope.kt#L106)

Describes the way a particular [Job](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/-job/index.html) will behave if the [lifecycle](../lifecycle.md) passes below the minimum state
before said [Job](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/-job/index.html) has completed.

### Enum Values

| Name | Summary |
|---|---|
| [CANCEL](-c-a-n-c-e-l.md) | When using `CANCEL`, a coroutine will be created the first time the [lifecycle](../lifecycle.md) meets the minimum state, and cancelled upon dropping below it. Subsequently meeting the minimum state again will not resume the coroutine or create a new one. |
| [RESTART_EVERY](-r-e-s-t-a-r-t_-e-v-e-r-y.md) | When using `RESTART_EVERY`, a coroutine will be created every time the [lifecycle](../lifecycle.md) meets the minimum state, and will be cancelled upon dropping below it. Subsequently meeting the minimum state again will create a new coroutine. |
