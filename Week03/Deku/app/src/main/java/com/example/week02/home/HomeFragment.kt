package com.example.week02.home

import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.week02.R
import com.example.week02.data.ItemData
import com.example.week02.databinding.FragmentHomeBinding
import com.example.week02.shop.DetailFragment
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale


class HomeFragment : Fragment(R.layout.fragment_home) {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding = FragmentHomeBinding.bind(view)

        binding.tvDate.text = LocalDate.now()
            .format(DateTimeFormatter.ofPattern("M월 d일 EEEE", Locale.KOREAN))

        binding.rvLatestProducts.layoutManager = LinearLayoutManager(
            requireContext(),
            LinearLayoutManager.HORIZONTAL,
            false
        )
        binding.rvLatestProducts.adapter = HomeProductAdapter(
            ItemData.latestProducts()
        ) { item ->
            findNavController().navigate(
                R.id.detailFragment,
                DetailFragment.bundle(item.id)
            )
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
