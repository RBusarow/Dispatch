[dispatch-core](../index.md) / [dispatch.core](index.md) / [MainCoroutineScope](./-main-coroutine-scope.md)

# MainCoroutineScope

`interface MainCoroutineScope : `[`CoroutineScope`](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/-coroutine-scope/index.html) [(source)](https://github.com/RBusarow/Dispatch/tree/master/dispatch-core/src/main/java/dispatch/core/CoroutineScopes.kt#L36)

Marker interface which designates a [CoroutineScope](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/-coroutine-scope/index.html) with a [CoroutineDispatcher](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/-coroutine-dispatcher/index.html) of `main`.

`fun MainCoroutineScope(job: `[`Job`](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/-job/index.html)` = SupervisorJob(), dispatcherProvider: `[`DispatcherProvider`](-dispatcher-provider/index.md)` = DefaultDispatcherProvider.get()): `[`MainCoroutineScope`](./-main-coroutine-scope.md) [(source)](https://github.com/RBusarow/Dispatch/tree/master/dispatch-core/src/main/java/dispatch/core/CoroutineScopes.kt#L121)

Factory function for a [MainCoroutineScope](./-main-coroutine-scope.md) with a [DispatcherProvider](-dispatcher-provider/index.md).
Dispatch defaults to the `main` property of the `DispatcherProvider`.

### Parameters

`job` - [Job](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/-job/index.html) to be used for the resulting `CoroutineScope`.  Uses a [SupervisorJob](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/-supervisor-job.html) if one is not provided.

`dispatcherProvider` - [DispatcherProvider](-dispatcher-provider/index.md) to be used for the resulting `CoroutineScope`.  Uses [DefaultDispatcherProvider.get](-default-dispatcher-provider/get.md) if one is not provided.

**See Also**

[CoroutineScope](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/-coroutine-scope/index.html)

`fun MainCoroutineScope(coroutineContext: `[`CoroutineContext`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.coroutines/-coroutine-context/index.html)`): `[`MainCoroutineScope`](./-main-coroutine-scope.md) [(source)](https://github.com/RBusarow/Dispatch/tree/master/dispatch-core/src/main/java/dispatch/core/CoroutineScopes.kt#L138)

Factory function for a [MainCoroutineScope](./-main-coroutine-scope.md) with a [DispatcherProvider](-dispatcher-provider/index.md).
Dispatch defaults to the `main` property of the `DispatcherProvider`.

### Parameters

`coroutineContext` - [CoroutineContext](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.coroutines/-coroutine-context/index.html) to be used for the resulting `CoroutineScope`.
Any existing [ContinuationInterceptor](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.coroutines/-continuation-interceptor/index.html) will be overwritten.
If the `CoroutineContext` does not already contain a `DispatcherProvider`, [DefaultDispatcherProvider.get](-default-dispatcher-provider/get.md) will be added.

**See Also**

[CoroutineScope](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/-coroutine-scope/index.html)

