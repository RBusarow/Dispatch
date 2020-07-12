[dispatch-android-lifecycle](../../index.md) / [dispatch.android.lifecycle](../index.md) / [LifecycleCoroutineScopeFactory](./index.md)

# LifecycleCoroutineScopeFactory

`class LifecycleCoroutineScopeFactory` [(source)](https://github.com/RBusarow/Dispatch/tree/master/dispatch-android-lifecycle/src/main/java/dispatch/android/lifecycle/LifecycleCoroutineScopeFactory.kt#L28)

Factory for [LifecycleCoroutineScope](../-lifecycle-coroutine-scope/index.md)s.  This may be injected into a lifecycle-aware class
to provide custom [CoroutineContexts](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.coroutines/-coroutine-context/index.html).

``` kotlin
@Provides
fun provideFactory(): LifecycleCoroutineScopeFactory =
  LifecycleCoroutineScopeFactory {
    // other elements are added automatically
    MyCustomElement()
  }

class MyFragment @Inject constructor(
  factory: LifecycleCoroutineScopeFactory
) : Fragment() {

  val lifecycleScope = factory.create(lifecycle)

  init {
    lifecycleScope.launchOnStart {
      // ...
    }
  }
}
```

### Parameters

`coroutineContextFactory` - the lambda defining the creating of a [CoroutineContext](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.coroutines/-coroutine-context/index.html)

### Constructors

| Name | Summary |
|---|---|
| [&lt;init&gt;](-init-.md) | Factory for [LifecycleCoroutineScope](../-lifecycle-coroutine-scope/index.md)s.  This may be injected into a lifecycle-aware class to provide custom [CoroutineContexts](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.coroutines/-coroutine-context/index.html).`LifecycleCoroutineScopeFactory(coroutineContextFactory: () -> `[`CoroutineContext`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.coroutines/-coroutine-context/index.html)`)` |

### Functions

| Name | Summary |
|---|---|
| [create](create.md) | Creates a new [LifecycleCoroutineScope](../-lifecycle-coroutine-scope/index.md) using `coroutineContextFactory``fun create(lifecycle: `[`Lifecycle`](https://developer.android.com/reference/androidx/androidx/lifecycle/Lifecycle.html)`): `[`LifecycleCoroutineScope`](../-lifecycle-coroutine-scope/index.md) |
