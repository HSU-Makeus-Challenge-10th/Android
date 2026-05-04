package com.example.yido.ui.shop

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.example.yido.R
import com.example.yido.databinding.FragmentShopBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ShopFragment : Fragment() {

    private var _binding: FragmentShopBinding? = null
    private val binding get() = _binding!!
    private val viewModel: ShopViewModel by viewModels()
    private lateinit var adapter: ShopProductAdapter

    companion object { private const val TAG = "LIFE_QUIZ" }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        Log.d(TAG, "ShopFragment : onCreateView")
        _binding = FragmentShopBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d(TAG, "ShopFragment : onViewCreated")

        adapter = ShopProductAdapter(mutableListOf()) { product ->
            viewModel.toggleWish(product)
        }
        binding.shopRvProducts.adapter = adapter
        binding.shopRvProducts.layoutManager = GridLayoutManager(requireContext(), 2)

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.uiState.collect { state ->
                adapter.updateList(state.filteredProducts.toMutableList())
                updateTabUI(state.currentTabIndex)
            }
        }

        binding.shopLlTabAll.setOnClickListener { viewModel.selectTab(0) }
        binding.shopLlTabTops.setOnClickListener { viewModel.selectTab(1) }
        binding.shopLlTabSale.setOnClickListener { viewModel.selectTab(2) }
    }

    private fun updateTabUI(index: Int) {
        val activeColor = androidx.core.content.ContextCompat.getColor(requireContext(), R.color.shop_tab_text_active)
        val inactiveColor = androidx.core.content.ContextCompat.getColor(requireContext(), R.color.shop_tab_text_inactive)

        binding.shopTvTabAll.setTextColor(if (index == 0) activeColor else inactiveColor)
        binding.shopTvTabAll.setTypeface(null, if (index == 0) android.graphics.Typeface.BOLD else android.graphics.Typeface.NORMAL)
        binding.shopViewTabAll.visibility = if (index == 0) View.VISIBLE else View.INVISIBLE

        binding.shopTvTabTops.setTextColor(if (index == 1) activeColor else inactiveColor)
        binding.shopTvTabTops.setTypeface(null, if (index == 1) android.graphics.Typeface.BOLD else android.graphics.Typeface.NORMAL)
        binding.shopViewTabTops.visibility = if (index == 1) View.VISIBLE else View.INVISIBLE

        binding.shopTvTabSale.setTextColor(if (index == 2) activeColor else inactiveColor)
        binding.shopTvTabSale.setTypeface(null, if (index == 2) android.graphics.Typeface.BOLD else android.graphics.Typeface.NORMAL)
        binding.shopViewTabSale.visibility = if (index == 2) View.VISIBLE else View.INVISIBLE
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
