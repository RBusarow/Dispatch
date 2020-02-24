# Module core-test-junit5

## Contents
<!--- TOC -->

* [Features](#features)
  * [CoroutineTest example](#coroutinetest-example)
  * [TestCoroutineExtension example](#testcoroutineextension-example)
* [Setting Dispatchers.Main](#setting-dispatchersmain)
* [This module replaces core-test](#this-module-replaces-core-test)
* [JUnit dependencies](#junit-dependencies)
  * [minimum config](#minimum-config)
  * [JUnit 4 interoperability](#junit-4-interoperability)

<!--- END -->

## Features

In addition to all the functionality in [core-test], this module exposes a JUnit 5 [TestCoroutineExtension] and [CoroutineTest] marker interface to handle set-up and tear-down of a [TestProvidedCoroutineScope].

Since [TestProvidedCoroutineScope] is a [TestCoroutineScope], this Extension also invokes [cleanupTestCoroutines] after the test.

### CoroutineTest example

```kotlin
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

```kotlin
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

### minimum config

Because this is a JUnit 5 Extension, it requires a the JUnit 5 artifact.  No external libraries are bundled as part of Dispatch, so you’ll need to add it to your `dependencies` block yourself.

- `org.junit.jupiter:junit-jupiter:5.5.1`

<details>
<summary>
<b>module build.gradle (Click to expand)</b>
</summary>


```groovy
repositories {
  mavenCentral()
}

dependencies {
	
  def coroutines_version = "1.3.3"
  def dispatch_version = "1.0.0-beta03"

  // core
  implementation "org.jetbrains.kotlinx:kotlinx-coroutines-core:$coroutines_version"
  implementation "org.jetbrains.kotlinx:kotlinx-coroutines-android:$coroutines_version"
  implementation "com.rickbusarow.dispatch:dispatch-core:$dispatch_version"

  // the junit5 artifact also provides the dispatch-core-test artifact
  testImplementation "com.rickbusarow.dispatch:dispatch-core-test-junit5:$dispatch_version"
  testImplementation "org.jetbrains.kotlinx:kotlinx-coroutines-test:$coroutines_version"
}
```

</details>


<details>
<summary>
<b>module build.gradle.kts (Click to expand)</b>
</summary>


``` kotlin
repositories {
  mavenCentral()
}

dependencies {
	
    val coroutinesVersion = "1.3.3"
    val dispatchVersion = "1.0.0-beta03"   
 
    // core
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:$coroutinesVersion")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:$coroutinesVersion")
    implementation("com.rickbusarow.dispatch:dispatch-core:$dispatchVersion")   

    // the junit5 artifact also provides the dispatch-core-test artifact
    testImplementation("com.rickbusarow.dispatch:dispatch-core-test-junit5:$dispatchVersion")
    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:$coroutinesVersion")
}
```

</details>

### JUnit 4 interoperability

Junit 4 provides a “vintage” (JUnit 4) artifact for legacy support (such as Robolectric or Android instrumented tests).  Dispatch also supports running both in the same project by just adding both artifacts.

<details>
<summary>
<b>module build.gradle (Click to expand)</b>
</summary>

```groovy
repositories {
  mavenCentral()
}

dependencies {
	
  def coroutines_version = "1.3.3"
  def dispatch_version = "1.0.0-beta03" 

  // core
  implementation "org.jetbrains.kotlinx:kotlinx-coroutines-core:$coroutines_version"
  implementation "org.jetbrains.kotlinx:kotlinx-coroutines-android:$coroutines_version"
  implementation "com.rickbusarow.dispatch:dispatch-core:$dispatch_version" 

  // the junit4 and junit5 artifacts also provides the dispatch-core-test artifact
  testImplementation "com.rickbusarow.dispatch:dispatch-core-test-junit4:$dispatch_version"
  testImplementation "com.rickbusarow.dispatch:dispatch-core-test-junit5:$dispatch_version"
  testImplementation "org.jetbrains.kotlinx:kotlinx-coroutines-test:$coroutines_version"
}
```
</details>


<details>
<summary>
<b>module build.gradle.kts (Click to expand)</b>
</summary>

``` kotlin
repositories {
  mavenCentral()
}

dependencies {
	
    val coroutinesVersion = "1.3.3"
    val dispatchVersion = "1.0.0-beta03"  
  
    // core
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:$coroutinesVersion")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:$coroutinesVersion")
    implementation("com.rickbusarow.dispatch:dispatch-core:$dispatchVersion")   

    // the junit4 and junit5 artifacts also provides the dispatch-core-test artifact
    testImplementation("com.rickbusarow.dispatch:dispatch-core-test-junit4:$dispatchVersion")
    testImplementation("com.rickbusarow.dispatch:dispatch-core-test-junit5:$dispatchVersion")
    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:$coroutinesVersion")
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



[cleanupTestCoroutines]: https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-test/kotlinx.coroutines.test/-test-coroutine-scope/cleanup-test-coroutines.html

[TestCoroutineScope]: https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-test/kotlinx.coroutines.test/-test-coroutine-scope/index.html
[core-test]: https://rbusarow.github.io/Dispatch/core-test//index.html
