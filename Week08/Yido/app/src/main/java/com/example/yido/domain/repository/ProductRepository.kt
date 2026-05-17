package com.example.yido.domain.repository

import com.example.yido.data.model.Product
import kotlinx.coroutines.flow.Flow

interface ProductRepository {
    fun getProductsStream(): Flow<List<Product>>
    suspend fun saveProducts(products: List<Product>)
    fun getDefaultProducts(): List<Product>
}
