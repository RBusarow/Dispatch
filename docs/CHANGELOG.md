# Change log for Dispatch

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
