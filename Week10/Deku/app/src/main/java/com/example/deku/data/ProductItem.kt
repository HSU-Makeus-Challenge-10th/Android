// 화면 전반에서 공유하는 상품 모델로, 위시리스트 상태까지 함께 보관합니다.

package com.example.deku.data

import androidx.annotation.DrawableRes

data class ProductItem(
    val id: Int,
    val name: String,
    val price: String,
    @param:DrawableRes val imageResId: Int,
    val category: String,
    val colorCount: Int,
    val description: String,
    val isWish: Boolean = false
)
