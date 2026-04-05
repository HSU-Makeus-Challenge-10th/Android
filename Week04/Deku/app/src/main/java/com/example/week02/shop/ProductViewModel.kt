package com.example.week02.shop

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.week02.data.ItemList
import com.example.week02.data.ProductDataStore
import com.example.week02.data.ProductRepository
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class ProductViewModel(application: Application) : AndroidViewModel(application) {

    // 여러 Fragment가 저장소를 공유하도록 지정
    private val repository = ProductRepository(
        ProductDataStore(application.applicationContext)
    )

    val products: LiveData<List<ItemList>> = repository.products.asLiveData()

    val latestProducts: LiveData<List<ItemList>> = repository.products
        .map { products -> products.take(5) }
        .asLiveData()

    val wishlistProducts: LiveData<List<ItemList>> = repository.wishlistProducts.asLiveData()

    init {
        //DataStore가 비어 있으면 더미 데이터를 채움
        viewModelScope.launch {
            repository.seedIfNeeded()
        }
    }

    fun productsByCategory(category: String?): List<ItemList> {
        val currentProducts = products.value.orEmpty()
        return if (category == null) {
            currentProducts
        } else {
            currentProducts.filter { it.category == category }
        }
    }

    fun toggleWish(productId: Int) {
        // 저장 작업은 ViewModel scope에서 비동기로 실행
        viewModelScope.launch {
            repository.toggleWish(productId)
        }
    }

    fun productById(productId: Int): ItemList? {
        return products.value.orEmpty().find { it.id == productId }
    }
}
