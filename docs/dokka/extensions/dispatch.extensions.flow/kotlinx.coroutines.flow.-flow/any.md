[extensions](../../index.md) / [dispatch.extensions.flow](../index.md) / [kotlinx.coroutines.flow.Flow](index.md) / [any](./any.md)

# any

`suspend fun <T> `[`Flow`](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines.flow/-flow/index.html)`<T>.any(predicate: (T) -> `[`Boolean`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html)`): `[`Boolean`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) [(source)](https://github.com/RBusarow/Dispatch/tree/master/extensions/src/main/java/dispatch/extensions/flow/Terminal.kt#L37)

Terminal operator which suspends until the [Flow](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines.flow/-flow/index.html) has completed
or a value has been emitted which matches the provided [predicate](any.md#dispatch.extensions.flow$any(kotlinx.coroutines.flow.Flow((dispatch.extensions.flow.any.T)), kotlin.Function1((dispatch.extensions.flow.any.T, kotlin.Boolean)))/predicate).

Returns true immediately upon collecting a matching value.

This terminal operator returns false if the flow completes without ever matching.

If the flow being collected never completes and never emits a matching value, the function will suspend indefinitely.

``` kotlin
runBlocking {

    val flow = flowOf(1, 2, 3, 4)
    flow.any { it == 2 } shouldBe true

    flow.any { it == 5 } shouldBe false
  }
```

