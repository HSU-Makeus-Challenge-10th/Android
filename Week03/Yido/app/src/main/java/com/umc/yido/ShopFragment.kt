package com.umc.yido

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
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

    override fun onAttach(context: Context) {
        super.onAttach(context)
        Log.d(TAG, "ShopFragment : onAttach")
    }

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

        binding.shopLlTabAll.setOnClickListener { selectTab(0) }
        binding.shopLlTabTops.setOnClickListener { selectTab(1) }
        binding.shopLlTabSale.setOnClickListener { selectTab(2) }
    }

    private fun selectTab(index: Int) {
        val activeColor = androidx.core.content.ContextCompat.getColor(requireContext(), R.color.shop_tab_text_active)
        val inactiveColor = androidx.core.content.ContextCompat.getColor(requireContext(), R.color.shop_tab_text_inactive)

        binding.shopTvTabAll.setTextColor(if (index == 0) activeColor else inactiveColor)
        binding.shopTvTabAll.setTypeface(null, if (index == 0) android.graphics.Typeface.BOLD else android.graphics.Typeface.NORMAL)
        binding.shopViewTabAll.visibility = if (index == 0) View.VISIBLE else View.INVISIBLE

        binding.shopTvTabTops.setTextColor(if (index == 1) activeColor else inactiveColor)
        binding.shopTvTabTops.setTypeface(null, if (index == 1) android.graphics.Typeface.BOLD else android.graphics.Typeface.NORMAL)
        binding.shopViewTabTops.visibility = if (index == 1) View.VISIBLE else View.INVISIBLE

        binding.shopTvTabSale.setTextColor(if (index == 2) activeColor else inactiveColor)
        binding.shopTvTabSale.setTypeface(null, if (index == 2) android.graphics.Typeface.BOLD else android.graphics.Typeface.NORMAL)
        binding.shopViewTabSale.visibility = if (index == 2) View.VISIBLE else View.INVISIBLE

        val selectedCategory = when (index) {
            1 -> "Tops & T-Shirts"
            2 -> "sale"
            else -> "전체"
        }
        val filtered = if (selectedCategory == "전체") {
            allProducts.toMutableList()
        } else {
            allProducts.filter { it.category == selectedCategory }.toMutableList()
        }
        adapter.updateList(filtered)
    }

    override fun onStart() {
        super.onStart()
        Log.d(TAG, "ShopFragment : onStart")
    }

    override fun onResume() {
        super.onResume()
        Log.d(TAG, "ShopFragment : onResume")
    }

    override fun onPause() {
        super.onPause()
        Log.d(TAG, "ShopFragment : onPause")
    }

    override fun onStop() {
        super.onStop()
        Log.d(TAG, "ShopFragment : onStop")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        Log.d(TAG, "ShopFragment : onDestroyView")
        _binding = null
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "ShopFragment : onDestroy")
    }

    override fun onDetach() {
        super.onDetach()
        Log.d(TAG, "ShopFragment : onDetach")
    }
}
