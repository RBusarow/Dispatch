[core](./index.md)

Never reference [Dispatchers](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/-dispatchers/index.html) again, and never inject a `dispatchers` interface into your classes.

All the standard [CoroutineDispatcher](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/-coroutine-dispatcher/index.html) types are embedded in a [CoroutineContext](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.coroutines/-coroutine-context/) and can be accessed explicitly
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

You can define custom mappings via a [factory](#marker-interfaces-and-factories), making testing much easier, or use the default, which simply maps to [Dispatchers](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/-dispatchers/index.html).

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

Custom [CoroutineScopes](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/coroutine-scope.html) interfaces allow for more granularity when defining a class or function with a [CoroutineScope](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/coroutine-scope.html) dependency.

There are also factory functions for conveniently creating any implementation, with a built-in [DispatcherProvider](https://rbusarow.github.io/Dispatch/core//dispatch.core/-dispatcher-provider/index.html).

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

* [Types](#types)
  * [Marker interfaces and factories](#marker-interfaces-and-factories)
* [Extensions](#extensions)
  * [async](#async)
  * [launch](#launch)
  * [Flow](#flow)
* [Builders](#builders)
  * [suspend](#suspend)
* [Minimum Gradle Config](#minimum-gradle-config)

## Types

| **Name**                     | **Description**
| -------------                | --------------- |
| [DispatcherProvider](https://rbusarow.github.io/Dispatch/core//dispatch.core/-dispatcher-provider/index.html)         | Interface which provides the 5 standard [CoroutineDispatcher](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/-coroutine-dispatcher/index.html) properties of the [Dispatchers](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/-dispatchers/index.html) object, but which can be embedded in a [CoroutineContext](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.coroutines/-coroutine-context/)
| [DefaultDispatcherProvider](https://rbusarow.github.io/Dispatch/core//dispatch.core/-default-dispatcher-provider/index.html)  | Default implementation of [DispatcherProvider](https://rbusarow.github.io/Dispatch/core//dispatch.core/-dispatcher-provider/index.html) which simply delegates to the corresponding properties in the [Dispatchers](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/-dispatchers/index.html) singleton

### Marker interfaces and factories

| **Name**                        | **Description**
| -------------                   | --------------- |
| [DefaultCoroutineScope](https://rbusarow.github.io/Dispatch/core//dispatch.core/-default-coroutine-scope.html)         | A [CoroutineScope](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/coroutine-scope.html) with a [CoroutineDispatcher](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/-coroutine-dispatcher/index.html) of [DispatcherProvider.default](https://rbusarow.github.io/Dispatch/core//dispatch.core/-dispatcher-provider/default.html)
| [IOCoroutineScope](https://rbusarow.github.io/Dispatch/core//dispatch.core/-i-o-coroutine-scope.html)              | A [CoroutineScope](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/coroutine-scope.html) with a [CoroutineDispatcher](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/-coroutine-dispatcher/index.html) of [DispatcherProvider.io](https://rbusarow.github.io/Dispatch/core//dispatch.core/-dispatcher-provider/io.html)
| [MainCoroutineScope](https://rbusarow.github.io/Dispatch/core//dispatch.core/-main-coroutine-scope.html)            | A [CoroutineScope](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/coroutine-scope.html) with a [CoroutineDispatcher](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/-coroutine-dispatcher/index.html) of [DispatcherProvider.main](https://rbusarow.github.io/Dispatch/core//dispatch.core/-dispatcher-provider/main.html)
| [MainImmediateCoroutineScope](https://rbusarow.github.io/Dispatch/core//dispatch.core/-main-immediate-coroutine-scope.html)   | A [CoroutineScope](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/coroutine-scope.html) with a [CoroutineDispatcher](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/-coroutine-dispatcher/index.html) of [DispatcherProvider.mainImmediate](https://rbusarow.github.io/Dispatch/core//dispatch.core/-dispatcher-provider/main-immediate.html)
| [UnconfinedCoroutineScope](https://rbusarow.github.io/Dispatch/core//dispatch.core/-unconfined-coroutine-scope.html)      | A [CoroutineScope](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/coroutine-scope.html) with a [CoroutineDispatcher](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/-coroutine-dispatcher/index.html) of [DispatcherProvider.unconfined](https://rbusarow.github.io/Dispatch/core//dispatch.core/-dispatcher-provider/unconfined.html)

## Extensions

### async

| **Name**                    | **Description**
| -------------------         | ---------------
| [asyncDefault](https://rbusarow.github.io/Dispatch/core//dispatch.core/kotlinx.coroutines.-coroutine-scope/async-default.html)             | Creates a [Deferred](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/-deferred/index.html) using [DispatcherProvider.default](https://rbusarow.github.io/Dispatch/core//dispatch.core/-dispatcher-provider/default.html)
| [asyncIO](https://rbusarow.github.io/Dispatch/core//dispatch.core/kotlinx.coroutines.-coroutine-scope/async-i-o.html)                  | Creates a [Deferred](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/-deferred/index.html) using [DispatcherProvider.io](https://rbusarow.github.io/Dispatch/core//dispatch.core/-dispatcher-provider/io.html)
| [asyncMain](https://rbusarow.github.io/Dispatch/core//dispatch.core/kotlinx.coroutines.-coroutine-scope/async-main.html)                | Creates a [Deferred](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/-deferred/index.html) using [DispatcherProvider.main](https://rbusarow.github.io/Dispatch/core//dispatch.core/-dispatcher-provider/main.html)
| [asyncMainImmediate](https://rbusarow.github.io/Dispatch/core//dispatch.core/kotlinx.coroutines.-coroutine-scope/async-main-immediate.html)       | Creates a [Deferred](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/-deferred/index.html) using [DispatcherProvider.mainImmediate](https://rbusarow.github.io/Dispatch/core//dispatch.core/-dispatcher-provider/main-immediate.html)
| [asyncUnconfined](https://rbusarow.github.io/Dispatch/core//dispatch.core/kotlinx.coroutines.-coroutine-scope/async-unconfined.html)          | Creates a [Deferred](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/-deferred/index.html) using [DispatcherProvider.unconfined](https://rbusarow.github.io/Dispatch/core//dispatch.core/-dispatcher-provider/unconfined.html)

### launch

| **Name**                    | **Description**
| -------------------         | ---------------
| [launchDefault](https://rbusarow.github.io/Dispatch/core//dispatch.core/kotlinx.coroutines.-coroutine-scope/launch-default.html)             | Creates a [Job](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/-job/index.html) using [DispatcherProvider.default](https://rbusarow.github.io/Dispatch/core//dispatch.core/-dispatcher-provider/default.html)
| [launchIO](https://rbusarow.github.io/Dispatch/core//dispatch.core/kotlinx.coroutines.-coroutine-scope/launch-i-o.html)                  | Creates a [Job](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/-job/index.html) using [DispatcherProvider.io](https://rbusarow.github.io/Dispatch/core//dispatch.core/-dispatcher-provider/io.html)
| [launchMain](https://rbusarow.github.io/Dispatch/core//dispatch.core/kotlinx.coroutines.-coroutine-scope/launch-main.html)                | Creates a [Job](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/-job/index.html) using [DispatcherProvider.main](https://rbusarow.github.io/Dispatch/core//dispatch.core/-dispatcher-provider/main.html)
| [launchMainImmediate](https://rbusarow.github.io/Dispatch/core//dispatch.core/kotlinx.coroutines.-coroutine-scope/launch-main-immediate.html)       | Creates a [Job](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/-job/index.html) using [DispatcherProvider.mainImmediate](https://rbusarow.github.io/Dispatch/core//dispatch.core/-dispatcher-provider/main-immediate.html)
| [launchUnconfined](https://rbusarow.github.io/Dispatch/core//dispatch.core/kotlinx.coroutines.-coroutine-scope/launch-unconfined.html)          | Creates a [Job](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/-job/index.html) using [DispatcherProvider.unconfined](https://rbusarow.github.io/Dispatch/core//dispatch.core/-dispatcher-provider/unconfined.html)

### Flow

These functions are shorthand for [Flow.flowOn](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines.flow/flow-on.html) with a [DispatcherProvider](https://rbusarow.github.io/Dispatch/core//dispatch.core/-dispatcher-provider/index.html)

| **Name**                    | **Description**
| -------------------         | ---------------
| [flowOnDefault](https://rbusarow.github.io/Dispatch/core//dispatch.core/kotlinx.coroutines.flow.-flow/flow-on-default.html)             | Dispatches an upstream [Flow](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines.flow/-flow/index.html) using [DispatcherProvider.default](https://rbusarow.github.io/Dispatch/core//dispatch.core/-dispatcher-provider/default.html)
| [flowOnIO](https://rbusarow.github.io/Dispatch/core//dispatch.core/kotlinx.coroutines.flow.-flow/flow-on-i-o.html)                  | Dispatches an upstream [Flow](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines.flow/-flow/index.html) using [DispatcherProvider.io](https://rbusarow.github.io/Dispatch/core//dispatch.core/-dispatcher-provider/io.html)
| [flowOnMain](https://rbusarow.github.io/Dispatch/core//dispatch.core/kotlinx.coroutines.flow.-flow/flow-on-main.html)                | Dispatches an upstream [Flow](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines.flow/-flow/index.html) using [DispatcherProvider.main](https://rbusarow.github.io/Dispatch/core//dispatch.core/-dispatcher-provider/main.html)
| [flowOnMainImmediate](https://rbusarow.github.io/Dispatch/core//dispatch.core/kotlinx.coroutines.flow.-flow/flow-on-main-immediate.html)       | Dispatches an upstream [Flow](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines.flow/-flow/index.html) using [DispatcherProvider.mainImmediate](https://rbusarow.github.io/Dispatch/core//dispatch.core/-dispatcher-provider/main-immediate.html)
| [flowOnUnconfined](https://rbusarow.github.io/Dispatch/core//dispatch.core/kotlinx.coroutines.flow.-flow/flow-on-unconfined.html)          | Dispatches an upstream [Flow](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines.flow/-flow/index.html) using [DispatcherProvider.unconfined](https://rbusarow.github.io/Dispatch/core//dispatch.core/-dispatcher-provider/unconfined.html)

## Builders

### suspend

| **Name**                    | **Description**
| -------------------         | ---------------
| [withDefault](https://rbusarow.github.io/Dispatch/core//dispatch.core/with-default.html)               | Calls [withContext](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/with-context.html) in a [suspend](https://kotlinlang.org/docs/reference/coroutines/composing-suspending-functions.html) function using [DispatcherProvider.default](https://rbusarow.github.io/Dispatch/core//dispatch.core/-dispatcher-provider/default.html)
| [withIO](https://rbusarow.github.io/Dispatch/core//dispatch.core/with-i-o.html)                    | Calls [withContext](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/with-context.html) in a [suspend](https://kotlinlang.org/docs/reference/coroutines/composing-suspending-functions.html) function using [DispatcherProvider.io](https://rbusarow.github.io/Dispatch/core//dispatch.core/-dispatcher-provider/io.html)
| [withMain](https://rbusarow.github.io/Dispatch/core//dispatch.core/with-main.html)                  | Calls [withContext](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/with-context.html) in a [suspend](https://kotlinlang.org/docs/reference/coroutines/composing-suspending-functions.html) function using [DispatcherProvider.main](https://rbusarow.github.io/Dispatch/core//dispatch.core/-dispatcher-provider/main.html)
| [withMainImmediate](https://rbusarow.github.io/Dispatch/core//dispatch.core/with-main-immediate.html)         | Calls [withContext](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/with-context.html) in a [suspend](https://kotlinlang.org/docs/reference/coroutines/composing-suspending-functions.html) function using [DispatcherProvider.mainImmediate](https://rbusarow.github.io/Dispatch/core//dispatch.core/-dispatcher-provider/main-immediate.html)
| [withUnconfined](https://rbusarow.github.io/Dispatch/core//dispatch.core/with-unconfined.html)            | Calls [withContext](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/with-context.html) in a [suspend](https://kotlinlang.org/docs/reference/coroutines/composing-suspending-functions.html) function using [DispatcherProvider.unconfined](https://rbusarow.github.io/Dispatch/core//dispatch.core/-dispatcher-provider/unconfined.html)

## Minimum Gradle Config

Click to expand a field.

&nbsp;  Groovy

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

&nbsp;  Kotlin Gradle DSL

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

### Packages

| Name | Summary |
|---|---|
| [dispatch.core](dispatch.core/index.md) |  |

### Index

[All Types](alltypes/index.md)