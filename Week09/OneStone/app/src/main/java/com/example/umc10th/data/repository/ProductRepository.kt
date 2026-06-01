package com.example.umc10th.data.repository

import com.example.umc10th.data.model.Product
import com.example.umc10th.data.model.PurchaseProduct
import kotlinx.coroutines.flow.Flow

interface ProductRepository {
    suspend fun initializeProducts()

    fun getProducts(): Flow<List<Product>>

    suspend fun initializePurchaseProducts()

    fun getPurchaseProducts(): Flow<List<PurchaseProduct>>

    suspend fun savePurchaseProducts(products: List<PurchaseProduct>)

    suspend fun toggleWishlist(productId: Int)
}
