package com.umc.yido

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.material.tabs.TabLayout
import com.umc.yido.databinding.FragmentShopBinding

class ShopFragment : Fragment() {

    private var _binding: FragmentShopBinding? = null
    private val binding get() = _binding!!

    companion object {
        private const val TAG = "LIFE_QUIZ"
    }

    private val allProducts = mutableListOf(
        Product(R.drawable.img_shoe_1, "Nike Everyday Plus Cushioned", "Training Ankle Socks\nUS\$10"),
        Product(R.drawable.img_shoe_2, "Nike Elite Crew", "Basketball Socks\nUS\$16"),
        Product(R.drawable.img_shoe_3, "Nike Air Force 1 '07", "Women's Shoes\nUS\$115", isWished = true),
        Product(R.drawable.img_shoe_4, "Jordan Nike Air Force 1 '07 Essentials", "Men's Shoes\nUS\$115", isWished = true),
        Product(R.drawable.img_shoe_5, "Air Jordan 1 Mid", "Men's Shoes\nUS\$125")
    )

    private lateinit var adapter: ShopProductAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "ShopFragment : onCreate")
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        Log.d(TAG, "ShopFragment : onCreateView")
        _binding = FragmentShopBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d(TAG, "ShopFragment : onViewCreated")

        adapter = ShopProductAdapter(allProducts.toMutableList())
        binding.shopRvProducts.adapter = adapter
        binding.shopRvProducts.layoutManager = GridLayoutManager(requireContext(), 2)

        binding.shopTabLayout.elevation = 0f

        val tabs = listOf("전체", "Tops & T-Shirts", "sale")
        tabs.forEach { tabTitle ->
            binding.shopTabLayout.addTab(
                binding.shopTabLayout.newTab().setText(tabTitle)
            )
        }

        binding.shopTabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                val selectedCategory = tab?.text?.toString() ?: "전체"
                val filtered = if (selectedCategory == "전체") {
                    allProducts.toMutableList()
                } else {
                    allProducts.filter { it.category == selectedCategory }.toMutableList()
                }
                adapter.updateList(filtered)
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {}
            override fun onTabReselected(tab: TabLayout.Tab?) {}
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        Log.d(TAG, "ShopFragment : onDestroyView")
        _binding = null
    }
}
