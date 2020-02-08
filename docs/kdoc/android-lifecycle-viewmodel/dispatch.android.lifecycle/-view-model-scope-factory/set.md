[android-lifecycle-viewmodel](../../index.md) / [dispatch.android.lifecycle](../index.md) / [ViewModelScopeFactory](index.md) / [set](./set.md)

# set

`fun set(factory: () -> `[`CoroutineScope`](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/-coroutine-scope/index.html)`): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html) [(source)](https://github.com/RBusarow/Dispatch/tree/master/android-lifecycle-viewmodel/src/main/java/dispatch/android/lifecycle/ViewModelScopeFactory.kt#L81)

example:

```
class MyApplication : Application() {

  override fun onCreate() {
    super.onCreate()
    ViewModelScopeFactory.set { MainImmediateCoroutineScope() + MyCustomElement() }
  }
}
```

```
class MyEspressoTest {

  @Before fun setUp() {
    ViewModelScopeFactory.set { MainImmediateIdlingCoroutineScope() }
  }
}
```

### Parameters

`factory` - sets a custom [CoroutineScope](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/-coroutine-scope/index.html) factory to be used for all new instance creations until reset.