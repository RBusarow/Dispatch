[dispatch-test-junit5](../index.md) / [dispatch.core.test](./index.md)

## Package dispatch.core.test

### Types

| Name | Summary |
|---|---|
| [CoroutineTestExtension](-coroutine-test-extension/index.md) | JUnit 5 [ParameterResolver](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.io/java.io.-file/extension.html)[Extension](https://junit.org/junit5/docs/current/api/org/junit/jupiter/api/extension/Extension.html) for injecting and managing a [TestProvidedCoroutineScope](https://rbusarow.github.io/Dispatch/dispatch-test/dispatch.core.test/-test-provided-coroutine-scope/index.md) in a test instance. This creates a new instance of [TestProvidedCoroutineScope](https://rbusarow.github.io/Dispatch/dispatch-test/dispatch.core.test/-test-provided-coroutine-scope/index.md) each time the scope is injected, optionally using a custom [ScopeFactory](https://rbusarow.github.io/Dispatch/dispatch-test/dispatch.core.test/-coroutine-test-extension/-scope-factory/index.md).`class CoroutineTestExtension : `[`TypeBasedParameterResolver`](https://junit.org/junit5/docs/current/api/org/junit/jupiter/api/extension/support/TypeBasedParameterResolver.html)`<`[`TestProvidedCoroutineScope`](https://rbusarow.github.io/Dispatch/dispatch-test/dispatch.core.test/-test-provided-coroutine-scope/index.md)`>, `[`BeforeEachCallback`](https://junit.org/junit5/docs/current/api/org/junit/jupiter/api/extension/BeforeEachCallback.html)`, `[`AfterEachCallback`](https://junit.org/junit5/docs/current/api/org/junit/jupiter/api/extension/AfterEachCallback.html) |

### Annotations

| Name | Summary |
|---|---|
| [CoroutineTest](-coroutine-test/index.md) | Annotation for specifying a custom [CoroutineTestExtension.ScopeFactory](https://rbusarow.github.io/Dispatch/dispatch-test/dispatch.core.test/-coroutine-test-extension/-scope-factory/index.md) while extending a test class or function with [CoroutineTestExtension](https://rbusarow.github.io/Dispatch/dispatch-test/dispatch.core.test/-coroutine-test-extension/index.md).`annotation class CoroutineTest` |

### Functions

| Name | Summary |
|---|---|
| [coroutineTestExtension](coroutine-test-extension.md) | Factory function for creating a [CoroutineTestExtension](https://rbusarow.github.io/Dispatch/dispatch-test/dispatch.core.test/-coroutine-test-extension/index.md).`fun coroutineTestExtension(scopeFactory: () -> `[`TestProvidedCoroutineScope`](https://rbusarow.github.io/Dispatch/dispatch-test/dispatch.core.test/-test-provided-coroutine-scope/index.md)` = { TestProvidedCoroutineScope() }): `[`CoroutineTestExtension`](https://rbusarow.github.io/Dispatch/dispatch-test/dispatch.core.test/-coroutine-test-extension/index.md) |
