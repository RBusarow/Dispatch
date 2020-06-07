[dispatch-android-lifecycle-extensions](./index.md)

## Contents

* [Api](#api)
  * [One-time suspend functions](#one-time-suspend-functions)
* [Difference from AndroidX](#difference-from-androidx)
* [Custom CoroutineScope factories](#custom-coroutinescope-factories)
* [Automatic lifecycle jobs](#automatic-lifecycle-jobs)
* [Future plans](#future-plans)
* [Minimum Gradle Config](#minimum-gradle-config)

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

This module is really just a slightly different version of [androidx-lifecycle-runtime-ktx](https://developer.android.com/jetpack/androidx/releases/lifecycle) — the library which gives us the [lifecycleScope](https://cs.android.com/androidx/platform/frameworks/support/+/androidx-master-dev:lifecycle/lifecycle-runtime-ktx/src/main/java/androidx/lifecycle/Lifecycle.kt;l=44) property.

Why not just use AndroidX?  Because we need two things it doesn't offer.

## Custom CoroutineScope factories

The way `androidx-lifecycle-runtime` constructs its [CoroutineScope](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/coroutine-scope.html) is [hard-coded](https://cs.android.com/androidx/platform/frameworks/support/+/androidx-master-dev:lifecycle/lifecycle-runtime-ktx/src/main/java/androidx/lifecycle/Lifecycle.kt;l=44), which eliminates the possibility of using a custom [CoroutineContext](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.coroutines/-coroutine-context/) such as a `DispatcherProvider` or [IdlingDispatcher](https://rbusarow.github.io/Dispatch/dispatch-android-espresso//dispatch.android.espresso/-idling-dispatcher/index.html). With `dispatch.android.lifecycle`, we can set a custom factory.

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

Structured concurrency relies upon cancellation, but [androidx-lifecycle-runtime.ktx](https://developer.android.com/jetpack/androidx/releases/lifecycle) doesn't cancel.  It uses a special [PausingDispatcher](https://cs.android.com/androidx/platform/frameworks/support/+/androidx-master-dev:lifecycle/lifecycle-runtime-ktx/src/main/java/androidx/lifecycle/PausingDispatcher.kt). This pausing behavior then **leaks** upstream, creating backpressure and potentially deadlocks.

There's a [bug filed in their issue tracker](https://issuetracker.google.com/issues/146370660), but 2.2.0 got released anyway.

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

&nbsp;  Groovy

Add to your module's `build.gradle`:

``` groovy
repositories {
  mavenCentral()
}

dependencies {

  implementation "org.jetbrains.kotlinx:kotlinx-coroutines-core:1.3.7"
  implementation "org.jetbrains.kotlinx:kotlinx-coroutines-android:1.3.7"
  implementation "com.rickbusarow.dispatch:dispatch-android-lifecycle-extensions:1.0.0-beta03"
  
  implementation "androidx.lifecycle:lifecycle-common:2.2.0" 
}
```

&nbsp;  Kotlin Gradle DSL

Add to your module's `build.gradle.kts`:

``` kotlin
repositories {
  mavenCentral()
}

dependencies {

  implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.3.7")
  implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.3.7")
  implementation("com.rickbusarow.dispatch:dispatch-android-lifecycle-extensions:1.0.0-beta03")
  
  implementation("androidx.lifecycle:lifecycle-common:2.2.0")
}
```

### Packages

| Name | Summary |
|---|---|
| [dispatch.android.lifecycle](dispatch.android.lifecycle/index.md) |  |

### Index

[All Types](alltypes/index.md)