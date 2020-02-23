[core-test-junit4](./index.md)

## Features

In addition to all the functionality in [dispatch-core-test](/kdoc/core-test/dispatch.core.text/index.md), this module exposes a [TestCoroutineRule](/kdoc/core-test-junit4/dispatch.core.test/-test-coroutine-rule) to handle set-up and tear-down of a [TestProvidedCoroutineScope](/kdoc/core-test/dispatch.core.test/-test-provided-coroutine-scope).

Since [TestProvidedCoroutineScope](/kdoc/core-test/dispatch.core.test/-test-provided-coroutine-scope) is a [TestCoroutineScope](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-test/kotlinx.coroutines.test/-test-coroutine-scope/), this Rule also invokes [cleanupTestCoroutines](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-test/kotlinx.coroutines.test/-test-coroutine-scope/cleanup-test-coroutines.html) after the test.

``` kotlin
class SomeClassTest {

  @JvmField
  @Rule
  val rule = TestCoroutineRule()

  @Test
  fun `some test`() = runBlocking {

    val subject = SomeClass(rule)
  
    val job = subject.fireAndForget()
    
    // TODO: assertions go here
  }

}

class SomeClass(val coroutineScope: CoroutineScope) {
  fun fireAndForget() = launch { }
}
```

## Setting Dispatchers.Main

Even though `dispatch-core` eliminates the need to use `Dispatchers.Main` in internal code, it’s still possible that code which has yet to be migrated, or a third-party library is making use of the hard-coded dispatcher.  Because of this, the rule still calls `Dispatchers.setMain(...)` in its setup and `Dispatchers.resetMain()` afterwards.

## This module replaces core-test

If using this module, there is no need to include the `dispatch-core-test` artifact in your dependencies.

## JUnit dependencies

### minimum config

Because this is a JUnit 4 Rule, it requires a variant of that artifact.  No external libraries are bundled as part of Dispatch, so you’ll need to add it to your `dependencies` block yourself.  The two official options would be:

* classic JUnit 4
  * `org.junit.jupiter:junit-jupiter:4.13`
* JUnit 5 "vintage"
  * `org.junit.vintage:junit-vintage-engine:5.5.1`

``` groovy
repositories {
  mavenCentral()
}

dependencies {
	
	def coroutines_version = "1.3.3"
	def dispatch_version = "1.0.0-beta03"

	// core
	implementation "org.jetbrains.kotlinx:kotlinx-coroutines-core:$coroutines_version"
	implementation "org.jetbrains.kotlinx:kotlinx-coroutines-android:$coroutines_version"
	implementation "com.rickbusarow.dispatch:dispatch-core:$dispatch_version"

	// the junit4 artifact also provides the dispatch-core-test artifact
	testImplementation "com.rickbusarow.dispatch:dispatch-core-test-junit4:$dispatch_version"
	testImplementation "org.jetbrains.kotlinx:kotlinx-coroutines-test:$coroutines_version"
}
```

``` kotlin
repositories {
  mavenCentral()
}

dependencies {
	
	val coroutinesVersion = "1.3.3"
	val dispatchVersion = "1.0.0-beta03"

	// core
	implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:$coroutinesVersion")
	implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:$coroutinesVersion")
	implementation("com.rickbusarow.dispatch:dispatch-core:$dispatchVersion")

	// the junit4 artifact also provides the dispatch-core-test artifact
	testImplementation("com.rickbusarow.dispatch:dispatch-core-test-junit4:$dispatchVersion")
	testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:$coroutinesVersion")
}
```

### JUnit 5 interoperability

Junit 5 provides a “vintage” (JUnit 4) artifact for legacy support (such as Robolectric or Android instrumented tests).  Dispatch also supports running both in the same project by just adding both artifacts.

``` groovy
repositories {
  mavenCentral()
}

dependencies {
	
	def coroutines_version = "1.3.3"
	def dispatch_version = "1.0.0-beta03"

	// core
	implementation "org.jetbrains.kotlinx:kotlinx-coroutines-core:$coroutines_version"
	implementation "org.jetbrains.kotlinx:kotlinx-coroutines-android:$coroutines_version"
	implementation "com.rickbusarow.dispatch:dispatch-core:$dispatch_version"

	// the junit4 and junit5 artifacts also provides the dispatch-core-test artifact
	testImplementation "com.rickbusarow.dispatch:dispatch-core-test-junit4:$dispatch_version"
	testImplementation "com.rickbusarow.dispatch:dispatch-core-test-junit5:$dispatch_version"
	testImplementation "org.jetbrains.kotlinx:kotlinx-coroutines-test:$coroutines_version"
}
```

``` kotlin
repositories {
  mavenCentral()
}

dependencies {
	
	val coroutinesVersion = "1.3.3"
	val dispatchVersion = "1.0.0-beta03"

	// core
	implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:$coroutinesVersion")
	implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:$coroutinesVersion")
	implementation("com.rickbusarow.dispatch:dispatch-core:$dispatchVersion")

	// the junit4 and junit5 artifacts also provides the dispatch-core-test artifact
	testImplementation("com.rickbusarow.dispatch:dispatch-core-test-junit4:$dispatchVersion")
	testImplementation("com.rickbusarow.dispatch:dispatch-core-test-junit5:$dispatchVersion")
	testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:$coroutinesVersion")
}
```

### Packages

| Name | Summary |
|---|---|
| [dispatch.core.test](dispatch.core.test/index.md) |  |

### Index

[All Types](alltypes/index.md)