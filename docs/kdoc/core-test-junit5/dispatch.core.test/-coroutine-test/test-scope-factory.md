[core-test-junit5](../../index.md) / [dispatch.core.test](../index.md) / [CoroutineTest](index.md) / [testScopeFactory](./test-scope-factory.md)

# testScopeFactory

`open val testScopeFactory: () -> TestProvidedCoroutineScope` [(source)](https://github.com/RBusarow/Dispatch/tree/master/core-test-junit5/src/main/java/dispatch/core/test/CoroutineTest.kt#L55)

Optional parameter for defining a custom [TestProvidedCoroutineScope](#).

Each iteration of a test will be a new invocation of this lambda.

