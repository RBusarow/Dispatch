# Change log for Dispatch

## Version 1.0.0-beta03

### Breaking changes

* Maven coordinates for existing modules have changed, and several new ones have been added.
* Base package names have been updated.

### New artifacts

* [dispatch-android-espresso] adds support for [IdlingResource] support in Espresso testing.
* [dispatch-android-lifecycle] adds an alternative to the Androidx [LifecycleScope] functionality, with lots of configuration options.
  * [dispatch-android-lifecycle-extensions] adds the [lifecycleScope] extension property, but with a configurable factory (useful for Espresso testing).
* [dispatch-android-viewmodel] adds an alternative to Androidx [viewModelScope] with configuration options.
* [dispatch-detekt] adds [Detekt] rules to warn against accidentally using a hard-coded CoroutineDispatcher.
* [dispatch-test-junit4] adds a JUnit4 Rule for automatically providing and cleaning up a [TestProvidedCoroutineScope].
* [dispatch-test-junit5] adds JUnit5 Extension support for automatically providing and cleaning up a [TestProvidedCoroutineScope].

## Version 1.0.0-beta02

### Test features

* Added `TestBasicDispatcherProvider` factory which uses `CommonPool` for `default` and `io`, but a shared single-threaded `ExecutorCoroutineDispatcher` for `main` and `mainImmediate` to provide "natural" dispatch behavior in tests without `Dispatchers.setMain(...)`.

### Bug fixes and improvements

