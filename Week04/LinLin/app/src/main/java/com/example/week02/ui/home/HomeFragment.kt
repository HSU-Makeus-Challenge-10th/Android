package com.example.week02.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.week02.data.ProductItem
import com.example.week02.data.ProductPreferencesRepository
import com.example.week02.databinding.FragmentHomeBinding
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private val homeItems = mutableListOf<ProductItem>()
    private var homeAdapter: HomeNewProductAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.tvDate.text = formatKoreanDateLine()
        binding.rvNewProducts.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        homeAdapter = HomeNewProductAdapter(homeItems)
        binding.rvNewProducts.adapter = homeAdapter
        val repo = ProductPreferencesRepository(requireContext())
        viewLifecycleOwner.lifecycleScope.launch {
            repo.ensureSeeded()
            repo.homeProductsFlow().collect { list ->
                homeItems.clear()
                homeItems.addAll(list)
                homeAdapter?.notifyDataSetChanged()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun formatKoreanDateLine(): String {
        val cal = Calendar.getInstance()
        val month = cal.get(Calendar.MONTH) + 1
        val day = cal.get(Calendar.DAY_OF_MONTH)
        val weekday = SimpleDateFormat("EEEE", Locale.KOREAN).format(cal.time)
        return "${month}월 ${day}일 $weekday"
    }
}
