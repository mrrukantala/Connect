package com.example.connect.presentation.main.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.connect.R
import com.example.connect.databinding.FragmentHomeBinding
import com.example.connect.presentation.main.home.tablayout.agenda.AgendaFragment
import com.example.connect.presentation.main.home.tablayout.agenda.AgendaViewModel
import com.example.connect.presentation.main.home.tablayout.news.NewsFragment
import com.example.connect.presentation.main.home.tablayout.news.NewsViewModel
import com.example.connect.utilites.TabAdapter
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val viewModelNews: NewsViewModel by activityViewModels()
    private val viewModelAgenda: AgendaViewModel by activityViewModels()

    private val binding get() = _binding!!

    val name = arrayOf(
        "News",
        "Agendas"
    )

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val fragmentList = arrayListOf(
            NewsFragment(),
            AgendaFragment()
        )

        val tabLayout = binding.tabLayoutHome
        val viewPager = binding.viewPagerHome

        val adapter = TabAdapter(fragmentList, requireActivity().supportFragmentManager, lifecycle)

        viewPager.adapter = adapter


        TabLayoutMediator(
            tabLayout,
            viewPager
        ) { tab, position ->
            tab.text = name[position]
        }.attach()

        binding.include12.menu.setOnClickListener {
            val popupMenu = PopupMenu(requireContext(), it)
            popupMenu.menuInflater.inflate(R.menu.menu, popupMenu.menu)

            popupMenu.setOnMenuItemClickListener {
                val id = it.itemId

                if (id == R.id.mynews) {
                    findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToMyNewsFragment())
                } else if (id == R.id.myagenda) {
                    findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToMyAgendaFragment())
                }
                false

            }
            popupMenu.show()
        }

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModelNews.berita()
        viewModelAgenda.agenda()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}