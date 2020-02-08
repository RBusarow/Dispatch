[extensions](../../index.md) / [dispatch.extensions.flow](../index.md) / [kotlinx.coroutines.flow.Flow](index.md) / [onEachLatest](./on-each-latest.md)

# onEachLatest

`@ExperimentalCoroutinesApi fun <T> `[`Flow`](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines.flow/-flow/index.html)`<T>.onEachLatest(action: suspend (T) -> `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)`): `[`Flow`](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines.flow/-flow/index.html)`<T>` [(source)](https://github.com/RBusarow/Dispatch/tree/master/extensions/src/main/java/dispatch/extensions/flow/Operators.kt#L50)

Returns a flow which performs the given [action](on-each-latest.md#dispatch.extensions.flow$onEachLatest(kotlinx.coroutines.flow.Flow((dispatch.extensions.flow.onEachLatest.T)), kotlin.coroutines.SuspendFunction1((dispatch.extensions.flow.onEachLatest.T, kotlin.Unit)))/action) on each value of the original flow.

The crucial difference from [onEach](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines.flow/on-each.html) is that when the original flow emits a new value, the [action](on-each-latest.md#dispatch.extensions.flow$onEachLatest(kotlinx.coroutines.flow.Flow((dispatch.extensions.flow.onEachLatest.T)), kotlin.coroutines.SuspendFunction1((dispatch.extensions.flow.onEachLatest.T, kotlin.Unit)))/action) block for previous
value is cancelled.

It can be demonstrated by the following example:

```
flow {
    emit(1)
    delay(50)
    emit(2)
}
.onEachLatest { value ->
    println("Collecting $value")
    delay(100) // Emulate work
    println("$value collected")
}
.launchIn(myScope)
```

prints "Collecting 1, Collecting 2, 2 collected"

