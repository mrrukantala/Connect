package com.example.connect.main.ui.product.tabLayout.myproduct

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.connect.R
import com.example.connect.databinding.MyProductFragmentBinding
import com.example.connect.main.ui.product.DashboardFragmentDirections

class MyProductFragment : Fragment() {

    lateinit var binding: MyProductFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(inflater, R.layout.my_product_fragment, container, false)
        binding.lifecycleOwner = this

        val token = requireActivity()
            .getSharedPreferences("my_data_pref", Context.MODE_PRIVATE)
            .getString("token", "").toString()
        val id = requireActivity()
            .getSharedPreferences("my_data_pref", Context.MODE_PRIVATE)
            .getInt("id", -1)

        val application = requireNotNull(activity).application
        val factory =
            MyProductViewModelFactory(
                token,
                id,
                application
            )


        val viewModel = ViewModelProvider(this, factory).get(MyProductViewModel::class.java)
        binding.viewModel = viewModel

        binding.apply {
            viewModel.properties.observe(viewLifecycleOwner, {
                if(null != it){
                    binding.empty.visibility = View.GONE
                }
            })
        }
        binding.recyclerViewMyProduk.adapter = Adapter(
            Adapter.OnClickListener {
                viewModel.displayNewsDetails(it)
            }
        )

        binding.fabNews.setOnClickListener {
            viewModel.wa.observe(viewLifecycleOwner, {
                if(null != it){
                    findNavController().navigate(DashboardFragmentDirections.actionDashboardFragmentToAddMyProdukFragment())
                } else if(it == "" && it == " "){
                    Toast.makeText(requireContext(), "Maaf Anda harus mengisi no wa dulu", Toast.LENGTH_LONG).show()
                } else{
                    Toast.makeText(requireContext(), "Maaf Anda harus mengisi no wa dulu", Toast.LENGTH_LONG).show()
                }
            })


        }

        viewModel.navigatedToSelectedNews.observe(viewLifecycleOwner, {
            if (null != it) {
                this.findNavController().navigate(
                    DashboardFragmentDirections.actionDashboardFragmentToDetailProductFragment(it)
                )
                viewModel.displayNewsDetailsCompelete()
            }
        })

        return binding.root
    }



}