# Module core

Never reference [Dispatchers] again, and never inject a `dispatchers` interface into your classes.

All the standard [CoroutineDispatcher] types are embedded in a [CoroutineContext] and can be accessed explicitly
or via convenient [extension functions](#extensions).

```kotlin
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

<!--- END -->

## Types

| **Name**              | **Description**
| -------------         | --------------- |
| [DispatcherProvider]  | Interface which provides the 5 standard [CoroutineDispatcher] properties of the [Dispatchers] object, but which can be embedded in a [CoroutineContext]


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

<!--- MODULE core-->
<!--- INDEX  -->
[DispatcherProvider]: https://rbusarow.github.io/Dispatch/core//dispatch.core/-dispatcher-provider/index.html
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

<!-- kotlinx.coroutines -->
[Job]: https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/-job/index.html
[Flow]: https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines.flow/-flow/index.html
[suspend]: https://kotlinlang.org/docs/reference/coroutines/composing-suspending-functions.html
[Flow.flowOn]: https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines.flow/flow-on.html
[CoroutineScope]: https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/-coroutine-scope/index.html
[Dispatchers]: https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/-dispatchers/index.html
[CoroutineContext]: https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.coroutines.experimental/-coroutine-context/index.html
[Deferred]: https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/-deferred/index.html
[CoroutineDispatcher]: https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/-coroutine-dispatcher/index.html
[withContext]: https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/with-context.html
