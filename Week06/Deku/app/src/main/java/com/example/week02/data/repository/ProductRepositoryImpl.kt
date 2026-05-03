package com.example.week02.data.repository

import com.example.week02.data.model.ItemList
import com.example.week02.data.local.ProductDataStore
import com.example.week02.domain.repository.ProductRepository
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class ProductRepositoryImpl @Inject constructor(
    private val productDataStore: ProductDataStore,
) : ProductRepository {

    override val products: Flow<List<ItemList>> = productDataStore.productsFlow

    override val wishlistProducts: Flow<List<ItemList>> = products.map { products ->
        products.filter { it.isWish }
    }

    override suspend fun seedIfNeeded() {
        productDataStore.seedIfNeeded()
    }

    override suspend fun toggleWish(productId: Int) {
        productDataStore.toggleWish(productId)
    }
}
