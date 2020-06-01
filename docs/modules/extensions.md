# Module extensions

Extension functions for [Flow] and [Channel] which have not yet made their way into [kotlinx.coroutines].

## Contents
<!--- TOC -->

* [Deprecation policy](#deprecation-policy)
* [Flow](#flow)
  * [Cache and Share](#cache-and-share)
  * [Intermediary Operators](#intermediary-operators)
  * [Terminal Operators](#terminal-operators)
* [Channel](#channel)
* [Minimum Gradle Config](#minimum-gradle-config)

<!--- END -->

## Deprecation policy

These are expected to become irrelevant, either through being added to [kotlinx.coroutines]
or by modification to their API.

As an extension function reaches this point, they will be deprecated within this library.  If [kotlinx.coroutines] has
a corresponding function, the deprecated version will be changed to simply delegate to the official library version.

The deprecated version will then be scheduled for removal in a following release.

## Flow

### Cache and Share

| **Name**            | **Description**            |
| ------------------- | -----------------------    |
| [shareIn]           | Broadcast a [Flow] to multiple collectors, automatically starting with the first and cancelling with the last.  Automatically restart after cancellation upon new collectors.
| [cache]             | Save the last *n* elements emitted by a [Flow], and replay them for a new collector.


### Intermediary Operators

| **Name**            | **Description**            |
| ------------------- | -----------------------    |
| [onEachLatest]      | Invoke `suspend (T) -> Unit` each time a new value is emitted to a [Flow].  If the operation hasn't completed before a new value is emitted, the ongoing operation is cancelled and restarted.

### Terminal Operators

| **Name**            | **Description**            |
| ------------------- | -----------------------    |
| [any]               | Suspends while collecting a [Flow], invoking a `suspend (T) -> Boolean` predicate upon each value.  Returns `true` immediately upon finding a value which satisfies the predicate, otherwise suspends until the [Flow] is cancelled, then returns `false`.
| [collectUntil]      | Suspends while collecting a [Flow], invoking a `suspend (T) -> Boolean` block upon each value.  Collects until the predicate returns `true` or the [Flow] is cancelled.
| [firstOrNull]       | Suspends while collecting a [Flow] until a single value is emitted.  The single value is immediately returned.  If no value is emitted before the [Flow] is cancelled, returns `null`.

## Channel

| **Name**             | **Description**            |
| -------------------- | -----------------------    |
| [sendBlockingOrNull] | Wrapper for [SendChannel.sendBlocking] which returns `null` if the [SendChannel] is closed for send, instead of throwing a [ClosedSendChannelException].


## Minimum Gradle Config
Click to expand a field.

&nbsp;<details open> <summary> <b>Groovy</b> </summary>

Add to your module's `build.gradle`:

``` groovy
repositories {
  mavenCentral()
}

dependencies {

  implementation "org.jetbrains.kotlinx:kotlinx-coroutines-core:1.3.7"
  implementation "org.jetbrains.kotlinx:kotlinx-coroutines-android:1.3.7"
  implementation "com.rickbusarow.dispatch:dispatch-extensions:1.0.0-beta03"
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

  implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.3.7")
  implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.3.7")
  implementation("com.rickbusarow.dispatch:dispatch-extensions:1.0.0-beta03")
}
```

</details>

<!--- MODULE extensions-->
<!--- INDEX  -->
[shareIn]: https://rbusarow.github.io/Dispatch/extensions//dispatch.extensions.flow/kotlinx.coroutines.flow.-flow/share-in.html
[cache]: https://rbusarow.github.io/Dispatch/extensions//dispatch.extensions.flow/kotlinx.coroutines.flow.-flow/cache.html
[onEachLatest]: https://rbusarow.github.io/Dispatch/extensions//dispatch.extensions.flow/kotlinx.coroutines.flow.-flow/on-each-latest.html
[any]: https://rbusarow.github.io/Dispatch/extensions//dispatch.extensions.flow/kotlinx.coroutines.flow.-flow/any.html
[collectUntil]: https://rbusarow.github.io/Dispatch/extensions//dispatch.extensions.flow/kotlinx.coroutines.flow.-flow/collect-until.html
[firstOrNull]: https://rbusarow.github.io/Dispatch/extensions//dispatch.extensions.flow/kotlinx.coroutines.flow.-flow/first-or-null.html
[sendBlockingOrNull]: https://rbusarow.github.io/Dispatch/extensions//dispatch.extensions.channel/kotlinx.coroutines.channels.-send-channel/send-blocking-or-null.html
<!--- END -->

[Channel]: https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines.channels/-channel/
[ClosedSendChannelException]: https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines.channels/-closed-send-channel-exception/index.html
[Flow]: https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines.flow/-flow/index.html
[kotlinx.coroutines]: https://kotlin.github.io/kotlinx.coroutines/
[SendChannel]: https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines.channels/-send-channel/index.html
[SendChannel.sendBlocking]: https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines.channels/send-blocking.html
