[dispatch-android-viewmodel](../../index.md) / [dispatch.android.viewmodel](../index.md) / [ViewModelScopeFactory](index.md) / [set](./set.md)

# set

`fun set(factory: () -> `[`CoroutineScope`](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/-coroutine-scope/index.html)`): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html) [(source)](https://github.com/RBusarow/Dispatch/tree/master/dispatch-android-viewmodel/src/main/java/dispatch/android/viewmodel/ViewModelScopeFactory.kt#L48)

Override the default [MainImmediateCoroutineScope](https://rbusarow.github.io/Dispatch/dispatch-core/dispatch.core/-main-immediate-coroutine-scope/index.md) factory, for testing or to include a custom [CoroutineContext](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.coroutines/-coroutine-context/index.html)
in production code.  This may be done in [Application.onCreate](https://developer.android.com/reference/android/app/Application.html#onCreate())

``` kotlin
class MyApplication : Application {

  override fun onCreate() {
    ViewModelScopeFactory.set { MainImmediateCoroutineScope() }
  }
}
```

``` kotlin
class MyEspressoTest {

  @Before
  fun setUp() {
    ViewModelScopeFactory.set { MainImmediateIdlingCoroutineScope() }
  }
}
```

``` kotlin
class MyJvmTest {

  @Before
  fun setUp() {
    ViewModelScopeFactory.set { TestProvidedCoroutineScope() }
  }
}
```

### Parameters

`factory` - sets a custom [CoroutineScope](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/-coroutine-scope/index.html) factory to be used for all new instance creations until reset.