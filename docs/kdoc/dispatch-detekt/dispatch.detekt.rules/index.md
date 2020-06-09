[dispatch-detekt](../index.md) / [dispatch.detekt.rules](./index.md)

## Package dispatch.detekt.rules

### Types

| Name | Summary |
|---|---|
| [AndroidXLifecycleScope](-android-x-lifecycle-scope/index.md) | Detects use of `androidx.lifecycle.lifecycleScope`, which shares a namespace with `dispatch.android.lifecycle.lifecycleScope`.`class AndroidXLifecycleScope : Rule` |
| [HardCodedDispatcher](-hard-coded-dispatcher/index.md) | Detects use of a hard-coded reference to the `kotlinx.coroutines.Dispatchers` singleton.`class HardCodedDispatcher : Rule` |
