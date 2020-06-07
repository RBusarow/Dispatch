[core](../index.md) / [dispatch.core](index.md) / [UnconfinedCoroutineScope](./-unconfined-coroutine-scope.md)

# UnconfinedCoroutineScope

`interface UnconfinedCoroutineScope : `[`CoroutineScope`](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/-coroutine-scope/index.html) [(source)](https://github.com/RBusarow/Dispatch/tree/master/core/src/main/java/dispatch/core/CoroutineScopes.kt#L46)

Marker interface which designates a [CoroutineScope](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/-coroutine-scope/index.html) with a [CoroutineDispatcher](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/-coroutine-dispatcher/index.html) of `unconfined`.

`fun UnconfinedCoroutineScope(job: `[`Job`](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/-job/index.html)` = SupervisorJob(), dispatcherProvider: `[`DispatcherProvider`](-dispatcher-provider/index.md)` = DefaultDispatcherProvider()): `[`UnconfinedCoroutineScope`](./-unconfined-coroutine-scope.md) [(source)](https://github.com/RBusarow/Dispatch/tree/master/core/src/main/java/dispatch/core/CoroutineScopes.kt#L186)

Factory function for a [UnconfinedCoroutineScope](./-unconfined-coroutine-scope.md) with a [DispatcherProvider](-dispatcher-provider/index.md).
Dispatch defaults to the `unconfined` property of the `DispatcherProvider`.

### Parameters

`job` - [Job](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/-job/index.html) to be used for the resulting `CoroutineScope`.  Uses a [SupervisorJob](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/-supervisor-job.html) if one is not provided.

`dispatcherProvider` - [DispatcherProvider](-dispatcher-provider/index.md) to be used for the resulting `CoroutineScope`.  Uses a [DefaultDispatcherProvider](-default-dispatcher-provider/index.md) if one is not provided.

**See Also**

[CoroutineScope](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/-coroutine-scope/index.html)

`fun UnconfinedCoroutineScope(coroutineContext: `[`CoroutineContext`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.coroutines/-coroutine-context/index.html)`): `[`UnconfinedCoroutineScope`](./-unconfined-coroutine-scope.md) [(source)](https://github.com/RBusarow/Dispatch/tree/master/core/src/main/java/dispatch/core/CoroutineScopes.kt#L203)

Factory function for a [UnconfinedCoroutineScope](./-unconfined-coroutine-scope.md) with a [DispatcherProvider](-dispatcher-provider/index.md).
Dispatch defaults to the `unconfined` property of the `DispatcherProvider`.

### Parameters

`coroutineContext` - [CoroutineContext](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.coroutines/-coroutine-context/index.html) to be used for the resulting `CoroutineScope`.
Any existing [ContinuationInterceptor](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.coroutines/-continuation-interceptor/index.html) will be overwritten.
If the `CoroutineContext` does not already contain a `DispatcherProvider`, a [DefaultDispatcherProvider](-default-dispatcher-provider/index.md) will be added.

**See Also**

[CoroutineScope](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/-coroutine-scope/index.html)

