package com.example.week02.ui.wishlist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.example.week02.data.ProductDummyData
import com.example.week02.databinding.FragmentWishlistBinding
import com.example.week02.ui.shop.ShopProductAdapter

class WishlistFragment : Fragment() {
    private var _binding: FragmentWishlistBinding? = null
    private val binding get() = _binding!!

    private val wishlistItems = ProductDummyData.wishlistProducts().toMutableList()
    private val wishlistAdapter by lazy { ShopProductAdapter(wishlistItems) }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentWishlistBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.rvWishlist.layoutManager = GridLayoutManager(requireContext(), 2)
        binding.rvWishlist.adapter = wishlistAdapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
