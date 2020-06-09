[dispatch-detekt](../../index.md) / [dispatch.detekt.rules](../index.md) / [HardCodedDispatcher](index.md) / [&lt;init&gt;](./-init-.md)

# &lt;init&gt;

`HardCodedDispatcher(config: Config = Config.empty)`

Detects use of a hard-coded reference to the `kotlinx.coroutines.Dispatchers` singleton.

The `CoroutineDispatcher`'s in this singleton do not contain a `DispatcherProvider`,
so they're unaffected by this library.

## Config

* allowDefaultDispatcher
* allowIODispatcher
* allowMainDispatcher
* allowMainImmediateDispatcher
* allowUnconfinedDispatcher
