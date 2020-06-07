[dispatch-android-viewmodel](../../index.md) / [dispatch.android.lifecycle](../index.md) / [ViewModelScopeFactory](index.md) / [reset](./reset.md)

# reset

`fun reset(): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html) [(source)](https://github.com/RBusarow/Dispatch/tree/master/dispatch-android-viewmodel/src/main/java/dispatch/android/lifecycle/ViewModelScopeFactory.kt#L59)

Immediately resets the factory function to its default.

``` kotlin
class MyEspressoTest {

  @Before
  fun setUp() {
    ViewModelScopeFactory.set { MainImmediateIdlingCoroutineScope() }
  }

  @After
  fun tearDown() {
    ViewModelScopeFactory.reset()
  }
}
```

