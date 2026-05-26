package com.example.yido.data.local

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.example.yido.R
import com.example.yido.data.model.Product
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "product_store")

object ProductDataStore {

    private val gson = Gson()
    private val PRODUCTS_KEY = stringPreferencesKey("products_v2")

    val defaultProducts = listOf(
        Product(
            imageResId = R.drawable.img_shoe_1,
            name = "Nike Everyday Plus Cushioned",
            subtitle = "Training Ankle Socks (6 Pairs)",
            colours = "5 Colours",
            price = "US\$10"
        ),
        Product(
            imageResId = R.drawable.img_shoe_3,
            name = "Nike Elite Crew",
            subtitle = "Basketball Socks",
            colours = "7 Colours",
            price = "US\$16"
        ),
        Product(
            imageResId = R.drawable.img_shoe_5,
            name = "Nike Air Force 1 '07",
            subtitle = "Women's Shoes",
            colours = "5 Colours",
            price = "US\$115",
            isWished = true,
            isBestSeller = true
        ),
        Product(
            imageResId = R.drawable.img_shoe_2,
            name = "Nike Air Force 1 '07 Essentials",
            subtitle = "Men's Shoes",
            colours = "2 Colours",
            price = "US\$115",
            isWished = true,
            isBestSeller = true
        ),
        Product(
            imageResId = R.drawable.img_shoe_4,
            name = "Air Jordan 1 Mid",
            subtitle = "",
            colours = "",
            price = "US\$125"
        )
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
