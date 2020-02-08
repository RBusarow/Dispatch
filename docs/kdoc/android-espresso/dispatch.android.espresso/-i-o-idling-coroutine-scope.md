[android-espresso](../index.md) / [dispatch.android.espresso](index.md) / [IOIdlingCoroutineScope](./-i-o-idling-coroutine-scope.md)

# IOIdlingCoroutineScope

`interface IOIdlingCoroutineScope : `[`IdlingCoroutineScope`](-idling-coroutine-scope/index.md)`, IOCoroutineScope` [(source)](https://github.com/RBusarow/Dispatch/tree/master/android-espresso/src/main/java/dispatch/android/espresso/IdlingCoroutineScope.kt#L50)

Marker interface for an [IdlingCoroutineScope](-idling-coroutine-scope/index.md) which indicates that its [CoroutineDispatcher](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/-coroutine-dispatcher/index.html) is [DispatcherProvider.io](#)

**See Also**

[IdlingDispatcherProvider](-idling-dispatcher-provider/index.md)

[IdlingResource](#)

[IdlingCoroutineScope](-idling-coroutine-scope/index.md)

`fun IOIdlingCoroutineScope(job: `[`Job`](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/-job/index.html)` = SupervisorJob(), dispatcherProvider: `[`IdlingDispatcherProvider`](-idling-dispatcher-provider/index.md)` = IdlingDispatcherProvider()): `[`IOIdlingCoroutineScope`](./-i-o-idling-coroutine-scope.md) [(source)](https://github.com/RBusarow/Dispatch/tree/master/android-espresso/src/main/java/dispatch/android/espresso/IdlingCoroutineScope.kt#L125)

Factory function for an [IOIdlingCoroutineScope](./-i-o-idling-coroutine-scope.md).

### Parameters

`job` - *optional* The [Job](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/-job/index.html) used in creation of the [CoroutineContext](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.coroutines/-coroutine-context/index.html).  Uses [SupervisorJob](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/-supervisor-job.html) by default.

`dispatcherProvider` - The [IdlingDispatcherProvider](-idling-dispatcher-provider/index.md) used in creation of the [CoroutineContext](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.coroutines/-coroutine-context/index.html).
Uses the default [IdlingDispatcherProvider](-idling-dispatcher-provider/index.md) factory by default.

**See Also**

[IdlingDispatcherProvider](-idling-dispatcher-provider/index.md)

[IdlingResource](#)

[IdlingCoroutineScope](-idling-coroutine-scope/index.md)

