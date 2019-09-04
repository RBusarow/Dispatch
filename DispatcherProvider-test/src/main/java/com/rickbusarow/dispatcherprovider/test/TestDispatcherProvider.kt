package com.rickbusarow.dispatcherprovider.test

import com.rickbusarow.dispatcherprovider.DispatcherProvider
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher

@ExperimentalCoroutinesApi
class TestDispatcherProvider(
  override val default: TestCoroutineDispatcher = TestCoroutineDispatcher(),
  override val io: TestCoroutineDispatcher = TestCoroutineDispatcher(),
  override val main: TestCoroutineDispatcher = TestCoroutineDispatcher(),
  override val mainImmediate: TestCoroutineDispatcher = TestCoroutineDispatcher(),
  override val unconfined: TestCoroutineDispatcher = TestCoroutineDispatcher()
) : DispatcherProvider

@ExperimentalCoroutinesApi
fun TestDispatcherProvider(dispatcher: TestCoroutineDispatcher): TestDispatcherProvider =
  TestDispatcherProvider(
    default = dispatcher,
    io = dispatcher,
    main = dispatcher,
    mainImmediate = dispatcher,
    unconfined = dispatcher
  )

