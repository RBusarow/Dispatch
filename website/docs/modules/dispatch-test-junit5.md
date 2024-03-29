---
id: dispatch-test-junit5

title: Test JUnit5

sidebar_label: Test JUnit5
---

## Features

In addition to all the functionality in [dispatch-test], this module exposes a JUnit
5 [CoroutineTestExtension] and [CoroutineTest] annotation to handle set-up and tear-down of
a [TestProvidedCoroutineScope].

Since [TestProvidedCoroutineScope] is a [TestCoroutineScope], this Extension also
invokes [cleanupTestCoroutines] after the test.

### CoroutineTest example

```kotlin
@CoroutineTest(CustomScopeFactory::class)
class SomeClassTest(
  val testScope: TestProvidedCoroutineScope
) {

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

class CustomScopeFactory : CoroutineTestExtension.ScopeFactory() {
  override fun create() = TestProvidedCoroutineScope(context = Job())
}
```

### CoroutineTestExtension example

```kotlin
class SomeClassTest {

  @JvmField
  @RegisterExtension
  val extension = CoroutineTestExtension()

  @Test
  fun `some test`(scope: TestProvidedCoroutineScope) = runBlocking {

    val subject = SomeClass(scope)

    val job = subject.fireAndForget()

    // TODO: assertions go here
  }

}

class SomeClass(val coroutineScope: CoroutineScope) {
  fun fireAndForget() = launch { }
}
```

## Setting Dispatchers.Main

Even though `dispatch-core` eliminates the need to use `Dispatchers.Main` in internal code, it’s
still possible that code which has yet to be migrated, or a third-party library is making use of the
hard-coded dispatcher. Because of this, the extension still calls `Dispatchers.setMain(...)` in its
setup and `Dispatchers.resetMain()` afterwards.

## This module replaces dispatch-test

If using this module, there is no need to include the `dispatch-test` artifact in your dependencies.

## JUnit dependencies

### Minimum Gradle Config

Because this is a JUnit 5 Extension, it requires a the JUnit 5 artifact. No external libraries are
bundled as part of Dispatch, so you’ll need to add it to your `dependencies` block yourself.

- `org.junit.jupiter:junit-jupiter:5.6.2`

Add to your module's `build.gradle.kts`:

```kotlin
repositories {
  mavenCentral()
}

dependencies {

  // core
  implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.5.2")
  implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.5.2")
  implementation("com.rickbusarow.dispatch:dispatch-core:1.0.0-beta10-SNAPSHOT")

  // the junit5 artifact also provides the dispatch-test artifact
  testImplementation("com.rickbusarow.dispatch:dispatch-test-junit5:1.0.0-beta10-SNAPSHOT")
  testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.5.2")
  testImplementation("org.junit.jupiter:junit-jupiter:5.7.0")
}
```

### JUnit 4 interoperability

Junit 4 provides a “vintage” (JUnit 4) artifact for legacy support (such as Robolectric or Android
instrumented tests). Dispatch also supports running both in the same project by just adding both
artifacts.

Add to your module's `build.gradle.kts`:

```kotlin
repositories {
  mavenCentral()
}

dependencies {

  // core
  implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.5.2")
  implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.5.2")
  implementation("com.rickbusarow.dispatch:dispatch-core:1.0.0-beta10-SNAPSHOT")

  // the junit4 and junit5 artifacts also provides the dispatch-test artifact
  testImplementation("com.rickbusarow.dispatch:dispatch-test-junit4:1.0.0-beta10-SNAPSHOT")
  testImplementation("com.rickbusarow.dispatch:dispatch-test-junit5:1.0.0-beta10-SNAPSHOT")
  testImplementation("org.junit.jupiter:junit-jupiter:5.7.0")
  testImplementation("org.junit.vintage:junit-vintage-engine:5.7.0")
  testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.5.2")
}
```

[TestProvidedCoroutineScope]: https://rbusarow.github.io/Dispatch/api/dispatch-test/dispatch.test/-test-provided-coroutine-scope/index.html

[cleanupTestCoroutines]: https://rbusarow.github.io/Dispatch/api/dispatch-test/dispatch.test/-test-provided-coroutine-scope/index.html#kotlinx.coroutines.test/TestCoroutineScope/cleanupTestCoroutines/#/PointingToDeclaration/


[CoroutineTestExtension]: https://rbusarow.github.io/Dispatch/api/dispatch-test-junit5/dispatch.test/-coroutine-test-extension/index.html

[CoroutineTest]: https://rbusarow.github.io/Dispatch/api/dispatch-test-junit5/dispatch.test/-coroutine-test/index.html

[dispatch-test]: https://rbusarow.github.io/Dispatch/api/dispatch-test/dispatch.test/index.html

[TestCoroutineScope]: https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-test/kotlinx.coroutines.test/-test-coroutine-scope/index.html
