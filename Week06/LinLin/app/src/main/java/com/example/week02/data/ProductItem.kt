package com.example.week02.data

import androidx.annotation.DrawableRes

/**
 * 홈·구매하기·위시리스트에서 쓰는 상품 UI용 더미 데이터 모델.
 * 이미지는 [imageResId]로 drawable 리소스를 가리킵니다(플레이스홀더 또는 본인이 넣은 PNG).
 */
data class ProductItem(
    val id: Int,
    @DrawableRes val imageResId: Int,
    val name: String,
    val subtitle: String? = null,
    val colorsLabel: String? = null,
    val price: String,
    val isBestSeller: Boolean = false,
    /** 구매하기에서만 하트 표시 */
    val showHeart: Boolean = false,
    val heartFilled: Boolean = false,
)
