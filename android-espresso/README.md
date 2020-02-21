# Module dispatch-android-espresso

Tools to provide [Espresso] [IdlingResource] functionality for coroutines.

``` kotlin
class IdlingCoroutineScopeRuleWithLifecycleSample {

  // Retrieve the DispatcherProvider from a dependency graph,
  // so that the same one is used throughout the codebase.
  val customDispatcherProvider = testAppComponent.customDispatcherProvider

  @JvmField
  @Rule
  val idlingRule = IdlingDispatcherProviderRule {
    IdlingDispatcherProvider(customDispatcherProvider)
  }

  /**
  * If you don't provide CoroutineScopes to your lifecycle components via a dependency injection framework,
  * you need to use the `dispatch-android-lifecycle-extensions` and `dispatch-android-viewmodel` artifacts
  * to ensure that the same `IdlingDispatcherProvider` is used.
  */
  @Before
  fun setUp() {
    LifecycleScopeFactory.set {
      MainImmediateCoroutineScope(customDispatcherProvider)
    }
    ViewModelScopeFactory.set {
      MainImmediateCoroutineScope(customDispatcherProvider)
    }
  }

  @Test
  fun testThings() = runBlocking {

    // Now any CoroutineScope which uses the DispatcherProvider
    // in TestAppComponent will sync its "idle" state with Espresso

  }

}
```

## Contents
<!--- TOC -->

