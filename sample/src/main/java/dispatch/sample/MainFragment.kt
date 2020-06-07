/*
 * Copyright (C) 2020 Rick Busarow
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package dispatch.sample

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.createViewModelLazy
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import dispatch.core.*
import dispatch.sample.databinding.FragmentMainBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.plus
import timber.log.Timber

@ExperimentalCoroutinesApi
class MainFragment : Fragment() {

  val binding: FragmentMainBinding by lazy { FragmentMainBinding.inflate(layoutInflater) }

  val scope = MainCoroutineScope()

  private val factory = viewModelFactory {
    MainViewModel(DefaultCoroutineScope(), SomeRepository(IOCoroutineScope()))
  }
  val viewModel: MainViewModel by viewModels { factory }

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? = binding.root

  @Suppress("SetTextI18n", "HardCodedDispatcher")
  override fun onAttach(context: Context) {
    super.onAttach(context)

    // share the same Job as the `scope` property above, but use the IO dispatcher as default
    val ioDefaultScope = scope + Dispatchers.IO

    viewModel.message
      .onEach { Timber.v("I'm using the default dispatcher!") }
      // extract the default dispatcher from the CoroutineScope and apply it upstream
      .flowOn(scope.defaultDispatcher)
      .onEach { message ->
        Timber.v("I'm using the main dispatcher!")
        binding.tvMessage.text = message
      }
      .onCompletion { binding.tvMessage.text = "All done!" }
      // the .flowOn____() operator pulls the desired dispatcher out of the CoroutineScope
      // and applies it.  So in this case .flowOnMain() is pulling
      // the dispatcher assigned to "main" out of `ioDefaultScope`
      // and dispatching upstream execution
      .flowOnMain()
      .onEach { Timber.v("I'm using the IO dispatcher!") }
      // the default dispatcher in this scope is now Dispatchers.IO
      .launchIn(ioDefaultScope)
  }

}

@Suppress("UNCHECKED_CAST")
inline fun <reified VM : ViewModel> viewModelFactory(crossinline f: () -> VM):
    ViewModelProvider.Factory =
  object : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(aClass: Class<T>): T = f() as T
  }

inline fun <reified VM : ViewModel> Fragment.viewModels(
  noinline ownerProducer: () -> ViewModelStoreOwner = { this },
  noinline factoryProducer: (() -> ViewModelProvider.Factory)? = null
) = createViewModelLazy(VM::class, { ownerProducer().viewModelStore }, factoryProducer)
