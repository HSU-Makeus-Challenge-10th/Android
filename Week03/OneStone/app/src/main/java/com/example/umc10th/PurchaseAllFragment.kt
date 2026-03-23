package com.example.umc10th

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.example.umc10th.databinding.FragmentPurchaseAllBinding

class PurchaseAllFragment : Fragment() {

    private var _binding: FragmentPurchaseAllBinding? = null
    private val binding get() = _binding!!

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
    }

    private fun setupRecyclerView() {
        val products = listOf(
            PurchaseProduct(R.drawable.socks1, false, "Nike Everyday Plus Cushioned", "Training Ankle Socks (6 Pairs)", "5 Colours", "US\$10"),
            PurchaseProduct(R.drawable.socks2, false, "Nike Elite Crew", "Basketball Socks", "7 Colours","US\$16"),
            PurchaseProduct(R.drawable.shoes1, true,"Nike Air Force 1 '07", "Women's Shoes", "5 Colours", "US\$115"),
            PurchaseProduct(R.drawable.shoes2, true, "Jordan ENike Air Force 1 '07ssentials", "Men's Shoes", "2 Colours","US\$115"),
            PurchaseProduct(R.drawable.socks1, false, "Nike Everyday Plus Cushioned", "Training Ankle Socks (6 Pairs)", "5 Colours", "US\$10"),
            PurchaseProduct(R.drawable.socks2, false, "Nike Elite Crew", "Basketball Socks", "7 Colours","US\$16"),
            PurchaseProduct(R.drawable.shoes1, true,"Nike Air Force 1 '07", "Women's Shoes", "5 Colours", "US\$115"),
            PurchaseProduct(R.drawable.shoes2, true, "Jordan ENike Air Force 1 '07ssentials", "Men's Shoes", "2 Colours","US\$115"),
            PurchaseProduct(R.drawable.socks1, false, "Nike Everyday Plus Cushioned", "Training Ankle Socks (6 Pairs)", "5 Colours", "US\$10"),
            PurchaseProduct(R.drawable.socks2, false, "Nike Elite Crew", "Basketball Socks", "7 Colours","US\$16"),
            PurchaseProduct(R.drawable.shoes1, true,"Nike Air Force 1 '07", "Women's Shoes", "5 Colours", "US\$115"),
            PurchaseProduct(R.drawable.shoes2, true, "Jordan ENike Air Force 1 '07ssentials", "Men's Shoes", "2 Colours","US\$115"),
            PurchaseProduct(R.drawable.socks1, false, "Nike Everyday Plus Cushioned", "Training Ankle Socks (6 Pairs)", "5 Colours", "US\$10"),
            PurchaseProduct(R.drawable.socks2, false, "Nike Elite Crew", "Basketball Socks", "7 Colours","US\$16"),
            PurchaseProduct(R.drawable.shoes1, true,"Nike Air Force 1 '07", "Women's Shoes", "5 Colours", "US\$115"),
            PurchaseProduct(R.drawable.shoes2, true, "Jordan ENike Air Force 1 '07ssentials", "Men's Shoes", "2 Colours","US\$115"),
            PurchaseProduct(R.drawable.socks1, false, "Nike Everyday Plus Cushioned", "Training Ankle Socks (6 Pairs)", "5 Colours", "US\$10"),
            PurchaseProduct(R.drawable.socks2, false, "Nike Elite Crew", "Basketball Socks", "7 Colours","US\$16"),
            PurchaseProduct(R.drawable.shoes1, true,"Nike Air Force 1 '07", "Women's Shoes", "5 Colours", "US\$115"),
            PurchaseProduct(R.drawable.shoes2, true, "Jordan ENike Air Force 1 '07ssentials", "Men's Shoes", "2 Colours","US\$115"),
            PurchaseProduct(R.drawable.socks1, false, "Nike Everyday Plus Cushioned", "Training Ankle Socks (6 Pairs)", "5 Colours", "US\$10"),
            PurchaseProduct(R.drawable.socks2, false, "Nike Elite Crew", "Basketball Socks", "7 Colours","US\$16"),
            PurchaseProduct(R.drawable.shoes1, true,"Nike Air Force 1 '07", "Women's Shoes", "5 Colours", "US\$115"),
            PurchaseProduct(R.drawable.shoes2, true, "Jordan ENike Air Force 1 '07ssentials", "Men's Shoes", "2 Colours","US\$115"),

            )

        binding.purchaseAllRecyclerView.layoutManager = GridLayoutManager(requireContext(), 2)
        binding.purchaseAllRecyclerView.adapter = PurchaseProductAdapter(products)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
