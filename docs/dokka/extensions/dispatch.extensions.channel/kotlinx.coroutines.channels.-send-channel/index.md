[extensions](../../index.md) / [dispatch.extensions.channel](../index.md) / [kotlinx.coroutines.channels.SendChannel](./index.md)

### Extensions for kotlinx.coroutines.channels.SendChannel

| Name | Summary |
|---|---|
| [sendBlockingOrNull](send-blocking-or-null.md) | Attempts to add [element](send-blocking-or-null.md#dispatch.extensions.channel$sendBlockingOrNull(kotlinx.coroutines.channels.SendChannel((dispatch.extensions.channel.sendBlockingOrNull.E)), dispatch.extensions.channel.sendBlockingOrNull.E)/element) into this channel via [sendBlocking](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines.channels/send-blocking.html) inside a try/catch for a [ClosedSendChannelException](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines.channels/-closed-send-channel-exception/index.html).`fun <E> `[`SendChannel`](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines.channels/-send-channel/index.html)`<E>.sendBlockingOrNull(element: E): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)`?` |
