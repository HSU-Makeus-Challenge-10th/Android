package com.example.yido.ui.navigation

import androidx.annotation.DrawableRes
import com.example.yido.R
import kotlinx.serialization.Serializable

sealed interface AppDestination {
    @Serializable data object Home : AppDestination
    @Serializable data object Shop : AppDestination
    @Serializable data object Wishlist : AppDestination
    @Serializable data object Cart : AppDestination
    @Serializable data object Profile : AppDestination
}

data class BottomNavItem(
    val destination: AppDestination,
    val label: String,
    @DrawableRes val iconRes: Int
)

val bottomNavItems = listOf(
    BottomNavItem(AppDestination.Home, "홈", R.drawable.ic_nav_home),
    BottomNavItem(AppDestination.Shop, "구매하기", R.drawable.ic_nav_shop),
    BottomNavItem(AppDestination.Wishlist, "위시리스트", R.drawable.ic_nav_wishlist),
    BottomNavItem(AppDestination.Cart, "장바구니", R.drawable.ic_nav_cart),
    BottomNavItem(AppDestination.Profile, "프로필", R.drawable.ic_nav_profile),
)
