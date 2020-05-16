[extensions](../../index.md) / [dispatch.extensions.flow](../index.md) / [kotlinx.coroutines.flow.Flow](index.md) / [cache](./cache.md)

# cache

`@ExperimentalCoroutinesApi @FlowPreview fun <T> `[`Flow`](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines.flow/-flow/index.html)`<T>.cache(history: `[`Int`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)`): `[`Flow`](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines.flow/-flow/index.html)`<T>` [(source)](https://github.com/RBusarow/Dispatch/tree/master/extensions/src/main/java/dispatch/extensions/flow/Cache.kt#L39)

A "cached" [Flow](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines.flow/-flow/index.html) which will record the last [history](cache.md#dispatch.extensions.flow$cache(kotlinx.coroutines.flow.Flow((dispatch.extensions.flow.cache.T)), kotlin.Int)/history) collected values.

When a collector begins collecting after values have already been recorded,
those values will be collected *before* values from the receiver [Flow](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines.flow/-flow/index.html) are collected.

Throws [IllegalArgumentException](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-illegal-argument-exception/index.html) if size parameter is not greater than 0

``` kotlin
runBlocking {

    val ints = flowOf(1, 2, 3, 4)
      .cache(2)    // cache the last 2 values

    ints.take(4)
      .collect { }        // 4 values are emitted, but also recorded.  The last 2 remain.

    ints.collect { }      // collects [3, 4, 1, 2, 3, 4]
  }
```

``` kotlin
runBlocking {

    val sourceFlow = flowOf(1, 2, 3, 4, 5)
      .onEach {
        delay(50)
        println("emit $it")

      }
      .shareIn(this, 1)

    val a = async { sourceFlow.toList() }
    delay(125)

    val b = async {
      // begin collecting after "emit 3"
      sourceFlow.toList()
    }

    println(a.await())
    println(b.await())

    println("** break **")

    println(sourceFlow.toList())   // the shared flow has been reset, so the cached values are cleared

    /*
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
     */
  }
```

### Parameters

`history` - the number of items to keep in the [Flow](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines.flow/-flow/index.html)'s history -- must be greater than 0