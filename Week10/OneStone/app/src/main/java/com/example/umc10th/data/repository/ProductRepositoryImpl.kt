package com.example.umc10th.data.repository

import android.content.Context
import com.example.umc10th.data.local.getProducts
import com.example.umc10th.data.local.getPurchaseProducts
import com.example.umc10th.data.local.initializeProducts
import com.example.umc10th.data.local.initializePurchaseProducts
import com.example.umc10th.data.local.savePurchaseProducts
import com.example.umc10th.data.model.Product
import com.example.umc10th.data.model.PurchaseProduct
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ProductRepositoryImpl @Inject constructor(
    @param:ApplicationContext private val context: Context
) : ProductRepository {
    override suspend fun initializeProducts() {
        initializeProducts(context)
    }

    override fun getProducts(): Flow<List<Product>> {
        return getProducts(context)
    }

    override suspend fun initializePurchaseProducts() {
        initializePurchaseProducts(context)
    }

    override fun getPurchaseProducts(): Flow<List<PurchaseProduct>> {
        return getPurchaseProducts(context)
    }

    override suspend fun savePurchaseProducts(products: List<PurchaseProduct>) {
        savePurchaseProducts(context, products)
    }

    override suspend fun toggleWishlist(productId: Int) {
        val updatedProducts = getPurchaseProducts(context).first().map { product ->
            if (product.id == productId) {
                product.copy(isWishlisted = !product.isWishlisted)
            } else {
                product
            }
        }
        savePurchaseProducts(context, updatedProducts)
    }
}
