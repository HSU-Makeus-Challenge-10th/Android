package com.example.umc10th

import androidx.annotation.DrawableRes

data class PurchaseProduct(
    val imageResId: Int,
    val isBest: Boolean,
    val title: String,
    val description: String,
    val colornum: String,
    val price: String,
    var isWishlisted: Boolean = false
)
