package com.example.umc10th

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.example.umc10th.databinding.FragmentWishlistBinding
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class WishlistFragment : Fragment() {

    private var _binding: FragmentWishlistBinding? = null
    private val binding get() = _binding!!

    private lateinit var wishListAdapter: PurchaseProductAdapter

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
        setupRecyclerView()
        observeWishListProducts()
    }

    private fun observeWishListProducts(){
        viewLifecycleOwner.lifecycleScope.launch{
            getPurchaseProducts(requireContext()).collect{ purchaseProducts ->
                val new_wishListProducts = purchaseProducts.filter{ it.isWishlisted}
                wishListAdapter.submitList(new_wishListProducts)
            }
        }
    }
    private fun setupRecyclerView() {
        wishListAdapter = PurchaseProductAdapter(emptyList()) { clickedItem ->
            viewLifecycleOwner.lifecycleScope.launch {
                val updatedProducts = getPurchaseProducts(requireContext()).first().map { product ->
                    if (product.id == clickedItem.id) {
                        product.copy(isWishlisted = !product.isWishlisted)
                    } else {
                        product
                    }
                }
                savePurchaseProducts(requireContext(), updatedProducts)
            }
        }

        binding.wishlistRecyclerView.layoutManager = GridLayoutManager(requireContext(), 2)
        binding.wishlistRecyclerView.adapter = wishListAdapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
