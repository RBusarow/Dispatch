package com.rickbusarow.dispatcherprovidersample

import android.annotation.*
import android.os.*
import androidx.appcompat.app.*
import androidx.lifecycle.*
import com.rickbusarow.dispatcherprovider.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import timber.log.*

@ExperimentalCoroutinesApi
@SuppressLint("SetTextI18n")
class MainActivity : AppCompatActivity() {

  val scope = MainCoroutineScope()

  private val factory = viewModelFactory {
    MainViewModel(DefaultCoroutineScope(), SomeRepository(IOCoroutineScope()))
  }

  val viewModel: MainViewModel by lazy {
    ViewModelProviders.of(this, factory)[factory.viewModelClass]
  }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)

    Timber.plant(Timber.DebugTree())

    viewModel.message
      .onEach { Timber.v("I'm using the default dispatcher!") }
      // extract the default dispatcher from the CoroutineScope and apply it upstream
      .flowOn(scope.defaultDispatcher)
      .onEach { message ->
        Timber.v("I'm using the main dispatcher!")
        tvMessage.text = message
      }
      .onCompletion { tvMessage.text = "All done!" }
      // extract the main dispatcher from the CoroutineScope and apply it upstream for the UI updates
      .flowOn(scope.mainDispatcher)
      .launchIn(scope)

  }
}

