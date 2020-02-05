# dispatch-android-lifecycle-viewmodel

The artifact I hope you don't need, but if you're not doing dependency injection, you probably do.

### Examples

```Kotlin
import dispatch.android.*

// CoroutineViewModel is just a ViewModel with a lazy viewModelScope
class SomeViewModel : CoroutineViewModel() {
  // ...

  init {

    // auto-creates a MainImmediateCoroutineScope which is closed in onCleared()
    viewModelScope. //...

    // multiple invocations use the same instance
    viewModelScope.launch { ... }

    // it works as a normal CoroutineScope (because it is)
    viewModelScope.launchMain { ... }

  }
}

class SomeApplication : Application() {
  override fun onCreate() {
    super.onCreate()
    // A custom factory can be set to add elements to the CoroutineContext
    ViewModelScopeFactory.set { MainImmediateCoroutineScope() + SomeCustomElement() }
  }
}

class SomeViewModelTest {

  val viewModel = SomeViewModel()

  @Before
  fun setUp() {
    // This custom factory can be used to use custom scopes for testing
    ViewModelScopeFactory.set { TestProvidedCoroutineScope() }
  }

  @After
  fun tearDown() {
    // The factory can also be reset to default
    ViewModelScopeFactory.reset()
  }

  @Test
  fun `some test`() = runBlocking {
    // the AndroidX version is public, so it's public here as well.
    viewModel.viewModelScope.launch { ... }
  }
}
```

### Isn't this just AndroidX?

This module is essentially a fork of [androidx-lifecycle-viewmodel-ktx][androidx-lifecycle] — the library which gives us the [viewModelScope][viewModelScope] property.

It exists entirely so that we can have a settable factory.  This gives us a lot more options for JVM or instrumented tests, with custom dispatchers or other custom `CoroutineContext` elements.

### Custom CoroutineScope factories

The way `androidx-lifecycle-viewModel` constructs its [CoroutineScope][coroutineScope] is [hard-coded][lifecycle.kt], which eliminates the possibility of using a custom [CoroutineContext][coroutineContext] such as a [DispatcherProvider][dispatcherProvider] or [IdlingDispatcher][idlingDispatcher]. With `dispatch.android.lifecycle`, we can set a custom factory.

```Kotlin
class SomeViewModelTest {

  @Before
  fun setUp() {
    // This custom factory can be used to use custom scopes for testing
    ViewModelScopeFactory.set { TestProvidedCoroutineScope() }

    // it could also return a specific instance
    val someTestScope = TestProvidedCoroutineScope()
    ViewModelScopeFactory.set { someTestScope }
  }

  @After
  fun tearDown() {
    // The factory can also be reset to default
    ViewModelScopeFactory.reset()
  }
}
```

### Automatic cancellation in onCleared()

Just like AndroidX, this version of `viewModelScope` is automatically cancelled in `ViewModel.onCleared()`.

### viewModelScope != lifecycleScope

It's important to remember that `onCleared()` is only called when a `ViewModel` is about to be destroyed -- when its associated `LifecycleOwner`(s) are all destroyed.  This means that a `viewModelScope` is active while the `LifecycleOwner` is in the backstack.

Consider this example:

```Kotlin
// Don't do this
class SomeViewModel : CoroutineViewModel() {

  init {
    viewModelScope.launch {
      // this job will continue forever even if the ViewModel is on the backstack.
      someRepository.dataFlow.collect {
        parseData(it)
      }
    }
  }
}
```

A `CoroutineScope` in a `ViewModel` is better utilized for single-shot requests which shouldn't be restarted in the event of a configuration change.  "Observer" behavior should be scoped to the associated view.

```Kotlin
// Maybe do this
class SomeFragment : Fragment() {

  val viewModel: SomeViewModel by viewModels()

  init {
    lifecycleScope.launchWhenResumed {
      viewModel.dataFlow.collect { ... }
    }
  }
}

class SomeViewModel : CoroutineViewModel() {

  // a single shot request is made using the viewModelScope
  val lazyData by lazy {
    CompletableDeferred<Data>().apply {
      viewModelScope.launch {
        complete(someRepository.getData())
      }
    }
  }

  // collection of the Flow is done using the view's lifecycleScope,
  // meaning that it will stop as soon as the screen is in the backstack
  val dataFlow = someRepository.dataFlow.onEach {
    parseData(it)
  }
}

```

### Extending ViewModel

Since nothing about the `clear` event is actually exposed outside of `ViewModel`, it's necessary to extend `ViewModel` in order to consume it for cancelling the `viewModelScope`.  This is especially galling since `ViewModel` could absolutely have just been an interface to begin with.

### Future plans

I'll be suggesting to make this change in AndroidX as well, but I can get it released and usable a lot faster in my own project, so here we are.

I will maintain this artifact until it is implemented AndroidX, or they provide some other suitable solution for custom scopes.

### The artifact

in your project `build.gradle`:

```Groovy
dependencies {

  // core is required
  implementation 'com.rickbusarow.dispatcherprovider:dispatch-core:$version'

  implementation 'com.rickbusarow.dispatcherprovider:dispatch-android-lifecycle-viewModel:$version'
}
```



[androidx-lifecycle]:https://developer.android.com/jetpack/androidx/releases/lifecycle
[viewModelScope]:https://developer.android.com/topic/libraries/architecture/coroutines#viewmodelscope
[coroutineContext]:https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.coroutines/-coroutine-context/
[coroutineScope]:https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/coroutine-scope.html
[flow.conflate]:https://github.com/Kotlin/kotlinx.coroutines/blob/master/docs/flow.md#conflation
[lifecycle.java]:https://cs.android.com/androidx/platform/frameworks/support/+/androidx-master-dev:lifecycle/lifecycle-common/src/main/java/androidx/lifecycle/Lifecycle.java
[idlingDispatcher]:https://github.com/RBusarow/Dispatch/blob/dev/dispatch-android-espresso/src/main/java/dispatch/test/android/IdlingDispatcher.kt
[channel]:https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines.channels/-channel/
[pausingDispatcher]:https://cs.android.com/androidx/platform/frameworks/support/+/androidx-master-dev:lifecycle/lifecycle-runtime-ktx/src/main/java/androidx/lifecycle/PausingDispatcher.kt
[b/146370660]:https://issuetracker.google.com/issues/146370660
