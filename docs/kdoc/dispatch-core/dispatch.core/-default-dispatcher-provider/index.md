[dispatch-core](../../index.md) / [dispatch.core](../index.md) / [DefaultDispatcherProvider](./index.md)

# DefaultDispatcherProvider

`object DefaultDispatcherProvider` [(source)](https://github.com/RBusarow/Dispatch/tree/master/dispatch-core/src/main/java/dispatch/core/DefaultDispatcherProvider.kt#L30)

Holder singleton for a [DispatcherProvider](../-dispatcher-provider/index.md) instance.

If [CoroutineScope.dispatcherProvider](../kotlinx.coroutines.-coroutine-scope/dispatcher-provider.md) or [CoroutineContext.dispatcherProvider](../kotlinx.coroutines.-coroutine-scope/dispatcher-provider.md) is referenced
in a [CoroutineContext](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.coroutines/-coroutine-context/index.html) which does not have one,
it will use a default defined by this object.

``` kotlin
val custom = CustomDispatcherProvider()
DefaultDispatcherProvider.set(custom)

val scope = MainImmediateCoroutineScope()

scope.mainImmediateDispatcher shouldBe custom.mainImmediate
DefaultDispatcherProvider.get().mainImmediate shouldBe custom.mainImmediate
```

### Functions

| Name | Summary |
|---|---|
| [get](get.md) | Returns the current configured default [DispatcherProvider](../-dispatcher-provider/index.md)`fun get(): `[`DispatcherProvider`](../-dispatcher-provider/index.md) |
| [set](set.md) | Atomically sets a default [DispatcherProvider](../-dispatcher-provider/index.md) instance.`fun set(value: `[`DispatcherProvider`](../-dispatcher-provider/index.md)`): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html) |
