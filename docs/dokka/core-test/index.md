[core-test](./index.md)

Test helpers for the [dispatch-core](https://rbusarow.github.io/Dispatch/core//index.html) module.  Most of the tools you need to automatically handle [DispatcherProvider](https://rbusarow.github.io/Dispatch/core//dispatch.core/-dispatcher-provider/index.html) in your tests.  (see [dispatch-core-test-junit4](https://rbusarow.github.io/Dispatch/core-test-junit4//index.html) or [dispatch-core-test-junit5](https://rbusarow.github.io/Dispatch/core-test-junit5//index.html) for the rest.)

## Contents

* [TestDispatcherProvider](#testdispatcherprovider)
  * [Constructor with default arguments](#constructor-with-default-arguments)
  * [Single-arg factory](#single-arg-factory)
  * [Basic TestDispatcherProvider](#basic-testdispatcherprovider)
* [TestProvidedCoroutineScope](#testprovidedcoroutinescope)
* [Builders](#builders)
  * [Minimum Gradle Config](#minimum-gradle-config)

## TestDispatcherProvider

Testing version of the [DispatcherProvider](https://rbusarow.github.io/Dispatch/core//dispatch.core/-dispatcher-provider/index.html) with three main styles of creation:

### Constructor with default arguments

[(link)](https://rbusarow.github.io/Dispatch/core-test//dispatch.core.test/-test-dispatcher-provider/index.html)

Each property becomes its own [TestCoroutineDispatcher](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-test/kotlinx.coroutines.test/-test-coroutine-dispatcher/index.html) by default, but may be replaced by any [CoroutineDispatcher](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/-coroutine-dispatcher/index.html).

``` kotlin
val customTestDispatcherProvider = TestDispatcherProvider(
  default = newSingleThreadContext("default"),
  io = newSingleThreadContext("io"),
  main = newSingleThreadContext("main"),
  mainImmediate = newSingleThreadContext("main immediate"),
  unconfined = newSingleThreadContext("unconfined")
)

val defaultTetsDispatcherProvider = TestDispatcherProvider()
```

### Single-arg factory

[(link)](https://rbusarow.github.io/Dispatch/core-test//dispatch.core.test/-test-dispatcher-provider/index.html)

Another option is to pass a single [CoroutineDispatcher](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/-coroutine-dispatcher/index.html), which is then used to populate all fields.

``` kotlin
val dispatcher = newSingleThreadContext("custom")

val dispatcherProvider = TestDispatcherProvider(dispatcher)

dispatcherProvider.default shouldBe myDispatcher
dispatcherProvider.io shouldBe myDispatcher
dispatcherProvider.main shouldBe myDispatcher
dispatcherProvider.mainImmediate shouldBe myDispatcher
dispatcherProvider.unconfined shouldBe myDispatcher
```

### Basic TestDispatcherProvider

[(link)](https://rbusarow.github.io/Dispatch/core-test//dispatch.core.test/-test-dispatcher-provider/index.html)

Sometimes we want to have the normal dispatch behaviors of a production environment,
just without the awkward mechanics of [Dispatchers.setMain](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-test/kotlinx.coroutines.test/kotlinx.coroutines.-dispatchers/set-main.html).

This is essentially [DefaultDispatcherProvider](https://rbusarow.github.io/Dispatch/core//dispatch.core/-default-dispatcher-provider/index.html) except with a [single-threaded executor](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/new-single-thread-context.html) handling the "main" thread.

``` kotlin
fun TestBasicDispatcherProvider(): TestDispatcherProvider {

  val mainThread = newSingleThreadContext("main thread proxy")

  return TestDispatcherProvider(
    default = Dispatchers.Default,
    io = Dispatchers.IO,
    main = mainThread,
    mainImmediate = mainThread,
    unconfined = Dispatchers.Unconfined
  )
}
```

## TestProvidedCoroutineScope

[(link)](https://rbusarow.github.io/Dispatch/core-test//dispatch.core.test/-test-provided-coroutine-scope/index.html)

A polymorphic [CoroutineScope](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/coroutine-scope.html) which implements all the type-safe versions from [dispatch-core](https://rbusarow.github.io/Dispatch/core//index.html), as well as [TestCoroutineScope](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-test/kotlinx.coroutines.test/-test-coroutine-scope/index.html).

This type may be injected anywhere, regardless of the requirement.

## Builders

Sometimes, instead of explicitly creating a [CoroutineScope](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/coroutine-scope.html) object, we prefer to just use a coroutineScope builder function within a function.

``` kotlin
@Test
fun some_test() = runBlockingProvided {
  someSuspendFunction()
}

@Test
fun some_test() = testProvided {
  someSuspendFunctionWithADelay()
}
```

| **Name**               | **Description**
| -------------------    | ---------------
| [runBlockingProvided](https://rbusarow.github.io/Dispatch/core-test//dispatch.core.test/run-blocking-provided.html)  | Uses [runBlocking](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/run-blocking.html), but injects a [DispatcherProvider](https://rbusarow.github.io/Dispatch/core//dispatch.core/-dispatcher-provider/index.html) into its [CoroutineScope](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/coroutine-scope.html).  Use this function if you want normal dispatch behavior.
| [testProvided](https://rbusarow.github.io/Dispatch/core-test//dispatch.core.test/test-provided.html)         | Uses [runBlockingTest](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-test/kotlinx.coroutines.test/run-blocking-test.html), but injects a [DispatcherProvider](https://rbusarow.github.io/Dispatch/core//dispatch.core/-dispatcher-provider/index.html) into its [TestCoroutineScope](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-test/kotlinx.coroutines.test/-test-coroutine-scope/index.html).  Use this function if you want the explicit time control of [runBlockingTest](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-test/kotlinx.coroutines.test/run-blocking-test.html).

### Minimum Gradle Config

Click to expand a field.

&nbsp;  Groovy

Add to your module's `build.gradle`:

``` groovy
repositories {
  mavenCentral()
}

dependencies {

  // core
  implementation "org.jetbrains.kotlinx:kotlinx-coroutines-core:1.3.7"
  implementation "org.jetbrains.kotlinx:kotlinx-coroutines-android:1.3.7"
  implementation "com.rickbusarow.dispatch:dispatch-core:1.0.0-beta03"

  testImplementation "com.rickbusarow.dispatch:dispatch-core-test:1.0.0-beta03"
  testImplementation "org.jetbrains.kotlinx:kotlinx-coroutines-test:1.3.7"
}
```

&nbsp;  Kotlin Gradle DSL

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

  testImplementation("com.rickbusarow.dispatch:dispatch-core-test:1.0.0-beta03")
  testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.3.7")
}
```

### Packages

| Name | Summary |
|---|---|
| [dispatch.core.test](dispatch.core.test/index.md) |  |

### Index

[All Types](alltypes/index.md)