[dispatch-android-espresso](../index.md) / [dispatch.android.espresso](index.md) / [MainImmediateIdlingCoroutineScope](./-main-immediate-idling-coroutine-scope.md)

# MainImmediateIdlingCoroutineScope

`interface MainImmediateIdlingCoroutineScope : `[`IdlingCoroutineScope`](-idling-coroutine-scope/index.md)`, `[`MainImmediateCoroutineScope`](https://rbusarow.github.io/Dispatch/dispatch-core/dispatch.core/-main-immediate-coroutine-scope/index.md) [(source)](https://github.com/RBusarow/Dispatch/tree/master/dispatch-android-espresso/src/main/java/dispatch/android/espresso/IdlingCoroutineScope.kt#L71)

Marker interface for an [IdlingCoroutineScope](-idling-coroutine-scope/index.md) which indicates that its [CoroutineDispatcher](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/-coroutine-dispatcher/index.html) is [DispatcherProvider.mainImmediate](https://rbusarow.github.io/Dispatch/dispatch-core/dispatch.core/-dispatcher-provider/main-immediate.md)

**See Also**

[IdlingDispatcherProvider](-idling-dispatcher-provider/index.md)

[IdlingResource](https://developer.android.com/reference/androidx/test/androidx/test/espresso/IdlingResource.html)

[IdlingCoroutineScope](-idling-coroutine-scope/index.md)

`fun MainImmediateIdlingCoroutineScope(job: `[`Job`](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/-job/index.html)` = SupervisorJob(), dispatcherProvider: `[`IdlingDispatcherProvider`](-idling-dispatcher-provider/index.md)` = IdlingDispatcherProvider()): `[`MainImmediateIdlingCoroutineScope`](./-main-immediate-idling-coroutine-scope.md) [(source)](https://github.com/RBusarow/Dispatch/tree/master/dispatch-android-espresso/src/main/java/dispatch/android/espresso/IdlingCoroutineScope.kt#L174)

Factory function for a [MainImmediateIdlingCoroutineScope](./-main-immediate-idling-coroutine-scope.md).

``` kotlin
val scope = MainImmediateIdlingCoroutineScope()

scope.idlingDispatcherProvider.registerAllIdlingResources()
```

``` kotlin
val scope = MainImmediateIdlingCoroutineScope(
  job = Job(),
  dispatcherProvider = SomeCustomIdlingDispatcherProvider()
)

scope.idlingDispatcherProvider.registerAllIdlingResources()
```

### Parameters

`job` - *optional* The [Job](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/-job/index.html) used in creation of the [CoroutineContext](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.coroutines/-coroutine-context/index.html).  Uses [SupervisorJob](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/-supervisor-job.html) by default.

`dispatcherProvider` - The [IdlingDispatcherProvider](-idling-dispatcher-provider/index.md) used in creation of the [CoroutineContext](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.coroutines/-coroutine-context/index.html).
Uses the default [IdlingDispatcherProvider](-idling-dispatcher-provider/index.md) factory by default.

**See Also**

[IdlingDispatcherProvider](-idling-dispatcher-provider/index.md)

[IdlingResource](https://developer.android.com/reference/androidx/test/androidx/test/espresso/IdlingResource.html)

[IdlingCoroutineScope](-idling-coroutine-scope/index.md)

