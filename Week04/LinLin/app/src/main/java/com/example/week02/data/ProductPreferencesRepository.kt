package com.example.week02.data

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

private val Context.productPreferencesDataStore: DataStore<Preferences> by preferencesDataStore(
    name = "nike_product_prefs",
)

private val KEY_HOME = stringPreferencesKey("home_products_json")
private val KEY_SHOP = stringPreferencesKey("shop_products_json")
private val KEY_WISHLIST = stringPreferencesKey("wishlist_products_json")

class ProductPreferencesRepository(context: Context) {

    private val appContext = context.applicationContext
    private val gson = Gson()
    private val listType = object : TypeToken<List<ProductItem>>() {}.type

    private fun parseList(json: String?): List<ProductItem> {
        if (json.isNullOrBlank()) return emptyList()
        return gson.fromJson(json, listType) ?: emptyList()
    }

    fun homeProductsFlow(): Flow<List<ProductItem>> =
        appContext.productPreferencesDataStore.data.map { prefs -> parseList(prefs[KEY_HOME]) }

    fun shopProductsFlow(): Flow<List<ProductItem>> =
        appContext.productPreferencesDataStore.data.map { prefs -> parseList(prefs[KEY_SHOP]) }

    fun wishlistProductsFlow(): Flow<List<ProductItem>> =
        appContext.productPreferencesDataStore.data.map { prefs -> parseList(prefs[KEY_WISHLIST]) }

    /** 최초 실행 시 ProductDummyData 기준으로 JSON 시드 */
    suspend fun ensureSeeded() {
        appContext.productPreferencesDataStore.edit { prefs ->
            if (prefs[KEY_HOME] == null) {
                prefs[KEY_HOME] = gson.toJson(ProductDummyData.homeNewProducts())
            }
            if (prefs[KEY_SHOP] == null) {
                prefs[KEY_SHOP] = gson.toJson(ProductDummyData.shopProducts())
            }
            if (prefs[KEY_WISHLIST] == null) {
                val shop = parseList(prefs[KEY_SHOP])
                val wish = shop.filter { it.heartFilled }.map { it.forWishlistRow() }
                prefs[KEY_WISHLIST] = gson.toJson(wish)
            }
        }
    }

    /** 구매하기 하트 토글 + 위시리스트 동기화 (DataStore에 저장) */
    suspend fun toggleShopHeart(productId: Int) {
        appContext.productPreferencesDataStore.edit { prefs ->
            val shop = parseList(prefs[KEY_SHOP]).toMutableList()
            val idx = shop.indexOfFirst { it.id == productId }
            if (idx < 0) return@edit
            val old = shop[idx]
            val newHeart = !old.heartFilled
            shop[idx] = old.copy(heartFilled = newHeart)
            val wish = parseList(prefs[KEY_WISHLIST]).toMutableList()
            if (newHeart) {
                if (wish.none { it.id == productId }) {
                    wish.add(old.copy(heartFilled = true, showHeart = false))
                }
            } else {
                wish.removeAll { it.id == productId }
            }
            prefs[KEY_SHOP] = gson.toJson(shop)
            prefs[KEY_WISHLIST] = gson.toJson(wish)
        }
    }

    private fun ProductItem.forWishlistRow(): ProductItem =
        copy(showHeart = false, heartFilled = false)
}
