package com.example.week02.data.local

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.example.week02.data.ProductDummyData
import com.example.week02.data.ProductItem
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

private val Context.productPreferencesDataStore: DataStore<Preferences> by preferencesDataStore(
    name = "nike_product_prefs",
)

private val KEY_HOME = stringPreferencesKey("home_products_json")
private val KEY_SHOP = stringPreferencesKey("shop_products_json")
private val KEY_WISHLIST = stringPreferencesKey("wishlist_products_json")
private val KEY_CATALOG_VERSION = intPreferencesKey("catalog_version")

/** 더미/리소스 매핑을 바꾸면 올려서 로컬 JSON을 현재 [ProductDummyData]로 다시 채움 */
private const val CATALOG_VERSION = 3

@Singleton
class ProductLocalDataSourceImpl @Inject constructor(
    @ApplicationContext context: Context,
) : ProductLocalDataSource {

    private val appContext = context.applicationContext
    private val gson = Gson()
    private val listType = object : TypeToken<List<ProductItem>>() {}.type

    private fun parseList(json: String?): List<ProductItem> {
        if (json.isNullOrBlank()) return emptyList()
        return gson.fromJson(json, listType) ?: emptyList()
    }

    /** JSON에 박힌 imageResId는 무시하고, id 기준으로 현재 R.drawable을 붙인다. */
    private fun List<ProductItem>.withCanonicalDrawables(): List<ProductItem> = map { stored ->
        ProductDummyData.catalogItemById(stored.id)?.let { canonical ->
            stored.copy(imageResId = canonical.imageResId)
        } ?: stored
    }

    /**
     * 홈 "What's new"는 더미 카탈로그만 쓰면 됨.
     * DataStore JSON의 구형 [ProductItem.imageResId]를 읽으면 돋보기(ic_search) 등 잘못된 리소스가 붙을 수 있어
     * 저장값과 무관하게 항상 [ProductDummyData.homeNewProducts]를 내보냄.
     */
    override fun homeProductsFlow(): Flow<List<ProductItem>> =
        flowOf(ProductDummyData.homeNewProducts())

    override fun shopProductsFlow(): Flow<List<ProductItem>> =
        appContext.productPreferencesDataStore.data.map { prefs ->
            parseList(prefs[KEY_SHOP]).withCanonicalDrawables()
        }

    override fun wishlistProductsFlow(): Flow<List<ProductItem>> =
        appContext.productPreferencesDataStore.data.map { prefs ->
            parseList(prefs[KEY_WISHLIST]).withCanonicalDrawables()
        }

    override suspend fun ensureSeeded() {
        appContext.productPreferencesDataStore.edit { prefs ->
            val storedVersion = prefs[KEY_CATALOG_VERSION] ?: 0
            if (storedVersion < CATALOG_VERSION) {
                val shop = ProductDummyData.shopProducts()
                prefs[KEY_HOME] = gson.toJson(ProductDummyData.homeNewProducts())
                prefs[KEY_SHOP] = gson.toJson(shop)
                val wish = shop.filter { it.heartFilled }.map { it.forWishlistRow() }
                prefs[KEY_WISHLIST] = gson.toJson(wish)
                prefs[KEY_CATALOG_VERSION] = CATALOG_VERSION
                return@edit
            }
            if (prefs[KEY_HOME] == null) {
                prefs[KEY_HOME] = gson.toJson(ProductDummyData.homeNewProducts())
            }
            if (prefs[KEY_SHOP] == null) {
                prefs[KEY_SHOP] = gson.toJson(ProductDummyData.shopProducts())
            }
            if (prefs[KEY_WISHLIST] == null) {
                val shop = parseList(prefs[KEY_SHOP]).withCanonicalDrawables()
                val wish = shop.filter { it.heartFilled }.map { it.forWishlistRow() }
                prefs[KEY_WISHLIST] = gson.toJson(wish)
            }
        }
    }

    override suspend fun toggleShopHeart(productId: Int) {
        appContext.productPreferencesDataStore.edit { prefs ->
            val shop = parseList(prefs[KEY_SHOP]).withCanonicalDrawables().toMutableList()
            val idx = shop.indexOfFirst { it.id == productId }
            if (idx < 0) return@edit
            val old = shop[idx]
            val newHeart = !old.heartFilled
            shop[idx] = old.copy(heartFilled = newHeart)
            val wish = parseList(prefs[KEY_WISHLIST]).withCanonicalDrawables().toMutableList()
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
