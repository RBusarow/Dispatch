[dispatch-detekt](../../index.md) / [dispatch.detekt.rules](../index.md) / [AndroidXLifecycleScope](index.md) / [&lt;init&gt;](./-init-.md)

# &lt;init&gt;

`AndroidXLifecycleScope(config: Config = Config.empty)`

Detects use of `androidx.lifecycle.lifecycleScope`,
which shares a namespace with `dispatch.android.lifecycle.lifecycleScope`.

The AndroidX library uses a hard-coded `Dispatchers.Main` and does not contain a `DispatcherProvider`,
and also leaks its pausing behavior.

