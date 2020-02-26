[core-test-junit5](./index.md)

## Contents

* [Features](#features)
  * [CoroutineTest example](#coroutinetest-example)
  * [TestCoroutineExtension example](#testcoroutineextension-example)
* [Setting Dispatchers.Main](#setting-dispatchersmain)
* [This module replaces core-test](#this-module-replaces-core-test)
* [JUnit dependencies](#junit-dependencies)
  * [Minimum Gradle Config](#minimum-gradle-config)
  * [JUnit 4 interoperability](#junit-4-interoperability)

## Features

In addition to all the functionality in [core-test](#), this module exposes a JUnit 5 [TestCoroutineExtension](https://rbusarow.github.io/Dispatch/core-test-junit5//dispatch.core.test/-test-coroutine-extension/index.html) and [CoroutineTest](https://rbusarow.github.io/Dispatch/core-test-junit5//dispatch.core.test/-coroutine-test/index.html) marker interface to handle set-up and tear-down of a [TestProvidedCoroutineScope](https://rbusarow.github.io/Dispatch/core-test//dispatch.core.test/-test-provided-coroutine-scope/index.html).

Since [TestProvidedCoroutineScope](https://rbusarow.github.io/Dispatch/core-test//dispatch.core.test/-test-provided-coroutine-scope/index.html) is a [TestCoroutineScope](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-test/kotlinx.coroutines.test/-test-coroutine-scope/index.html), this Extension also invokes [cleanupTestCoroutines](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-test/kotlinx.coroutines.test/-test-coroutine-scope/cleanup-test-coroutines.html) after the test.

### CoroutineTest example

``` kotlin
class SomeClassTest : CoroutineTest {

  override val testScope: TestCoroutineScope

  @Test
  fun `some test`() = runBlocking {

    val subject = SomeClass(testScope)

    val job = subject.fireAndForget()

    // TODO: assertions go here
  }

}

class SomeClass(val coroutineScope: CoroutineScope) {
  fun fireAndForget() = launch { }
}
```

### TestCoroutineExtension example

``` kotlin
class SomeClassTest {

  @JvmField
  @RegisterExtension
  val extension = TestCoroutineExtension()

  @Test
  fun `some test`() = runBlocking {

    val subject = SomeClass(extension.testScope)

    val job = subject.fireAndForget()

    // TODO: assertions go here
  }

}

class SomeClass(val coroutineScope: CoroutineScope) {
  fun fireAndForget() = launch { }
}
```

## Setting Dispatchers.Main

Even though `dispatch-core` eliminates the need to use `Dispatchers.Main` in internal code, it’s still possible that code which has yet to be migrated, or a third-party library is making use of the hard-coded dispatcher.  Because of this, the extension still calls `Dispatchers.setMain(...)` in its setup and `Dispatchers.resetMain()` afterwards.

## This module replaces core-test

If using this module, there is no need to include the `dispatch-core-test` artifact in your dependencies.

## JUnit dependencies

### Minimum Gradle Config

Click to expand a field.

Because this is a JUnit 5 Extension, it requires a the JUnit 5 artifact.  No external libraries are bundled as part of Dispatch, so you’ll need to add it to your `dependencies` block yourself.

* `org.junit.jupiter:junit-jupiter:5.5.1`

Add to your module's `build.gradle`:

``` groovy
repositories {
  mavenCentral()
}

dependencies {

  // core
  implementation "org.jetbrains.kotlinx:kotlinx-coroutines-core:1.3.3"
  implementation "org.jetbrains.kotlinx:kotlinx-coroutines-android:1.3.3"
  implementation "com.rickbusarow.dispatch:dispatch-core:1.0.0-beta03"

  // the junit5 artifact also provides the dispatch-core-test artifact
  testImplementation "com.rickbusarow.dispatch:dispatch-core-test-junit5:1.0.0-beta03"
  testImplementation "org.jetbrains.kotlinx:kotlinx-coroutines-test:1.3.3"
  testImplementation "org.junit.jupiter:junit-jupiter:5.6.0"
}
```

Add to your module's `build.gradle.kts`:

``` kotlin
repositories {
  mavenCentral()
}

dependencies {

  // core
  implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.3.3")
  implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.3.3")
  implementation("com.rickbusarow.dispatch:dispatch-core:1.0.0-beta03")

  // the junit5 artifact also provides the dispatch-core-test artifact
  testImplementation("com.rickbusarow.dispatch:dispatch-core-test-junit5:1.0.0-beta03")
  testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.3.3")
  testImplementation("org.junit.jupiter:junit-jupiter:5.6.0")
}
```

### JUnit 4 interoperability

Junit 4 provides a “vintage” (JUnit 4) artifact for legacy support (such as Robolectric or Android instrumented tests).  Dispatch also supports running both in the same project by just adding both artifacts.

Add to your module's `build.gradle`:

``` groovy
repositories {
  mavenCentral()
}

dependencies {

  // core
  implementation "org.jetbrains.kotlinx:kotlinx-coroutines-core:1.3.3"
  implementation "org.jetbrains.kotlinx:kotlinx-coroutines-android:1.3.3"
  implementation "com.rickbusarow.dispatch:dispatch-core:1.0.0-beta03"

  // the junit4 and junit5 artifacts also provides the dispatch-core-test artifact
  testImplementation "com.rickbusarow.dispatch:dispatch-core-test-junit4:1.0.0-beta03"
  testImplementation "com.rickbusarow.dispatch:dispatch-core-test-junit5:1.0.0-beta03"
  testImplementation "org.junit.jupiter:junit-jupiter:5.6.0"
  testImplementation "org.junit.vintage:junit-vintage-engine:5.6.0"
  testImplementation "org.jetbrains.kotlinx:kotlinx-coroutines-test:1.3.3"
}
```

Add to your module's `build.gradle.kts`:

``` kotlin
repositories {
  mavenCentral()
}

dependencies {

  // core
  implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.3.3")
  implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.3.3")
  implementation("com.rickbusarow.dispatch:dispatch-core:1.0.0-beta03")

  // the junit4 and junit5 artifacts also provides the dispatch-core-test artifact
  testImplementation("com.rickbusarow.dispatch:dispatch-core-test-junit4:1.0.0-beta03")
  testImplementation("com.rickbusarow.dispatch:dispatch-core-test-junit5:1.0.0-beta03")
  testImplementation("org.junit.jupiter:junit-jupiter:5.6.0")
  testImplementation("org.junit.vintage:junit-vintage-engine:5.6.0")
  testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.3.3")
}
```

### Packages

| Name | Summary |
|---|---|
| [dispatch.core.test](dispatch.core.test/index.md) |  |

### Index

[All Types](alltypes/index.md)