# Module core-test-junit4

## Features

In addition to all the functionality in [dispatch-core-test][dispatch_core_test_readme], 
this module exposes a [TestCoroutineRule][testCoroutineRule] to handle set-up and tear-down
of a [TestProvidedCoroutineScope][testProvidedCoroutineScope].

```kotlin
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

## This module provides core-test

If using this module, there is no need to include `dispatch-core-test` in 

## Gradle

<details>
<summary>
<b>module build.gradle (Click to expand)</b>
</summary>

```groovy
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
</details>


<details>
<summary>
<b>module build.gradle.kts (Click to expand)</b>
</summary>

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

</details>


[dispatch_core_test_readme]: /kdoc/core-test/dispatch.core.text/index.md
[testCoroutineRule]: /kdoc/core-test-junit4/dispatch.core.test/-test-coroutine-rule
[testProvidedCoroutineScope]: /kdoc/core-test/dispatch.core.test/-test-provided-coroutine-scope
