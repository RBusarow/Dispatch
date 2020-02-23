[core-test](../../index.md) / [dispatch.core.test](../index.md) / [TestProvidedCoroutineScope](./index.md)

# TestProvidedCoroutineScope

`@ExperimentalCoroutinesApi interface TestProvidedCoroutineScope : `[`TestCoroutineScope`](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines.test/-test-coroutine-scope/index.html)`, `[`DefaultCoroutineScope`](https://rbusarow.github.io/Dispatch/core/dispatch.core/-default-coroutine-scope/index.md)`, `[`IOCoroutineScope`](https://rbusarow.github.io/Dispatch/core/dispatch.core/-i-o-coroutine-scope/index.md)`, `[`MainCoroutineScope`](https://rbusarow.github.io/Dispatch/core/dispatch.core/-main-coroutine-scope/index.md)`, `[`MainImmediateCoroutineScope`](https://rbusarow.github.io/Dispatch/core/dispatch.core/-main-immediate-coroutine-scope/index.md)`, `[`UnconfinedCoroutineScope`](https://rbusarow.github.io/Dispatch/core/dispatch.core/-unconfined-coroutine-scope/index.md) [(source)](https://github.com/RBusarow/Dispatch/tree/master/core-test/src/main/java/dispatch/core/test/TestProvidedCoroutineScope.kt#L38)

A polymorphic testing [CoroutineScope](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/-coroutine-scope/index.html) interface.

This single interface implements:
[TestCoroutineScope](https://rbusarow.github.io/Dispatch/core/dispatch.core/-default-coroutine-scope/index.md)
[IOCoroutineScope](https://rbusarow.github.io/Dispatch/core/dispatch.core/-main-coroutine-scope/index.md)
[MainImmediateCoroutineScope](https://rbusarow.github.io/Dispatch/core/dispatch.core/-unconfined-coroutine-scope/index.md)

This means that it can be injected into any class or function
regardless of what type of `CoroutineScope` is required.

### Properties

| Name | Summary |
|---|---|
| [dispatcherProvider](dispatcher-provider.md) | `abstract val dispatcherProvider: `[`DispatcherProvider`](https://rbusarow.github.io/Dispatch/core/dispatch.core/-dispatcher-provider/index.md) |
