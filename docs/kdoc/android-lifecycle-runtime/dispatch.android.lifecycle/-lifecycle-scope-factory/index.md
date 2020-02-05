[android-lifecycle-runtime](../../index.md) / [dispatch.android.lifecycle](../index.md) / [LifecycleScopeFactory](./index.md)

# LifecycleScopeFactory

`object LifecycleScopeFactory` [(source)](https://github.com/RBusarow/Dispatch/tree/master/android-lifecycle-runtime/src/main/java/dispatch/android/lifecycle/LifecycleScopeFactory.kt#L53)

Factory holder for [LifecycleCoroutineScope](../-lifecycle-coroutine-scope/index.md)'s.

By default, [create](#) returns a [MainImmediateCoroutineScope](#).

This factory can be overridden for testing or to include a custom [CoroutineContext](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.coroutines/-coroutine-context/index.html)
in production code.  This may be done in [Application.onCreate](https://developer.android.com/reference/android/app/Application.html#onCreate()).

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

[reset](reset.md) may be used to reset the factory to default at any time.

### Functions

| Name | Summary |
|---|---|
| [reset](reset.md) | Immediately resets the factory function to its default.`fun reset(): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html) |
| [set](set.md) | example:`fun set(factory: () -> MainImmediateCoroutineScope): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html) |
