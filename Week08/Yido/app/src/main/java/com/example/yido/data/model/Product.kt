package com.example.yido.data.model

data class Product(
    val imageResId: Int,
    val name: String,
    val subtitle: String,
    val colours: String,
    val price: String,
    val isWished: Boolean = false,
    val isBestSeller: Boolean = false,
    val category: String = "전체"
)
