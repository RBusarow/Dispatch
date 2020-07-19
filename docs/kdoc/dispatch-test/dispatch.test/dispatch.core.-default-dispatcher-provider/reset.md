[dispatch-test](../../index.md) / [dispatch.test](../index.md) / [dispatch.core.DefaultDispatcherProvider](index.md) / [reset](./reset.md)

# reset

`fun `[`DefaultDispatcherProvider`](https://rbusarow.github.io/Dispatch/dispatch-core/dispatch.core/-default-dispatcher-provider/index.md)`.reset(): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html) [(source)](https://github.com/RBusarow/Dispatch/tree/master/dispatch-test/src/main/java/dispatch/test/defaultDispatcherProvider.kt#L28)

Resets the singleton [DispatcherProvider](https://rbusarow.github.io/Dispatch/dispatch-core/dispatch.core/-dispatcher-provider/index.md) instance to the true default.
This default instance delegates to the [Dispatchers](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/-dispatchers/index.html) singleton object properties.

``` kotlin
val custom = CustomDispatcherProvider()
DefaultDispatcherProvider.set(custom)

DefaultDispatcherProvider.get() shouldBe custom

DefaultDispatcherProvider.reset()

val default = DefaultDispatcherProvider.get()

default shouldNotBe custom

default.default shouldBe Dispatchers.Default
default.io shouldBe Dispatchers.IO
default.main shouldBe Dispatchers.Main
default.mainImmediate shouldBe Dispatchers.Main.immediate
default.unconfined shouldBe Dispatchers.Unconfined
```

**See Also**

[DefaultDispatcherProvider](https://rbusarow.github.io/Dispatch/dispatch-core/dispatch.core/-default-dispatcher-provider/index.md)

