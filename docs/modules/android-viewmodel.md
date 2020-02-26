# Module dispatch-android-viewmodel

The artifact I hope you don't need, but if you're not doing dependency injection, you probably do.
## Contents
<!--- TOC -->

* [Examples](#examples)
* [Difference from AndroidX](#difference-from-androidx)
* [Custom CoroutineScope factories](#custom-coroutinescope-factories)
* [Automatic cancellation in onCleared()](#automatic-cancellation-in-oncleared)
* [viewModelScope is not lifecycleScope](#viewmodelscope-is-not-lifecyclescope)
* [Extending ViewModel](#extending-viewmodel)
* [Future plans](#future-plans)
* [Minimum Gradle Config](#minimum-gradle-config)

<!--- END -->
## Examples

```Kotlin
import dispatch.android.*

// CoroutineViewModel is just a ViewModel with a lazy viewModelScope
class SomeViewModel : CoroutineViewModel() {
  // ...

  init {

    // auto-creates a MainImmediateCoroutineScope which is closed in onCleared()
    viewModelScope. //...

    // multiple invocations use the same instance
    viewModelScope.launch {  }

    // it works as a normal CoroutineScope (because it is)
    viewModelScope.launchMain {  }

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
  fun someTest() = runBlocking {
    // the AndroidX version is public, so it's public here as well.
    viewModel.viewModelScope.launch {  }
  }
}
```

## Difference from AndroidX

This module is essentially a fork of [androidx-lifecycle-viewmodel-ktx] — the library which gives us the [viewModelScope][androidx-viewModelScope] property.

It exists entirely so that we can have a settable factory.  This gives us a lot more options for JVM or instrumented tests, with custom dispatchers or other custom `CoroutineContext` elements.

## Custom CoroutineScope factories

The way `androidx-lifecycle-viewModel` constructs its [CoroutineScope] is [hard-coded][androidx-lifecycle-viewmodel-ktx],
which eliminates the possibility of using a custom [CoroutineContext]
such as a [DispatcherProvider] or [IdlingDispatcher]. With [dispatch-android-lifecycle], we can set a custom factory.

``` kotlin
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

## Automatic cancellation in onCleared()

Just like AndroidX, this version of `viewModelScope` is automatically cancelled in `ViewModel.onCleared()`.

## viewModelScope is not lifecycleScope

It's important to remember that `onCleared()` is only called when a `ViewModel` is about to be destroyed -- when its associated `LifecycleOwner`(s) are all destroyed.  This means that a `viewModelScope` is active while the `LifecycleOwner` is in the backstack.

Consider this example:

``` kotlin
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

``` kotlin
// Maybe do this
class SomeFragment : Fragment() {

  val viewModel: SomeViewModel by viewModels()

  init {
    lifecycleScope.launchWhenResumed {
      viewModel.dataFlow.collect {  }
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

## Extending ViewModel

Since nothing about the `clear` event is actually exposed outside of `ViewModel`, it's necessary to extend `ViewModel` in order to consume it for cancelling the `viewModelScope`.  This is especially galling since `ViewModel` could absolutely have just been an interface to begin with.

## Future plans

I'll be suggesting to make this change in AndroidX as well, but I can get it released and usable a lot faster in my own project, so here we are.

I will maintain this artifact until it is implemented AndroidX, or they provide some other suitable solution for custom scopes.

## Minimum Gradle Config
Click to expand a field.

<details open>
<summary>
<b>Groovy</b>
</summary>

Add to your module's `build.gradle`:

``` groovy
repositories {
  mavenCentral()
}

dependencies {

  implementation "org.jetbrains.kotlinx:kotlinx-coroutines-core:1.3.3"
  implementation "org.jetbrains.kotlinx:kotlinx-coroutines-android:1.3.3"
  implementation "com.rickbusarow.dispatch:dispatch-android-viewmodel:1.0.0-beta03"
}
```

</details>


<details>
<summary>
<b>Kotlin Gradle DSL</b>
</summary>

Add to your module's `build.gradle.kts`:

``` kotlin
repositories {
  mavenCentral()
}

dependencies {

  implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.3.3")
  implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.3.3")
  implementation("com.rickbusarow.dispatch:dispatch-android-viewmodel:1.0.0-beta03")
}
```

</details>

<!--- MODULE core-->
<!--- INDEX  -->
[DispatcherProvider]: https://rbusarow.github.io/Dispatch/core//dispatch.core/-dispatcher-provider/index.html
<!--- MODULE core-test-->
<!--- INDEX  -->
<!--- MODULE core-test-junit4-->
<!--- INDEX  -->
<!--- MODULE core-test-junit5-->
<!--- INDEX  -->
<!--- MODULE extensions-->
<!--- INDEX  -->
<!--- MODULE android-espresso-->
<!--- INDEX  -->
[IdlingDispatcher]: https://rbusarow.github.io/Dispatch/android-espresso//dispatch.android.espresso/-idling-dispatcher/index.html
<!--- MODULE android-lifecycle-->
<!--- INDEX  -->
<!--- MODULE android-viewmodel-->
<!--- INDEX  -->
<!--- MODULE android-viewmodel-->
<!--- INDEX  -->
<!--- END -->


[Android Lifecycle]: https://developer.android.com/reference/androidx/lifecycle/Lifecycle.html
[androidx-lifecycle-runtime-ktx]: https://developer.android.com/jetpack/androidx/releases/lifecycle
[androidx-lifecycle-viewmodel-ktx]: https://cs.android.com/androidx/platform/frameworks/support/+/androidx-master-dev:lifecycle/lifecycle-viewmodel-ktx/src/main/java/androidx/lifecycle/ViewModel.kt;l=42
[androidx.lifecycle.lifecycleScope]: https://cs.android.com/androidx/platform/frameworks/support/+/androidx-master-dev:lifecycle/lifecycle-runtime-ktx/src/main/java/androidx/lifecycle/Lifecycle.kt;l=44
[async]: https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/async.html
[awaitAll]: https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/await-all.html
[b/146370660]: https://issuetracker.google.com/issues/146370660
[channel]: https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines.channels/-channel/
[cleanupTestCoroutines]: https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-test/kotlinx.coroutines.test/-test-coroutine-scope/cleanup-test-coroutines.html
[CoroutineContext]: https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.coroutines/-coroutine-context/
[CoroutineDispatcher]: https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/-coroutine-dispatcher/index.html
[CoroutineExceptionHandler]: https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/-coroutine-exception-handler/index.html
[CoroutineScope]: https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/coroutine-scope.html
[CountingIdlingResource]: https://developer.android.com/reference/androidx/test/espresso/idling/CountingIdlingResource
[Deferred.await]: https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/-deferred/await.html
[Deferred.onAwait]: https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/-deferred/on-await.html
[Deferred]: https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/-deferred/index.html
[delay]: https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/delay.html
[dispatch-android-espresso]: https://rbusarow.github.io/Dispatch/android-espresso//index.html
[dispatch-android-lifecycle-extensions]: https://rbusarow.github.io/Dispatch/android-lifecycle-extensions//index.html
[dispatch-android-lifecycle]: https://rbusarow.github.io/Dispatch/android-lifecycle//index.html
[dispatch-android-viewmodel]: https://rbusarow.github.io/Dispatch/android-lifecycle-viewmodel//index.html
[dispatch-core-test-junit4]: https://rbusarow.github.io/Dispatch/core-test-junit4//index.html
[dispatch-core-test-junit5]: https://rbusarow.github.io/Dispatch/core-test-junit5//index.html
[dispatch-core-test]: https://rbusarow.github.io/Dispatch/core-test//index.html
[dispatch-core]: https://rbusarow.github.io/Dispatch/core//index.html
[dispatch-extensions]: https://rbusarow.github.io/Dispatch/extensions//index.html
[Dispatchers.Default]: https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/-dispatchers/-default.html
[Dispatchers.setMain]: https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-test/kotlinx.coroutines.test/kotlinx.coroutines.-dispatchers/set-main.html
[Dispatchers.Unconfined]: https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/-dispatchers/-unconfined.html
[Dispatchers]: https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/-dispatchers/index.html
[Espresso]: https://developer.android.com/training/testing/espresso
[flow.conflate]: https://github.com/Kotlin/kotlinx.coroutines/blob/master/docs/flow.md#conflation
[Flow.flowOn]: https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines.flow/flow-on.html
[Flow]: https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines.flow/-flow/index.html
[IdlingRegistry]: https://developer.android.com/reference/androidx/test/espresso/IdlingRegistry
[IdlingResource]: https://developer.android.com/training/testing/espresso/idling-resource
[Job.isCompleted]: https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/-job/is-completed.html
[Job.join]: https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/-job/join.html
[Job.onJoin]: https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/-job/on-join.html
[Job]: https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/-job/index.html
[joinAll]: https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/join-all.html
[kotlinx.coroutines.channels.Channel]: https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines.channels/-channel/index.html
[kotlinx.coroutines.channels.onReceiveOrNull]: https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines.channels/on-receive-or-null.html
[kotlinx.coroutines.channels.produce]: https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines.channels/produce.html
[kotlinx.coroutines.channels.ProducerScope]: https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines.channels/-producer-scope/index.html
[kotlinx.coroutines.channels.ReceiveChannel.onReceive]: https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines.channels/-receive-channel/on-receive.html
[kotlinx.coroutines.channels.ReceiveChannel.poll]: https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines.channels/-receive-channel/poll.html
[kotlinx.coroutines.channels.ReceiveChannel.receive]: https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines.channels/-receive-channel/receive.html
[kotlinx.coroutines.channels.ReceiveChannel]: https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines.channels/-receive-channel/index.html
[kotlinx.coroutines.channels.receiveOrNull]: https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines.channels/receive-or-null.html
[kotlinx.coroutines.channels.SendChannel.offer]: https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines.channels/-send-channel/offer.html
[kotlinx.coroutines.channels.SendChannel.onSend]: https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines.channels/-send-channel/on-send.html
[kotlinx.coroutines.channels.SendChannel.send]: https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines.channels/-send-channel/send.html
[kotlinx.coroutines.channels.SendChannel]: https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines.channels/-send-channel/index.html
[kotlinx.coroutines.selects.select]: https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines.selects/select.html
[kotlinx.coroutines.selects.SelectBuilder.onTimeout]: https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines.selects/-select-builder/on-timeout.html
[kotlinx.coroutines.sync.Mutex.lock]: https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines.sync/-mutex/lock.html
[kotlinx.coroutines.sync.Mutex.onLock]: https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines.sync/-mutex/on-lock.html
[kotlinx.coroutines.sync.Mutex.tryLock]: https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines.sync/-mutex/try-lock.html
[kotlinx.coroutines.sync.Mutex]: https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines.sync/-mutex/index.html
[launch]: https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/launch.html
[lifecycle.java]: https://cs.android.com/androidx/platform/frameworks/support/+/androidx-master-dev:lifecycle/lifecycle-common/src/main/java/androidx/lifecycle/Lifecycle.java
[Lifecycle.State.CREATED]: https://developer.android.com/reference/androidx/lifecycle/Lifecycle.State.html#CREATED
[Lifecycle.State.RESUMED]: https://developer.android.com/reference/androidx/lifecycle/Lifecycle.State.html#RESUMED
[Lifecycle.State.STARTED]: https://developer.android.com/reference/androidx/lifecycle/Lifecycle.State.html#STARTED
[Lifecycle.State]: https://developer.android.com/reference/androidx/lifecycle/Lifecycle.State.html
[Lifecycle]: https://developer.android.com/reference/androidx/lifecycle/Lifecycle.html
[LifecycleOwner]: https://developer.android.com/reference/androidx/lifecycle/LifecycleOwner.html
[androidx-lifecycleScope]: https://cs.android.com/androidx/platform/frameworks/support/+/androidx-master-dev:lifecycle/lifecycle-runtime-ktx/src/main/java/androidx/lifecycle/Lifecycle.kt;l=44
[newSingleThreadContext]: https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/new-single-thread-context.html
[NonCancellable]: https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/-non-cancellable.html
[androidx-pausingDispatcher]: https://cs.android.com/androidx/platform/frameworks/support/+/androidx-master-dev:lifecycle/lifecycle-runtime-ktx/src/main/java/androidx/lifecycle/PausingDispatcher.kt
[Rule]: https://junit.org/junit4/javadoc/4.12/org/junit/Rule.html
[runBlocking]: https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/run-blocking.html
[runBlockingTest]: https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-test/kotlinx.coroutines.test/run-blocking-test.html
[suspend]: https://kotlinlang.org/docs/reference/coroutines/composing-suspending-functions.html
[suspendCancellableCoroutine]: https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/suspend-cancellable-coroutine.html
[TestCoroutineDispatcher]: https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-test/kotlinx.coroutines.test/-test-coroutine-dispatcher/index.html
[TestCoroutineScope]: https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-test/kotlinx.coroutines.test/-test-coroutine-scope/index.html
[androidx-viewModelScope]: https://developer.android.com/topic/libraries/architecture/coroutines#viewmodelscope
[withContext]: https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/with-context.html
[withTimeout]: https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/with-timeout.html
[withTimeoutOrNull]: https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/with-timeout-or-null.html
[yield]: https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/yield.html
