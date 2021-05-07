# Module dispatch-android-lifecycle

[CoroutineScope] functionality linked with an [Android Lifecycle].

## Contents
<!--- TOC -->

* [Types](#types)
* [Member functions](#member-functions)
* [Extension functions](#extension-functions)
* [Minimum Gradle Config](#minimum-gradle-config)

<!--- END -->

## Types

| **Name**       | **Description**
| -------------  | --------------- |
| [DispatchLifecycleScope] | [MainCoroutineScope] with a [Lifecycle], capable of automatically cancelling and restarting coroutines along with that lifecycle.
| [MinimumStatePolicy] | Defines the behavior of a [Lifecycle]-aware [Job] when it passes below its minimum [Lifecycle.State]

## Member functions

| **Name**          | **Description**
| -------------     | --------------- |
| [launchOnCreate]  | Creates a coroutine tied to a [Lifecycle] which will automatically enact a [MinimumStatePolicy] upon dropping below [Lifecycle.State.CREATED]
| [launchOnStart]   | Creates a coroutine tied to a [Lifecycle] which will automatically enact a [MinimumStatePolicy] upon dropping below [Lifecycle.State.STARTED]
| [launchOnResume]  | Creates a coroutine tied to a [Lifecycle] which will automatically enact a [MinimumStatePolicy] upon dropping below [Lifecycle.State.RESUMED]

## Extension functions

[LifecycleOwner] extension suspending functions:

| **Name**        | **Description**
| --------------- | ---------------
| [onNextCreate]  | Executes code one time upon reaching a state of [Lifecycle.State.CREATED]
| [onNextStart]   | Executes code one time upon reaching a state of [Lifecycle.State.STARTED]
| [onNextResume]  | Executes code one time upon reaching a state of [Lifecycle.State.RESUMED]

[Lifecycle] extension suspending functions:

| **Name**        | **Description**
| --------------  | ---------------
| [onNextCreate]  | Executes code one time upon reaching a state of [Lifecycle.State.CREATED]
| [onNextStart]   | Executes code one time upon reaching a state of [Lifecycle.State.STARTED]
| [onNextResume]  | Executes code one time upon reaching a state of [Lifecycle.State.RESUMED]


## Minimum Gradle Config

Add to your module's `build.gradle.kts`:

``` kotlin
repositories {
  mavenCentral()
}

dependencies {

  implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.4.3")
  implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.4.3")
  implementation("com.rickbusarow.dispatch:dispatch-android-lifecycle:1.0.0-beta09")
  implementation("androidx.lifecycle:lifecycle-common:2.3.1")
}
```

<!--- MODULE dispatch-core-->
<!--- INDEX  -->
<!--- MODULE dispatch-android-lifecycle-->
<!--- INDEX  -->
<!--- END -->
[Android Lifecycle]: https://developer.android.com/reference/androidx/lifecycle/Lifecycle.html
[CoroutineScope]: https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/coroutine-scope.html
[Job]: https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/-job/index.html
[Lifecycle.State.CREATED]: https://developer.android.com/reference/androidx/lifecycle/Lifecycle.State.html#CREATED
[Lifecycle.State.RESUMED]: https://developer.android.com/reference/androidx/lifecycle/Lifecycle.State.html#RESUMED
[Lifecycle.State.STARTED]: https://developer.android.com/reference/androidx/lifecycle/Lifecycle.State.html#STARTED
[Lifecycle.State]: https://developer.android.com/reference/androidx/lifecycle/Lifecycle.State.html
[Lifecycle]: https://developer.android.com/reference/androidx/lifecycle/Lifecycle.html
[LifecycleOwner]: https://developer.android.com/reference/androidx/lifecycle/LifecycleOwner.html
