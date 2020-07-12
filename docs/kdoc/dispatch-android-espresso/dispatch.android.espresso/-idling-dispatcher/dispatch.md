[dispatch-android-espresso](../../index.md) / [dispatch.android.espresso](../index.md) / [IdlingDispatcher](index.md) / [dispatch](./dispatch.md)

# dispatch

`fun dispatch(context: `[`CoroutineContext`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.coroutines/-coroutine-context/index.html)`, block: `[`Runnable`](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/-runnable.html)`): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html) [(source)](https://github.com/RBusarow/Dispatch/tree/master/dispatch-android-espresso/src/main/java/dispatch/android/espresso/IdlingDispatcher.kt#L53)

Counting implementation of the [dispatch](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/-coroutine-dispatcher/dispatch.html) function.

The count is incremented for every dispatch, and decremented for every completion, including suspension.
