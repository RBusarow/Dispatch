[extensions](./index.md)

Extension functions for [Flow](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines.flow/-flow/index.html) and [Channel](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines.channels/-channel/) which have not yet made their way into [kotlinx.coroutines](https://kotlin.github.io/kotlinx.coroutines/).

## Contents

* [Deprecation policy](#deprecation-policy)
* [Flow](#flow)
  * [Cache and Share](#cache-and-share)
  * [Intermediary Operators](#intermediary-operators)
  * [Terminal Operators](#terminal-operators)
* [Channel](#channel)
* [Minimum Gradle Config](#minimum-gradle-config)

## Deprecation policy

These are expected to become irrelevant, either through being added to [kotlinx.coroutines](https://kotlin.github.io/kotlinx.coroutines/)
or by modification to their API.

As an extension function reaches this point, they will be deprecated within this library.  If [kotlinx.coroutines](https://kotlin.github.io/kotlinx.coroutines/) has
a corresponding function, the deprecated version will be changed to simply delegate to the official library version.

The deprecated version will then be scheduled for removal in a following release.

## Flow

### Cache and Share

| **Name**            | **Description**            |
| ------------------- | -----------------------    |
| [shareIn](https://rbusarow.github.io/Dispatch/extensions//dispatch.extensions.flow/kotlinx.coroutines.flow.-flow/share-in.html)           | Broadcast a [Flow](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines.flow/-flow/index.html) to multiple collectors, automatically starting with the first and cancelling with the last.  Automatically restart after cancellation upon new collectors.
| [cache](https://rbusarow.github.io/Dispatch/extensions//dispatch.extensions.flow/kotlinx.coroutines.flow.-flow/cache.html)             | Save the last *n* elements emitted by a [Flow](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines.flow/-flow/index.html), and replay them for a new collector.

### Intermediary Operators

| **Name**            | **Description**            |
| ------------------- | -----------------------    |
| [onEachLatest](https://rbusarow.github.io/Dispatch/extensions//dispatch.extensions.flow/kotlinx.coroutines.flow.-flow/on-each-latest.html)      | Invoke `suspend (T) -> Unit` each time a new value is emitted to a [Flow](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines.flow/-flow/index.html).  If the operation hasn't completed before a new value is emitted, the ongoing operation is cancelled and restarted.

### Terminal Operators

| **Name**            | **Description**            |
| ------------------- | -----------------------    |
| [any](https://rbusarow.github.io/Dispatch/extensions//dispatch.extensions.flow/kotlinx.coroutines.flow.-flow/any.html)               | Suspends while collecting a [Flow](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines.flow/-flow/index.html), invoking a `suspend (T) -> Boolean` predicate upon each value.  Returns `true` immediately upon finding a value which satisfies the predicate, otherwise suspends until the [Flow](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines.flow/-flow/index.html) is cancelled, then returns `false`.
| [collectUntil](https://rbusarow.github.io/Dispatch/extensions//dispatch.extensions.flow/kotlinx.coroutines.flow.-flow/collect-until.html)      | Suspends while collecting a [Flow](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines.flow/-flow/index.html), invoking a `suspend (T) -> Boolean` block upon each value.  Collects until the predicate returns `true` or the [Flow](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines.flow/-flow/index.html) is cancelled.
| [firstOrNull](https://rbusarow.github.io/Dispatch/extensions//dispatch.extensions.flow/kotlinx.coroutines.flow.-flow/first-or-null.html)       | Suspends while collecting a [Flow](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines.flow/-flow/index.html) until a single value is emitted.  The single value is immediately returned.  If no value is emitted before the [Flow](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines.flow/-flow/index.html) is cancelled, returns `null`.

## Channel

| **Name**             | **Description**            |
| -------------------- | -----------------------    |
| [sendBlockingOrNull](https://rbusarow.github.io/Dispatch/extensions//dispatch.extensions.channel/kotlinx.coroutines.channels.-send-channel/send-blocking-or-null.html) | Wrapper for [SendChannel.sendBlocking](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines.channels/send-blocking.html) which returns `null` if the [SendChannel](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines.channels/-send-channel/index.html) is closed for send, instead of throwing a [ClosedSendChannelException](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines.channels/-closed-send-channel-exception/index.html).

## Minimum Gradle Config

Click to expand a field.

Add to your module's `build.gradle`:

``` groovy
repositories {
  mavenCentral()
}

dependencies {

  implementation "org.jetbrains.kotlinx:kotlinx-coroutines-core:1.3.3"
  implementation "org.jetbrains.kotlinx:kotlinx-coroutines-android:1.3.3"
  implementation "com.rickbusarow.dispatch:dispatch-extensions:1.0.0-beta03"
}
```

Add to your module's `build.gradle.kts`:

``` kotlin
repositories {
  mavenCentral()
}

dependencies {

  implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.3.3")
  implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.3.3")
  implementation("com.rickbusarow.dispatch:dispatch-extensions:1.0.0-beta03")
}
```

### Packages

| Name | Summary |
|---|---|
| [dispatch.extensions.channel](dispatch.extensions.channel/index.md) |  |
| [dispatch.extensions.flow](dispatch.extensions.flow/index.md) |  |

### Index

[All Types](alltypes/index.md)