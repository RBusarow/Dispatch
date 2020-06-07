# Change log for Dispatch

## Version 1.0.0-beta03

### Renames

* The project has been renamed from DispatcherProvider to Dispatch.
* Maven coordinates for existing modules have changed.
* The base Maven coordinate has changed from `com.rickbusarow.DispatcherProvider` to `com.rickbusarow.dispatch`.
* Base package names have been updated.

### New artifacts

* [dispatch-android-espresso] adds support for [IdlingResource] dispatchers in Espresso testing.
* [dispatch-android-lifecycle] adds an alternative to the Androidx [LifecycleScope][androidx-lifecycleScope] functionality, with lots of configuration options.
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
<!--- MODULE dispatch-test-->
<!--- INDEX  -->
[TestProvidedCoroutineScope]: https://rbusarow.github.io/Dispatch/dispatch-test//dispatch.core.test/-test-provided-coroutine-scope/index.html
<!--- MODULE dispatch-test-junit4-->
<!--- INDEX  -->
<!--- MODULE dispatch-test-junit5-->
<!--- INDEX  -->
<!--- MODULE dispatch-android-espresso-->
<!--- INDEX  -->
<!--- MODULE dispatch-android-lifecycle-->
<!--- INDEX  -->
<!--- MODULE dispatch-android-lifecycle-extensions-->
<!--- INDEX  -->
[lifecycleScope]: https://rbusarow.github.io/Dispatch/dispatch-android-lifecycle-extensions//dispatch.android.lifecycle/androidx.lifecycle.-lifecycle-owner/lifecycle-scope.html
<!--- MODULE dispatch-android-viewmodel-->
<!--- INDEX  -->
[viewModelScope]: https://rbusarow.github.io/Dispatch/dispatch-android-viewmodel//dispatch.android.viewmodel/-coroutine-view-model/view-model-scope.html
<!--- END -->

[androidx-lifecycleScope]: https://cs.android.com/androidx/platform/frameworks/support/+/androidx-master-dev:lifecycle/lifecycle-runtime-ktx/src/main/java/androidx/lifecycle/Lifecycle.kt;l=44
[Detekt]: https://github.com/detekt/detekt
[dispatch-android-espresso]: https://rbusarow.github.io/Dispatch/android-espresso//index.html
[dispatch-android-lifecycle-extensions]: https://rbusarow.github.io/Dispatch/android-lifecycle-extensions//index.html
[dispatch-android-lifecycle]: https://rbusarow.github.io/Dispatch/android-lifecycle//index.html
[dispatch-android-viewmodel]: https://rbusarow.github.io/Dispatch/android-lifecycle-viewmodel//index.html
[dispatch-detekt]: https://rbusarow.github.io/Dispatch/dispatch-detekt//index.html
[dispatch-test-junit4]: https://rbusarow.github.io/Dispatch/core-test-junit4//index.html
[dispatch-test-junit5]: https://rbusarow.github.io/Dispatch/core-test-junit5//index.html
[IdlingResource]: https://developer.android.com/training/testing/espresso/idling-resource
