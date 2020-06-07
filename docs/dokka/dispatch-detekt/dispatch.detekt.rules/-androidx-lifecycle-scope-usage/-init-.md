[dispatch-detekt](../../index.md) / [dispatch.detekt.rules](../index.md) / [AndroidxLifecycleScopeUsage](index.md) / [&lt;init&gt;](./-init-.md)

# &lt;init&gt;

`AndroidxLifecycleScopeUsage(config: Config = Config.empty)`

Detects use of `androidx.lifecycle.lifecycleScope`,
which shares a namespace with `dispatch.android.lifecycle.lifecycleScope`.

The Androidx library uses a hard-coded `Dispatchers.Main` and does not contain a `DispatcherProvider`,
and also leaks its pausing behavior.

