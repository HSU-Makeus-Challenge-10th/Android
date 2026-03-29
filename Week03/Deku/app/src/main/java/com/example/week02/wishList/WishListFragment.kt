package com.example.week02.wishList

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.week02.R
import com.example.week02.databinding.FragmentWishListBinding
import com.example.week02.shop.DetailFragment
import com.example.week02.shop.ProductViewModel
import com.example.week02.shop.ShopProductAdapter

class WishListFragment : Fragment(R.layout.fragment_wish_list) {

    private var _binding: FragmentWishListBinding? = null
    private val binding get() = _binding!!
    private val productViewModel: ProductViewModel by activityViewModels()
    private lateinit var adapter: ShopProductAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentWishListBinding.bind(view)

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

        binding.rvWishList.layoutManager = GridLayoutManager(requireContext(), 2)
        binding.rvWishList.adapter = adapter

        productViewModel.products.observe(viewLifecycleOwner) { products ->
            val wishItems = products.filter { it.isWish }
            adapter.updateItems(wishItems)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
