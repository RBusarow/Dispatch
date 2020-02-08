[core-test-junit4](../index.md) / [dispatch.core.test](./index.md)

## Package dispatch.core.test

### Types

| Name | Summary |
|---|---|
| [TestCoroutineRule](-test-coroutine-rule/index.md) | A basic JUnit 4 [TestRule](#) which creates a new [TestProvidedCoroutineScope](#) for each test, sets [Dispatchers.Main](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/-dispatchers/-main.html), and calls [cleanupTestCoroutines](#) afterwards.`class TestCoroutineRule : TestWatcher, TestProvidedCoroutineScope` |
