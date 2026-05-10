package com.example.week02.data.local

import com.example.week02.data.ProductItem
import kotlinx.coroutines.flow.Flow

/**
 * 로컬 데이터 소스 (DataStore 기반 영속화).
 * 워크북의 “Local DB / DataSource”에 해당.
 */
interface ProductLocalDataSource {
    fun homeProductsFlow(): Flow<List<ProductItem>>
    fun shopProductsFlow(): Flow<List<ProductItem>>
    fun wishlistProductsFlow(): Flow<List<ProductItem>>
    suspend fun ensureSeeded()
    suspend fun toggleShopHeart(productId: Int)
}
