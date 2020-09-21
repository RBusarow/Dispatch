[dispatch-core](../../index.md) / [dispatch.core](../index.md) / [DefaultDispatcherProvider](index.md) / [set](./set.md)

# set

`fun set(value: `[`DispatcherProvider`](../-dispatcher-provider/index.md)`): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html) [(source)](https://github.com/RBusarow/Dispatch/tree/master/dispatch-core/src/main/java/dispatch/core/DefaultDispatcherProvider.kt#L47)

Atomically sets a default [DispatcherProvider](../-dispatcher-provider/index.md) instance.

``` kotlin
val custom = CustomDispatcherProvider()
DefaultDispatcherProvider.set(custom)

val scope = MainImmediateCoroutineScope()

scope.mainImmediateDispatcher shouldBe custom.mainImmediate
DefaultDispatcherProvider.get().mainImmediate shouldBe custom.mainImmediate
```

**See Also**

[get](get.md)

