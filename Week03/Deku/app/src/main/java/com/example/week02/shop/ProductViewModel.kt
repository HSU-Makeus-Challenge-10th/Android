package com.example.week02.shop

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.week02.data.ItemData
import com.example.week02.data.ItemList

class ProductViewModel : ViewModel() {

    ///수정하는 원본 데이터 저장소
    private val _products = MutableLiveData(ItemData.products)
    ///읽기 전용
    val products: LiveData<List<ItemList>> = _products

    fun productsByCategory(category: String?): List<ItemList> {
        val currentProducts = _products.value.orEmpty()
        return if (category == null) {
            currentProducts
        } else {
            currentProducts.filter { it.category == category }
        }
    }

    fun toggleWish(productId: Int) {
        val updatedProducts = _products.value.orEmpty().map { item ->
            if (item.id == productId) {
                item.copy(isWish = !item.isWish)
            } else {
                item
            }
        }
        _products.value = updatedProducts
    }

    fun productById(productId: Int): ItemList? {
        return _products.value.orEmpty().find { it.id == productId }
    }
}
