[extensions](../../index.md) / [dispatch.extensions.flow](../index.md) / [kotlinx.coroutines.flow.Flow](index.md) / [cache](./cache.md)

# cache

`@ExperimentalCoroutinesApi @FlowPreview fun <T> `[`Flow`](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines.flow/-flow/index.html)`<T>.cache(history: `[`Int`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)`): `[`Flow`](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines.flow/-flow/index.html)`<T>` [(source)](https://github.com/RBusarow/Dispatch/tree/master/extensions/src/main/java/dispatch/extensions/flow/Cache.kt#L46)

A "cached" [Flow](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines.flow/-flow/index.html) which will record the last [history](cache.md#dispatch.extensions.flow$cache(kotlinx.coroutines.flow.Flow((dispatch.extensions.flow.cache.T)), kotlin.Int)/history) collected values.

When a collector begins collecting after values have already been recorded,
those values will be collected *before* values from the receiver [Flow](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines.flow/-flow/index.html) are collected.

example:

``` Kotlin
val ints = flowOf(1, 2, 3, 4).cache(2)   // cache the last 2 values

ints.take(4).collect {  }             // 4 values are emitted, but also recorded.  The last 2 remain.

ints.collect {  }                     // collects [3, 4, 1, 2, 3, 4]
```

Throws [IllegalArgumentException](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-illegal-argument-exception/index.html) if size parameter is not greater than 0

### Parameters

`history` - the number of items to keep in the [Flow](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines.flow/-flow/index.html)'s history -- must be greater than 0
