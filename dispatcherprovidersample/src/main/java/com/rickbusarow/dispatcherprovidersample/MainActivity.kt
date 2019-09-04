package com.rickbusarow.dispatcherprovidersample

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import com.rickbusarow.dispatcherprovider.DefaultCoroutineScope
import com.rickbusarow.dispatcherprovider.IOCoroutineScope
import com.rickbusarow.dispatcherprovider.MainCoroutineScope
import com.rickbusarow.dispatcherprovider.withMain
import kotlinx.android.synthetic.main.activity_main.*

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

    tvMessage.text = "Get ready..."

    viewModel.message.observe(scope) { message ->
      withMain { tvMessage.text = message }
    }
  }
}

