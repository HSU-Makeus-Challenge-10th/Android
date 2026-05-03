package com.example.week02.ui.product

import com.example.week02.data.model.ItemList

data class ProductUiState(
    val products: List<ItemList> = emptyList(),
    val latestProducts: List<ItemList> = emptyList(),
    val wishlistProducts: List<ItemList> = emptyList(),
    val topsProducts: List<ItemList> = emptyList(),
    val shoesProducts: List<ItemList> = emptyList(),
) {
    fun productById(productId: Int): ItemList? {
        return products.find { it.id == productId }
    }
}
