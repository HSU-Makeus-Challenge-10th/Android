package com.example.week02.domain.repository

import com.example.week02.data.ProductItem
import kotlinx.coroutines.flow.Flow

/**
 * 상품·위시리스트 등 로컬 데이터.
 * [com.example.week02.data.local.ProductLocalDataSource]를 사용하는 Repository 계층.
 */
interface ProductLocalRepository {
    fun homeProductsFlow(): Flow<List<ProductItem>>
    fun shopProductsFlow(): Flow<List<ProductItem>>
    fun wishlistProductsFlow(): Flow<List<ProductItem>>
    suspend fun ensureSeeded()
    suspend fun toggleShopHeart(productId: Int)
}
