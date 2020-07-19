[dispatch-core](./index.md)

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

There are also factory functions for conveniently creating any implementation, with a built-in [DispatcherProvider](https://rbusarow.github.io/Dispatch/dispatch-core//dispatch.core/-dispatcher-provider/index.html).

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
  * [Launch](#launch)
  * [Async](#async)
  * [WithContext](#withcontext)
  * [Flow](#flow)
* [DefaultDispatcherProvider](#defaultdispatcherprovider)
  * [Out-of-box default functionality](#out-of-box-default-functionality)
  * [Easy global dispatcher overrides](#easy-global-dispatcher-overrides)
* [Minimum Gradle Config](#minimum-gradle-config)

## Types

| **Name**                     | **Description**
| -------------                | --------------- |
| [DispatcherProvider](https://rbusarow.github.io/Dispatch/dispatch-core//dispatch.core/-dispatcher-provider/index.html)         | Interface which provides the 5 standard [CoroutineDispatcher](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/-coroutine-dispatcher/index.html) properties of the [Dispatchers](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/-dispatchers/index.html) object, but which can be embedded in a [CoroutineContext](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.coroutines/-coroutine-context/)
| [DefaultDispatcherProvider](https://rbusarow.github.io/Dispatch/dispatch-core//dispatch.core/-default-dispatcher-provider/index.html)  | Mutable singleton holder for an implementation of [DispatcherProvider](https://rbusarow.github.io/Dispatch/dispatch-core//dispatch.core/-dispatcher-provider/index.html). By default, it simply delegates to the corresponding properties in the [Dispatchers](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/-dispatchers/index.html) singleton.  Whenever a [CoroutineContext](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.coroutines/-coroutine-context/) does not have a [DispatcherProvider](https://rbusarow.github.io/Dispatch/dispatch-core//dispatch.core/-dispatcher-provider/index.html), this singleton's value will be used by default.

## Marker interfaces and factories

| **Name**                        | **Dispatcher**
| -------------                   | --------------- |
| [DefaultCoroutineScope](https://rbusarow.github.io/Dispatch/dispatch-core//dispatch.core/-default-coroutine-scope.html)         | [DispatcherProvider.default](https://rbusarow.github.io/Dispatch/dispatch-core//dispatch.core/-dispatcher-provider/default.html)
| [IOCoroutineScope](https://rbusarow.github.io/Dispatch/dispatch-core//dispatch.core/-i-o-coroutine-scope.html)              | [DispatcherProvider.io](https://rbusarow.github.io/Dispatch/dispatch-core//dispatch.core/-dispatcher-provider/io.html)
| [MainCoroutineScope](https://rbusarow.github.io/Dispatch/dispatch-core//dispatch.core/-main-coroutine-scope.html)            | [DispatcherProvider.main](https://rbusarow.github.io/Dispatch/dispatch-core//dispatch.core/-dispatcher-provider/main.html)
| [MainImmediateCoroutineScope](https://rbusarow.github.io/Dispatch/dispatch-core//dispatch.core/-main-immediate-coroutine-scope.html)   | [DispatcherProvider.mainImmediate](https://rbusarow.github.io/Dispatch/dispatch-core//dispatch.core/-dispatcher-provider/main-immediate.html)
| [UnconfinedCoroutineScope](https://rbusarow.github.io/Dispatch/dispatch-core//dispatch.core/-unconfined-coroutine-scope.html)      | [DispatcherProvider.unconfined](https://rbusarow.github.io/Dispatch/dispatch-core//dispatch.core/-dispatcher-provider/unconfined.html)

## Extensions

|              | **Default**     | **IO**     | **Main**     | **Main.immediate**    | **Unconfined**     |
| ------------ | --------------- | ---------- | ------------ | --------------------- | ------------------ |
| [Job](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/-job/index.html)        | [launchDefault](https://rbusarow.github.io/Dispatch/dispatch-core//dispatch.core/kotlinx.coroutines.-coroutine-scope/launch-default.html) | [launchIO](https://rbusarow.github.io/Dispatch/dispatch-core//dispatch.core/kotlinx.coroutines.-coroutine-scope/launch-i-o.html) | [launchMain](https://rbusarow.github.io/Dispatch/dispatch-core//dispatch.core/kotlinx.coroutines.-coroutine-scope/launch-main.html) | [launchMainImmediate](https://rbusarow.github.io/Dispatch/dispatch-core//dispatch.core/kotlinx.coroutines.-coroutine-scope/launch-main-immediate.html) | [launchUnconfined](https://rbusarow.github.io/Dispatch/dispatch-core//dispatch.core/kotlinx.coroutines.-coroutine-scope/launch-unconfined.html)
| [Deferred](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/-deferred/index.html)   | [asyncDefault](https://rbusarow.github.io/Dispatch/dispatch-core//dispatch.core/kotlinx.coroutines.-coroutine-scope/async-default.html)  | [asyncIO](https://rbusarow.github.io/Dispatch/dispatch-core//dispatch.core/kotlinx.coroutines.-coroutine-scope/async-i-o.html)  | [asyncMain](https://rbusarow.github.io/Dispatch/dispatch-core//dispatch.core/kotlinx.coroutines.-coroutine-scope/async-main.html)  | [asyncMainImmediate](https://rbusarow.github.io/Dispatch/dispatch-core//dispatch.core/kotlinx.coroutines.-coroutine-scope/async-main-immediate.html)  | [asyncUnconfined](https://rbusarow.github.io/Dispatch/dispatch-core//dispatch.core/kotlinx.coroutines.-coroutine-scope/async-unconfined.html)
| `suspend T`  | [withDefault](https://rbusarow.github.io/Dispatch/dispatch-core//dispatch.core/with-default.html)   | [withIO](https://rbusarow.github.io/Dispatch/dispatch-core//dispatch.core/with-i-o.html)   | [withMain](https://rbusarow.github.io/Dispatch/dispatch-core//dispatch.core/with-main.html)   | [withMainImmediate](https://rbusarow.github.io/Dispatch/dispatch-core//dispatch.core/with-main-immediate.html)   | [withUnconfined](https://rbusarow.github.io/Dispatch/dispatch-core//dispatch.core/with-unconfined.html)
| `Flow<T>`    | [flowOnDefault](https://rbusarow.github.io/Dispatch/dispatch-core//dispatch.core/kotlinx.coroutines.flow.-flow/flow-on-default.html) | [flowOnIO](https://rbusarow.github.io/Dispatch/dispatch-core//dispatch.core/kotlinx.coroutines.flow.-flow/flow-on-i-o.html) | [flowOnMain](https://rbusarow.github.io/Dispatch/dispatch-core//dispatch.core/kotlinx.coroutines.flow.-flow/flow-on-main.html) | [flowOnMainImmediate](https://rbusarow.github.io/Dispatch/dispatch-core//dispatch.core/kotlinx.coroutines.flow.-flow/flow-on-main-immediate.html) | [flowOnUnconfined](https://rbusarow.github.io/Dispatch/dispatch-core//dispatch.core/kotlinx.coroutines.flow.-flow/flow-on-unconfined.html)

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

The [CoroutineContext](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.coroutines/-coroutine-context/) used for [withContext](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/with-context.html) comes from the [coroutineContext](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.coroutines/coroutine-context.html) top-level suspend property in [kotlin.coroutines](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.coroutines/index.html).  It returns the current context, so the `default`, `io`, etc. used here are the ones defined in the [CoroutineScope](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/coroutine-scope.html) of the caller. There is no need to inject any other dependencies.

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

### Flow

Like [withContext](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/with-context.html), [Flow](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines.flow/-flow/index.html) typically doesn’t get a [CoroutineScope](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/coroutine-scope.html) of its own.  They inherit the [coroutineContext](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.coroutines/coroutine-context.html) from the collector in a pattern called [context preservation](https://medium.com/@elizarov/execution-context-of-kotlin-flows-b8c151c9309b). These new operators maintain context preservation (*they’re forced to, actually*), and extract the [coroutineContext](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.coroutines/coroutine-context.html) from the collector.

``` kotlin
val someFlow = flow {  }
  .flowOnDefault()
  .flowOnIO()
  .flowOnMain()
  .flowOnMainImmediate()
  .flowOnUnconfined()
```

## DefaultDispatcherProvider

The simplest way to get up and running with Dispatch. All library access to a
[CoroutineContext's](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.coroutines/-coroutine-context/) [DispatcherProvider](https://rbusarow.github.io/Dispatch/dispatch-core//dispatch.core/-dispatcher-provider/index.html) filters through a single extension
property:

``` kotlin
public val CoroutineContext.dispatcherProvider: DispatcherProvider
  get() = get(DispatcherProvider) ?: DefaultDispatcherProvider.get() 
```

If the receiver does not have a `DispatcherProvider`, the value from [DefaultDispatcherProvider](https://rbusarow.github.io/Dispatch/dispatch-core//dispatch.core/-default-dispatcher-provider/index.html)
will be returned. In practice, this brings at least two benefits:

### Out-of-box default functionality

Calls such as `launchIO { ... }`or `withMain { ... }` are safe to use (guaranteed to have a
`DispatcherProvider`) regardless of the source of the `CoroutineContext` or [CoroutineScope](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/coroutine-scope.html) and
without any additional configuration. By default, they will access the corresponding
[CoroutineDispatchers](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/-coroutine-dispatcher/index.html) from the [Dispatchers](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/-dispatchers/index.html) singleton.

### Easy global dispatcher overrides

`DefaultDispatcherProvider` has similar
[set](https://rbusarow.github.io/Dispatch/dispatch-core//dispatch.core/-default-dispatcher-provider/set.html)/[reset](https://rbusarow.github.io/Dispatch/dispatch-core//dispatch.core/-default-dispatcher-provider/set.html) functionality to the
[Dispatchers.setMain](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-test/kotlinx.coroutines.test/kotlinx.coroutines.-dispatchers/set-main.html)/[Dispatchers.resetMain](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-test/kotlinx.coroutines.test/kotlinx.coroutines.-dispatchers/reset-main.html) extensions in `kotlinx-coroutines-test`, except it
doesn't need to be confined to testing.

You can use [DefaultDispatcherProvider.set](https://rbusarow.github.io/Dispatch/dispatch-core//dispatch.core/-default-dispatcher-provider/set.html) to globally set a custom implementation at the beginning
of an application's lifecycle, but the most likely use-case is certainly in testing.

``` kotlin
@Test
fun `my test`() = runBlocking {

  DefaultDispatcherProvider.set(TestDispatcherProvider())
  
  withMain {
    // this would normally crash without using Dispatchers.setMain
    // but "main" here comes from the TestDispatcherProvider created above -- not Dispatchers.Main
  }
  
  DefaultDispatcherProvider.reset() // from dispatch-test
} 
```

See [dispatch-test](https://rbusarow.github.io/Dispatch/dispatch-test//index.html) and [TestDispatcherProvider](https://rbusarow.github.io/Dispatch/dispatch-test//dispatch.test/-test-dispatcher-provider/index.html)

## Minimum Gradle Config

Add to your module's `build.gradle.kts`:

``` kotlin
repositories {
  mavenCentral()
}

dependencies {

  implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.3.8")
  implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.3.8")
  implementation("com.rickbusarow.dispatch:dispatch-core:1.0.0-beta04")
}
```

### Packages

| Name | Summary |
|---|---|
| [dispatch.core](dispatch.core/index.md) |  |

### Index

[All Types](alltypes/index.md)