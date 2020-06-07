[dispatch-detekt](../../index.md) / [dispatch.detekt.rules](../index.md) / [AndroidxLifecycleScopeUsage](./index.md)

# AndroidxLifecycleScopeUsage

`class AndroidxLifecycleScopeUsage : Rule` [(source)](https://github.com/RBusarow/Dispatch/tree/master/dispatch-detekt/src/main/java/dispatch/detekt/rules/AndroidxLifecycleScopeUsage.kt#L29)

Detects use of `androidx.lifecycle.lifecycleScope`,
which shares a namespace with `dispatch.android.lifecycle.lifecycleScope`.

The Androidx library uses a hard-coded `Dispatchers.Main` and does not contain a `DispatcherProvider`,
and also leaks its pausing behavior.

### Constructors

| Name | Summary |
|---|---|
| [&lt;init&gt;](-init-.md) | Detects use of `androidx.lifecycle.lifecycleScope`, which shares a namespace with `dispatch.android.lifecycle.lifecycleScope`.`AndroidxLifecycleScopeUsage(config: Config = Config.empty)` |
