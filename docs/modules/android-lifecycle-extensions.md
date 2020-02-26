# Module dispatch-android-lifecycle-extensions

## Contents
<!--- TOC -->

* [Api](#api)
  * [One-time suspend functions](#one-time-suspend-functions)
* [Difference from AndroidX](#difference-from-androidx)
* [Custom CoroutineScope factories](#custom-coroutinescope-factories)
* [Automatic lifecycle jobs](#automatic-lifecycle-jobs)
* [Future plans](#future-plans)
* [Minimum Gradle Config](#minimum-gradle-config)

<!--- END -->

## Api

### One-time suspend functions

Examples

``` kotlin
import dispatch.android.*

// This could be any LifecycleOwner -- Fragments, Activities, Services...
class SomeScreen : Fragment() {

  init {

    // auto-created MainImmediateCoroutineScope which is lifecycle-aware
    lifecycleScope //...

    // active only when "resumed".  starts a fresh coroutine each time
    // this is a rough proxy for LiveData behavior
    lifecycleScope.launchEveryResume {  }

    // active only when "started".  starts a fresh coroutine each time
    lifecycleScope.launchEveryStart {  }

    // launch when created, automatically stop on destroy
    lifecycleScope.launchEveryCreate {  }

    // it works as a normal CoroutineScope as well (because it is)
    lifecycleScope.launchMain {  }

  }
}
```

``` kotlin
class SomeApplication : Application() {
  override fun onCreate() {
    super.onCreate()
    // A custom factory can be set to add elements to the CoroutineContext
    LifecycleScopeFactory.set { MainImmediateCoroutineScope() + SomeCustomElement() }
  }
}
```

``` kotlin
class SomeEspressoTest {
  @Before
  fun setUp() {
    // This custom factory can be used to use custom scopes for testing,
    // such as an idling dispatcher
    LifecycleScopeFactory.set { MainImmediateIdlingCoroutineScope() }
  }

  @After
  fun tearDown() {
    // The factory can also be reset to default
    LifecycleScopeFactory.reset()
  }
}
```

## Difference from AndroidX

This module is really just a slightly different version of [androidx-lifecycle-runtime-ktx][androidx-lifecycle-runtime-ktx] — the library which gives us the [lifecycleScope][androidx-lifecycleScope] property.

Why not just use AndroidX?  Because we need two things it doesn't offer.

## Custom CoroutineScope factories

The way `androidx-lifecycle-runtime` constructs its [CoroutineScope] is [hard-coded][androidx-lifecycleScope], which eliminates the possibility of using a custom [CoroutineContext] such as a `DispatcherProvider` or [IdlingDispatcher]. With `dispatch.android.lifecycle`, we can set a custom factory.

``` kotlin
class SomeFragmentEspressoTest {

  // Not part of this artifact.  see dispatch-android-espresso
  @JvmField @Rule val idlingRule = IdlingDispatcherProviderRule()

  @Before
  fun setUp() {
    // set a custom factory which is applied to all newly created lifecycleScopes
    LifecycleScopeFactory.set {
      MainImmediateCoroutineScope() + idlingRule.dispatcherProvider
    }

    // now SomeFragment will use an IdlingDispatcher in its CoroutineScope
  }
}
```

## Automatic lifecycle jobs

Structured concurrency relies upon cancellation, but [androidx-lifecycle-runtime.ktx][androidx-lifecycle-runtime-ktx] doesn't cancel.  It uses a special [PausingDispatcher][androidx-pausingDispatcher]. This pausing behavior then **leaks** upstream, creating backpressure and potentially deadlocks.

There's a [bug filed in their issue tracker][b/146370660], but 2.2.0 got released anyway.

This library's API surface is the same as that within the AndroidX version, but has a different strategy for handling lifecycle events.  When a lifecycle state enters the desired range, such as at `ON_RESUME`, a new coroutine is created.  When the state exists the range, that coroutine is *cancelled*. If the lifecycle state enters the desired range again, a **new** coroutine is created.

``` kotlin
import dispatch.android.*

class SomeFragment : Fragment() {

  val viewModel: SomeViewModel by viewModels()

  init {
    // automatically created CoroutineScope using the factory described above
    lifecycleScope.launchWhenResumed {
      viewModel.someFlow.consume {  }
    }
  }

}
```

This has the desired effect of not leaking backpressure upstream (which in this example is the `viewModel`).

## Future plans

This artifact exists primarily as a solution to the bug, but also for the sake of the factory.  I'll be working to make this change in AndroidX as well, since it has value even outside of `DispatcherProvider`.  But I can get it released and usable a lot faster in my own project, so here we are.

I will maintain this artifact until the pausing bug is fixed in production AndroidX and it has a settable factory, or some other suitable solution for custom scopes.


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
  implementation "com.rickbusarow.dispatch:dispatch-android-lifecycle-extensions:1.0.0-beta03"
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
  implementation("com.rickbusarow.dispatch:dispatch-android-lifecycle-extensions:1.0.0-beta03")
}
```

</details>


<!--- MODULE core-->
<!--- INDEX  -->
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
