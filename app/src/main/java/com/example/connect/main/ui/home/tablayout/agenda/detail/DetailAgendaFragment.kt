package com.example.connect.main.ui.home.tablayout.agenda.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.connect.R
import com.example.connect.databinding.DetailAgendaFragmentBinding
import com.example.connect.main.ui.home.tablayout.news.detail.DetailNewsFragmentDirections

class DetailAgendaFragment : Fragment() {

    lateinit var binding: DetailAgendaFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding =
            DataBindingUtil.inflate(inflater, R.layout.detail_agenda_fragment, container, false)
        binding.lifecycleOwner = this

        val selected = DetailAgendaFragmentArgs.fromBundle(requireArguments()).selectedAgenda
        val viewModelFactory = DetailViewModelFactory(
            selected,
            requireNotNull(activity).application
        )

        binding.agenda =
            ViewModelProvider(this, viewModelFactory).get(DetailAgendaViewModel::class.java)

        binding.include6.backImage.setOnClickListener {
            findNavController().popBackStack()
        }

        binding.cardView.setOnClickListener {
            findNavController().navigate(
                DetailAgendaFragmentDirections.actionDetailAgendaFragmentToImageOpener(selected.photo)
            )
        }
        return binding.root
    }


}