# MODULE dispatch-core

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
  * [Launch](#launch)
  * [Async](#async)
  * [WithContext](#withcontext)
  * [Flow](#flow)
* [Minimum Gradle Config](#minimum-gradle-config)

<!--- END -->

## Types

| **Name**                     | **Description**
| -------------                | --------------- |
| [DispatcherProvider]         | Interface which provides the 5 standard [CoroutineDispatcher] properties of the [Dispatchers] object, but which can be embedded in a [CoroutineContext]
| [DefaultDispatcherProvider]  | Default implementation of [DispatcherProvider] which simply delegates to the corresponding properties in the [Dispatchers] singleton


### Marker interfaces and factories

| **Name**                        | **Dispatcher**
| -------------                   | --------------- |
| [DefaultCoroutineScope]         | [DispatcherProvider.default]
| [IOCoroutineScope]              | [DispatcherProvider.io]
| [MainCoroutineScope]            | [DispatcherProvider.main]
| [MainImmediateCoroutineScope]   | [DispatcherProvider.mainImmediate]
| [UnconfinedCoroutineScope]      | [DispatcherProvider.unconfined]

## Extensions

|              | **Default**     | **IO**     | **Main**     | **Main.immediate**    | **Unconfined**     |
| ------------ | --------------- | ---------- | ------------ | --------------------- | ------------------ |
| [Job]        | [launchDefault] | [launchIO] | [launchMain] | [launchMainImmediate] | [launchUnconfined]
| [Deferred]   | [asyncDefault]  | [asyncIO]  | [asyncMain]  | [asyncMainImmediate]  | [asyncUnconfined]
| `suspend T`  | [withDefault]   | [withIO]   | [withMain]   | [withMainImmediate]   | [withUnconfined]
| `Flow<T>`    | [flowOnDefault] | [flowOnIO] | [flowOnMain] | [flowOnMainImmediate] | [flowOnUnconfined]



### Launch
``` kotlin
fun foo(scope: CoroutineScope) {
  scope.launchDefault {  }
  scope.launchIO {  }
  scope.launchMain {  }
  scope.launchMainImmediate {  }
  scope.launchUnconfined {  }
}
```

### Async
``` kotlin
fun foo(scope: CoroutineScope) {
  scope.asyncDefault {  }
  scope.asyncIO {  }
  scope.asyncMain {  }
  scope.asyncMainImmediate {  }
  scope.asyncUnconfined {  }
}
```

### WithContext

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

###  Flow
Like [withContext], [Flow] typically doesn’t get a [CoroutineScope] of its own.  They inherit the [coroutineContext][kotlin.coroutineContext] from the collector in a pattern called [context preservation][context_preservation]. These new operators maintain context preservation (*they’re forced to, actually*), and extract the [coroutineContext][kotlin.coroutineContext] from the collector.

``` kotlin
val someFlow = flow {  }
  .flowOnDefault()
  .flowOnIO()
  .flowOnMain()
  .flowOnMainImmediate()
  .flowOnUnconfined()
```

## Minimum Gradle Config

Add to your module's `build.gradle.kts`:

``` kotlin
repositories {
  mavenCentral()
}

dependencies {

  implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.3.7")
  implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.3.7")
  implementation("com.rickbusarow.dispatch:dispatch-core:1.0.0-beta04")
}
```

<!--- MODULE dispatch-core-->
<!--- INDEX  -->
[DispatcherProvider]: https://rbusarow.github.io/Dispatch/dispatch-core//dispatch.core/-dispatcher-provider/index.html
[DefaultDispatcherProvider]: https://rbusarow.github.io/Dispatch/dispatch-core//dispatch.core/-default-dispatcher-provider/index.html
[DefaultCoroutineScope]: https://rbusarow.github.io/Dispatch/dispatch-core//dispatch.core/-default-coroutine-scope.html
[DispatcherProvider.default]: https://rbusarow.github.io/Dispatch/dispatch-core//dispatch.core/-dispatcher-provider/default.html
[IOCoroutineScope]: https://rbusarow.github.io/Dispatch/dispatch-core//dispatch.core/-i-o-coroutine-scope.html
[DispatcherProvider.io]: https://rbusarow.github.io/Dispatch/dispatch-core//dispatch.core/-dispatcher-provider/io.html
[MainCoroutineScope]: https://rbusarow.github.io/Dispatch/dispatch-core//dispatch.core/-main-coroutine-scope.html
[DispatcherProvider.main]: https://rbusarow.github.io/Dispatch/dispatch-core//dispatch.core/-dispatcher-provider/main.html
[MainImmediateCoroutineScope]: https://rbusarow.github.io/Dispatch/dispatch-core//dispatch.core/-main-immediate-coroutine-scope.html
[DispatcherProvider.mainImmediate]: https://rbusarow.github.io/Dispatch/dispatch-core//dispatch.core/-dispatcher-provider/main-immediate.html
[UnconfinedCoroutineScope]: https://rbusarow.github.io/Dispatch/dispatch-core//dispatch.core/-unconfined-coroutine-scope.html
[DispatcherProvider.unconfined]: https://rbusarow.github.io/Dispatch/dispatch-core//dispatch.core/-dispatcher-provider/unconfined.html
[launchDefault]: https://rbusarow.github.io/Dispatch/dispatch-core//dispatch.core/kotlinx.coroutines.-coroutine-scope/launch-default.html
[launchIO]: https://rbusarow.github.io/Dispatch/dispatch-core//dispatch.core/kotlinx.coroutines.-coroutine-scope/launch-i-o.html
[launchMain]: https://rbusarow.github.io/Dispatch/dispatch-core//dispatch.core/kotlinx.coroutines.-coroutine-scope/launch-main.html
[launchMainImmediate]: https://rbusarow.github.io/Dispatch/dispatch-core//dispatch.core/kotlinx.coroutines.-coroutine-scope/launch-main-immediate.html
[launchUnconfined]: https://rbusarow.github.io/Dispatch/dispatch-core//dispatch.core/kotlinx.coroutines.-coroutine-scope/launch-unconfined.html
[asyncDefault]: https://rbusarow.github.io/Dispatch/dispatch-core//dispatch.core/kotlinx.coroutines.-coroutine-scope/async-default.html
[asyncIO]: https://rbusarow.github.io/Dispatch/dispatch-core//dispatch.core/kotlinx.coroutines.-coroutine-scope/async-i-o.html
[asyncMain]: https://rbusarow.github.io/Dispatch/dispatch-core//dispatch.core/kotlinx.coroutines.-coroutine-scope/async-main.html
[asyncMainImmediate]: https://rbusarow.github.io/Dispatch/dispatch-core//dispatch.core/kotlinx.coroutines.-coroutine-scope/async-main-immediate.html
[asyncUnconfined]: https://rbusarow.github.io/Dispatch/dispatch-core//dispatch.core/kotlinx.coroutines.-coroutine-scope/async-unconfined.html
[withDefault]: https://rbusarow.github.io/Dispatch/dispatch-core//dispatch.core/with-default.html
[withIO]: https://rbusarow.github.io/Dispatch/dispatch-core//dispatch.core/with-i-o.html
[withMain]: https://rbusarow.github.io/Dispatch/dispatch-core//dispatch.core/with-main.html
[withMainImmediate]: https://rbusarow.github.io/Dispatch/dispatch-core//dispatch.core/with-main-immediate.html
[withUnconfined]: https://rbusarow.github.io/Dispatch/dispatch-core//dispatch.core/with-unconfined.html
[flowOnDefault]: https://rbusarow.github.io/Dispatch/dispatch-core//dispatch.core/kotlinx.coroutines.flow.-flow/flow-on-default.html
[flowOnIO]: https://rbusarow.github.io/Dispatch/dispatch-core//dispatch.core/kotlinx.coroutines.flow.-flow/flow-on-i-o.html
[flowOnMain]: https://rbusarow.github.io/Dispatch/dispatch-core//dispatch.core/kotlinx.coroutines.flow.-flow/flow-on-main.html
[flowOnMainImmediate]: https://rbusarow.github.io/Dispatch/dispatch-core//dispatch.core/kotlinx.coroutines.flow.-flow/flow-on-main-immediate.html
[flowOnUnconfined]: https://rbusarow.github.io/Dispatch/dispatch-core//dispatch.core/kotlinx.coroutines.flow.-flow/flow-on-unconfined.html
<!--- END -->


[context_preservation]: https://medium.com/@elizarov/execution-context-of-kotlin-flows-b8c151c9309b
[CoroutineContext]: https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.coroutines/-coroutine-context/
[CoroutineDispatcher]: https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/-coroutine-dispatcher/index.html
[CoroutineScope]: https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/coroutine-scope.html
[Deferred]: https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/-deferred/index.html
[Dispatchers]: https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/-dispatchers/index.html
[Flow]: https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines.flow/-flow/index.html
[Job]: https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/-job/index.html
[kotlin.coroutineContext]: https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.coroutines/coroutine-context.html
[kotlin.coroutines]: https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.coroutines/index.html
[withContext]: https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/with-context.html