[dispatch-android-lifecycle](../../index.md) / [dispatch.android.lifecycle](../index.md) / [LifecycleCoroutineScopeFactory](index.md) / [&lt;init&gt;](./-init-.md)

# &lt;init&gt;

`LifecycleCoroutineScopeFactory(coroutineContextFactory: () -> `[`CoroutineContext`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.coroutines/-coroutine-context/index.html)`)`

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