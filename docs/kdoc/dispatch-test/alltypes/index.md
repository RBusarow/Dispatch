

Test helpers for the [dispatch-core](https://rbusarow.github.io/Dispatch/dispatch-core//index.html) module.  Most of the tools you need to automatically handle [DispatcherProvider](https://rbusarow.github.io/Dispatch/dispatch-core//dispatch.core/-dispatcher-provider/index.html) in your tests.  (see [dispatch-test-junit4](https://rbusarow.github.io/Dispatch/dispatch-test-junit4//index.html) or [dispatch-test-junit5](https://rbusarow.github.io/Dispatch/dispatch-test-junit5//index.html) for the rest.)

### All Types

| Name | Summary |
|---|---|
|

##### [dispatch.test.TestDispatcherProvider](../dispatch.test/-test-dispatcher-provider/index.md)

[DispatcherProvider](https://rbusarow.github.io/Dispatch/dispatch-core/dispatch.core/-dispatcher-provider/index.md) implementation for testing, where each property defaults to a [TestCoroutineDispatcher](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-test/kotlinx.coroutines.test/-test-coroutine-dispatcher/index.html).


|

##### [dispatch.test.TestProvidedCoroutineScope](../dispatch.test/-test-provided-coroutine-scope/index.md)

A polymorphic testing [CoroutineScope](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/-coroutine-scope/index.html) interface.


