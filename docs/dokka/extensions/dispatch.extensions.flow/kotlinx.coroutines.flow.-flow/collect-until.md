[extensions](../../index.md) / [dispatch.extensions.flow](../index.md) / [kotlinx.coroutines.flow.Flow](index.md) / [collectUntil](./collect-until.md)

# collectUntil

`@ExperimentalCoroutinesApi suspend fun <T> `[`Flow`](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines.flow/-flow/index.html)`<T>.collectUntil(block: suspend (T) -> `[`Boolean`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html)`): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html) [(source)](https://github.com/RBusarow/Dispatch/tree/master/extensions/src/main/java/dispatch/extensions/flow/Terminal.kt#L59)

Terminal operator which collects the given [Flow](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines.flow/-flow/index.html) until the predicate [block](collect-until.md#dispatch.extensions.flow$collectUntil(kotlinx.coroutines.flow.Flow((dispatch.extensions.flow.collectUntil.T)), kotlin.coroutines.SuspendFunction1((dispatch.extensions.flow.collectUntil.T, kotlin.Boolean)))/block) returns true.

``` kotlin
runBlocking {

    val flow = flowOf(1, 2, 3, 4)
    flow
      .onCompletion { println("all done") }
      .collectUntil {
        println("collected $it")
        it == 2
      }

    /*
      output:

      1
      2
      all done
     */
  }
```

