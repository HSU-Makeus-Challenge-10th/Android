package com.example.umc10th.ui.main

import com.example.umc10th.R

enum class MainTab(
    val route: String,
    val label: String,
    val iconResId: Int
) {
    Home("home", "홈", R.drawable.ic_house_simple),
    Purchase("purchase", "구매하기", R.drawable.ic_list_magnifying_glass),
    Wishlist("wishlist", "위시리스트", R.drawable.ic_heart_straight),
    Cart("cart", "장바구니", R.drawable.ic_bag_simple),
    Profile("profile", "프로필", R.drawable.ic_user);

    companion object {
        fun fromRoute(route: String?): MainTab {
            return entries.firstOrNull { it.route == route } ?: Home
        }
    }
}
