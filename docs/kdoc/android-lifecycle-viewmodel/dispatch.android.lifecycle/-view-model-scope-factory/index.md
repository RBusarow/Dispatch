[android-lifecycle-viewmodel](../../index.md) / [dispatch.android.lifecycle](../index.md) / [ViewModelScopeFactory](./index.md)

# ViewModelScopeFactory

`object ViewModelScopeFactory` [(source)](https://github.com/RBusarow/Dispatch/tree/master/android-lifecycle-viewmodel/src/main/java/dispatch/android/lifecycle/ViewModelScopeFactory.kt#L54)

Factory holder for [viewModelScope](../-coroutine-view-model/view-model-scope.md)'s.

By default, [create](#) returns a [MainImmediateCoroutineScope](#), but may return any [CoroutineScope](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/-coroutine-scope/index.html).

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
| [set](set.md) | example:`fun set(factory: () -> `[`CoroutineScope`](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/-coroutine-scope/index.html)`): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html) |
