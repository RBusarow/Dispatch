

Test helpers for the [core](https://rbusarow.github.io/Dispatch/core//index.html) module.  Most of the tools you need to automatically handle [DispatcherProvider](https://rbusarow.github.io/Dispatch/core//dispatch.core/-dispatcher-provider/index.html) in your tests.  (see [core-test-junit4](https://rbusarow.github.io/Dispatch/core-test-junit4//index.html) or [core-test-junit5](https://rbusarow.github.io/Dispatch/core-test-junit5//index.html) for the rest.)

### All Types

| Name | Summary |
|---|---|
|

##### [dispatch.core.test.TestDispatcherProvider](../dispatch.core.test/-test-dispatcher-provider/index.md)

[DispatcherProvider](https://rbusarow.github.io/Dispatch/core/dispatch.core/-dispatcher-provider/index.md) implementation for testing, where each property defaults to a [TestCoroutineDispatcher](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines.test/-test-coroutine-dispatcher/index.html).


|

##### [dispatch.core.test.TestProvidedCoroutineScope](../dispatch.core.test/-test-provided-coroutine-scope/index.md)

A polymorphic testing [CoroutineScope](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/-coroutine-scope/index.html) interface.


