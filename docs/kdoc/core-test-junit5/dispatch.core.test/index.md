[core-test-junit5](../index.md) / [dispatch.core.test](./index.md)

## Package dispatch.core.test

### Types

| Name | Summary |
|---|---|
| [CoroutineTest](-coroutine-test/index.md) | Convenience interface which tags a test class with a JUnit 5 extension.  This creates a new instance of [testScope](-coroutine-test/test-scope.md) before each test, optionally using a custom [testScopeFactory](-coroutine-test/test-scope-factory.md).`interface CoroutineTest` |
| [TestCoroutineExtension](-test-coroutine-extension/index.md) | Convenience interface for tagging a test class with a JUnit 5 extension.  This creates a new instance of [testScope](-test-coroutine-extension/test-scope.md) before each test, optionally using a custom [factory](#).`class TestCoroutineExtension : TestInstancePostProcessor, BeforeEachCallback, AfterEachCallback` |
