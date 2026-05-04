package com.example.yido

data class Product(
    val imageResId: Int,
    val name: String,
    val price: String,
    val isWished: Boolean = false,
    val category: String = "?„ì²´"   // "?„ì²´" | "Tops & T-Shirts" | "sale"
)
