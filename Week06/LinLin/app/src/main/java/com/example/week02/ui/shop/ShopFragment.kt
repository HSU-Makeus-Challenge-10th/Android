package com.example.week02.ui.shop

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.week02.R
import com.example.week02.databinding.FragmentShopBinding
import com.google.android.material.tabs.TabLayout

class ShopFragment : Fragment() {
    private var _binding: FragmentShopBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentShopBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.tabLayout.addTab(binding.tabLayout.newTab().setText("전체"))
        binding.tabLayout.addTab(binding.tabLayout.newTab().setText("Tops & T-Shirts"))
        binding.tabLayout.addTab(binding.tabLayout.newTab().setText("sale"))

        binding.tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                showTabContent(tab.position)
            }

            override fun onTabUnselected(tab: TabLayout.Tab) = Unit
            override fun onTabReselected(tab: TabLayout.Tab) = Unit
        })

        if (savedInstanceState == null) {
            showTabContent(0)
        }
    }

    private fun showTabContent(position: Int) {
        val fragment = when (position) {
            0 -> ShopGridFragment()
            1 -> ShopTabPlaceholderFragment.newInstance("")
            2 -> ShopTabPlaceholderFragment.newInstance("")
            else -> ShopGridFragment()
        }
        childFragmentManager.beginTransaction()
            .setReorderingAllowed(true)
            .replace(R.id.shop_tab_container, fragment)
            .commit()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
