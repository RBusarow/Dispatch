

### All Types

| Name | Summary |
|---|---|
|

##### [dispatch.core.test.CoroutineTest](../dispatch.core.test/-coroutine-test/index.md)

Convenience interface for tagging a test class with a JUnit 5 extension.  This creates a new instance
of [testScope](../dispatch.core.test/-coroutine-test/test-scope.md) before each test, optionally using a custom [testScopeFactory](../dispatch.core.test/-coroutine-test/test-scope-factory.md).  It sets [Dispatchers.Main](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/-dispatchers/-main.html)
to the new [TestCoroutineDispatcher](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines.test/-test-coroutine-dispatcher/index.html) before each test and calls [Dispatchers.resetMain](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines.test/reset-main.html) afterwards.
After the test, it also calls [cleanupTestCoroutines](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines.test/-test-coroutine-scope/cleanup-test-coroutines.html).


|

##### [dispatch.core.test.TestCoroutineExtension](../dispatch.core.test/-test-coroutine-extension/index.md)

Convenience interface for tagging a test class with a JUnit 5 extension.  This creates a new instance
of [testScope](../dispatch.core.test/-test-coroutine-extension/test-scope.md) before each test, optionally using a custom [factory](#).  It sets [Dispatchers.Main](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/-dispatchers/-main.html)
to the new [TestCoroutineDispatcher](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines.test/-test-coroutine-dispatcher/index.html) before each test and calls [Dispatchers.resetMain](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines.test/reset-main.html) afterwards.
After the test, it also calls [cleanupTestCoroutines](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines.test/-test-coroutine-scope/cleanup-test-coroutines.html).


|

##### [dispatch.core.test.TestCoroutineRule](../dispatch.core.test/-test-coroutine-rule/index.md)

A basic JUnit 4 [TestRule](#) which creates a new [TestProvidedCoroutineScope](../dispatch.core.test/-test-provided-coroutine-scope/index.md) for each test,
sets [Dispatchers.Main](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/-dispatchers/-main.html), and calls [cleanupTestCoroutines](#) afterwards.


|

##### [dispatch.core.test.TestDispatcherProvider](../dispatch.core.test/-test-dispatcher-provider/index.md)

[DispatcherProvider](#) implementation for testing, where each property is a [CoroutineDispatcher](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/-coroutine-dispatcher/index.html).


|

##### [dispatch.core.test.TestProvidedCoroutineScope](../dispatch.core.test/-test-provided-coroutine-scope/index.md)

A polymorphic testing [CoroutineScope](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/-coroutine-scope/index.html) interface.


