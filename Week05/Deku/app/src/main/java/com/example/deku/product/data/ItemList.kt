package com.example.deku.product.data

data class ItemList(
    val id: Int,
    val name: String,
    val price: String,
    val imageResId: Int,
    val category: String,
    val colorCount: Int,
    val description: String,
    val isWish: Boolean = false
)
