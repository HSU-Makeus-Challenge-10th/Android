package com.example.week02.domain.repository

import com.example.week02.data.model.ItemList
import kotlinx.coroutines.flow.Flow

interface ProductRepository {
    val products: Flow<List<ItemList>>
    val wishlistProducts: Flow<List<ItemList>>

    suspend fun seedIfNeeded()
    suspend fun toggleWish(productId: Int)
}
