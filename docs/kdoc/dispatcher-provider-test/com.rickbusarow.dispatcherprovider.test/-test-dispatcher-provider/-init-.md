[dispatcher-provider-test](../../index.md) / [com.rickbusarow.dispatcherprovider.test](../index.md) / [TestDispatcherProvider](index.md) / [&lt;init&gt;](./-init-.md)

# &lt;init&gt;

`TestDispatcherProvider(default: `[`CoroutineDispatcher`](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/-coroutine-dispatcher/index.html)` = TestCoroutineDispatcher(), io: `[`CoroutineDispatcher`](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/-coroutine-dispatcher/index.html)` = TestCoroutineDispatcher(), main: `[`CoroutineDispatcher`](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/-coroutine-dispatcher/index.html)` = TestCoroutineDispatcher(), mainImmediate: `[`CoroutineDispatcher`](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/-coroutine-dispatcher/index.html)` = TestCoroutineDispatcher(), unconfined: `[`CoroutineDispatcher`](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/-coroutine-dispatcher/index.html)` = TestCoroutineDispatcher())`

[DispatcherProvider](#) implementation for testing, where each property is a [CoroutineDispatcher](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/-coroutine-dispatcher/index.html).

A default version will create a different `TestCoroutineDispatcher` for each property.

