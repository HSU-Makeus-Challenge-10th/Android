package com.example.week02.home

import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.week02.R
import com.example.week02.databinding.FragmentHomeBinding
import com.example.week02.shop.DetailFragment
import com.example.week02.shop.ProductViewModel
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale


class HomeFragment : Fragment(R.layout.fragment_home) {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val productViewModel: ProductViewModel by activityViewModels()
    private lateinit var adapter: HomeProductAdapter

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
        adapter = HomeProductAdapter { item ->
            findNavController().navigate(
                R.id.detailFragment,
                DetailFragment.bundle(item.id)
            )
        }
        binding.rvLatestProducts.adapter = adapter

        // 최신 정보 5개만 가지고옴
        productViewModel.latestProducts.observe(viewLifecycleOwner) { products ->
            adapter.updateItems(products)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
