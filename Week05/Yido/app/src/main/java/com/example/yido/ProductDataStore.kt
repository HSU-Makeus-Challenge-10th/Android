package com.example.yido

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "product_store")

object ProductDataStore {

    private val gson = Gson()
    private val PRODUCTS_KEY = stringPreferencesKey("products")

    val defaultProducts = listOf(
        Product(R.drawable.img_shoe_1, "Nike Everyday Plus Cushioned", "Training Ankle Socks\nUS\$10"),
        Product(R.drawable.img_shoe_2, "Nike Elite Crew", "Basketball Socks\nUS\$16"),
        Product(R.drawable.img_shoe_3, "Nike Air Force 1 '07", "Women's Shoes\nUS\$115", isWished = true),
        Product(R.drawable.img_shoe_4, "Jordan Nike Air Force 1 '07 Essentials", "Men's Shoes\nUS\$115", isWished = true),
        Product(R.drawable.img_shoe_5, "Air Jordan 1 Mid", "Men's Shoes\nUS\$125")
    )

    fun getProducts(context: Context): Flow<List<Product>> {
        return context.dataStore.data.map { prefs ->
            val json = prefs[PRODUCTS_KEY]
            if (json.isNullOrEmpty()) {
                emptyList()
            } else {
                gson.fromJson(json, object : TypeToken<List<Product>>() {}.type)
            }
        }
    }

    suspend fun saveProducts(context: Context, products: List<Product>) {
        context.dataStore.edit { prefs ->
            prefs[PRODUCTS_KEY] = gson.toJson(products)
        }
    }
}
