package com.example.umc10th

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class PurchasePagerAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {

    override fun getItemCount(): Int = 3

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> PurchaseAllFragment()
            1 -> PurchaseTopsFragment()
            else -> PurchaseShoesFragment()
        }
    }
}
