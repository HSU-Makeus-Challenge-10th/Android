package com.example.deku.feature.wishlist.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.deku.R
import com.example.deku.databinding.FragmentWishListBinding
import com.example.deku.feature.shop.ui.DetailFragment
import com.example.deku.product.presentation.ProductViewModel
import com.example.deku.product.ui.ProductGridAdapter

class WishlistFragment : Fragment(R.layout.fragment_wish_list) {

    private var _binding: FragmentWishListBinding? = null
    private val binding get() = _binding!!
    private val productViewModel: ProductViewModel by activityViewModels()
    private lateinit var adapter: ProductGridAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentWishListBinding.bind(view)

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

        binding.rvWishList.layoutManager = GridLayoutManager(requireContext(), 2)
        binding.rvWishList.adapter = adapter

        productViewModel.wishlistProducts.observe(viewLifecycleOwner) { wishItems ->
            adapter.updateItems(wishItems)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
