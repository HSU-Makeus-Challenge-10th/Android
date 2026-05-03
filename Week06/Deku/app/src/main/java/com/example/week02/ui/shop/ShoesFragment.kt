package com.example.week02.ui.shop

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.week02.R
import com.example.week02.databinding.FragmentProductListBinding
import com.example.week02.ui.detail.DetailFragment
import com.example.week02.ui.product.ProductViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ShoesFragment : Fragment(R.layout.fragment_product_list) {

    private var _binding: FragmentProductListBinding? = null
    private val binding get() = _binding!!
    private val productViewModel: ProductViewModel by activityViewModels()
    private lateinit var adapter: ShopProductAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentProductListBinding.bind(view)

        adapter = ShopProductAdapter(
            onHeartClick = { item ->
                productViewModel.toggleWish(item.id)
            },
            onItemClick = { item ->
                findNavController().navigate(
                    R.id.detailFragment,
                    DetailFragment.bundle(item.id)
                )
            }
        )

        binding.rvProducts.layoutManager = GridLayoutManager(requireContext(), 2)
        binding.rvProducts.adapter = adapter

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                productViewModel.uiState.collect { state ->
                    adapter.updateItems(state.shoesProducts)
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
