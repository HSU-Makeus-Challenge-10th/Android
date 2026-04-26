package com.example.week02.data

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.IOException
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map

// Preferences DataStore 인스턴스를 Context에 한 번만 붙여서 재사용한다.
// 안전하게 인스턴스를 공유하기 위해서
private val Context.productDataStore by preferencesDataStore(name = "product_store")

class ProductDataStore(private val context: Context) {

    private val gson = Gson()
    // 상품 리스트 전체를 JSON 문자열 하나로 저장한다.
    private val productsKey = stringPreferencesKey("products_json")
    private val productListType = object : TypeToken<List<ItemList>>() {}.type

    // DataStore 값을 읽을 때마다 JSON을 ItemList 리스트로 복원해서 내보낸다.
    // DataStore은 키-값으로 이뤄진 저장소이기 때문에->객체를 넣을 수 없음
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

    // 앱 첫 실행 더미 데이터 json 변환 후 저장
    suspend fun seedIfNeeded() {
        context.productDataStore.edit { preferences ->
            if (preferences[productsKey].isNullOrBlank()) {
                preferences[productsKey] = gson.toJson(ItemData.products)
            }
        }
    }

    // 하트 토글 및 json으로 저장
    // copy로 원본 복사 후 새 객체 만듬
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

    // 값 json값 읽고 리스트로 복원 -> decodeproducts
    private suspend fun updateProducts(transform: (List<ItemList>) -> List<ItemList>) {
        context.productDataStore.edit { preferences ->
            val currentProducts = decodeProducts(preferences[productsKey])
            preferences[productsKey] = gson.toJson(transform(currentProducts))
        }
    }

    // 저장된 JSON이 없거나 파싱에 실패하면 기본 더미 데이터를 fallback으로 사용
    private fun decodeProducts(json: String?): List<ItemList> {
        if (json.isNullOrBlank()) {
            return ItemData.products
        }

        return runCatching {
            gson.fromJson<List<ItemList>>(json, productListType)
        }.getOrNull().orEmpty().ifEmpty {
            ItemData.products
        }
    }
}
