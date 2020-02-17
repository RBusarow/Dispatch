[extensions](../../index.md) / [dispatch.extensions.channel](../index.md) / [kotlinx.coroutines.channels.SendChannel](index.md) / [sendBlockingOrNull](./send-blocking-or-null.md)

# sendBlockingOrNull

`fun <E> `[`SendChannel`](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines.channels/-send-channel/index.html)`<E>.sendBlockingOrNull(element: E): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)`?` [(source)](https://github.com/RBusarow/Dispatch/tree/master/extensions/src/main/java/dispatch/extensions/channel/Send.kt#L33)

Attempts to add [element](send-blocking-or-null.md#dispatch.extensions.channel$sendBlockingOrNull(kotlinx.coroutines.channels.SendChannel((dispatch.extensions.channel.sendBlockingOrNull.E)), dispatch.extensions.channel.sendBlockingOrNull.E)/element) into this channel via [sendBlocking](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines.channels/send-blocking.html) inside a try/catch for a [ClosedSendChannelException](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines.channels/-closed-send-channel-exception/index.html).

### Note

Attempting to send to a cancelled Channel throws a [CancellationException](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/-cancellation-exception.html).
This is a more generic Exception which would be unsafe to catch in this manner.

``` kotlin
val channel = Channel<Int>(10)

channel.sendBlockingOrNull(1) shouldBe Unit

channel.close()
channel.sendBlockingOrNull(2) shouldBe null

// using the normal sendBlocking, we would have just crashed
```

**Return**
null if this channel is closed for send.

**See Also**

[sendBlocking](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines.channels/send-blocking.html)