* `runBlockingTestProvided` now uses the same `TestCoroutineDispatcher` as its `ContinuationInterceptor` and in its `TestDispatcherProvider` (#15).
* `runBlockingProvided` now uses `TestBasicDispatcherProvider` as its `DispatcherProvider`.

## Version 1.0.0-beta01

### Flow

* Add non-suspending `flowOn___()` operators for the `Flow` api.

### Misc

* Lots of Kdocs.
* Maven artifacts.
* Lower JDK version to 1.6

<!--- MODULE dispatch-core-->
<!--- INDEX  -->

[DispatcherProvider]: https://rbusarow.github.io/Dispatch/dispatch-core//dispatch.core/-dispatcher-provider/index.html
[launchDefault]: https://rbusarow.github.io/Dispatch/dispatch-core//dispatch.core/kotlinx.coroutines.-coroutine-scope/launch-default.html
[launchIO]: https://rbusarow.github.io/Dispatch/dispatch-core//dispatch.core/kotlinx.coroutines.-coroutine-scope/launch-i-o.html
[launchMain]: https://rbusarow.github.io/Dispatch/dispatch-core//dispatch.core/kotlinx.coroutines.-coroutine-scope/launch-main.html
[launchMainImmediate]: https://rbusarow.github.io/Dispatch/dispatch-core//dispatch.core/kotlinx.coroutines.-coroutine-scope/launch-main-immediate.html
[launchUnconfined]: https://rbusarow.github.io/Dispatch/dispatch-core//dispatch.core/kotlinx.coroutines.-coroutine-scope/launch-unconfined.html
[asyncDefault]: https://rbusarow.github.io/Dispatch/dispatch-core//dispatch.core/kotlinx.coroutines.-coroutine-scope/async-default.html
[asyncIO]: https://rbusarow.github.io/Dispatch/dispatch-core//dispatch.core/kotlinx.coroutines.-coroutine-scope/async-i-o.html
[asyncMain]: https://rbusarow.github.io/Dispatch/dispatch-core//dispatch.core/kotlinx.coroutines.-coroutine-scope/async-main.html
[asyncMainImmediate]: https://rbusarow.github.io/Dispatch/dispatch-core//dispatch.core/kotlinx.coroutines.-coroutine-scope/async-main-immediate.html
[asyncUnconfined]: https://rbusarow.github.io/Dispatch/dispatch-core//dispatch.core/kotlinx.coroutines.-coroutine-scope/async-unconfined.html
[withDefault]: https://rbusarow.github.io/Dispatch/dispatch-core//dispatch.core/with-default.html
[withIO]: https://rbusarow.github.io/Dispatch/dispatch-core//dispatch.core/with-i-o.html
[withMain]: https://rbusarow.github.io/Dispatch/dispatch-core//dispatch.core/with-main.html
[withMainImmediate]: https://rbusarow.github.io/Dispatch/dispatch-core//dispatch.core/with-main-immediate.html
[withUnconfined]: https://rbusarow.github.io/Dispatch/dispatch-core//dispatch.core/with-unconfined.html
[flowOnDefault]: https://rbusarow.github.io/Dispatch/dispatch-core//dispatch.core/kotlinx.coroutines.flow.-flow/flow-on-default.html
[flowOnIO]: https://rbusarow.github.io/Dispatch/dispatch-core//dispatch.core/kotlinx.coroutines.flow.-flow/flow-on-i-o.html
[flowOnMain]: https://rbusarow.github.io/Dispatch/dispatch-core//dispatch.core/kotlinx.coroutines.flow.-flow/flow-on-main.html
[flowOnMainImmediate]: https://rbusarow.github.io/Dispatch/dispatch-core//dispatch.core/kotlinx.coroutines.flow.-flow/flow-on-main-immediate.html
[flowOnUnconfined]: https://rbusarow.github.io/Dispatch/dispatch-core//dispatch.core/kotlinx.coroutines.flow.-flow/flow-on-unconfined.html
[DefaultCoroutineScope]: https://rbusarow.github.io/Dispatch/dispatch-core//dispatch.core/-default-coroutine-scope.html
[IOCoroutineScope]: https://rbusarow.github.io/Dispatch/dispatch-core//dispatch.core/-i-o-coroutine-scope.html
[MainCoroutineScope]: https://rbusarow.github.io/Dispatch/dispatch-core//dispatch.core/-main-coroutine-scope.html
[MainImmediateCoroutineScope]: https://rbusarow.github.io/Dispatch/dispatch-core//dispatch.core/-main-immediate-coroutine-scope.html
[UnconfinedCoroutineScope]: https://rbusarow.github.io/Dispatch/dispatch-core//dispatch.core/-unconfined-coroutine-scope.html

<!--- MODULE dispatch-test-->
<!--- INDEX  -->

[TestDispatcherProvider]: https://rbusarow.github.io/Dispatch/dispatch-test//dispatch.core.test/-test-dispatcher-provider/index.html
[TestProvidedCoroutineScope]: https://rbusarow.github.io/Dispatch/dispatch-test//dispatch.core.test/-test-provided-coroutine-scope/index.html
[runBlockingProvided]: https://rbusarow.github.io/Dispatch/dispatch-test//dispatch.core.test/run-blocking-provided.html
[testProvided]: https://rbusarow.github.io/Dispatch/dispatch-test//dispatch.core.test/test-provided.html

<!--- MODULE dispatch-test-junit4-->
<!--- INDEX  -->

[TestCoroutineRule]: https://rbusarow.github.io/Dispatch/dispatch-test-junit4//dispatch.core.test/-test-coroutine-rule/index.html

<!--- MODULE dispatch-test-junit5-->
<!--- INDEX  -->

[CoroutineTest]: https://rbusarow.github.io/Dispatch/dispatch-test-junit5//dispatch.core.test/-coroutine-test/index.html
[CoroutineTestExtension]: https://rbusarow.github.io/Dispatch/dispatch-test-junit5//dispatch.core.test/-coroutine-test-extension/index.html

<!--- MODULE dispatch-android-espresso-->
<!--- INDEX  -->

[IdlingDispatcher]: https://rbusarow.github.io/Dispatch/dispatch-android-espresso//dispatch.android.espresso/-idling-dispatcher/index.html
[IdlingDispatcherProvider]: https://rbusarow.github.io/Dispatch/dispatch-android-espresso//dispatch.android.espresso/-idling-dispatcher-provider/index.html

<!--- MODULE dispatch-android-lifecycle-->
<!--- INDEX  -->

[LifecycleCoroutineScope]: https://rbusarow.github.io/Dispatch/dispatch-android-lifecycle//dispatch.android.lifecycle/-lifecycle-coroutine-scope/index.html
[launchOnCreate]: https://rbusarow.github.io/Dispatch/dispatch-android-lifecycle//dispatch.android.lifecycle/-lifecycle-coroutine-scope/launch-on-create.html
[launchOnStart]: https://rbusarow.github.io/Dispatch/dispatch-android-lifecycle//dispatch.android.lifecycle/-lifecycle-coroutine-scope/launch-on-start.html
[launchOnResume]: https://rbusarow.github.io/Dispatch/dispatch-android-lifecycle//dispatch.android.lifecycle/-lifecycle-coroutine-scope/launch-on-resume.html
[onNextCreate]: https://rbusarow.github.io/Dispatch/dispatch-android-lifecycle//dispatch.android.lifecycle/androidx.lifecycle.-lifecycle-owner/on-next-create.html
[onNextStart]: https://rbusarow.github.io/Dispatch/dispatch-android-lifecycle//dispatch.android.lifecycle/androidx.lifecycle.-lifecycle-owner/on-next-start.html
[onNextResume]: https://rbusarow.github.io/Dispatch/dispatch-android-lifecycle//dispatch.android.lifecycle/androidx.lifecycle.-lifecycle-owner/on-next-resume.html

<!--- MODULE dispatch-android-lifecycle-extensions-->
<!--- INDEX  -->

[lifecycleScope]: https://rbusarow.github.io/Dispatch/dispatch-android-lifecycle-extensions//dispatch.android.lifecycle/androidx.lifecycle.-lifecycle-owner/lifecycle-scope.html

<!--- MODULE dispatch-android-viewmodel-->
<!--- INDEX  -->

[CoroutineViewModel]: https://rbusarow.github.io/Dispatch/dispatch-android-viewmodel//dispatch.android.lifecycle/-coroutine-view-model/index.html
[viewModelScope]: https://rbusarow.github.io/Dispatch/dispatch-android-viewmodel//dispatch.android.lifecycle/-coroutine-view-model/view-model-scope.html

<!--- END -->

[Channel]: https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines.channels/-channel/
[context_preservation]: https://medium.com/@elizarov/execution-context-of-kotlin-flows-b8c151c9309b
[ContinuationInterceptor]: https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.coroutines.experimental/-continuation-interceptor/index.html
[CoroutineContext.Element]: https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.coroutines.experimental/-coroutine-context/index.html#types
[CoroutineContext.Key]: https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.coroutines.experimental/-coroutine-context/index.html#types
[CoroutineContext]: https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.coroutines/-coroutine-context/
[CoroutineDispatcher]: https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/-coroutine-dispatcher/index.html
[CoroutineScope]: https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/coroutine-scope.html
[Deferred]: https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/-deferred/index.html
[dispatch-android-espresso]: https://rbusarow.github.io/Dispatch/android-espresso//index.html
[dispatch-android-lifecycle-extensions]: https://rbusarow.github.io/Dispatch/android-lifecycle-extensions//index.html
[dispatch-android-lifecycle]: https://rbusarow.github.io/Dispatch/android-lifecycle//index.html
[dispatch-android-viewmodel]: https://rbusarow.github.io/Dispatch/android-lifecycle-viewmodel//index.html
[dispatch-core-test-junit4]: https://rbusarow.github.io/Dispatch/core-test-junit4//index.html
[dispatch-core-test-junit5]: https://rbusarow.github.io/Dispatch/core-test-junit5//index.html
[dispatch-core-test]: https://rbusarow.github.io/Dispatch/core-test//index.html
[dispatch-core]: https://rbusarow.github.io/Dispatch/core//index.html
[dispatch-detekt]: https://rbusarow.github.io/Dispatch/dispatch-detekt//index.html
[Dispatchers.Default]: https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/-dispatchers/-default.html
[Dispatchers.IO]: https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/-dispatchers/-io.html
[Dispatchers.Main.immediate]: https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/-main-coroutine-dispatcher/immediate.html
[Dispatchers.Main]: https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/-dispatchers/-main.html
[Dispatchers.Unconfined]: https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/-dispatchers/-unconfined.html
[Dispatchers]: https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/-dispatchers/index.html
[Detekt]: https://github.com/detekt/detekt
[Flow]: https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines.flow/-flow/index.html
[Job]: https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/-job/index.html
[kotlin.coroutineContext]: https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.coroutines/coroutine-context.html
[kotlin.coroutines]: https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.coroutines/index.html
[kotlinx.coroutines]: https://kotlin.github.io/kotlinx.coroutines/
[runBlocking]: https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/run-blocking.html
[kotlinx.runBlockingTest]: https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-test/kotlinx.coroutines.test/run-blocking-test.html
[TestCoroutineScope]: https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-test/kotlinx.coroutines.test/-test-coroutine-scope/index.html
[withContext]: https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/with-context.html
