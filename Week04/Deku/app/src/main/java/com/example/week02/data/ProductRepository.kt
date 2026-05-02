package com.example.week02.data

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

/// DataStore를 ViewModel대신 호출하는 Repository
///
///
class ProductRepository(
    private val productDataStore: ProductDataStore
) {

    // 원본 상품 리스트는 DataStore에서 받아옴
    val products: Flow<List<ItemList>> = productDataStore.productsFlow


    val wishlistProducts: Flow<List<ItemList>> = products.map { products ->
        products.filter { it.isWish }
    }

    suspend fun seedIfNeeded() {
        productDataStore.seedIfNeeded()
    }


    suspend fun toggleWish(productId: Int) {
        productDataStore.toggleWish(productId)
    }
}
