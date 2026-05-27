package com.example.deku.navigation

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

object MainRouteName {
    const val HOME = "home"
    const val SHOP = "shop"
    const val WISH_LIST = "wish_list"
    const val CART = "cart"
    const val PROFILE = "profile"
    const val PRODUCT_DETAIL = "product_detail"
}

// 문자열 route 대신 Serializable 타입으로 목적지를 정의해 Navigation 인자 오타를 컴파일 단계에서 줄입니다.
sealed interface MainRoute {
    @Serializable
    @SerialName(MainRouteName.HOME)
    data class Home(val title: String) : MainRoute

    @Serializable
    @SerialName(MainRouteName.SHOP)
    data object Shop : MainRoute

    @Serializable
    @SerialName(MainRouteName.WISH_LIST)
    data object WishList : MainRoute

    @Serializable
    @SerialName(MainRouteName.CART)
    data object Cart : MainRoute

    @Serializable
    @SerialName(MainRouteName.PROFILE)
    data object Profile : MainRoute

    @Serializable
    @SerialName(MainRouteName.PRODUCT_DETAIL)
    data class ProductDetail(val productId: Int) : MainRoute
}
