[core-test](../../index.md) / [dispatch.core.test](../index.md) / [CoroutineTest](./index.md)

# CoroutineTest

`@ExperimentalCoroutinesApi @ExtendWith([NormalClass(value=dispatch/core/test/TestCoroutineExtension)]) interface CoroutineTest` [(source)](https://github.com/RBusarow/Dispatch/tree/master/core-test/src/main/java/dispatch/core/test/CoroutineTest.kt#L69)

Convenience interface for tagging a test class with a JUnit 5 extension.  This creates a new instance
of [testScope](test-scope.md) before each test, optionally using a custom [testScopeFactory](test-scope-factory.md).  It sets [Dispatchers.Main](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/-dispatchers/-main.html)
to the new [TestCoroutineDispatcher](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines.test/-test-coroutine-dispatcher/index.html) before each test and calls [Dispatchers.resetMain](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines.test/reset-main.html) afterwards.
After the test, it also calls [cleanupTestCoroutines](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines.test/-test-coroutine-scope/cleanup-test-coroutines.html).

```
class SomeTest : CoroutineTest {

  override val testScopeFactory = { MyCustomCoroutineScope() }
  override lateinit var testScope: TestProvidedCoroutineScope

  lateinit var someClass: SomeClass

  @BeforeEach
  fun beforeEach() {
    someClass = SomeClass(testScope)
  }

  @Test
  fun testSomething() = runBlocking {
    someClass.doSomething { ... }
  }

}
```

### Before `@BeforeEach`:

* A new [TestProvidedCoroutineScope](../-test-provided-coroutine-scope/index.md) is created using [testScopeFactory](test-scope-factory.md).
* [Dispatchers.Main](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/-dispatchers/-main.html) is set to the [TestCoroutineDispatcher](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines.test/-test-coroutine-dispatcher/index.html) used by the [CoroutineContext](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.coroutines/-coroutine-context/index.html).

### After `@AfterEach`:

* [cleanupTestCoroutines](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines.test/-test-coroutine-scope/cleanup-test-coroutines.html) is called to ensure there are no leaking coroutines.  Any unfinished coroutine
will throw an [UncompletedCoroutinesError](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines.test/-uncompleted-coroutines-error/index.html).
* [Dispatchers.Main](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/-dispatchers/-main.html) is reset via [Dispatchers.resetMain](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines.test/reset-main.html).

### Requires JUnit 5.

```
dependencies {
  testImplementation "org.junit.jupiter:junit-jupiter:5.5.1"
}
```

**See Also**

[TestCoroutineExtension](../-test-coroutine-extension/index.md)

### Properties

| Name | Summary |
|---|---|
| [testScope](test-scope.md) | `abstract var testScope: `[`TestProvidedCoroutineScope`](../-test-provided-coroutine-scope/index.md) |
| [testScopeFactory](test-scope-factory.md) | `open val testScopeFactory: () -> `[`TestProvidedCoroutineScope`](../-test-provided-coroutine-scope/index.md) |

### Functions

| Name | Summary |
|---|---|
| [runBlockingTest](run-blocking-test.md) | `open fun runBlockingTest(context: `[`CoroutineContext`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.coroutines/-coroutine-context/index.html)` = EmptyCoroutineContext, testBody: suspend `[`TestCoroutineScope`](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines.test/-test-coroutine-scope/index.html)`.() -> `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)`): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html) |
