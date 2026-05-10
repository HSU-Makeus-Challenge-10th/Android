package com.example.umc10th.ui.wishlist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.GridLayoutManager
import com.example.umc10th.databinding.FragmentWishlistBinding
import com.example.umc10th.ui.purchase.PurchaseProductAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class WishlistFragment : Fragment() {

    private var _binding: FragmentWishlistBinding? = null
    private val binding get() = _binding!!
    private val viewModel: WishlistViewModel by viewModels()

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
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect { uiState ->
                    wishListAdapter.submitList(uiState.products)
                }
            }
        }
        viewModel.loadWishlistProducts()
    }
    private fun setupRecyclerView() {
        wishListAdapter = PurchaseProductAdapter(emptyList()) { clickedItem ->
            viewModel.toggleWishlist(clickedItem.id)
        }

        binding.wishlistRecyclerView.layoutManager = GridLayoutManager(requireContext(), 2)
        binding.wishlistRecyclerView.adapter = wishListAdapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
