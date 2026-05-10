package com.example.week02.data.repository

import com.example.week02.data.local.ProductLocalDataSource
import com.example.week02.data.ProductItem
import com.example.week02.domain.repository.ProductLocalRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ProductLocalRepositoryImpl @Inject constructor(
    private val dataSource: ProductLocalDataSource,
) : ProductLocalRepository {

    override fun homeProductsFlow(): Flow<List<ProductItem>> = dataSource.homeProductsFlow()

    override fun shopProductsFlow(): Flow<List<ProductItem>> = dataSource.shopProductsFlow()

    override fun wishlistProductsFlow(): Flow<List<ProductItem>> = dataSource.wishlistProductsFlow()

    override suspend fun ensureSeeded() = dataSource.ensureSeeded()

    override suspend fun toggleShopHeart(productId: Int) = dataSource.toggleShopHeart(productId)
}
