package com.example.connect.presentation.main.menu.info_pendidikan.pendidikan

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.findNavController
import com.example.connect.R
import com.example.connect.databinding.PendidikanUserFragmentBinding
import com.example.connect.presentation.main.menu.info_pendidikan.ContainerInfoDirections
import com.example.connect.presentation.main.menu.info_pendidikan.info.InfoUserViewModel
import com.example.connect.presentation.main.menu.info_pendidikan.info.InfoUserViewModelState
import com.kennyc.view.MultiStateView
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class PendidikanUserFragment : Fragment() {

    lateinit var binding: PendidikanUserFragmentBinding

    private val viewModel: InfoUserViewModel by activityViewModels()

    private val mainNavigation : NavController? by lazy{
        activity?.findNavController(R.id.nav_host_fragment_menu)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding =
            PendidikanUserFragmentBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel

        binding.fabNews.setOnClickListener {

        }



        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getProfile(52)
        observe()

        binding.rv.adapter = PendidikanAdapter(

            PendidikanAdapter.OnclickListener {
                runCatching {
                    mainNavigation?.navigate(
                        ContainerInfoDirections.actionContainerInfoPendidikanFragmentToFormPendidikanFragment(
                            it,
                            1
                        )
                    )
                }
            }
        )

//        binding.fabNews.setOnClickListener {
//            Log.v("CLICK", "click")
//            mainNavigation?.navigate(
//                ContainerInfoDirections.actionContainerInfoPendidikanFragmentToFormPendidikanFragment(
//                    viewModel.data.value?.get(0)?.listPendidikan?.get(0),
//                    2
//                )
//            )
//        }


    }

    private fun observe() {
        viewModel.state.flowWithLifecycle(lifecycle)
            .onEach { state -> handleState(state) }
    }

    private fun handleState(state: InfoUserViewModelState) {

        when (state) {
            is InfoUserViewModelState.Loading -> {
                binding.msvPendidikan.viewState = MultiStateView.ViewState.LOADING
            }
            is InfoUserViewModelState.Success -> {
//                Log.v("PENDIDIKAN", state.infoUserEntity.get(0).listPendidikan.toString())

                binding.msvPendidikan.viewState =
                    if (state.infoUserEntity.isEmpty()) MultiStateView.ViewState.EMPTY
                    else MultiStateView.ViewState.CONTENT


            }

            else -> {}
        }


    }
}