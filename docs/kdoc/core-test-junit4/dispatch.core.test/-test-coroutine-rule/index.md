[core-test-junit4](../../index.md) / [dispatch.core.test](../index.md) / [TestCoroutineRule](./index.md)

# TestCoroutineRule

`@ExperimentalCoroutinesApi class TestCoroutineRule : TestWatcher, TestProvidedCoroutineScope` [(source)](https://github.com/RBusarow/Dispatch/tree/master/core-test-junit4/src/main/java/dispatch/core/test/TestCoroutineRule.kt#L58)

A basic JUnit 4 [TestRule](#) which creates a new [TestProvidedCoroutineScope](#) for each test,
sets [Dispatchers.Main](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/-dispatchers/-main.html), and calls [cleanupTestCoroutines](#) afterwards.

The rule itself implements [TestProvidedCoroutineScope](#), so it can be used as follows:

### Before the test:

* [Dispatchers.Main](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/-dispatchers/-main.html) is set to the [TestCoroutineDispatcher](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines.test/-test-coroutine-dispatcher/index.html) used by the [CoroutineContext](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.coroutines/-coroutine-context/index.html).

### After the test:

* [cleanupTestCoroutines](#) is called to ensure there are no leaking coroutines.  Any unfinished coroutine
will throw an [UncompletedCoroutinesError](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines.test/-uncompleted-coroutines-error/index.html).
* [Dispatchers.Main](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/-dispatchers/-main.html) is reset via [Dispatchers.resetMain](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines.test/reset-main.html).

### Requires JUnit 4.

``` groovy
dependencies {
  testImplementation "junit:junit:4.12"
  -- or --
  testImplementation "org.junit.vintage:junit-vintage-engine:5.5.1"
}
```

``` kotlin
@ExperimentalCoroutinesApi
class TestCoroutineRuleSample {

  @JvmField
  @Rule
  val rule = TestCoroutineRule()

  @Test
  fun `rule should be a TestProvidedCoroutineScope`() = runBlocking {

    rule.shouldBeInstanceOf<TestProvidedCoroutineScope>()

    rule.launch {
      // use the rule like any other CoroutineScope
    }
      .join()
  }

}
```

``` kotlin
@ExperimentalCoroutinesApi
class TestCoroutineRuleWithFactorySample {

  val customScope = TestProvidedCoroutineScope(
    context = CoroutineName("custom name")
  )

  @JvmField
  @Rule
  val rule = TestCoroutineRule { customScope }

  @Test
  fun `rule should be a TestProvidedCoroutineScope`() = runBlocking {

    rule.shouldBeInstanceOf<TestProvidedCoroutineScope>()

    rule.launch {
      // use the rule like any other CoroutineScope
    }
      .join()
  }

  @Test
  fun `rule should be the provided custom scope`() = runBlocking {

    val context = rule.coroutineContext

    context shouldBe customScope.coroutineContext
  }
}
```

### Parameters

`factory` - *optional* factory for a custom [TestProvidedCoroutineScope](#).  If a factory is not provided,
the resultant scope uses the same [TestCoroutineDispatcher](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines.test/-test-coroutine-dispatcher/index.html) for each property in its [TestDispatcherProvider](#)

**See Also**

[TestRule](#)

[TestCoroutineScope](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines.test/-test-coroutine-scope/index.html)

[TestProvidedCoroutineScope](#)

### Constructors

| Name | Summary |
|---|---|
| [&lt;init&gt;](-init-.md) | A basic JUnit 4 [TestRule](#) which creates a new [TestProvidedCoroutineScope](#) for each test, sets [Dispatchers.Main](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/-dispatchers/-main.html), and calls [cleanupTestCoroutines](#) afterwards.`TestCoroutineRule(factory: () -> TestProvidedCoroutineScope = { TestProvidedCoroutineScope() })` |

### Properties

| Name | Summary |
|---|---|
| [dispatcher](dispatcher.md) | `val dispatcher: `[`TestCoroutineDispatcher`](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines.test/-test-coroutine-dispatcher/index.html) |

### Functions

| Name | Summary |
|---|---|
| [finished](finished.md) | `fun finished(description: Description?): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html) |
| [starting](starting.md) | `fun starting(description: Description?): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html) |