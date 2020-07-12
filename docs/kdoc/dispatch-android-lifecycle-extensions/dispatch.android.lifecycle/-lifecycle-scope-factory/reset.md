[dispatch-android-lifecycle-extensions](../../index.md) / [dispatch.android.lifecycle](../index.md) / [LifecycleScopeFactory](index.md) / [reset](./reset.md)

# reset

`fun reset(): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html) [(source)](https://github.com/RBusarow/Dispatch/tree/master/dispatch-android-lifecycle-extensions/src/main/java/dispatch/android/lifecycle/LifecycleScopeFactory.kt#L59)

Immediately resets the factory function to its default.

``` kotlin
class MyEspressoTest {

  @Before
  fun setUp() {

    val dispatcherProvider = DefaultDispatcherProvider()

    LifecycleScopeFactory.set { SupervisorJob() + dispatcherProvider + dispatcherProvider.mainImmediate }
  }

  @After
  fun tearDown() {
    LifecycleScopeFactory.reset()
  }
}
```

