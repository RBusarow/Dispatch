[dispatch-detekt](../../index.md) / [dispatch.detekt.rules](../index.md) / [AndroidXLifecycleScope](./index.md)

# AndroidXLifecycleScope

`class AndroidXLifecycleScope : Rule` [(source)](https://github.com/RBusarow/Dispatch/tree/master/dispatch-detekt/src/main/java/dispatch/detekt/rules/AndroidXLifecycleScope.kt#L29)

Detects use of `androidx.lifecycle.lifecycleScope`,
which shares a namespace with `dispatch.android.lifecycle.lifecycleScope`.

The AndroidX library uses a hard-coded `Dispatchers.Main` and does not contain a `DispatcherProvider`,
and also leaks its pausing behavior.

### Constructors

| Name | Summary |
|---|---|
| [&lt;init&gt;](-init-.md) | Detects use of `androidx.lifecycle.lifecycleScope`, which shares a namespace with `dispatch.android.lifecycle.lifecycleScope`.`AndroidXLifecycleScope(config: Config = Config.empty)` |
