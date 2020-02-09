[dispatcher-provider-test](../../index.md) / [com.rickbusarow.dispatcherprovider.test](../index.md) / [TestProvidedCoroutineScope](./index.md)

# TestProvidedCoroutineScope

`@ExperimentalCoroutinesApi interface TestProvidedCoroutineScope : `[`TestCoroutineScope`](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines.test/-test-coroutine-scope/index.html)`, DefaultCoroutineScope, IOCoroutineScope, MainCoroutineScope, MainImmediateCoroutineScope, UnconfinedCoroutineScope` [(source)](https://github.com/RBusarow/Dispatch/tree/master/dispatcher-provider-test/src/main/java/com/rickbusarow/dispatcherprovider/test/TestProvidedCoroutineScope.kt#L38)

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
