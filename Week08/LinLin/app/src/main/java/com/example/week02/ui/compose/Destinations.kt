package com.example.week02.ui.compose

import kotlinx.serialization.Serializable

sealed interface AppDestination {
    @Serializable
    data object Home : AppDestination

    @Serializable
    data object Shop : AppDestination

    @Serializable
    data object Wishlist : AppDestination

    @Serializable
    data object Profile : AppDestination

    // 장바구니는 "탭은 아니어도 됨" 요구사항을 반영해 별도 목적지로 둡니다.
    @Serializable
    data object Cart : AppDestination
}

