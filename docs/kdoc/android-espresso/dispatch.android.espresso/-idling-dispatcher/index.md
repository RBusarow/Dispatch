[android-espresso](../../index.md) / [dispatch.android.espresso](../index.md) / [IdlingDispatcher](./index.md)

# IdlingDispatcher

`class IdlingDispatcher : `[`CoroutineDispatcher`](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/-coroutine-dispatcher/index.html) [(source)](https://github.com/RBusarow/Dispatch/tree/master/android-espresso/src/main/java/dispatch/android/espresso/IdlingDispatcher.kt#L32)

[IdlingResource](#) helper for coroutines.  This class simply wraps a delegate [CoroutineDispatcher](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/-coroutine-dispatcher/index.html)
and keeps a running count of all coroutines it creates, decrementing the count when they complete.

### Parameters

`delegate` - The [CoroutineDispatcher](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/-coroutine-dispatcher/index.html) which will be used for actual dispatch.

**See Also**

[IdlingResource](#)

[Espresso](#)

[CoroutineDispatcher](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/-coroutine-dispatcher/index.html)

### Constructors

| Name | Summary |
|---|---|
| [&lt;init&gt;](-init-.md) | [IdlingResource](#) helper for coroutines.  This class simply wraps a delegate [CoroutineDispatcher](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/-coroutine-dispatcher/index.html) and keeps a running count of all coroutines it creates, decrementing the count when they complete.`IdlingDispatcher(delegate: `[`CoroutineDispatcher`](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/-coroutine-dispatcher/index.html)`)` |

### Properties

| Name | Summary |
|---|---|
| [counter](counter.md) | The [CountingIdlingResource](#) which is responsible for [Espresso](#) functionality.`val counter: CountingIdlingResource` |

### Functions

| Name | Summary |
|---|---|
| [dispatch](dispatch.md) | Counting implementation of the [dispatch](#) function.`fun dispatch(context: `[`CoroutineContext`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.coroutines/-coroutine-context/index.html)`, block: `[`Runnable`](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/-runnable.html)`): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html) |
| [isIdle](is-idle.md) | `fun isIdle(): `[`Boolean`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) |
