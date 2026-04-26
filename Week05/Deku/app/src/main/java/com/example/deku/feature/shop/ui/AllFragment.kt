package com.example.deku.feature.shop.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.deku.R
import com.example.deku.databinding.FragmentProductListBinding
import com.example.deku.product.presentation.ProductViewModel
import com.example.deku.product.ui.ProductGridAdapter

class AllFragment : Fragment(R.layout.fragment_product_list) {

    private var _binding: FragmentProductListBinding? = null
    private val binding get() = _binding!!
    private val productViewModel: ProductViewModel by activityViewModels()
    private lateinit var adapter: ProductGridAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentProductListBinding.bind(view)

        adapter = ProductGridAdapter(
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

        //데이터 변경 시 ui를 다시 그려줌
        //viewlifecycleowner -> 현재 view가 살아있는 동안 observe함
        productViewModel.products.observe(viewLifecycleOwner) {
            adapter.updateItems(productViewModel.productsByCategory(null))
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
