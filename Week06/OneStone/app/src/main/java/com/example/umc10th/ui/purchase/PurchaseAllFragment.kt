package com.example.umc10th.ui.purchase

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
import com.example.umc10th.databinding.FragmentPurchaseAllBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class PurchaseAllFragment : Fragment() {

    private var _binding: FragmentPurchaseAllBinding? = null
    private val binding get() = _binding!!
    private val viewModel: PurchaseViewModel by viewModels()


    private lateinit var purchaseProductAdapter: PurchaseProductAdapter
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPurchaseAllBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        observePurchaseProducts()

    }

    private fun observePurchaseProducts(){
        viewLifecycleOwner.lifecycleScope.launch{
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect { uiState ->
                    purchaseProductAdapter.submitList(uiState.products)
                }
            }
        }
        viewModel.loadPurchaseProducts()
    }
    private fun setupRecyclerView() {
        purchaseProductAdapter = PurchaseProductAdapter(emptyList()) { clickedItem ->
            viewModel.toggleWishlist(clickedItem.id)
        }
        binding.purchaseAllRecyclerView.layoutManager = GridLayoutManager(requireContext(), 2)
        binding.purchaseAllRecyclerView.adapter = purchaseProductAdapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
