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
