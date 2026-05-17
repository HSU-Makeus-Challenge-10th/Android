package com.example.yido.data.repository

import android.content.Context
import com.example.yido.data.local.ProductDataStore
import com.example.yido.data.model.Product
import com.example.yido.domain.repository.ProductRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ProductRepositoryImpl @Inject constructor(
    @ApplicationContext private val context: Context
) : ProductRepository {

    override fun getProductsStream(): Flow<List<Product>> {
        return ProductDataStore.getProducts(context)
    }

    override suspend fun saveProducts(products: List<Product>) {
        ProductDataStore.saveProducts(context, products)
    }

    override fun getDefaultProducts(): List<Product> {
        return ProductDataStore.defaultProducts
    }
}
