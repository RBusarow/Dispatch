[![Maven Central](https://maven-badges.herokuapp.com/maven-central/com.rickbusarow.dispatch/core/badge.svg)](https://maven-badges.herokuapp.com/maven-central/com.rickbusarow.dispatch/core)
![CI](https://github.com/RBusarow/Dispatch/workflows/CI/badge.svg)
[![License](https://img.shields.io/badge/License-Apache%202.0-blue.svg)](https://opensource.org/licenses/Apache-2.0)

# Artifacts

[dispatch-android-lifecycle][android-lifecycle] - better lifecycle management for [CoroutineScope][coroutineScope] from a `LifecycleOwner`.

[dispatch-android-viewmodel][android-viewmodel] - marginally better lifecycle management for [CoroutineScope][coroutineScope] from a `ViewModel`.

# DispatcherProvider

Utilities for [kotlinx.coroutines][coroutines] which make them type-safe, easier to test, and require less code. 
Define your [CoroutineDispatchers][coroutineDispatcher] once in a [CoroutineScope][coroutineScope] factory and then never think about them again.

```kotlin
val presenter = MyPresenter(MainCoroutineScope())

class MyPresenter @Inject constructor(
  val coroutineScope: MainCoroutineScope // <-- Main
) {

  fun loopSomething() = coroutineScope.launchDefault {  }

  suspend fun updateSomething() = withMainImmediate { }
}
```

```kotlin
class MyTest {

  @Test
  fun `no setting the main dispatcher`() = runBlockingProvidedTest {

    // this == TestProvidedCoroutineScope,
    // which automatically uses TestCoroutineDispatcher
    // for **every** dispatcher type
    val presenter = MyPresenter(this)

    // this call would normally crash
    presenter.updateSomething()

    // asserts
  }

}
```

---

### Injecting dispatchers

The core of this library is `DispatcherProvider` - an interface with properties corresponding to the 5 different [CoroutineDispatchers][coroutineDispatcher] we can get from [Dispatchers][Dispatchers].  It implements [CoroutineContext.Element][CoroutineContext.Element] and provides a unique [CoroutineContext.Key][CoroutineContext.Key].

```kotlin
interface DispatcherProvider : CoroutineContext.Element {

  override val key: CoroutineContext.Key<*> get() = Key

  val default: CoroutineDispatcher
  val io: CoroutineDispatcher
  val main: CoroutineDispatcher
  val mainImmediate: CoroutineDispatcher
  val unconfined: CoroutineDispatcher

  companion object Key : CoroutineContext.Key<DispatcherProvider>
}
```

This allows it to be included as an element in `CoroutineContext`:

```kotlin
val someCoroutineScope = CoroutineScope(
  //                         here
  Job() + Dispatchers.Main + DispatcherProvider()
)
```

The default implementation of this interface simply delegates to that `Dispatchers` object:

```kotlin
class DefaultDispatcherProvider : DispatcherProvider {

  override val default: CoroutineDispatcher = Dispatchers.Default
  override val io: CoroutineDispatcher = Dispatchers.IO
  override val main: CoroutineDispatcher = Dispatchers.Main
  override val mainImmediate: CoroutineDispatcher = Dispatchers.Main.immediate
  override val unconfined: CoroutineDispatcher = Dispatchers.Unconfined
}
```

---

### Referencing dispatchers

These references can then be accessed via extension functions upon [CoroutineScope][coroutineScope]:

```kotlin
// from any CoroutineScope
val mainDispatcher = myCoroutineScope.mainDispatcher
val ioDispatcher = myCoroutineScope.ioDispatcher
```

Or the `coroutineContext`:

```kotlin
suspend fun myFunction() {
  val defaultDispatcher = coroutineContext.dispatcherProvider.default
}
```

Or with builders and operators:

##### Launch

```kotlin
fun foo(scope: CoroutineScope) {
  scope.launchDefault {  }
  scope.launchIO {  }
  scope.launchMain {  }
  scope.launchMainImmediate {  }
  scope.launchUnconfined {  }
}
```

##### Async

```kotlin
fun foo(scope: CoroutineScope) {
  scope.asyncDefault {  }
  scope.asyncIO {  }
  scope.asyncMain {  }
  scope.asyncMainImmediate {  }
  scope.asyncUnconfined {  }
}
```

##### WithContext

The [CoroutineContext][CoroutineContext] used for `withContext` comes from the `coroutineContext` top-level suspend property in `kotlin.coroutines`.  It returns the current context, so the `default`, `io`, etc. used here are the ones defined in the `CoroutineScope` of the caller. There is no need to inject any other dependencies.

```kotlin
suspend fun foo() {
  // note that we have no CoroutineContext
  withDefault {  }
  withIO {  }
  withMain {  }
  withMainImmediate {  }
  withUnconfined {  }
}
```

##### Flow

Like `withContext`, `Flow` typically doesn’t get a `CoroutineScope` of its own.  They inherit the `coroutineContext` from the collector in a pattern called [context preservation][context_preservation]. These new operators maintain context preservation (*they’re forced to, actually*), and extract the `coroutineContext` from the collector.

```kotlin
val someFlow = flow {  }
  .flowOnDefault()
  .flowOnIO()
  .flowOnMain()
  .flowOnMainImmediate()
  .flowOnUnconfined()
```

---

### Types and Factories

A [CoroutineScope][coroutineScope] may have any type [CoroutineDispatcher][coroutineDispatcher] (*actually it’s a [ContinuationInterceptor][ContinuationInterceptor]*).  What if we have a class which will always use the [Main][Dispatchers.Main] thread, such as a View class?  We have marker interfaces and factories to ensure that the correct type of [CoroutineScope][coroutineScope] is always used.

```kotlin
val someDefaultScope = DefaultCoroutineScope()
val someNamedMainScope = MainCoroutineScope(CoroutineName("I'm handy for debugging"))
val someImmediateScope = MainImmediateCoroutineScope()
val scopeWithSharedJob = IOCoroutineScope(someExistingJob)

val customProviderScope = UnconfinedCoroutineScope(MyCustomDispatcherProvider())
```



We can also write an extension val which returns a default implementation of the `DispatcherProvider`,
meaning that we're able to immediately utilize the following pattern with [CoroutineScopes][coroutineScope] from unaltered legacy code or another library.

```kotlin
val CoroutineContext.dispatcherProvider: DispatcherProvider
  get() = get(DispatcherProvider) ?: DefaultDispatcherProvider()
```

---

### Testing

There's a convenience `TestDispatcherProvider`.

```kotlin
class TestDispatcherProvider(
  override val default: TestCoroutineDispatcher = TestCoroutineDispatcher(),
  override val io: TestCoroutineDispatcher = TestCoroutineDispatcher(),
  override val main: TestCoroutineDispatcher = TestCoroutineDispatcher(),
  override val mainImmediate: TestCoroutineDispatcher = TestCoroutineDispatcher(),
  override val unconfined: TestCoroutineDispatcher = TestCoroutineDispatcher()
) : DispatcherProvider
```

---

`runBlockingProvided` is really just delegating `runBlocking`, but creates a [CoroutineScope][coroutineScope] which includes  `TestDispatcherProvider`, so "io" here is really a `TestCoroutineDispatcher`.


```kotlin
@Test
fun `getSomeData should return some data`() = runBlockingProvided {

  val subject = SomeClass(this)
  // uses "io" TestCoroutineDispatcher
  subject.getSomeData() shouldBe MyData()
}
```

---

`runBlockingProvidedTest` delegates to `runBlockingTest`, but creates a `TestCoroutineScope` which includes the `TestDispatcherProvider`.

This delay will be automatically skipped.

The call to `main` is just a normal `TestCoroutineDispatcher`. There is no use of a service loader or  `MainCoroutineDispatcher`. No use of `Dispatchers.setMain()` or `Dispatchers.resetMain()` either.


```kotlin
@Test
fun `showToast should do Toast magic`() = runBlockingProvidedTest {

  val subject = SomeClass(this)
  // uses "main" TestCoroutineDispatcher
  subject.showToast("hello world!")

  verify {  }
}
```

---

### Gradle

In your application or library module(s) gradle file(s):


#### Groovy (build.gradle)
``` groovy
repositories {
  mavenCentral()
}

dependepencies {

  // still add the standard kotlinx-coroutines libraries.  This library just wraps them
  implementation "org.jetbrains.kotlinx:kotlinx-coroutines-core:$currentCoroutinesVersion"

  // necessary for Android projects
  implementation "org.jetbrains.kotlinx:kotlinx-coroutines-android:$currentCoroutinesVersion"

  // necessary for testing
  testImplementation "org.jetbrains.kotlinx:kotlinx-coroutines-test:$currentCoroutinesVersion"

  implementation "com.rickbusarow.dispatch:dispatcher-provider:1.0.0-beta02"
  testImplementation "com.rickbusarow.dispatch:dispatcher-provider-test:1.0.0-beta02"
}
```

#### Kotlin Gradle DSL (build.gradle.kts)

``` kotlin
repositories {
  mavenCentral()
}

dependepencies {

  // still add the standard kotlinx-coroutines libraries.  This library just wraps them
  implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:$currentCoroutinesVersion")

  // necessary for Android projects
  implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:$currentCoroutinesVersion")

  // necessary for testing
  testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:$currentCoroutinesVersion")

  implementation("com.rickbusarow.dispatch:dispatcher-provider:1.0.0-beta02")
  testImplementation("com.rickbusarow.dispatch:dispatcher-provider-test:1.0.0-beta02")
}
```

## License

``` text
Copyright (C) 2020 Rick Busarow
Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at
     http://www.apache.org/licenses/LICENSE-2.0
Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```
[coroutines]: https://github.com/Kotlin/kotlinx.coroutines
[CoroutineContext]: https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.coroutines.experimental/-coroutine-context/index.html
[CoroutineContext.Element]: https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.coroutines.experimental/-coroutine-context/index.html#types
[CoroutineContext.Key]: https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.coroutines.experimental/-coroutine-context/index.html#types
[ContinuationInterceptor]: https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.coroutines.experimental/-continuation-interceptor/index.html
[Dispatchers]: https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/-dispatchers/index.html
[coroutineDispatcher]: https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/coroutine-dispatcher.html
[coroutineScope]: https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/coroutine-scope.html
[context_preservation]: https://medium.com/@elizarov/execution-context-of-kotlin-flows-b8c151c9309b
[Dispatchers.Main]: https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/-dispatchers/-main.html
[android-lifecycle]: /docs/module-android-lifecycle.md
[android-viewmodel]: /docs/module-android-viewmodel.md
