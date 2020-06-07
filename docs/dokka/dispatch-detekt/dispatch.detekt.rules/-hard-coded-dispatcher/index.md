[dispatch-detekt](../../index.md) / [dispatch.detekt.rules](../index.md) / [HardCodedDispatcher](./index.md)

# HardCodedDispatcher

`class HardCodedDispatcher : Rule` [(source)](https://github.com/RBusarow/Dispatch/tree/master/dispatch-detekt/src/main/java/dispatch/detekt/rules/HardCodedDispatcher.kt#L41)

Detects use of a hard-coded reference to the `kotlinx.coroutines.Dispatchers` singleton.

The `CoroutineDispatcher`'s in this singleton do not contain a `DispatcherProvider`,
so they're unaffected by this library.

## Config

* allowDefaultDispatcher
* allowIODispatcher
* allowMainDispatcher
* allowMainImmediateDispatcher
* allowUnconfinedDispatcher

### Constructors

| Name | Summary |
|---|---|
| [&lt;init&gt;](-init-.md) | Detects use of a hard-coded reference to the `kotlinx.coroutines.Dispatchers` singleton.`HardCodedDispatcher(config: Config = Config.empty)` |
