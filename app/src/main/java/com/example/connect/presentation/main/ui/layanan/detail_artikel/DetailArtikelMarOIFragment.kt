package com.example.connect.presentation.main.ui.layanan.detail_artikel

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.connect.R
import com.example.connect.databinding.DetailArtikelMarOIFragmentBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class DetailArtikelMarOIFragment : Fragment() {

    lateinit var binding: DetailArtikelMarOIFragmentBinding
    private val viewModel: DetailArtikelMarOlViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = DetailArtikelMarOIFragmentBinding.inflate(
            inflater,
            container,
            false
        )
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel

//        val data = DetailArtikelMarOIFragmentArgs.fromBundle(requireArguments()).selectedArtikelMarkOI

//        val viewModelFactory = DetailArtikelMarkOIViewModelFactory(
//            data,
//            requireNotNull(activity).application
//        )

//        binding.viewModel = ViewModelProvider(this, viewModelFactory).get(DetailArtikelMarOIViewModel::class.java)

        binding.include4.backImage.setOnClickListener {
            findNavController().popBackStack()
        }

        binding.cardView8.setOnClickListener {

        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.detailLayanan(8)
        observe()
    }

    private fun observe() {
        viewModel.state.flowWithLifecycle(lifecycle)
            .onEach { state -> handleState(state) }
            .launchIn(lifecycleScope)
    }

    private fun handleState(state: DetailArtikelMarOlState) {
        when(state){
            is DetailArtikelMarOlState.Loading ->{}
            is DetailArtikelMarOlState.Success ->{}
            else -> {}
        }
    }


}