* [Types](#types)
* [Marker interfaces](#marker-interfaces)
* [Factory functions](#factory-functions)
* [Member functions](#member-functions)
* [Extensions](#extensions)

<!--- END -->

## Types

| **Name**       | **Description**
| -------------  | --------------- |
| [IdlingDispatcherProviderRule] | JUnit 4 [Rule] which automatically
| [IdlingCoroutineScope] | Things
| [IdlingDispatcherProvider] | Things
| [IdlingDispatcher] | Things

## Marker interfaces

| **Name**                 | **Description**
| -------------------      | ---------------
| [DefaultIdlingCoroutineScope] | Stuff
| [IOIdlingCoroutineScope] | Stuff
| [MainIdlingCoroutineScope] | Stuff
| [MainImmediateIdlingCoroutineScope] | Stuff
| [UnconfinedIdlingCoroutineScope] | Stuff

## Factory functions

| **Name**                 | **Description**
| -------------------      | ---------------
| [IdlingCoroutineScope] | Foo
| [DefaultIdlingCoroutineScope] | Foo
| [IOIdlingCoroutineScope] | Foo
| [MainIdlingCoroutineScope] | Foo
| [MainImmediateIdlingCoroutineScope] | Foo
| [UnconfinedIdlingCoroutineScope] | Foo


## Member functions

| **Name**                 | **Description**
| -------------------      | ---------------

## Extensions


| **Name**                 | **Description**
| -------------------      | ---------------

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
[IdlingDispatcherProviderRule]: https://rbusarow.github.io/Dispatch/android-espresso//dispatch.android.espresso/-idling-dispatcher-provider-rule/index.html
[IdlingCoroutineScope]: https://rbusarow.github.io/Dispatch/android-espresso//dispatch.android.espresso/-idling-coroutine-scope/index.html
[IdlingDispatcherProvider]: https://rbusarow.github.io/Dispatch/android-espresso//dispatch.android.espresso/-idling-dispatcher-provider/index.html
[IdlingDispatcher]: https://rbusarow.github.io/Dispatch/android-espresso//dispatch.android.espresso/-idling-dispatcher/index.html
[DefaultIdlingCoroutineScope]: https://rbusarow.github.io/Dispatch/android-espresso//dispatch.android.espresso/-default-idling-coroutine-scope.html
[IOIdlingCoroutineScope]: https://rbusarow.github.io/Dispatch/android-espresso//dispatch.android.espresso/-i-o-idling-coroutine-scope.html
[MainIdlingCoroutineScope]: https://rbusarow.github.io/Dispatch/android-espresso//dispatch.android.espresso/-main-idling-coroutine-scope.html
[MainImmediateIdlingCoroutineScope]: https://rbusarow.github.io/Dispatch/android-espresso//dispatch.android.espresso/-main-immediate-idling-coroutine-scope.html
[UnconfinedIdlingCoroutineScope]: https://rbusarow.github.io/Dispatch/android-espresso//dispatch.android.espresso/-unconfined-idling-coroutine-scope.html
<!--- MODULE android-lifecycle-->
<!--- INDEX  -->
<!--- MODULE android-viewmodel-->
<!--- INDEX  -->
<!--- MODULE android-viewmodel-->
<!--- INDEX  -->
<!--- END -->

<!-- kotlinx.coroutines -->
[launch]: https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/launch.html
[Job]: https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/-job/index.html
[CoroutineScope]: https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/-coroutine-scope/index.html
[async]: https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/async.html
[Deferred]: https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/-deferred/index.html
[runBlocking]: https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/run-blocking.html
[CoroutineDispatcher]: https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/-coroutine-dispatcher/index.html
[Dispatchers.Default]: https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/-dispatchers/-default.html
[Dispatchers.Unconfined]: https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/-dispatchers/-unconfined.html
[NonCancellable]: https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/-non-cancellable.html
[CoroutineExceptionHandler]: https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/-coroutine-exception-handler/index.html
[delay]: https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/delay.html
[yield]: https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/yield.html
[withContext]: https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/with-context.html
[withTimeout]: https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/with-timeout.html
[withTimeoutOrNull]: https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/with-timeout-or-null.html
[awaitAll]: https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/await-all.html
[joinAll]: https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/join-all.html
[suspendCancellableCoroutine]: https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/suspend-cancellable-coroutine.html
[Job.join]: https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/-job/join.html
[Job.onJoin]: https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/-job/on-join.html
[Job.isCompleted]: https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/-job/is-completed.html
[Deferred.await]: https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/-deferred/await.html
[Deferred.onAwait]: https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/-deferred/on-await.html
<!-- kotlinx.coroutines.sync -->
[kotlinx.coroutines.sync.Mutex]: https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines.sync/-mutex/index.html
[kotlinx.coroutines.sync.Mutex.lock]: https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines.sync/-mutex/lock.html
[kotlinx.coroutines.sync.Mutex.onLock]: https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines.sync/-mutex/on-lock.html
[kotlinx.coroutines.sync.Mutex.tryLock]: https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines.sync/-mutex/try-lock.html
<!-- kotlinx.coroutines.channels -->
[kotlinx.coroutines.channels.produce]: https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines.channels/produce.html
[kotlinx.coroutines.channels.ReceiveChannel]: https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines.channels/-receive-channel/index.html
[kotlinx.coroutines.channels.ProducerScope]: https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines.channels/-producer-scope/index.html
[kotlinx.coroutines.channels.Channel]: https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines.channels/-channel/index.html
[kotlinx.coroutines.channels.SendChannel.send]: https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines.channels/-send-channel/send.html
[kotlinx.coroutines.channels.ReceiveChannel.receive]: https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines.channels/-receive-channel/receive.html
[kotlinx.coroutines.channels.SendChannel]: https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines.channels/-send-channel/index.html
[kotlinx.coroutines.channels.SendChannel.onSend]: https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines.channels/-send-channel/on-send.html
[kotlinx.coroutines.channels.SendChannel.offer]: https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines.channels/-send-channel/offer.html
[kotlinx.coroutines.channels.ReceiveChannel.onReceive]: https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines.channels/-receive-channel/on-receive.html
[kotlinx.coroutines.channels.ReceiveChannel.poll]: https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines.channels/-receive-channel/poll.html
[kotlinx.coroutines.channels.receiveOrNull]: https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines.channels/receive-or-null.html
[kotlinx.coroutines.channels.onReceiveOrNull]: https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines.channels/on-receive-or-null.html
<!-- kotlinx.coroutines.selects -->
[kotlinx.coroutines.selects.select]: https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines.selects/select.html
[kotlinx.coroutines.selects.SelectBuilder.onTimeout]: https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines.selects/-select-builder/on-timeout.html


<!--Android Espresso-->
[Espresso]: https://developer.android.com/training/testing/espresso
[IdlingResource]: https://developer.android.com/training/testing/espresso/idling-resource


<!--AndroidX Lifecycle-->

[b/146370660]: https://issuetracker.google.com/issues/146370660

[Lifecycle.State.CREATED]: https://developer.android.com/reference/androidx/lifecycle/Lifecycle.State.html#CREATED
[Lifecycle.State.STARTED]: https://developer.android.com/reference/androidx/lifecycle/Lifecycle.State.html#STARTED
[Lifecycle.State.RESUMED]: https://developer.android.com/reference/androidx/lifecycle/Lifecycle.State.html#RESUMED

[Android Lifecycle]: https://developer.android.com/reference/androidx/lifecycle/Lifecycle.html
[Lifecycle]: https://developer.android.com/reference/androidx/lifecycle/Lifecycle.html
[LifecycleOwner]: https://developer.android.com/reference/androidx/lifecycle/LifecycleOwner.html

[androidx-lifecycle-runtime-ktx]: https://developer.android.com/jetpack/androidx/releases/lifecycle
[androidx.lifecycle.lifecycleScope]: https://cs.android.com/androidx/platform/frameworks/support/+/androidx-master-dev:lifecycle/lifecycle-runtime-ktx/src/main/java/androidx/lifecycle/Lifecycle.kt;l=44
[PausingDispatcher]: https://cs.android.com/androidx/platform/frameworks/support/+/androidx-master-dev:lifecycle/lifecycle-runtime-ktx/src/main/java/androidx/lifecycle/PausingDispatcher.kt

<!--JUnit-->

[Rule]: https://junit.org/junit4/javadoc/4.12/org/junit/Rule.html
