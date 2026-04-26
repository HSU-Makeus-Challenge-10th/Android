package com.umc.yido

data class Product(
    val imageResId: Int,
    val name: String,
    val price: String,
    val isWished: Boolean = false,
    val category: String = "전체"   // "전체" | "Tops & T-Shirts" | "sale"
)
