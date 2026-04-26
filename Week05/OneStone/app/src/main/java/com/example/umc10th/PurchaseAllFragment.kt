package com.example.umc10th

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.example.umc10th.databinding.FragmentPurchaseAllBinding
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class PurchaseAllFragment : Fragment() {

    private var _binding: FragmentPurchaseAllBinding? = null
    private val binding get() = _binding!!


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
            initializePurchaseProducts(requireContext())
            getPurchaseProducts(requireContext()).collect { purchaseProducts ->
                purchaseProductAdapter.submitList(purchaseProducts)
            }
        }
    }
    private fun setupRecyclerView() {
        purchaseProductAdapter = PurchaseProductAdapter(emptyList()) { clickedItem ->
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
        binding.purchaseAllRecyclerView.layoutManager = GridLayoutManager(requireContext(), 2)
        binding.purchaseAllRecyclerView.adapter = purchaseProductAdapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
