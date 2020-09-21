[dispatch-android-lifecycle-extensions](../../index.md) / [dispatch.android.lifecycle](../index.md) / [LifecycleScopeFactory](index.md) / [set](./set.md)

# set

`fun set(factory: `[`LifecycleCoroutineScopeFactory`](https://rbusarow.github.io/Dispatch/dispatch-android-lifecycle/dispatch.android.lifecycle/-lifecycle-coroutine-scope-factory/index.md)`): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html) [(source)](https://github.com/RBusarow/Dispatch/tree/master/dispatch-android-lifecycle-extensions/src/main/java/dispatch/android/lifecycle/LifecycleScopeFactory.kt#L78)
`fun set(factory: () -> `[`CoroutineContext`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.coroutines/-coroutine-context/index.html)`): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html) [(source)](https://github.com/RBusarow/Dispatch/tree/master/dispatch-android-lifecycle-extensions/src/main/java/dispatch/android/lifecycle/LifecycleScopeFactory.kt#L97)

Override the default [MainImmediateCoroutineScope](https://rbusarow.github.io/Dispatch/dispatch-core/dispatch.core/-main-immediate-coroutine-scope/index.md) factory, for testing or to include a custom [CoroutineContext](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.coroutines/-coroutine-context/index.html)
in production code.  This may be done in [Application.onCreate](https://developer.android.com/reference/android/app/Application.html#onCreate())

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

### Parameters

`factory` - sets a custom [CoroutineContext](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.coroutines/-coroutine-context/index.html) factory to be used for all new instance creations until reset.
Its [Elements](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.coroutines/-coroutine-context/-element/index.html) will be re-used, except: