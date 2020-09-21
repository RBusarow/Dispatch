[dispatch-android-lifecycle-extensions](../../index.md) / [dispatch.android.lifecycle](../index.md) / [LifecycleScopeFactory](./index.md)

# LifecycleScopeFactory

`object LifecycleScopeFactory` [(source)](https://github.com/RBusarow/Dispatch/tree/master/dispatch-android-lifecycle-extensions/src/main/java/dispatch/android/lifecycle/LifecycleScopeFactory.kt#L43)

Factory holder for [LifecycleCoroutineScope](https://rbusarow.github.io/Dispatch/dispatch-android-lifecycle/dispatch.android.lifecycle/-lifecycle-coroutine-scope/index.md)'s.

By default, `create` returns a [MainImmediateContext](https://rbusarow.github.io/Dispatch/dispatch-android-lifecycle/dispatch.android.lifecycle/-main-immediate-context.md).

This factory can be overridden for testing or to include a custom [CoroutineContext](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.coroutines/-coroutine-context/index.html)
in production code.  This may be done in [Application.onCreate](https://developer.android.com/reference/android/app/Application.html#onCreate()),
or else as early as possible in the Application's existence.

A custom factory will persist for the application's full lifecycle unless overwritten or [reset](https://rbusarow.github.io/Dispatch/dispatch-android-lifecycle/dispatch.android.lifecycle/-lifecycle-scope-factory/reset.md).

[reset](https://rbusarow.github.io/Dispatch/dispatch-android-lifecycle/dispatch.android.lifecycle/-lifecycle-scope-factory/reset.md) may be used to reset the factory to default at any time.

``` kotlin
class MyApplication : Application {

  override fun onCreate() {

    LifecycleScopeFactory.set { MyCustomElement() + MainImmediateContext() }
  }
}
```

``` kotlin
class MyEspressoTest {

  @Before
  fun setUp() {

    val dispatcherProvider = IdlingDispatcherProvider()

    LifecycleScopeFactory.set { SupervisorJob() + dispatcherProvider + dispatcherProvider.mainImmediate }
  }
}
```

**See Also**

[MainImmediateContext](https://rbusarow.github.io/Dispatch/dispatch-android-lifecycle/dispatch.android.lifecycle/-main-immediate-context.md)

[LifecycleCoroutineScope](https://rbusarow.github.io/Dispatch/dispatch-android-lifecycle/dispatch.android.lifecycle/-lifecycle-coroutine-scope/index.md)

[LifecycleCoroutineScopeFactory](https://rbusarow.github.io/Dispatch/dispatch-android-lifecycle/dispatch.android.lifecycle/-lifecycle-coroutine-scope-factory/index.md)

### Functions

| Name | Summary |
|---|---|
| [reset](reset.md) | Immediately resets the factory function to its default.`fun reset(): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html) |
| [set](set.md) | Override the default [MainImmediateCoroutineScope](https://rbusarow.github.io/Dispatch/dispatch-core/dispatch.core/-main-immediate-coroutine-scope/index.md) factory, for testing or to include a custom [CoroutineContext](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.coroutines/-coroutine-context/index.html) in production code.  This may be done in [Application.onCreate](https://developer.android.com/reference/android/app/Application.html#onCreate())`fun set(factory: `[`LifecycleCoroutineScopeFactory`](https://rbusarow.github.io/Dispatch/dispatch-android-lifecycle/dispatch.android.lifecycle/-lifecycle-coroutine-scope-factory/index.md)`): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)<br>`fun set(factory: () -> `[`CoroutineContext`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.coroutines/-coroutine-context/index.html)`): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html) |
