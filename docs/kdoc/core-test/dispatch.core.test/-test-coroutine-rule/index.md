[core-test](../../index.md) / [dispatch.core.test](../index.md) / [TestCoroutineRule](./index.md)

# TestCoroutineRule

`@ExperimentalCoroutinesApi class TestCoroutineRule : TestWatcher, `[`TestProvidedCoroutineScope`](../-test-provided-coroutine-scope/index.md) [(source)](https://github.com/RBusarow/Dispatch/tree/master/core-test/src/main/java/dispatch/core/test/TestCoroutineRule.kt#L66)

A basic JUnit 4 [TestRule](#) which creates a new [TestProvidedCoroutineScope](../-test-provided-coroutine-scope/index.md) for each test,
sets [Dispatchers.Main](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/-dispatchers/-main.html), and calls [cleanupTestCoroutines](#) afterwards.

The rule itself implements [TestProvidedCoroutineScope](../-test-provided-coroutine-scope/index.md), so it can be used as follows:

```
class SomeTest {

  @JvmField @Rule val testScope = TestCoroutineRule()

  @Test
  fun testThings() = runBlocking {
    testScope.launch { ... }
  }
}
```

### Before the test:

* [Dispatchers.Main](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/-dispatchers/-main.html) is set to the [TestCoroutineDispatcher](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines.test/-test-coroutine-dispatcher/index.html) used by the [CoroutineContext](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.coroutines/-coroutine-context/index.html).

### After the test:

* [cleanupTestCoroutines](#) is called to ensure there are no leaking coroutines.  Any unfinished coroutine
will throw an [UncompletedCoroutinesError](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines.test/-uncompleted-coroutines-error/index.html).
* [Dispatchers.Main](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/-dispatchers/-main.html) is reset via [Dispatchers.resetMain](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines.test/reset-main.html).

### Requires JUnit 4.

```
dependencies {
  testImplementation "junit:junit:4.12"
  -- or --
  testImplementation "org.junit.vintage:junit-vintage-engine:5.5.1"
}
```

### Parameters

`factory` - *optional* factory for a custom [TestProvidedCoroutineScope](../-test-provided-coroutine-scope/index.md).  If a factory is not provided,
the resultant scope uses the same [TestCoroutineDispatcher](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines.test/-test-coroutine-dispatcher/index.html) for each property in its [TestDispatcherProvider](../-test-dispatcher-provider/index.md)

**See Also**

[TestRule](#)

[TestCoroutineScope](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines.test/-test-coroutine-scope/index.html)

[TestProvidedCoroutineScope](../-test-provided-coroutine-scope/index.md)

### Constructors

| Name | Summary |
|---|---|
| [&lt;init&gt;](-init-.md) | A basic JUnit 4 [TestRule](#) which creates a new [TestProvidedCoroutineScope](../-test-provided-coroutine-scope/index.md) for each test, sets [Dispatchers.Main](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/-dispatchers/-main.html), and calls [cleanupTestCoroutines](#) afterwards.`TestCoroutineRule(factory: () -> `[`TestProvidedCoroutineScope`](../-test-provided-coroutine-scope/index.md)` = { TestProvidedCoroutineScope() })` |

### Properties

| Name | Summary |
|---|---|
| [dispatcher](dispatcher.md) | `val dispatcher: `[`TestCoroutineDispatcher`](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines.test/-test-coroutine-dispatcher/index.html) |

### Functions

| Name | Summary |
|---|---|
| [finished](finished.md) | `fun finished(description: Description?): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html) |
| [starting](starting.md) | `fun starting(description: Description?): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html) |
