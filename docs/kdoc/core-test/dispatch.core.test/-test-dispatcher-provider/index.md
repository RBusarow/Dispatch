[core-test](../../index.md) / [dispatch.core.test](../index.md) / [TestDispatcherProvider](./index.md)

# TestDispatcherProvider

`@ExperimentalCoroutinesApi class TestDispatcherProvider : DispatcherProvider` [(source)](https://github.com/RBusarow/Dispatch/tree/master/core-test/src/main/java/dispatch/core/test/TestDispatcherProvider.kt#L28)

[DispatcherProvider](#) implementation for testing, where each property is a [CoroutineDispatcher](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/-coroutine-dispatcher/index.html).

A default version will create a different `TestCoroutineDispatcher` for each property.

### Constructors

| Name | Summary |
|---|---|
| [&lt;init&gt;](-init-.md) | [DispatcherProvider](#) implementation for testing, where each property is a [CoroutineDispatcher](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/-coroutine-dispatcher/index.html).`TestDispatcherProvider(default: `[`CoroutineDispatcher`](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/-coroutine-dispatcher/index.html)` = TestCoroutineDispatcher(), io: `[`CoroutineDispatcher`](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/-coroutine-dispatcher/index.html)` = TestCoroutineDispatcher(), main: `[`CoroutineDispatcher`](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/-coroutine-dispatcher/index.html)` = TestCoroutineDispatcher(), mainImmediate: `[`CoroutineDispatcher`](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/-coroutine-dispatcher/index.html)` = TestCoroutineDispatcher(), unconfined: `[`CoroutineDispatcher`](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/-coroutine-dispatcher/index.html)` = TestCoroutineDispatcher())` |

### Properties

| Name | Summary |
|---|---|
| [default](default.md) | `val default: `[`CoroutineDispatcher`](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/-coroutine-dispatcher/index.html) |
| [io](io.md) | `val io: `[`CoroutineDispatcher`](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/-coroutine-dispatcher/index.html) |
| [main](main.md) | `val main: `[`CoroutineDispatcher`](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/-coroutine-dispatcher/index.html) |
| [mainImmediate](main-immediate.md) | `val mainImmediate: `[`CoroutineDispatcher`](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/-coroutine-dispatcher/index.html) |
| [unconfined](unconfined.md) | `val unconfined: `[`CoroutineDispatcher`](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/-coroutine-dispatcher/index.html) |

### Functions

| Name | Summary |
|---|---|
| [toString](to-string.md) | `fun toString(): `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) |
