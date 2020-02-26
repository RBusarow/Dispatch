# Module core-test-junit5

## Contents
<!--- TOC -->

* [Features](#features)
  * [CoroutineTest example](#coroutinetest-example)
  * [TestCoroutineExtension example](#testcoroutineextension-example)
* [Setting Dispatchers.Main](#setting-dispatchersmain)
* [This module replaces core-test](#this-module-replaces-core-test)
* [JUnit dependencies](#junit-dependencies)
  * [Minimum Gradle Config](#minimum-gradle-config)
  * [JUnit 4 interoperability](#junit-4-interoperability)

<!--- END -->

## Features

In addition to all the functionality in [core-test], this module exposes a JUnit 5 [TestCoroutineExtension] and [CoroutineTest] marker interface to handle set-up and tear-down of a [TestProvidedCoroutineScope].

Since [TestProvidedCoroutineScope] is a [TestCoroutineScope], this Extension also invokes [cleanupTestCoroutines] after the test.

### CoroutineTest example

``` kotlin
class SomeClassTest : CoroutineTest {

  override val testScope: TestCoroutineScope

  @Test
  fun `some test`() = runBlocking {

    val subject = SomeClass(testScope)

    val job = subject.fireAndForget()

    // TODO: assertions go here
  }

}

class SomeClass(val coroutineScope: CoroutineScope) {
  fun fireAndForget() = launch { }
}
```

### TestCoroutineExtension example

``` kotlin
class SomeClassTest {

  @JvmField
  @RegisterExtension
  val extension = TestCoroutineExtension()

  @Test
  fun `some test`() = runBlocking {

    val subject = SomeClass(extension.testScope)

    val job = subject.fireAndForget()

    // TODO: assertions go here
  }

}

class SomeClass(val coroutineScope: CoroutineScope) {
  fun fireAndForget() = launch { }
}
```

## Setting Dispatchers.Main

Even though `dispatch-core` eliminates the need to use `Dispatchers.Main` in internal code, it’s still possible that code which has yet to be migrated, or a third-party library is making use of the hard-coded dispatcher.  Because of this, the extension still calls `Dispatchers.setMain(...)` in its setup and `Dispatchers.resetMain()` afterwards.

## This module replaces core-test

If using this module, there is no need to include the `dispatch-core-test` artifact in your dependencies.

## JUnit dependencies

### Minimum Gradle Config
Click to expand a field.

Because this is a JUnit 5 Extension, it requires a the JUnit 5 artifact.  No external libraries are bundled as part of Dispatch, so you’ll need to add it to your `dependencies` block yourself.

- `org.junit.jupiter:junit-jupiter:5.5.1`

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

  // core
  implementation "org.jetbrains.kotlinx:kotlinx-coroutines-core:1.3.3"
  implementation "org.jetbrains.kotlinx:kotlinx-coroutines-android:1.3.3"
  implementation "com.rickbusarow.dispatch:dispatch-core:1.0.0-beta03"

  // the junit5 artifact also provides the dispatch-core-test artifact
  testImplementation "com.rickbusarow.dispatch:dispatch-core-test-junit5:1.0.0-beta03"
  testImplementation "org.jetbrains.kotlinx:kotlinx-coroutines-test:1.3.3"
  testImplementation "org.junit.jupiter:junit-jupiter:5.6.0"
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

  // core
  implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.3.3")
  implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.3.3")
  implementation("com.rickbusarow.dispatch:dispatch-core:1.0.0-beta03")

  // the junit5 artifact also provides the dispatch-core-test artifact
  testImplementation("com.rickbusarow.dispatch:dispatch-core-test-junit5:1.0.0-beta03")
  testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.3.3")
  testImplementation("org.junit.jupiter:junit-jupiter:5.6.0")
}
```

</details>

### JUnit 4 interoperability

Junit 4 provides a “vintage” (JUnit 4) artifact for legacy support (such as Robolectric or Android instrumented tests).  Dispatch also supports running both in the same project by just adding both artifacts.

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

  // core
  implementation "org.jetbrains.kotlinx:kotlinx-coroutines-core:1.3.3"
  implementation "org.jetbrains.kotlinx:kotlinx-coroutines-android:1.3.3"
  implementation "com.rickbusarow.dispatch:dispatch-core:1.0.0-beta03"

  // the junit4 and junit5 artifacts also provides the dispatch-core-test artifact
  testImplementation "com.rickbusarow.dispatch:dispatch-core-test-junit4:1.0.0-beta03"
  testImplementation "com.rickbusarow.dispatch:dispatch-core-test-junit5:1.0.0-beta03"
  testImplementation "org.junit.jupiter:junit-jupiter:5.6.0"
  testImplementation "org.junit.vintage:junit-vintage-engine:5.6.0"
  testImplementation "org.jetbrains.kotlinx:kotlinx-coroutines-test:1.3.3"
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

  // core
  implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.3.3")
  implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.3.3")
  implementation("com.rickbusarow.dispatch:dispatch-core:1.0.0-beta03")

  // the junit4 and junit5 artifacts also provides the dispatch-core-test artifact
  testImplementation("com.rickbusarow.dispatch:dispatch-core-test-junit4:1.0.0-beta03")
  testImplementation("com.rickbusarow.dispatch:dispatch-core-test-junit5:1.0.0-beta03")
  testImplementation("org.junit.jupiter:junit-jupiter:5.6.0")
  testImplementation("org.junit.vintage:junit-vintage-engine:5.6.0")
  testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.3.3")
}
```

</details>

<!--- MODULE core-->
<!--- INDEX  -->
<!--- MODULE core-test-->
<!--- INDEX  -->
[TestProvidedCoroutineScope]: https://rbusarow.github.io/Dispatch/core-test//dispatch.core.test/-test-provided-coroutine-scope/index.html
<!--- MODULE core-test-junit4-->
<!--- INDEX  -->
<!--- MODULE core-test-junit5-->
<!--- INDEX  -->
[TestCoroutineExtension]: https://rbusarow.github.io/Dispatch/core-test-junit5//dispatch.core.test/-test-coroutine-extension/index.html
[CoroutineTest]: https://rbusarow.github.io/Dispatch/core-test-junit5//dispatch.core.test/-coroutine-test/index.html
<!--- MODULE extensions-->
<!--- INDEX  -->
<!--- MODULE android-espresso-->
<!--- INDEX  -->
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
