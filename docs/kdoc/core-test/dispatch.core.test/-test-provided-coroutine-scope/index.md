[core-test](../../index.md) / [dispatch.core.test](../index.md) / [TestProvidedCoroutineScope](./index.md)

# TestProvidedCoroutineScope

`@ExperimentalCoroutinesApi interface TestProvidedCoroutineScope : `[`TestCoroutineScope`](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines.test/-test-coroutine-scope/index.html)`, DefaultCoroutineScope, IOCoroutineScope, MainCoroutineScope, MainImmediateCoroutineScope, UnconfinedCoroutineScope` [(source)](https://github.com/RBusarow/Dispatch/tree/master/core-test/src/main/java/dispatch/core/test/TestProvidedCoroutineScope.kt#L38)

A polymorphic testing [CoroutineScope](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/-coroutine-scope/index.html) interface.

This single interface implements:
[TestCoroutineScope](#)
[IOCoroutineScope](#)
[MainImmediateCoroutineScope](#)

This means that it can be injected into any class or function
regardless of what type of `CoroutineScope` is required.

### Properties

| Name | Summary |
|---|---|
| [dispatcherProvider](dispatcher-provider.md) | `abstract val dispatcherProvider: DispatcherProvider` |

### Inheritors

| Name | Summary |
|---|---|
| [TestCoroutineRule](../-test-coroutine-rule/index.md) | A basic JUnit 4 [TestRule](#) which creates a new [TestProvidedCoroutineScope](./index.md) for each test, sets [Dispatchers.Main](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/-dispatchers/-main.html), and calls [cleanupTestCoroutines](#) afterwards.`class TestCoroutineRule : TestWatcher, `[`TestProvidedCoroutineScope`](./index.md) |
