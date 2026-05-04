package com.example.deku.feature.shop.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.example.deku.R
import com.example.deku.databinding.FragmentShopBinding
import com.google.android.material.tabs.TabLayoutMediator

class ShopFragment : Fragment(R.layout.fragment_shop) {

    private var _binding: FragmentShopBinding? = null
    private val binding get() = _binding!!

    private val tabTitles = listOf(
        "전체",
        "Tops & T-Shirts",
        "Shoes"
    )

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding = FragmentShopBinding.bind(view)

        // ViewPager2 어댑터 연결
        binding.viewPager.adapter = ShopPagerAdapter(this)

        // TabLayout 연결
        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            tab.text = tabTitles[position]
        }.attach()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
