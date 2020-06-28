[dispatch-android-espresso](./index.md)

Tools to provide [Espresso](https://developer.android.com/training/testing/espresso/idling-resource) functionality for coroutines.

If an [IdlingDispatcherProvider](https://rbusarow.github.io/Dispatch/dispatch-android-espresso//dispatch.android.espresso/-idling-dispatcher-provider/index.html) is registered with the [IdlingRegistry](https://developer.android.com/reference/androidx/test/espresso/IdlingRegistry), [Espresso](https://developer.android.com/training/testing/espresso) will wait
for all associated coroutines to leave the *active* state before performing any assertions.

Coroutines which are in a *suspended* state (such as a [Job](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/-job/index.html) "observing" a [Flow](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines.flow/-flow/index.html) for updates)
do not prevent Espresso from performing assertions.

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

* [Types](#types)
* [IdlingCoroutineScopes](#idlingcoroutinescopes)
* [Minimum Gradle Config](#minimum-gradle-config)

## Types

| **Name**       | **Description**
| -------------  | --------------- |
| [IdlingDispatcherProviderRule](https://rbusarow.github.io/Dispatch/dispatch-android-espresso//dispatch.android.espresso/-idling-dispatcher-provider-rule/index.html) | JUnit 4 [Rule](https://junit.org/junit4/javadoc/4.12/org/junit/Rule.html) which automatically registers an [IdlingDispatcherProvider](https://rbusarow.github.io/Dispatch/dispatch-android-espresso//dispatch.android.espresso/-idling-dispatcher-provider/index.html) with the [IdlingRegistry](https://developer.android.com/reference/androidx/test/espresso/IdlingRegistry)
| [IdlingDispatcher](https://rbusarow.github.io/Dispatch/dispatch-android-espresso//dispatch.android.espresso/-idling-dispatcher/index.html) | A [CoroutineDispatcher](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/-coroutine-dispatcher/index.html) which tracks each dispatched coroutine using a [CountingIdlingResource](https://developer.android.com/reference/androidx/test/espresso/idling/CountingIdlingResource).  All actual dispatches are delegated to a provided [CoroutineDispatcher](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/-coroutine-dispatcher/index.html).
| [IdlingDispatcherProvider](https://rbusarow.github.io/Dispatch/dispatch-android-espresso//dispatch.android.espresso/-idling-dispatcher-provider/index.html) | A special [DispatcherProvider](https://rbusarow.github.io/Dispatch/dispatch-core//dispatch.core/-dispatcher-provider/index.html) which guarantees that each of its properties is an [IdlingDispatcher](https://rbusarow.github.io/Dispatch/dispatch-android-espresso//dispatch.android.espresso/-idling-dispatcher/index.html)
| [IdlingCoroutineScope](https://rbusarow.github.io/Dispatch/dispatch-android-espresso//dispatch.android.espresso/-idling-coroutine-scope/index.html) | A special [CoroutineScope](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/coroutine-scope.html) which guarantees a property of an [IdlingDispatcherProvider](https://rbusarow.github.io/Dispatch/dispatch-android-espresso//dispatch.android.espresso/-idling-dispatcher-provider/index.html)

## IdlingCoroutineScopes

| **Marker Interface**                | **Factory Function**                | **Description**
| -------------------                 | -------------------                 | ---------------
| [DefaultIdlingCoroutineScope](https://rbusarow.github.io/Dispatch/dispatch-android-espresso//dispatch.android.espresso/-default-idling-coroutine-scope.html)       | [DefaultIdlingCoroutineScope](https://rbusarow.github.io/Dispatch/dispatch-android-espresso//dispatch.android.espresso/-default-idling-coroutine-scope.html)       | A [IdlingCoroutineScope](https://rbusarow.github.io/Dispatch/dispatch-android-espresso//dispatch.android.espresso/-idling-coroutine-scope/index.html) with a [CoroutineDispatcher](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/-coroutine-dispatcher/index.html) of `default`.
| [IOIdlingCoroutineScope](https://rbusarow.github.io/Dispatch/dispatch-android-espresso//dispatch.android.espresso/-i-o-idling-coroutine-scope.html)            | [IOIdlingCoroutineScope](https://rbusarow.github.io/Dispatch/dispatch-android-espresso//dispatch.android.espresso/-i-o-idling-coroutine-scope.html)            | A [IdlingCoroutineScope](https://rbusarow.github.io/Dispatch/dispatch-android-espresso//dispatch.android.espresso/-idling-coroutine-scope/index.html) with a [CoroutineDispatcher](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/-coroutine-dispatcher/index.html) of `io`.
| [MainIdlingCoroutineScope](https://rbusarow.github.io/Dispatch/dispatch-android-espresso//dispatch.android.espresso/-main-idling-coroutine-scope.html)          | [MainIdlingCoroutineScope](https://rbusarow.github.io/Dispatch/dispatch-android-espresso//dispatch.android.espresso/-main-idling-coroutine-scope.html)          | A [IdlingCoroutineScope](https://rbusarow.github.io/Dispatch/dispatch-android-espresso//dispatch.android.espresso/-idling-coroutine-scope/index.html) with a [CoroutineDispatcher](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/-coroutine-dispatcher/index.html) of `main`.
| [MainImmediateIdlingCoroutineScope](https://rbusarow.github.io/Dispatch/dispatch-android-espresso//dispatch.android.espresso/-main-immediate-idling-coroutine-scope.html) | [MainImmediateIdlingCoroutineScope](https://rbusarow.github.io/Dispatch/dispatch-android-espresso//dispatch.android.espresso/-main-immediate-idling-coroutine-scope.html) | A [IdlingCoroutineScope](https://rbusarow.github.io/Dispatch/dispatch-android-espresso//dispatch.android.espresso/-idling-coroutine-scope/index.html) with a [CoroutineDispatcher](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/-coroutine-dispatcher/index.html) of `mainImmediate`.
| [UnconfinedIdlingCoroutineScope](https://rbusarow.github.io/Dispatch/dispatch-android-espresso//dispatch.android.espresso/-unconfined-idling-coroutine-scope.html)    | [UnconfinedIdlingCoroutineScope](https://rbusarow.github.io/Dispatch/dispatch-android-espresso//dispatch.android.espresso/-unconfined-idling-coroutine-scope.html)    | A [IdlingCoroutineScope](https://rbusarow.github.io/Dispatch/dispatch-android-espresso//dispatch.android.espresso/-idling-coroutine-scope/index.html) with a [CoroutineDispatcher](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/-coroutine-dispatcher/index.html) of `unconfined`.

## Minimum Gradle Config

Add to your module's `build.gradle.kts`:

``` kotlin
repositories {
  mavenCentral()
}

dependencies {

  // core
  implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.3.7")
  implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.3.7")
  implementation("com.rickbusarow.dispatch:dispatch-core:1.0.0-beta03")

  androidTestImplementation("com.rickbusarow.dispatch:dispatch-android-espresso:1.0.0-beta03")

  // android
  androidTestImplementation("androidx.test:runner:1.2.0")
  androidTestImplementation("androidx.test.espresso:espresso-core:3.2.0")
}
```

### Packages

| Name | Summary |
|---|---|
| [dispatch.android.espresso](dispatch.android.espresso/index.md) |  |

### Index

[All Types](alltypes/index.md)