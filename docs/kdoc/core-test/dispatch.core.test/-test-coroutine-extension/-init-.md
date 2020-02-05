[core-test](../../index.md) / [dispatch.core.test](../index.md) / [TestCoroutineExtension](index.md) / [&lt;init&gt;](./-init-.md)

# &lt;init&gt;

`TestCoroutineExtension(factory: () -> `[`TestProvidedCoroutineScope`](../-test-provided-coroutine-scope/index.md)` = { TestProvidedCoroutineScope() })`

Convenience interface for tagging a test class with a JUnit 5 extension.  This creates a new instance
of [testScope](test-scope.md) before each test, optionally using a custom [factory](#).  It sets [Dispatchers.Main](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/-dispatchers/-main.html)
to the new [TestCoroutineDispatcher](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines.test/-test-coroutine-dispatcher/index.html) before each test and calls [Dispatchers.resetMain](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines.test/reset-main.html) afterwards.
After the test, it also calls [cleanupTestCoroutines](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines.test/-test-coroutine-scope/cleanup-test-coroutines.html).

```
class SomeTest {

  @JvmField
  @RegisterExtension
  val extension = TestCoroutineExtension { MyCustomCoroutineScope() }

  lateinit var someClass: SomeClass

  @BeforeEach
  fun beforeEach() {
    someClass = SomeClass(extension.testScope)
  }

  @Test
  fun testSomething() = runBlocking {
    someClass.doSomething { ... }
  }

}
```

### Before `@BeforeEach`:

* A new [TestProvidedCoroutineScope](../-test-provided-coroutine-scope/index.md) is created using [factory](#).
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

### Parameters

`factory` - *optional* factory for a custom [TestProvidedCoroutineScope](../-test-provided-coroutine-scope/index.md).  If a factory is not provided,
the resultant scope uses the same [TestCoroutineDispatcher](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines.test/-test-coroutine-dispatcher/index.html) for each property in its [TestDispatcherProvider](../-test-dispatcher-provider/index.md)