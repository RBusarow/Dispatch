[extensions](../../index.md) / [dispatch.extensions.flow](../index.md) / [kotlinx.coroutines.flow.Flow](index.md) / [shareIn](./share-in.md)

# shareIn

`@ExperimentalCoroutinesApi @FlowPreview fun <T> `[`Flow`](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines.flow/-flow/index.html)`<T>.shareIn(scope: `[`CoroutineScope`](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/-coroutine-scope/index.html)`, cacheHistory: `[`Int`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)` = 0): `[`Flow`](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines.flow/-flow/index.html)`<T>` [(source)](https://github.com/RBusarow/Dispatch/tree/master/extensions/src/main/java/dispatch/extensions/flow/Share.kt#L154)

Creates a [broadcast](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines.channels/broadcast.html) coroutine which collects the [Flow](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines.flow/-flow/index.html) receiver and shares with multiple collectors.

A [BroadcastChannel](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines.channels/-broadcast-channel/index.html) with [default](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines.channels/-channel/-factory/-b-u-f-f-e-r-e-d.html) buffer size is created.
Use [buffer](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines.flow/buffer.html) operator on the flow before calling `shareIn` to specify a value other than
default and to control what happens when data is produced faster than it is consumed,
that is to control back-pressure behavior.

Concurrent collectors will all collect from a single [broadcast](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines.channels/broadcast.html) flow.  This flow will be cancelled automatically
when it is no longer being collected, and the underlying channel will be closed.

If a new collector is added after the channel has been closed, a new channel will be created.

By default, this flow is effectively **stateless** in that collectors will only receive values emitted after collection begins.

example:

```
  val sourceFlow = flowOf(1, 2, 3, 4, 5)
      .onStart { println("start source") }
      .onEach { println("emit $it") }
      .onCompletion { println("complete source") }
      .shareIn(this)

  val a = async { sourceFlow.toList() }
  val b = async { sourceFlow.toList() }  // collect concurrently

  println(a.await())
  println(b.await())

  println("** break **")

  println(sourceFlow.toList())

prints:

  start source
  emit 1
  emit 2
  emit 3
  emit 4
  emit 5
  complete source
  [1, 2, 3, 4, 5]
  [1, 2, 3, 4, 5]
   ** break **
  start source
  emit 1
  emit 2
  emit 3
  emit 4
  emit 5
  complete source
  [1, 2, 3, 4, 5]

```

### Caching

When a shared flow is cached, the values are recorded as they are emitted from the source Flow.
They are then replayed for each new subscriber.

When a shared flow is reset, the cached values are cleared.

example:

```
val sourceFlow = flowOf(1, 2, 3, 4, 5)
    .onEach {
        delay(50)
        println("emit $it")
    }.shareIn(this, 1)

val a = async { sourceFlow.toList() }
delay(125)
val b = async { sourceFlow.toList() } // begin collecting after "emit 3"

println(a.await())
println(b.await())

println("** break **")

println(sourceFlow.toList())          // the shared flow has been reset, so the cached values are cleared

prints:

  emit 1
  emit 2
  emit 3
  emit 4
  emit 5
  [1, 2, 3, 4, 5]
  [2, 3, 4, 5]
   ** break **
  emit 1
  emit 2
  emit 3
  emit 4
  emit 5
  [1, 2, 3, 4, 5]

```

In order to have cached values persist across resets, use `cache(n)` before `shareIn(...)`.

example:

```
// resets cache whenever the Flow is reset
flowOf(1, 2, 3).shareIn(myScope, 3)

// persists cache across resets
flowOf(1, 2, 3).cached(3).shareIn(myScope)
```

### Cancellation semantics

1. Flow consumer is cancelled when the original channel is cancelled.
2. Flow consumer completes normally when the original channel completes (~is closed) normally.
3. Collection is cancelled when the (scope)[CoroutineScope](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/-coroutine-scope/index.html) parameter is cancelled,
thereby ending the consumer when it has run out of elements.
4. If the flow consumer fails with an exception, subscription is cancelled.

### Parameters

`scope` - The [CoroutineScope](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/-coroutine-scope/index.html) used to create the [broadcast](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines.channels/broadcast.html) coroutine.  Cancellation of this scope
will close the underlying [BroadcastChannel](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines.channels/-broadcast-channel/index.html).

`cacheHistory` - (default = 0).  Any value greater than zero will add a [cache](cache.md) to the shared Flow.