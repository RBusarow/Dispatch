# Module core-test-junit4

## Contents
<!--- TOC -->

* [Features](#features)
* [Setting Dispatchers.Main](#setting-dispatchersmain)
* [This module replaces core-test](#this-module-replaces-core-test)
* [JUnit dependencies](#junit-dependencies)
  * [Minimum Gradle Config](#minimum-gradle-config)
  * [JUnit 5 interoperability](#junit-5-interoperability)

<!--- END -->

## Features

In addition to all the functionality in [dispatch-core-test], this module exposes a [TestCoroutineRule] to handle set-up and tear-down of a [TestProvidedCoroutineScope].

Since [TestProvidedCoroutineScope] is a [TestCoroutineScope], this Rule also invokes [cleanupTestCoroutines][cleanupTestCoroutines] after the test.

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

### Minimum Gradle Config
Click to expand a field.

Because this is a JUnit 4 Rule, it requires a variant of that artifact.  No external libraries are bundled as part of Dispatch, so you’ll need to add it to your `dependencies` block yourself.  The two official options would be:

- classic JUnit 4
  - `org.junit.jupiter:junit-jupiter:4.13`
- JUnit 5 "vintage"
  - `org.junit.vintage:junit-vintage-engine:5.5.1`

&nbsp;<details open> <summary> <b>Groovy</b> </summary>

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

  // the junit4 artifact also provides the dispatch-core-test artifact
  testImplementation "com.rickbusarow.dispatch:dispatch-core-test-junit4:1.0.0-beta03"
  testImplementation "org.jetbrains.kotlinx:kotlinx-coroutines-test:1.3.3"
}
```

</details>


&nbsp;<details> <summary> <b>Kotlin Gradle DSL</b> </summary>

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

  // the junit4 artifact also provides the dispatch-core-test artifact
  testImplementation("com.rickbusarow.dispatch:dispatch-core-test-junit4:1.0.0-beta03")
  testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.3.3")
}
```

</details>

### JUnit 5 interoperability

Junit 5 provides a “vintage” (JUnit 4) artifact for legacy support (such as Robolectric or Android instrumented tests).  Dispatch also supports running both in the same project by just adding both artifacts.

&nbsp;<details open> <summary> <b>Groovy</b> </summary>

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
  testImplementation "com.rickbusarow.dispatch:dispatch--core-test-junit4:1.0.0-beta03"
  testImplementation "com.rickbusarow.dispatch:dispatch--core-test-junit5:1.0.0-beta03"
  testImplementation "org.jetbrains.kotlinx:kotlinx-coroutines-test:1.3.3"
}
```
</details>


&nbsp;<details> <summary> <b>Kotlin Gradle DSL</b> </summary>
Add to your module's `build.gradle`:

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
  testImplementation("ccom.rickbusarow.dispatch:dispatch--core-test-junit4:1.0.0-beta03")
  testImplementation("ccom.rickbusarow.dispatch:dispatch--core-test-junit5:1.0.0-beta03")
  testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.3.3")
}
```

</details>

<!--- MODULE core-test-->
<!--- INDEX  -->
[TestProvidedCoroutineScope]: https://rbusarow.github.io/Dispatch/core-test//dispatch.core.test/-test-provided-coroutine-scope/index.html
<!--- MODULE core-test-junit4-->
<!--- INDEX  -->
[TestCoroutineRule]: https://rbusarow.github.io/Dispatch/core-test-junit4//dispatch.core.test/-test-coroutine-rule/index.html
<!--- END -->

[cleanupTestCoroutines]: https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-test/kotlinx.coroutines.test/-test-coroutine-scope/cleanup-test-coroutines.html

[dispatch-core-test]: https://rbusarow.github.io/Dispatch/core-test//index.html
[TestCoroutineScope]: https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-test/kotlinx.coroutines.test/-test-coroutine-scope/index.html

