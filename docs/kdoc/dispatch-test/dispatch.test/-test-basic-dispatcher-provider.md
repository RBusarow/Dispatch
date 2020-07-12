[dispatch-test](../index.md) / [dispatch.test](index.md) / [TestBasicDispatcherProvider](./-test-basic-dispatcher-provider.md)

# TestBasicDispatcherProvider

`@ExperimentalCoroutinesApi fun TestBasicDispatcherProvider(): `[`TestDispatcherProvider`](-test-dispatcher-provider/index.md) [(source)](https://github.com/RBusarow/Dispatch/tree/master/dispatch-test/src/main/java/dispatch/test/TestDispatcherProvider.kt#L108)

"Basic" [TestDispatcherProvider](-test-dispatcher-provider/index.md) which mimics production behavior,
without the automatic time control of [TestCoroutineDispatcher](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-test/kotlinx.coroutines.test/-test-coroutine-dispatcher/index.html)
and without the need for [Dispatchers.setMain](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-test/kotlinx.coroutines.test/kotlinx.coroutines.-dispatchers/set-main.html)

The `default`, `io`, and `unconfined` properties just delegate to their counterparts in [Dispatchers](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/-dispatchers/index.html).

The `main` and `mainImmediate` properties share a single dispatcher and thread
as they do with the `Dispatchers.setMain(...)` implementation from `kotlinx-coroutines-test`.
