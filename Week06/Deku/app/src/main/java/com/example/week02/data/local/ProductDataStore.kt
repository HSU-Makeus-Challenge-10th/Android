package com.example.week02.data.local

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.example.week02.data.model.ItemList
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.IOException
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map

private val Context.productDataStore by preferencesDataStore(name = "product_store")

class ProductDataStore(private val context: Context) {

    private val gson = Gson()
    private val productsKey = stringPreferencesKey("products_json")
    private val productListType = object : TypeToken<List<ItemList>>() {}.type

    val productsFlow: Flow<List<ItemList>> = context.productDataStore.data
        .catch { throwable ->
            if (throwable is IOException) {
                emit(emptyPreferences())
            } else {
                throw throwable
            }
        }
        .map { preferences ->
            decodeProducts(preferences[productsKey])
        }

    suspend fun seedIfNeeded() {
        context.productDataStore.edit { preferences ->
            if (preferences[productsKey].isNullOrBlank()) {
                preferences[productsKey] = gson.toJson(ItemData.products)
            }
        }
    }

    suspend fun toggleWish(productId: Int) {
        updateProducts { products ->
            products.map { product ->
                if (product.id == productId) {
                    product.copy(isWish = !product.isWish)
                } else {
                    product
                }
            }
        }
    }

    private suspend fun updateProducts(transform: (List<ItemList>) -> List<ItemList>) {
        context.productDataStore.edit { preferences ->
            val currentProducts = decodeProducts(preferences[productsKey])
            preferences[productsKey] = gson.toJson(transform(currentProducts))
        }
    }

    private fun decodeProducts(json: String?): List<ItemList> {
        if (json.isNullOrBlank()) {
            return ItemData.products
        }

        val savedProducts = runCatching {
            gson.fromJson<List<ItemList>>(json, productListType)
        }.getOrNull().orEmpty()

        return mergeWithCurrentProducts(savedProducts)
    }

    private fun mergeWithCurrentProducts(savedProducts: List<ItemList>): List<ItemList> {
        if (savedProducts.isEmpty()) {
            return ItemData.products
        }

        val savedProductsById = savedProducts.associateBy { product ->
            product.id
        }

        return ItemData.products.map { currentProduct ->
            currentProduct.copy(
                isWish = savedProductsById[currentProduct.id]?.isWish ?: currentProduct.isWish
            )
        }
    }
}
