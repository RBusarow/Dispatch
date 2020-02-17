[android-espresso](../../index.md) / [dispatch.android.espresso](../index.md) / [IdlingDispatcher](index.md) / [&lt;init&gt;](./-init-.md)

# &lt;init&gt;

`IdlingDispatcher(delegate: `[`CoroutineDispatcher`](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/-coroutine-dispatcher/index.html)`)`

[IdlingResource](#) helper for coroutines.  This class simply wraps a delegate [CoroutineDispatcher](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/-coroutine-dispatcher/index.html)
and keeps a running count of all coroutines it creates, decrementing the count when they complete.

### Parameters

`delegate` - The [CoroutineDispatcher](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/-coroutine-dispatcher/index.html) which will be used for actual dispatch.

**See Also**

[IdlingResource](#)

[Espresso](#)

[CoroutineDispatcher](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/-coroutine-dispatcher/index.html)

