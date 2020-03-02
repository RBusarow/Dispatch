[core-test-junit5](../../index.md) / [dispatch.core.test](../index.md) / [CoroutineTest](index.md) / [testScopeFactory](./test-scope-factory.md)

# testScopeFactory

`open val testScopeFactory: () -> `[`TestProvidedCoroutineScope`](https://rbusarow.github.io/Dispatch/core-test/dispatch.core.test/-test-provided-coroutine-scope/index.md) [(source)](https://github.com/RBusarow/Dispatch/tree/master/core-test-junit5/src/main/java/dispatch/core/test/CoroutineTest.kt#L55)

Optional parameter for defining a custom [TestProvidedCoroutineScope](https://rbusarow.github.io/Dispatch/core-test/dispatch.core.test/-test-provided-coroutine-scope/index.md).

Each iteration of a test will be a new invocation of this lambda.

