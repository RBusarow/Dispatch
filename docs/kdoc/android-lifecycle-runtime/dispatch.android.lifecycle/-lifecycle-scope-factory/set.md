[android-lifecycle-runtime](../../index.md) / [dispatch.android.lifecycle](../index.md) / [LifecycleScopeFactory](index.md) / [set](./set.md)

# set

`fun set(factory: () -> MainImmediateCoroutineScope): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html) [(source)](https://github.com/RBusarow/Dispatch/tree/master/android-lifecycle-runtime/src/main/java/dispatch/android/lifecycle/LifecycleScopeFactory.kt#L81)

example:

```
class MyApplication : Application() {

  override fun onCreate() {
    super.onCreate()
    LifecycleScopeFactory.set { MainImmediateCoroutineScope() + MyCustomElement() }
  }
}
```

```
class MyEspressoTest {

  @Before fun setUp() {
    LifecycleCoroutineScopeFactory.set { MainImmediateIdlingCoroutineScope() }
  }
}
```

### Parameters

`factory` - sets a custom [MainImmediateCoroutineScope](#) factory to be used for all new instance creations until reset.