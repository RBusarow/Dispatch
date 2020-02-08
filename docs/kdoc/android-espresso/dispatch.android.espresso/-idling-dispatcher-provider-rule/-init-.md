[android-espresso](../../index.md) / [dispatch.android.espresso](../index.md) / [IdlingDispatcherProviderRule](index.md) / [&lt;init&gt;](./-init-.md)

# &lt;init&gt;

`IdlingDispatcherProviderRule(factory: () -> `[`IdlingDispatcherProvider`](../-idling-dispatcher-provider/index.md)`)`

A basic JUnit 4 [TestRule](#) which creates a new [IdlingDispatcherProvider](../-idling-dispatcher-provider/index.md) for each test,
registering all [IdlingDispatcher](../-idling-dispatcher/index.md)s with [IdlingRegistry](#) before `@Before` and unregistering them after `@After`.

The rule takes an optional [IdlingDispatcherProvider](../-idling-dispatcher-provider/index.md) factory, in which case it only handles registration.

When doing Espresso testing, it's important that the same [IdlingDispatcher](../-idling-dispatcher/index.md)s are used throughout a codebase.
For this reason, it's a good idea to use a dependency injection framework just as Dagger or Koin
to provide [CoroutineScope](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/-coroutine-scope/index.html)s.

```
class SomeEspressoTest {

  // Retrieve the DispatcherProvider from a dependency graph,
  // so that the same one is used throughout the codebase.
  val customDispatcherProvider = testAppComponent.customDispatcherProvider

  @JvmField @Rule val idlingRule = IdlingDispatcherProviderRule { IdlingDispatcherProvider(customDispatcherProvider) }

  @Test
  fun testThings() = runBlocking {

    // for the duration of this test

  }
}
```

If using the `lifecycleScope` and `viewModelScope` properties,
be sure to use the versions from the `dispatch-android-lifecycle` artifacts to make use of their settable factories.

```
import dispatch.lifecycle.*

class SomeEspressoTest {

  val customDispatcherProvider = IdlingDispatcherProvider()

  @JvmField @Rule val idlingRule = IdlingDispatcherProviderRule { customDispatcherProvider }

  @Before
  fun setUp() {
    LifecycleScopeFactory.set { MainImmediateCoroutineScope(customDispatcherProvider) }
    ViewModelScopeFactory.set { MainImmediateCoroutineScope(customDispatcherProvider) }

    // now all scopes use the same IdlingDispatcherProvider
  }

  @Test
  fun testThings() = runBlocking {

    // for the duration of this test

  }
}
```

### Before the test:

* [IdlingDispatcherProvider.registerAllIdlingResources](../register-all-idling-resources.md) is called to register all dispatchers with [IdlingRegistry](#).

### After the test:

* [IdlingDispatcherProvider.unregisterAllIdlingResources](../unregister-all-idling-resources.md) is called to unregister all dispatchers with [IdlingRegistry](#).

### Requires JUnit 4.

```
dependencies {
  testImplementation "junit:junit:4.12"
  -- or --
  testImplementation "org.junit.vintage:junit-vintage-engine:5.5.1"
}
```

### Parameters

`factory` - factory for a custom [IdlingDispatcherProvider](../-idling-dispatcher-provider/index.md).
This must be the same [DispatcherProvider](#) which is used to create [CoroutineScope](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/-coroutine-scope/index.html)s in the code being tested.

**See Also**

[TestRule](#)

[TestCoroutineScope](#)

[IdlingRegistry](#)

