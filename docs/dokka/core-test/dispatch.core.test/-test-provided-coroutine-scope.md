[core-test](../index.md) / [dispatch.core.test](index.md) / [TestProvidedCoroutineScope](./-test-provided-coroutine-scope.md)

# TestProvidedCoroutineScope

`@ExperimentalCoroutinesApi fun TestProvidedCoroutineScope(dispatcher: `[`TestCoroutineDispatcher`](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines.test/-test-coroutine-dispatcher/index.html)` = TestCoroutineDispatcher(), dispatcherProvider: `[`TestDispatcherProvider`](-test-dispatcher-provider/index.md)` = TestDispatcherProvider(dispatcher), context: `[`CoroutineContext`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.coroutines/-coroutine-context/index.html)` = EmptyCoroutineContext): `[`TestProvidedCoroutineScope`](-test-provided-coroutine-scope/index.md) [(source)](https://github.com/RBusarow/Dispatch/tree/master/core-test/src/main/java/dispatch/core/test/TestProvidedCoroutineScope.kt#L64)

Creates a [TestProvidedCoroutineScope](-test-provided-coroutine-scope/index.md) implementation with optional parameters of
[TestCoroutineDispatcher](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines.test/-test-coroutine-dispatcher/index.html), [TestDispatcherProvider](-test-dispatcher-provider/index.md), and a generic [CoroutineContext](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.coroutines/-coroutine-context/index.html).

The resultant `TestProvidedCoroutineScope` will utilize a single `TestCoroutineDispatcher`
for all the [CoroutineDispatcher](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/-coroutine-dispatcher/index.html) properties of its [DispatcherProvider](https://rbusarow.github.io/Dispatch/core/dispatch.core/-dispatcher-provider/index.md),
and the [ContinuationInterceptor](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.coroutines/-continuation-interceptor/index.html) Key of the `CoroutineContext` will also return that `TestCoroutineDispatcher`.

