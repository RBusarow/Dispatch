# DispatcherProvider

This library replaces usage of the `Dispatchers` singleton object with
reference to an interface.

```Kotlin
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

The default implementation of this interface just delegates to that
`Dispatchers` object:

```kotlin
class DefaultDispatcherProvider : DispatcherProvider {

  override val default: CoroutineDispatcher = Dispatchers.Default
  override val io: CoroutineDispatcher = Dispatchers.IO
  override val main: CoroutineDispatcher = Dispatchers.Main
  override val mainImmediate: CoroutineDispatcher = Dispatchers.Main.immediate
  override val unconfined: CoroutineDispatcher = Dispatchers.Unconfined
}
```

Instead of just injecting the interface (in every single class which has
anything to do with coroutines...) we can embed it in the
`CoroutineContext` and access it using its key. This allows for easy
scoping and testing with much less boilerplate.

## Examples:

```kotlin

 class SomeClass(val coroutineScope: CoroutineScope) {

  // "withIO" extracts the dispatcher provider from the calling coroutine's coroutineContext, then uses the IO property
  suspend fun getSomeData(): SomeData = withIO {
    ...
  }
  
  // "asyncDefault" extracts the dispatcher provider from the scope, then uses the default property
  fun calculatePi(numDigits: Int): Deferred<String> = coroutineScope.asyncDefault {
    ...
  }
  
  // "launchMain" extracts the dispatcher provider from the scope, then uses the main property
  // mainImmediate is also available
  fun showToast(message: String) = coroutineScope.launchMain {
    delay(10.seconds)
    ...
  }
  
}
```

## Testing:

I've added a convenience TestDispatcherProvider.

```kotlin
class TestDispatcherProvider(
  override val default: TestCoroutineDispatcher = TestCoroutineDispatcher(),
  override val io: TestCoroutineDispatcher = TestCoroutineDispatcher(),
  override val main: TestCoroutineDispatcher = TestCoroutineDispatcher(),
  override val mainImmediate: TestCoroutineDispatcher = TestCoroutineDispatcher(),
  override val unconfined: TestCoroutineDispatcher = TestCoroutineDispatcher()
) : DispatcherProvider
```

`runBlockingProvided` is really just delegating `runBlocking`, but
creates a `CoroutineScope` which includes a `TestDispatcherProvider`, so
"IO" here is really a `TestCoroutineDispatcher`.
  

```Kotlin
@Test
fun `getSomeData should return some data`() = runBlockingProvided {

   
  val subject = SomeClass(this)
  
  subject.getSomeData() shouldBe MyData()
}
```

`runBlockingProvidedTest` delegates to `runBlockingTest`, but creates a
`TestCoroutineScope` which includes the `TestDispatcherProvider`.

This delay will be automatically skipped.

The call to `Main` is just a normal `TestCoroutineDispatcher`. There is
no use of a service loader or `MainCoroutineDispatcher`. No use of
`Dispatchers.setMain()` or `Dispatchers.resetMain()` either.


```Kotlin
@Test
fun `showToast should do Toast magic`() = runBlockingProvidedTest {

  val subject = SomeClass(this)
  
  subject.showToast("hello world!")
  
  verify { ... }
}
```

## Getting it

For now, I'm using Jitpack.

In your root/project-level build.gradle

```
allprojects {
    repositories {
        ...
        maven { url 'https://jitpack.io' }
    }
}
```

In your module build.gradle:

```
dependencies {
    implementation 'com.github.RBusarow:DispatcherProvider:0.9.0'
}
```

## License

``` 
Copyright (C) 2019 Rick Busarow
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
