[core-test](../../index.md) / [dispatch.core.test](../index.md) / [TestDispatcherProvider](./index.md)

# TestDispatcherProvider

`@ExperimentalCoroutinesApi class TestDispatcherProvider : `[`DispatcherProvider`](https://rbusarow.github.io/Dispatch/core/dispatch.core/-dispatcher-provider/index.md) [(source)](https://github.com/RBusarow/Dispatch/tree/master/core-test/src/main/java/dispatch/core/test/TestDispatcherProvider.kt#L28)

[DispatcherProvider](https://rbusarow.github.io/Dispatch/core/dispatch.core/-dispatcher-provider/index.md) implementation for testing, where each property defaults to a [TestCoroutineDispatcher](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines.test/-test-coroutine-dispatcher/index.html).

A default version will create a different `TestCoroutineDispatcher` for each property.

### Constructors

| Name | Summary |
|---|---|
| [&lt;init&gt;](-init-.md) | [DispatcherProvider](https://rbusarow.github.io/Dispatch/core/dispatch.core/-dispatcher-provider/index.md) implementation for testing, where each property defaults to a [TestCoroutineDispatcher](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines.test/-test-coroutine-dispatcher/index.html).`TestDispatcherProvider(default: `[`CoroutineDispatcher`](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/-coroutine-dispatcher/index.html)` = TestCoroutineDispatcher(), io: `[`CoroutineDispatcher`](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/-coroutine-dispatcher/index.html)` = TestCoroutineDispatcher(), main: `[`CoroutineDispatcher`](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/-coroutine-dispatcher/index.html)` = TestCoroutineDispatcher(), mainImmediate: `[`CoroutineDispatcher`](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/-coroutine-dispatcher/index.html)` = TestCoroutineDispatcher(), unconfined: `[`CoroutineDispatcher`](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/-coroutine-dispatcher/index.html)` = TestCoroutineDispatcher())` |

### Properties

| Name | Summary |
|---|---|
| [default](default.md) | [CoroutineDispatcher](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/-coroutine-dispatcher/index.html) generally intended for cpu-bound tasks.`val default: `[`CoroutineDispatcher`](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/-coroutine-dispatcher/index.html) |
| [io](io.md) | [CoroutineDispatcher](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/-coroutine-dispatcher/index.html) generally intended for blocking I/O tasks.`val io: `[`CoroutineDispatcher`](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/-coroutine-dispatcher/index.html) |
| [main](main.md) | [CoroutineDispatcher](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/-coroutine-dispatcher/index.html) which is confined to the "main" thread.`val main: `[`CoroutineDispatcher`](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/-coroutine-dispatcher/index.html) |
| [mainImmediate](main-immediate.md) | [CoroutineDispatcher](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/-coroutine-dispatcher/index.html) which is confined to the "main" thread with immediate dispatch.`val mainImmediate: `[`CoroutineDispatcher`](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/-coroutine-dispatcher/index.html) |
| [unconfined](unconfined.md) | Corresponds to the [Dispatchers.Unconfined](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/-dispatchers/-unconfined.html) property in a default implementation.`val unconfined: `[`CoroutineDispatcher`](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/-coroutine-dispatcher/index.html) |
