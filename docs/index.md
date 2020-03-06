[![Maven Central](https://maven-badges.herokuapp.com/maven-central/com.rickbusarow.dispatch/core/badge.svg)](https://maven-badges.herokuapp.com/maven-central/com.rickbusarow.dispatch/core)
![CI](https://github.com/RBusarow/Dispatch/workflows/CI/badge.svg)
[![License](https://img.shields.io/badge/License-Apache%202.0-blue.svg)](https://opensource.org/licenses/Apache-2.0)

# Dispatch

Utilities for [kotlinx.coroutines] which make them type-safe, easier to test, and require less code.
Define your [CoroutineDispatchers][CoroutineDispatcher] once in a [CoroutineScope] factory and never think about them again.

``` kotlin
val presenter = MyPresenter(MainCoroutineScope())

class MyPresenter @Inject constructor(
  val coroutineScope: MainCoroutineScope // <-- "Main" dispatcher
) {

  fun loopSomething() = coroutineScope.launchDefault {  }

  suspend fun updateSomething() = withMainImmediate {  }
}
```

``` kotlin
class MyTest {

  @Test
  fun `no setting the main dispatcher`() = runBlockingProvidedTest {

    // this == TestProvidedCoroutineScope,
    // which automatically uses TestCoroutineDispatcher
    // for **every** dispatcher type
    val presenter = MyPresenter(this)

    // this call would normally crash due to the main looper
    presenter.updateSomething()

    // asserts
  }

}
```

---

## Contents
<!--- TOC -->

* [Injecting dispatchers](#injecting-dispatchers)
* [Referencing dispatchers](#referencing-dispatchers)
  * [Builder Extensions](#builder-extensions)
    * [Launch](#launch)
    * [Async](#async)
    * [WithContext](#withcontext)
    * [Flow](#flow)
* [Types and Factories](#types-and-factories)
* [Testing](#testing)
* [Modules](#modules)
  * [JVM](#jvm)
  * [JVM Testing](#jvm-testing)
  * [Android](#android)
  * [Android Testing](#android-testing)
* [Full Gradle Config](#full-gradle-config)
* [License](#license)

<!--- END -->


## Injecting dispatchers

The core of this library is [DispatcherProvider] - an interface with properties corresponding to the 5 different [CoroutineDispatchers][CoroutineDispatcher] we can get from the [Dispatchers] singleton.  It implements [CoroutineContext.Element] and provides a unique [CoroutineContext.Key].

``` kotlin
interface DispatcherProvider : CoroutineContext.Element {

  override val key: CoroutineContext.Key<*> get() = Key

  val default: CoroutineDispatcher
  val io: CoroutineDispatcher
  val main: CoroutineDispatcher
  val mainImmediate: CoroutineDispatcher
  val unconfined: CoroutineDispatcher

  companion object Key : CoroutineContext.Key<DispatcherProvider>
}
```

This allows it to be included as an element in [CoroutineContext]:

``` kotlin
val someCoroutineScope = CoroutineScope(
  Job() + Dispatchers.Main + DispatcherProvider()
)
```

The default implementation of this interface simply delegates to that [Dispatchers] object:

``` kotlin
class DefaultDispatcherProvider : DispatcherProvider {

  override val default: CoroutineDispatcher = Dispatchers.Default
  override val io: CoroutineDispatcher = Dispatchers.IO
  override val main: CoroutineDispatcher = Dispatchers.Main
  override val mainImmediate: CoroutineDispatcher = Dispatchers.Main.immediate
  override val unconfined: CoroutineDispatcher = Dispatchers.Unconfined
}
```

There is also an extension which returns a default implementation of the [DispatcherProvider], meaning that we're able to immediately utilize the following pattern with [CoroutineScopes][CoroutineScope] from unaltered legacy code or another library.

``` kotlin
val CoroutineContext.dispatcherProvider: DispatcherProvider
  get() = get(DispatcherProvider) ?: DefaultDispatcherProvider()
```

---

## Referencing dispatchers

These references can then be accessed via extension functions upon [CoroutineScope]:

``` kotlin
// from ANY CoroutineScope
val mainDispatcher = GlobalScope.mainDispatcher
val ioDispatcher = myCoroutineScope.ioDispatcher
```

Or the [coroutineContext][kotlin.coroutineContext]:

``` kotlin
suspend fun myFunction() {
  val defaultDispatcher = coroutineContext.dispatcherProvider.default
}
```

Or coroutines can be created directly from extension functions:

### Builder Extensions

|              | **Default**     | **IO**     | **Main**     | **Main.immediate**    | **Unconfined**     |
| ------------ | --------------- | ---------- | ------------ | --------------------- | ------------------ |
| [Job]        | [launchDefault] | [launchIO] | [launchMain] | [launchMainImmediate] | [launchUnconfined]
| [Deferred]   | [asyncDefault]  | [asyncIO]  | [asyncMain]  | [asyncMainImmediate]  | [asyncUnconfined]
| `suspend T`  | [withDefault]   | [withIO]   | [withMain]   | [withMainImmediate]   | [withUnconfined]
| `Flow<T>`    | [flowOnDefault] | [flowOnIO] | [flowOnMain] | [flowOnMainImmediate] | [flowOnUnconfined]

#### Launch
``` kotlin
fun foo(scope: CoroutineScope) {
  scope.launchDefault {  }
  scope.launchIO {  }
  scope.launchMain {  }
  scope.launchMainImmediate {  }
  scope.launchUnconfined {  }
}
```

#### Async
``` kotlin
fun foo(scope: CoroutineScope) {
  scope.asyncDefault {  }
  scope.asyncIO {  }
  scope.asyncMain {  }
  scope.asyncMainImmediate {  }
  scope.asyncUnconfined {  }
}
```

#### WithContext

The [CoroutineContext] used for [withContext] comes from the [coroutineContext][kotlin.coroutineContext] top-level suspend property in [kotlin.coroutines].  It returns the current context, so the `default`, `io`, etc. used here are the ones defined in the [CoroutineScope] of the caller. There is no need to inject any other dependencies.

``` kotlin
suspend fun foo() {
  // note that we have no CoroutineContext
  withDefault {  }
  withIO {  }
  withMain {  }
  withMainImmediate {  }
  withUnconfined {  }
}
```
####  Flow
Like [withContext], [Flow] typically doesn’t get a [CoroutineScope] of its own.  They inherit the [coroutineContext][kotlin.coroutineContext] from the collector in a pattern called [context preservation][context_preservation]. These new operators maintain context preservation (*they’re forced to, actually*), and extract the [coroutineContext][kotlin.coroutineContext] from the collector.

``` kotlin
val someFlow = flow {  }
  .flowOnDefault()
  .flowOnIO()
  .flowOnMain()
  .flowOnMainImmediate()
  .flowOnUnconfined()
```

---

## Types and Factories

A [CoroutineScope] may have any type of [CoroutineDispatcher] (*actually it’s a [ContinuationInterceptor]*).  What if we have a View class which will always use the [Main][Dispatchers.Main] thread, or one which will always do I/O?

There are marker interfaces and factories to ensure that the correct type of [CoroutineScope] is always used.

| **Type**                         | **Dispatcher**       |
| -------------------------------- | -------------------- |
| [DefaultCoroutineScope]          | [Dispatchers.Default]
| [IOCoroutineScope]               | [Dispatchers.IO]
| [MainCoroutineScope]             | [Dispatchers.Main]
| [MainImmediateCoroutineScope]    | [Dispatchers.Main.immediate]
| [UnconfinedCoroutineScope]       | [Dispatchers.Unconfined]

``` kotlin
val mainScope = MainCoroutineScope()

val someUIClass = SomeUIClass(mainScope)

class SomeUIClass(val coroutineScope: MainCoroutineScope) {

  fun foo() = coroutineScope.launch {
    // ...
  }

}
```
---

## Testing

There's a configurable [TestDispatcherProvider]:

``` kotlin
class TestDispatcherProvider(
  override val default: CoroutineDispatcher = TestCoroutineDispatcher(),
  override val io: CoroutineDispatcher = TestCoroutineDispatcher(),
  override val main: CoroutineDispatcher = TestCoroutineDispatcher(),
  override val mainImmediate: CoroutineDispatcher = TestCoroutineDispatcher(),
  override val unconfined: CoroutineDispatcher = TestCoroutineDispatcher()
) : DispatcherProvider
```

As well as a [TestProvidedCoroutineScope] which may be used in place of any type-specific [CoroutineScope]:

``` kotlin
val testScope = TestProvidedCoroutineScope()

val someUIClass = SomeUIClass(testScope)

class SomeUIClass(val coroutineScope: MainCoroutineScope) {

  fun foo() = coroutineScope.launch {
    // ...
  }

}
```

[runBlockingProvided] is really just delegating [runBlocking], but creates a [CoroutineScope] which includes  `TestDispatcherProvider`, so "io" here is really a `TestCoroutineDispatcher`.

There's also [runBlockingTestProvided], which delegates to [runBlockingTest][kotlinx.runBlockingTest] but creates a [TestCoroutineScope] which includes the [TestDispatcherProvider].

``` kotlin
class Subject {
  // this would normally be a hard-coded reference to Dispatchers.Main
  suspend fun sayHello() = withMain {  }
}

@Test
fun `sayHello should say hello`() = runBlockingProvided {

  val subject = SomeClass(this)
  // uses "main" TestCoroutineDispatcher safely with no additional setup
  subject.getSomeData() shouldPrint "hello"
}
```

## Modules

### JVM

| **artifact**           | **features**                                   |
| ---------------------- | ---------------------------------------------- |
| [dispatch-core]        | Dispatcher-specific types and factories <p> Dispatcher-specific coroutine builders
| [dispatch-extensions]  | [Flow] intermediary and terminal operators <p> [Channel] operators

### JVM Testing

| **artifact**                | **features**                                   |
| --------------------------- | ---------------------------------------------- |
| [dispatch-core-test]        | [TestProvidedCoroutineScope] <p> [TestDispatcherProvider] <p> [runBlockingProvided] and [runBlockingTestProvided]
| [dispatch-core-test-junit4] | [TestCoroutineRule]
| [dispatch-core-test-junit5] | [CoroutineTest] <p> [TestCoroutineExtension]

### Android

| **artifact**                           | **features**                                   |
| -------------------------------------- | ---------------------------------------------- |
|[dispatch-android-lifecycle]            | [LifecycleCoroutineScope] <p> [launchOnCreate] <p> [launchOnStart] <p> [launchOnResume] <p> [onNextCreate] <p> [onNextStart] <p> [onNextResume]
|[dispatch-android-lifecycle-extensions] | [lifecycleScope]
|[dispatch-android-viewmodel]            | [CoroutineViewModel] <p> [viewModelScope]

### Android Testing

| **artifact**                 | **features**                                   |
| ---------------------------- | ---------------------------------------------- |
| [dispatch-android-espresso]  | [IdlingDispatcher] <p> [IdlingDispatcherProvider]

---

## Full Gradle Config
Click to expand a field.

&nbsp;<details open> <summary> <b>Groovy</b> </summary>

Add to your module's `build.gradle`:


``` groovy
repositories {
  mavenCentral()
}

dependencies {

  /*
  production code
  */

  // core coroutines
  implementation "org.jetbrains.kotlinx:kotlinx-coroutines-core:1.3.3" 
  implementation "org.jetbrains.kotlinx:kotlinx-coroutines-android:1.3.3" 
  
  // everything provides :core via "api", so you only need this if you have no other "implementation" dispatch artifacts  
  implementation "com.rickbusarow.dispatch:dispatch-core:1.0.0-beta03" 
  
  // extensions for Flow and Channel
  implementation "com.rickbusarow.dispatch:dispatch-extensions:1.0.0-beta03" 
  
  // LifecycleCoroutineScope for Android Fragments, Activities, etc.
  implementation "com.rickbusarow.dispatch:dispatch-android-lifecycle:1.0.0-beta03" 
  
  // lifecycleScope extension function with a settable factory.  Use this if you don't DI your CoroutineScopes
  // This provides :dispatch-android-lifecycle via "api", so you don't need to declare both
  implementation "com.rickbusarow.dispatch:dispatch-android-lifecycle-extensions:1.0.0-beta03" 
  
  // ViewModelScope for Android ViewModels
  implementation "com.rickbusarow.dispatch:dispatch-android-viewmodel:1.0.0-beta03" 
  
  
  /*
  jvm testing
  */
   
  // core coroutines-test
  testImplementation "org.jetbrains.kotlinx:kotlinx-coroutines-test:1.3.3" 
  
  // you only need this if you don't have the -junit4 or -junit5 artifacts
  testImplementation "com.rickbusarow.dispatch:dispatch-core-test:1.0.0-beta03" 
  
  // TestCoroutineRule and :core-test
  // This provides :dispatch-core-test via "api", so you don't need to declare both
  // This can be used at the same time as :dispatch-core-test-junit5
  testImplementation "com.rickbusarow.dispatch:dispatch-core-test-junit4:1.0.0-beta03" 
  
  // CoroutineTest, TestCoroutineExtension, and :core-test
  // This provides :dispatch-core-test via "api", so you don't need to declare both
  // This can be used at the same time as :dispatch-core-test-junit4
  testImplementation "com.rickbusarow.dispatch:dispatch-core-test-junit4:1.0.0-beta03" 
  
  /*
  Android testing
  */

  // core android
  androidTestImplementation "androidx.test:runner:1.2.0" 
  androidTestImplementation "androidx.test.espresso:espresso-core:3.2.0"
  
  // IdlingDispatcher, IdlingDispatcherProvider, and IdlingCoroutineScope
  androidTestImplementation "com.rickbusarow.dispatch:dispatch-android-espresso:1.0.0-beta03" 
}
```

</details>


&nbsp;<details> <summary> <b>Kotlin Gradle DSL</b> </summary>

Add to your module's `build.gradle.kts`:

``` kotlin
repositories {
  mavenCentral()
}

dependencies {

  /*
  production code
  */

  // core coroutines
  implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.3.3")
  implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.3.3")
  
  // everything provides :core via "api", so you only need this if you have no other "implementation" dispatch artifacts  
  implementation("com.rickbusarow.dispatch:dispatch-core:1.0.0-beta03") 
  
  // extensions for Flow and Channel
  implementation("com.rickbusarow.dispatch:dispatch-extensions:1.0.0-beta03")
  
  // LifecycleCoroutineScope for Android Fragments, Activities, etc.
  implementation("com.rickbusarow.dispatch:dispatch-android-lifecycle:1.0.0-beta03")
  
  // lifecycleScope extension function with a settable factory.  Use this if you don't DI your CoroutineScopes
  // This provides :dispatch-android-lifecycle via "api", so you don't need to declare both
  implementation("com.rickbusarow.dispatch:dispatch-android-lifecycle-extensions:1.0.0-beta03")
  
  // ViewModelScope for Android ViewModels
  implementation("com.rickbusarow.dispatch:dispatch-android-viewmodel:1.0.0-beta03")
  
  
  /*
  jvm testing
  */
   
  // core coroutines-test
  testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.3.3")
  
  // you only need this if you don't have the -junit4 or -junit5 artifacts
  testImplementation("com.rickbusarow.dispatch:dispatch-core-test:1.0.0-beta03")
  
  // CoroutineTestRule and :core-test
  // This provides :dispatch-core-test via "api", so you don't need to declare both
  // This can be used at the same time as :dispatch-core-test-junit5
  testImplementation("com.rickbusarow.dispatch:dispatch-core-test-junit4:1.0.0-beta03")
  
  // CoroutineTest, CoroutineTestExtension, and :core-test
  // This provides :dispatch-core-test via "api", so you don't need to declare both
  // This can be used at the same time as :dispatch-core-test-junit4
  testImplementation("com.rickbusarow.dispatch:dispatch-core-test-junit4:1.0.0-beta03")
  
  /*
  Android testing
  */

  // core android
  androidTestImplementation("androidx.test:runner:1.2.0") 
  androidTestImplementation("androidx.test.espresso:espresso-core:3.2.0")
  
  // IdlingDispatcher, IdlingDispatcherProvider, and IdlingCoroutineScope
  androidTestImplementation("com.rickbusarow.dispatch:dispatch-android-espresso:1.0.0-beta03") 
}
```

</details>

## License

``` text
Copyright (C) 2020 Rick Busarow
Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at
     http://www.apache.org/licenses/LICENSE-2.0
Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```

<!--- MODULE core-->
<!--- INDEX  -->
[DispatcherProvider]: https://rbusarow.github.io/Dispatch/core//dispatch.core/-dispatcher-provider/index.html
[launchDefault]: https://rbusarow.github.io/Dispatch/core//dispatch.core/kotlinx.coroutines.-coroutine-scope/launch-default.html
[launchIO]: https://rbusarow.github.io/Dispatch/core//dispatch.core/kotlinx.coroutines.-coroutine-scope/launch-i-o.html
[launchMain]: https://rbusarow.github.io/Dispatch/core//dispatch.core/kotlinx.coroutines.-coroutine-scope/launch-main.html
[launchMainImmediate]: https://rbusarow.github.io/Dispatch/core//dispatch.core/kotlinx.coroutines.-coroutine-scope/launch-main-immediate.html
[launchUnconfined]: https://rbusarow.github.io/Dispatch/core//dispatch.core/kotlinx.coroutines.-coroutine-scope/launch-unconfined.html
[asyncDefault]: https://rbusarow.github.io/Dispatch/core//dispatch.core/kotlinx.coroutines.-coroutine-scope/async-default.html
[asyncIO]: https://rbusarow.github.io/Dispatch/core//dispatch.core/kotlinx.coroutines.-coroutine-scope/async-i-o.html
[asyncMain]: https://rbusarow.github.io/Dispatch/core//dispatch.core/kotlinx.coroutines.-coroutine-scope/async-main.html
[asyncMainImmediate]: https://rbusarow.github.io/Dispatch/core//dispatch.core/kotlinx.coroutines.-coroutine-scope/async-main-immediate.html
[asyncUnconfined]: https://rbusarow.github.io/Dispatch/core//dispatch.core/kotlinx.coroutines.-coroutine-scope/async-unconfined.html
[withDefault]: https://rbusarow.github.io/Dispatch/core//dispatch.core/with-default.html
[withIO]: https://rbusarow.github.io/Dispatch/core//dispatch.core/with-i-o.html
[withMain]: https://rbusarow.github.io/Dispatch/core//dispatch.core/with-main.html
[withMainImmediate]: https://rbusarow.github.io/Dispatch/core//dispatch.core/with-main-immediate.html
[withUnconfined]: https://rbusarow.github.io/Dispatch/core//dispatch.core/with-unconfined.html
[flowOnDefault]: https://rbusarow.github.io/Dispatch/core//dispatch.core/kotlinx.coroutines.flow.-flow/flow-on-default.html
[flowOnIO]: https://rbusarow.github.io/Dispatch/core//dispatch.core/kotlinx.coroutines.flow.-flow/flow-on-i-o.html
[flowOnMain]: https://rbusarow.github.io/Dispatch/core//dispatch.core/kotlinx.coroutines.flow.-flow/flow-on-main.html
[flowOnMainImmediate]: https://rbusarow.github.io/Dispatch/core//dispatch.core/kotlinx.coroutines.flow.-flow/flow-on-main-immediate.html
[flowOnUnconfined]: https://rbusarow.github.io/Dispatch/core//dispatch.core/kotlinx.coroutines.flow.-flow/flow-on-unconfined.html
[DefaultCoroutineScope]: https://rbusarow.github.io/Dispatch/core//dispatch.core/-default-coroutine-scope.html
[IOCoroutineScope]: https://rbusarow.github.io/Dispatch/core//dispatch.core/-i-o-coroutine-scope.html
[MainCoroutineScope]: https://rbusarow.github.io/Dispatch/core//dispatch.core/-main-coroutine-scope.html
[MainImmediateCoroutineScope]: https://rbusarow.github.io/Dispatch/core//dispatch.core/-main-immediate-coroutine-scope.html
[UnconfinedCoroutineScope]: https://rbusarow.github.io/Dispatch/core//dispatch.core/-unconfined-coroutine-scope.html
<!--- MODULE core-test-->
<!--- INDEX  -->
[TestDispatcherProvider]: https://rbusarow.github.io/Dispatch/core-test//dispatch.core.test/-test-dispatcher-provider/index.html
[TestProvidedCoroutineScope]: https://rbusarow.github.io/Dispatch/core-test//dispatch.core.test/-test-provided-coroutine-scope/index.html
[runBlockingProvided]: https://rbusarow.github.io/Dispatch/core-test//dispatch.core.test/run-blocking-provided.html
[runBlockingTestProvided]: https://rbusarow.github.io/Dispatch/core-test//dispatch.core.test/run-blocking-test-provided.html
<!--- MODULE core-test-junit4-->
<!--- INDEX  -->
[TestCoroutineRule]: https://rbusarow.github.io/Dispatch/core-test-junit4//dispatch.core.test/-test-coroutine-rule/index.html
<!--- MODULE core-test-junit5-->
<!--- INDEX  -->
[CoroutineTest]: https://rbusarow.github.io/Dispatch/core-test-junit5//dispatch.core.test/-coroutine-test/index.html
[TestCoroutineExtension]: https://rbusarow.github.io/Dispatch/core-test-junit5//dispatch.core.test/-test-coroutine-extension/index.html
<!--- MODULE extensions-->
<!--- INDEX  -->
<!--- MODULE android-espresso-->
<!--- INDEX  -->
[IdlingDispatcher]: https://rbusarow.github.io/Dispatch/android-espresso//dispatch.android.espresso/-idling-dispatcher/index.html
[IdlingDispatcherProvider]: https://rbusarow.github.io/Dispatch/android-espresso//dispatch.android.espresso/-idling-dispatcher-provider/index.html
<!--- MODULE android-lifecycle-->
<!--- INDEX  -->
[LifecycleCoroutineScope]: https://rbusarow.github.io/Dispatch/android-lifecycle//dispatch.android.lifecycle/-lifecycle-coroutine-scope/index.html
[launchOnCreate]: https://rbusarow.github.io/Dispatch/android-lifecycle//dispatch.android.lifecycle/-lifecycle-coroutine-scope/launch-on-create.html
[launchOnStart]: https://rbusarow.github.io/Dispatch/android-lifecycle//dispatch.android.lifecycle/-lifecycle-coroutine-scope/launch-on-start.html
[launchOnResume]: https://rbusarow.github.io/Dispatch/android-lifecycle//dispatch.android.lifecycle/-lifecycle-coroutine-scope/launch-on-resume.html
[onNextCreate]: https://rbusarow.github.io/Dispatch/android-lifecycle//dispatch.android.lifecycle/androidx.lifecycle.-lifecycle-owner/on-next-create.html
[onNextStart]: https://rbusarow.github.io/Dispatch/android-lifecycle//dispatch.android.lifecycle/androidx.lifecycle.-lifecycle-owner/on-next-start.html
[onNextResume]: https://rbusarow.github.io/Dispatch/android-lifecycle//dispatch.android.lifecycle/androidx.lifecycle.-lifecycle-owner/on-next-resume.html
<!--- MODULE android-lifecycle-extensions-->
<!--- INDEX  -->
[lifecycleScope]: https://rbusarow.github.io/Dispatch/android-lifecycle-extensions//dispatch.android.lifecycle/androidx.lifecycle.-lifecycle-owner/lifecycle-scope.html
<!--- MODULE android-viewmodel-->
<!--- INDEX  -->
[CoroutineViewModel]: https://rbusarow.github.io/Dispatch/android-viewmodel//dispatch.android.lifecycle/-coroutine-view-model/index.html
[viewModelScope]: https://rbusarow.github.io/Dispatch/android-viewmodel//dispatch.android.lifecycle/-coroutine-view-model/view-model-scope.html
<!--- END -->

[Channel]: https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines.channels/-channel/
[context_preservation]: https://medium.com/@elizarov/execution-context-of-kotlin-flows-b8c151c9309b
[ContinuationInterceptor]: https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.coroutines.experimental/-continuation-interceptor/index.html
[CoroutineContext.Element]: https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.coroutines.experimental/-coroutine-context/index.html#types
[CoroutineContext.Key]: https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.coroutines.experimental/-coroutine-context/index.html#types
[CoroutineContext]: https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.coroutines/-coroutine-context/
[CoroutineDispatcher]: https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/-coroutine-dispatcher/index.html
[CoroutineScope]: https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/coroutine-scope.html
[Deferred]: https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/-deferred/index.html
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
[Dispatchers.IO]: https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/-dispatchers/-io.html
[Dispatchers.Main.immediate]: https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/-main-coroutine-dispatcher/immediate.html
[Dispatchers.Main]: https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/-dispatchers/-main.html
[Dispatchers.Unconfined]: https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/-dispatchers/-unconfined.html
[Dispatchers]: https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/-dispatchers/index.html
[Flow]: https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines.flow/-flow/index.html
[Job]: https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/-job/index.html
[kotlin.coroutineContext]: https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.coroutines/coroutine-context.html
[kotlin.coroutines]: https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.coroutines/index.html
[kotlinx.coroutines]: https://kotlin.github.io/kotlinx.coroutines/
[runBlocking]: https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/run-blocking.html
[kotlinx.runBlockingTest]: https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-test/kotlinx.coroutines.test/run-blocking-test.html
[TestCoroutineScope]: https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-test/kotlinx.coroutines.test/-test-coroutine-scope/index.html
[withContext]: https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/with-context.html

