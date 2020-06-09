

## Contents

### All Types

| Name | Summary |
|---|---|
|

##### [dispatch.test.CoroutineTest](../dispatch.test/-coroutine-test/index.md)

Annotation for specifying a custom [CoroutineTestExtension.ScopeFactory](https://rbusarow.github.io/Dispatch/dispatch-test/dispatch.test/-coroutine-test-extension/-scope-factory/index.md) while
extending a test class or function with [CoroutineTestExtension](https://rbusarow.github.io/Dispatch/dispatch-test/dispatch.test/-coroutine-test-extension/index.md).


|

##### [dispatch.test.CoroutineTestExtension](../dispatch.test/-coroutine-test-extension/index.md)

JUnit 5 [ParameterResolver](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.io/java.io.-file/extension.html)[Extension](https://junit.org/junit5/docs/current/api/org/junit/jupiter/api/extension/Extension.html) for injecting and managing a [TestProvidedCoroutineScope](https://rbusarow.github.io/Dispatch/dispatch-test/dispatch.test/-test-provided-coroutine-scope/index.md) in a test instance.
This creates a new instance of [TestProvidedCoroutineScope](https://rbusarow.github.io/Dispatch/dispatch-test/dispatch.test/-test-provided-coroutine-scope/index.md) each time the scope is injected, optionally using a custom [ScopeFactory](https://rbusarow.github.io/Dispatch/dispatch-test/dispatch.test/-coroutine-test-extension/-scope-factory/index.md).


