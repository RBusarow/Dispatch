# MODULE dispatch-android-lifecycle

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
| [LifecycleCoroutineScope] | [MainCoroutineScope] with a [Lifecycle], capable of automatically cancelling and restarting coroutines along with that lifecycle.
| [MinimumStatePolicy] | Defines the behavior of a [Lifecycle]-aware [Job] when it passes below its minimum [Lifecycle.State]

## Member functions

| **Name**          | **Description**
| -------------     | --------------- |
| [launchOnCreate]  | Creates a coroutine tied to a [Lifecycle] which will automatically enact a [MinimumStatePolicy] upon dropping below [Lifecycle.State.CREATED]
| [launchOnStart]   | Creates a coroutine tied to a [Lifecycle] which will automatically enact a [MinimumStatePolicy] upon dropping below [Lifecycle.State.STARTED]
| [launchOnResume]  | Creates a coroutine tied to a [Lifecycle] which will automatically enact a [MinimumStatePolicy] upon dropping below [Lifecycle.State.RESUMED]

## Extension functions

[LifecycleOwner] extension suspending functions:

| **Name**                                     | **Description**
| -------------------                          | ---------------
| [onNextCreate][LifecycleOwner.onNextCreate]  | Executes code one time upon reaching a state of [Lifecycle.State.CREATED]
| [onNextStart][LifecycleOwner.onNextStart]    | Executes code one time upon reaching a state of [Lifecycle.State.STARTED]
| [onNextResume][LifecycleOwner.onNextResume]  | Executes code one time upon reaching a state of [Lifecycle.State.RESUMED]

[Lifecycle] extension suspending functions:

| **Name**                                | **Description**
| -------------------                     | ---------------
| [onNextCreate][Lifecycle.onNextCreate]  | Executes code one time upon reaching a state of [Lifecycle.State.CREATED]
| [onNextStart][Lifecycle.onNextStart]    | Executes code one time upon reaching a state of [Lifecycle.State.STARTED]
| [onNextResume][Lifecycle.onNextResume]  | Executes code one time upon reaching a state of [Lifecycle.State.RESUMED]


## Minimum Gradle Config

Add to your module's `build.gradle.kts`:

``` kotlin
repositories {
  mavenCentral()
}

dependencies {

  implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.3.7")
  implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.3.7")
  implementation("com.rickbusarow.dispatch:dispatch-android-lifecycle:1.0.0-beta04")
  
  implementation("androidx.lifecycle:lifecycle-common:2.2.0")
}
```

<!--- MODULE dispatch-core-->
<!--- INDEX  -->
[MainCoroutineScope]: https://rbusarow.github.io/Dispatch/dispatch-core//dispatch.core/-main-coroutine-scope.html
<!--- MODULE dispatch-android-lifecycle-->
<!--- INDEX  -->
[LifecycleCoroutineScope]: https://rbusarow.github.io/Dispatch/dispatch-android-lifecycle//dispatch.android.lifecycle/-lifecycle-coroutine-scope/index.html
[MinimumStatePolicy]: https://rbusarow.github.io/Dispatch/dispatch-android-lifecycle//dispatch.android.lifecycle/-lifecycle-coroutine-scope/-minimum-state-policy/index.html
[launchOnCreate]: https://rbusarow.github.io/Dispatch/dispatch-android-lifecycle//dispatch.android.lifecycle/-lifecycle-coroutine-scope/launch-on-create.html
[launchOnStart]: https://rbusarow.github.io/Dispatch/dispatch-android-lifecycle//dispatch.android.lifecycle/-lifecycle-coroutine-scope/launch-on-start.html
[launchOnResume]: https://rbusarow.github.io/Dispatch/dispatch-android-lifecycle//dispatch.android.lifecycle/-lifecycle-coroutine-scope/launch-on-resume.html
[LifecycleOwner.onNextCreate]: https://rbusarow.github.io/Dispatch/dispatch-android-lifecycle//dispatch.android.lifecycle/androidx.lifecycle.-lifecycle-owner/on-next-create.html
[LifecycleOwner.onNextStart]: https://rbusarow.github.io/Dispatch/dispatch-android-lifecycle//dispatch.android.lifecycle/androidx.lifecycle.-lifecycle-owner/on-next-start.html
[LifecycleOwner.onNextResume]: https://rbusarow.github.io/Dispatch/dispatch-android-lifecycle//dispatch.android.lifecycle/androidx.lifecycle.-lifecycle-owner/on-next-resume.html
[Lifecycle.onNextCreate]: https://rbusarow.github.io/Dispatch/dispatch-android-lifecycle//dispatch.android.lifecycle/androidx.lifecycle.-lifecycle/on-next-create.html
[Lifecycle.onNextStart]: https://rbusarow.github.io/Dispatch/dispatch-android-lifecycle//dispatch.android.lifecycle/androidx.lifecycle.-lifecycle/on-next-start.html
[Lifecycle.onNextResume]: https://rbusarow.github.io/Dispatch/dispatch-android-lifecycle//dispatch.android.lifecycle/androidx.lifecycle.-lifecycle/on-next-resume.html
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
