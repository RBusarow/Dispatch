[dispatch-test-junit5](../../index.md) / [dispatch.test](../index.md) / [CoroutineTest](index.md) / [scopeFactory](./scope-factory.md)

# scopeFactory

`val scopeFactory: `[`KClass`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.reflect/-k-class/index.html)`<*>` [(source)](https://github.com/RBusarow/Dispatch/tree/master/dispatch-test-junit5/src/main/java/dispatch/test/CoroutineTest.kt#L35)

*optional* KClass which extends [CoroutineTestExtension.ScopeFactory](https://rbusarow.github.io/Dispatch/dispatch-test/dispatch.test/-coroutine-test-extension/-scope-factory/index.md).
**This class must have a default constructor**
An instance will be automatically initialized inside the [CoroutineTestExtension](https://rbusarow.github.io/Dispatch/dispatch-test/dispatch.test/-coroutine-test-extension/index.md) and used to create custom [TestProvidedCoroutineScope](https://rbusarow.github.io/Dispatch/dispatch-test/dispatch.test/-test-provided-coroutine-scope/index.md) instances.

