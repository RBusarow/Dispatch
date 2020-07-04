[dispatch-android-lifecycle-extensions](../../index.md) / [dispatch.android.lifecycle](../index.md) / [LifecycleScopeFactory](./index.md)

# LifecycleScopeFactory

`object LifecycleScopeFactory` [(source)](https://github.com/RBusarow/Dispatch/tree/master/dispatch-android-lifecycle-extensions/src/main/java/dispatch/android/lifecycle/LifecycleScopeFactory.kt#L34)

Factory holder for [LifecycleCoroutineScope](https://rbusarow.github.io/Dispatch/dispatch-android-lifecycle/dispatch.android.lifecycle/-lifecycle-coroutine-scope/index.md)'s.

By default, `create` returns a [MainImmediateCoroutineScope](https://rbusarow.github.io/Dispatch/dispatch-core/dispatch.core/-main-immediate-coroutine-scope/index.md).

This factory can be overridden for testing or to include a custom [CoroutineContext](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.coroutines/-coroutine-context/index.html)
in production code.  This may be done in [Application.onCreate](https://developer.android.com/reference/android/app/Application.html#onCreate())

[reset](https://rbusarow.github.io/Dispatch/dispatch-android-lifecycle/dispatch.android.lifecycle/-lifecycle-scope-factory/reset.md) may be used to reset the factory to default at any time.

``` kotlin
class MyApplication : Application {

  override fun onCreate() {
    LifecycleScopeFactory.set { MainImmediateCoroutineScope() }
  }
}
```

``` kotlin
class MyEspressoTest {

  @Before
  fun setUp() {
    LifecycleScopeFactory.set { MainImmediateIdlingCoroutineScope() }
  }
}
```

### Functions

| Name | Summary |
|---|---|
| [reset](reset.md) | Immediately resets the factory function to its default.`fun reset(): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html) |
| [set](set.md) | Override the default [MainImmediateCoroutineScope](https://rbusarow.github.io/Dispatch/dispatch-core/dispatch.core/-main-immediate-coroutine-scope/index.md) factory, for testing or to include a custom [CoroutineContext](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.coroutines/-coroutine-context/index.html) in production code.  This may be done in [Application.onCreate](https://developer.android.com/reference/android/app/Application.html#onCreate())`fun set(factory: () -> `[`MainImmediateCoroutineScope`](https://rbusarow.github.io/Dispatch/dispatch-core/dispatch.core/-main-immediate-coroutine-scope/index.md)`): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html) |
