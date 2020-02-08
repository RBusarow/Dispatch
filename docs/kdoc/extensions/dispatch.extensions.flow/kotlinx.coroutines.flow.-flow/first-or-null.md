[extensions](../../index.md) / [dispatch.extensions.flow](../index.md) / [kotlinx.coroutines.flow.Flow](index.md) / [firstOrNull](./first-or-null.md)

# firstOrNull

`@ExperimentalCoroutinesApi suspend fun <T> `[`Flow`](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines.flow/-flow/index.html)`<T>.firstOrNull(): T?` [(source)](https://github.com/RBusarow/Dispatch/tree/master/extensions/src/main/java/dispatch/extensions/flow/Terminal.kt#L107)

Terminal operator which suspends until the [Flow](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines.flow/-flow/index.html) has emitted one value, then immediately returns that value.

Returns null if the `Flow` completes without emitting any values.

Example:

```
val firstFlow = flowOf(1, 2, 3, 4)
firstFlow.firstOrNull() shouldBe 1

val secondFlow = flowOf<Int> { }
secondFlow.firstOrNull() shouldBe null
```

If the flow being collected never completes and never emits a matching value, the function will suspend indefinitely.

