[core-test-junit4](../index.md) / [dispatch.core.test](./index.md)

## Package dispatch.core.test

### Types

| Name | Summary |
|---|---|
| [TestCoroutineRule](-test-coroutine-rule/index.md) | A basic JUnit 4 [TestRule](https://junit.org/junit4/javadoc/latest/org/junit/rules/TestRule.html) which creates a new [TestProvidedCoroutineScope](https://rbusarow.github.io/Dispatch/core-test/dispatch.core.test/-test-provided-coroutine-scope/index.md) for each test, sets [Dispatchers.Main](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/-dispatchers/-main.html), and calls [cleanupTestCoroutines](https://rbusarow.github.io/Dispatch/core-test/dispatch.core.test/-test-coroutine-rule/cleanup-test-coroutines.md) afterwards.`class TestCoroutineRule : `[`TestWatcher`](https://junit.org/junit4/javadoc/latest/org/junit/rules/TestWatcher.html)`, `[`TestProvidedCoroutineScope`](https://rbusarow.github.io/Dispatch/core-test/dispatch.core.test/-test-provided-coroutine-scope/index.md) |
