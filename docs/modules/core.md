# Module core

Never reference [Dispatchers] again, and never inject a `dispatchers` interface into your classes.

All the standard [CoroutineDispatcher] types are embedded in a [CoroutineContext] and can be accessed explicitly
or via convenient [extension functions](#extensions).

``` kotlin
fun foo(scope: CoroutineScope) {
  scope.launchDefault {  }
  scope.launchIO {  }
  scope.launchMain {  }
  scope.launchMainImmediate {  }
  scope.launchUnconfined {  }
}
```

You can define custom mappings via a [factory](#marker-interfaces-and-factories), making testing much easier, or use the default, which simply maps to [Dispatchers].

``` kotlin
// a standard DispatcherProvider is easy to create
val myDefaultDispatchProvider = DispatcherProvider()

// but they're also very extensible.  This version is interchangeable and is convenient in some test scenarios.
val myCustomDispatcherProvider = object: DispatcherProvider {

  override val default: CoroutineDispatcher = newSingleThreadCoroutineContext("default")
  override val io: CoroutineDispatcher = newSingleThreadCoroutineContext("io")
  override val main: CoroutineDispatcher get() = newSingleThreadCoroutineContext("main")
  override val mainImmediate: CoroutineDispatcher get() = newSingleThreadCoroutineContext("mainImmediate")
  override val unconfined: CoroutineDispatcher = newSingleThreadCoroutineContext("unconfined")
}
```

Custom [CoroutineScopes][CoroutineScope] interfaces allow for more granularity when defining a class or function with a [CoroutineScope] dependency.

There are also factory functions for conveniently creating any implementation, with a built-in [DispatcherProvider].

``` kotlin
val mainScope = MainCoroutineScope()

val someUIClass = SomeUIClass(mainScope)

class SomeUIClass(val coroutineScope: MainCoroutineScope) {

  fun foo() = coroutineScope.launch {
    // ...
  }

}

```

## Contents
<!--- TOC -->

* [Types](#types)
  * [Marker interfaces and factories](#marker-interfaces-and-factories)
* [Extensions](#extensions)
  * [async](#async)
  * [launch](#launch)
  * [Flow](#flow)
* [Builders](#builders)
  * [suspend](#suspend)
* [Minimum Gradle Config](#minimum-gradle-config)

<!--- END -->

## Types

| **Name**                     | **Description**
| -------------                | --------------- |
| [DispatcherProvider]         | Interface which provides the 5 standard [CoroutineDispatcher] properties of the [Dispatchers] object, but which can be embedded in a [CoroutineContext]
| [DefaultDispatcherProvider]  | Default implementation of [DispatcherProvider] which simply delegates to the corresponding properties in the [Dispatchers] singleton


### Marker interfaces and factories


| **Name**                        | **Description**
| -------------                   | --------------- |
| [DefaultCoroutineScope]         | A [CoroutineScope] with a [CoroutineDispatcher] of [DispatcherProvider.default]
| [IOCoroutineScope]              | A [CoroutineScope] with a [CoroutineDispatcher] of [DispatcherProvider.io]
| [MainCoroutineScope]            | A [CoroutineScope] with a [CoroutineDispatcher] of [DispatcherProvider.main]
| [MainImmediateCoroutineScope]   | A [CoroutineScope] with a [CoroutineDispatcher] of [DispatcherProvider.mainImmediate]
| [UnconfinedCoroutineScope]      | A [CoroutineScope] with a [CoroutineDispatcher] of [DispatcherProvider.unconfined]

## Extensions

### async

| **Name**                    | **Description**
| -------------------         | ---------------
| [asyncDefault]             | Creates a [Deferred] using [DispatcherProvider.default]
| [asyncIO]                  | Creates a [Deferred] using [DispatcherProvider.io]
| [asyncMain]                | Creates a [Deferred] using [DispatcherProvider.main]
| [asyncMainImmediate]       | Creates a [Deferred] using [DispatcherProvider.mainImmediate]
| [asyncUnconfined]          | Creates a [Deferred] using [DispatcherProvider.unconfined]

### launch

| **Name**                    | **Description**
| -------------------         | ---------------
| [launchDefault]             | Creates a [Job] using [DispatcherProvider.default]
| [launchIO]                  | Creates a [Job] using [DispatcherProvider.io]
| [launchMain]                | Creates a [Job] using [DispatcherProvider.main]
| [launchMainImmediate]       | Creates a [Job] using [DispatcherProvider.mainImmediate]
| [launchUnconfined]          | Creates a [Job] using [DispatcherProvider.unconfined]

### Flow

These functions are shorthand for [Flow.flowOn] with a [DispatcherProvider]

| **Name**                    | **Description**
| -------------------         | ---------------
| [flowOnDefault]             | Dispatches an upstream [Flow] using [DispatcherProvider.default]
| [flowOnIO]                  | Dispatches an upstream [Flow] using [DispatcherProvider.io]
| [flowOnMain]                | Dispatches an upstream [Flow] using [DispatcherProvider.main]
| [flowOnMainImmediate]       | Dispatches an upstream [Flow] using [DispatcherProvider.mainImmediate]
| [flowOnUnconfined]          | Dispatches an upstream [Flow] using [DispatcherProvider.unconfined]

## Builders

### suspend

| **Name**                    | **Description**
| -------------------         | ---------------
| [withDefault]               | Calls [withContext] in a [suspend] function using [DispatcherProvider.default]
| [withIO]                    | Calls [withContext] in a [suspend] function using [DispatcherProvider.io]
| [withMain]                  | Calls [withContext] in a [suspend] function using [DispatcherProvider.main]
| [withMainImmediate]         | Calls [withContext] in a [suspend] function using [DispatcherProvider.mainImmediate]
| [withUnconfined]            | Calls [withContext] in a [suspend] function using [DispatcherProvider.unconfined]




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
  implementation "com.rickbusarow.dispatch:dispatch-core:1.0.0-beta03"
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
  implementation("com.rickbusarow.dispatch:dispatch-core:1.0.0-beta03")
}
```

</details>

<!--- MODULE core-->
<!--- INDEX  -->
[DispatcherProvider]: https://rbusarow.github.io/Dispatch/core//dispatch.core/-dispatcher-provider/index.html
[DefaultDispatcherProvider]: https://rbusarow.github.io/Dispatch/core//dispatch.core/-default-dispatcher-provider/index.html
[DefaultCoroutineScope]: https://rbusarow.github.io/Dispatch/core//dispatch.core/-default-coroutine-scope.html
[DispatcherProvider.default]: https://rbusarow.github.io/Dispatch/core//dispatch.core/-dispatcher-provider/default.html
[IOCoroutineScope]: https://rbusarow.github.io/Dispatch/core//dispatch.core/-i-o-coroutine-scope.html
[DispatcherProvider.io]: https://rbusarow.github.io/Dispatch/core//dispatch.core/-dispatcher-provider/io.html
[MainCoroutineScope]: https://rbusarow.github.io/Dispatch/core//dispatch.core/-main-coroutine-scope.html
[DispatcherProvider.main]: https://rbusarow.github.io/Dispatch/core//dispatch.core/-dispatcher-provider/main.html
[MainImmediateCoroutineScope]: https://rbusarow.github.io/Dispatch/core//dispatch.core/-main-immediate-coroutine-scope.html
[DispatcherProvider.mainImmediate]: https://rbusarow.github.io/Dispatch/core//dispatch.core/-dispatcher-provider/main-immediate.html
[UnconfinedCoroutineScope]: https://rbusarow.github.io/Dispatch/core//dispatch.core/-unconfined-coroutine-scope.html
[DispatcherProvider.unconfined]: https://rbusarow.github.io/Dispatch/core//dispatch.core/-dispatcher-provider/unconfined.html
[asyncDefault]: https://rbusarow.github.io/Dispatch/core//dispatch.core/kotlinx.coroutines.-coroutine-scope/async-default.html
[asyncIO]: https://rbusarow.github.io/Dispatch/core//dispatch.core/kotlinx.coroutines.-coroutine-scope/async-i-o.html
[asyncMain]: https://rbusarow.github.io/Dispatch/core//dispatch.core/kotlinx.coroutines.-coroutine-scope/async-main.html
[asyncMainImmediate]: https://rbusarow.github.io/Dispatch/core//dispatch.core/kotlinx.coroutines.-coroutine-scope/async-main-immediate.html
[asyncUnconfined]: https://rbusarow.github.io/Dispatch/core//dispatch.core/kotlinx.coroutines.-coroutine-scope/async-unconfined.html
[launchDefault]: https://rbusarow.github.io/Dispatch/core//dispatch.core/kotlinx.coroutines.-coroutine-scope/launch-default.html
[launchIO]: https://rbusarow.github.io/Dispatch/core//dispatch.core/kotlinx.coroutines.-coroutine-scope/launch-i-o.html
[launchMain]: https://rbusarow.github.io/Dispatch/core//dispatch.core/kotlinx.coroutines.-coroutine-scope/launch-main.html
[launchMainImmediate]: https://rbusarow.github.io/Dispatch/core//dispatch.core/kotlinx.coroutines.-coroutine-scope/launch-main-immediate.html
[launchUnconfined]: https://rbusarow.github.io/Dispatch/core//dispatch.core/kotlinx.coroutines.-coroutine-scope/launch-unconfined.html
[flowOnDefault]: https://rbusarow.github.io/Dispatch/core//dispatch.core/kotlinx.coroutines.flow.-flow/flow-on-default.html
[flowOnIO]: https://rbusarow.github.io/Dispatch/core//dispatch.core/kotlinx.coroutines.flow.-flow/flow-on-i-o.html
[flowOnMain]: https://rbusarow.github.io/Dispatch/core//dispatch.core/kotlinx.coroutines.flow.-flow/flow-on-main.html
[flowOnMainImmediate]: https://rbusarow.github.io/Dispatch/core//dispatch.core/kotlinx.coroutines.flow.-flow/flow-on-main-immediate.html
[flowOnUnconfined]: https://rbusarow.github.io/Dispatch/core//dispatch.core/kotlinx.coroutines.flow.-flow/flow-on-unconfined.html
[withDefault]: https://rbusarow.github.io/Dispatch/core//dispatch.core/with-default.html
[withIO]: https://rbusarow.github.io/Dispatch/core//dispatch.core/with-i-o.html
[withMain]: https://rbusarow.github.io/Dispatch/core//dispatch.core/with-main.html
[withMainImmediate]: https://rbusarow.github.io/Dispatch/core//dispatch.core/with-main-immediate.html
[withUnconfined]: https://rbusarow.github.io/Dispatch/core//dispatch.core/with-unconfined.html
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
