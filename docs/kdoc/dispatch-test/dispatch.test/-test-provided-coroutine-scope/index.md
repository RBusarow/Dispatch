[dispatch-test](../../index.md) / [dispatch.test](../index.md) / [TestProvidedCoroutineScope](./index.md)

# TestProvidedCoroutineScope

`@ExperimentalCoroutinesApi interface TestProvidedCoroutineScope : `[`TestCoroutineScope`](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-test/kotlinx.coroutines.test/-test-coroutine-scope/index.html)`, `[`DefaultCoroutineScope`](https://rbusarow.github.io/Dispatch/dispatch-core/dispatch.core/-default-coroutine-scope/index.md)`, `[`IOCoroutineScope`](https://rbusarow.github.io/Dispatch/dispatch-core/dispatch.core/-i-o-coroutine-scope/index.md)`, `[`MainCoroutineScope`](https://rbusarow.github.io/Dispatch/dispatch-core/dispatch.core/-main-coroutine-scope/index.md)`, `[`MainImmediateCoroutineScope`](https://rbusarow.github.io/Dispatch/dispatch-core/dispatch.core/-main-immediate-coroutine-scope/index.md)`, `[`UnconfinedCoroutineScope`](https://rbusarow.github.io/Dispatch/dispatch-core/dispatch.core/-unconfined-coroutine-scope/index.md) [(source)](https://github.com/RBusarow/Dispatch/tree/master/dispatch-test/src/main/java/dispatch/test/TestProvidedCoroutineScope.kt#L38)

A polymorphic testing [CoroutineScope](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/-coroutine-scope/index.html) interface.

This single interface implements:
[TestCoroutineScope](https://rbusarow.github.io/Dispatch/dispatch-core/dispatch.core/-default-coroutine-scope/index.md)
[IOCoroutineScope](https://rbusarow.github.io/Dispatch/dispatch-core/dispatch.core/-main-coroutine-scope/index.md)
[MainImmediateCoroutineScope](https://rbusarow.github.io/Dispatch/dispatch-core/dispatch.core/-unconfined-coroutine-scope/index.md)

This means that it can be injected into any class or function
regardless of what type of `CoroutineScope` is required.

### Properties

| Name | Summary |
|---|---|
| [dispatcherProvider](dispatcher-provider.md) | single [DispatcherProvider](https://rbusarow.github.io/Dispatch/dispatch-core/dispatch.core/-dispatcher-provider/index.md) promise for the [TestProvidedCoroutineScope](./index.md)`abstract val dispatcherProvider: `[`DispatcherProvider`](https://rbusarow.github.io/Dispatch/dispatch-core/dispatch.core/-dispatcher-provider/index.md) |

### Extension Functions

| Name | Summary |
|---|---|
| [testProvided](../test-provided.md) | Delegates to [runBlockingTest](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-test/kotlinx.coroutines.test/run-blocking-test.html), but injects a [DispatcherProvider](https://rbusarow.github.io/Dispatch/dispatch-core/dispatch.core/-dispatcher-provider/index.md) into the created [TestCoroutineScope](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-test/kotlinx.coroutines.test/-test-coroutine-scope/index.html).`fun `[`TestProvidedCoroutineScope`](./index.md)`.testProvided(testBody: suspend `[`TestCoroutineScope`](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-test/kotlinx.coroutines.test/-test-coroutine-scope/index.html)`.() -> `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)`): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html) |
