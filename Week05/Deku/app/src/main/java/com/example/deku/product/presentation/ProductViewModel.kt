package com.example.deku.product.presentation

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.deku.product.data.ItemList
import com.example.deku.product.data.ProductDataStore
import com.example.deku.product.data.ProductRepository
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class ProductViewModel(application: Application) : AndroidViewModel(application) {

    // 여러 Fragment가 저장소를 공유하도록 지정
    private val repository = ProductRepository(
        ProductDataStore(application.applicationContext)
    )

    //Livedata ->값이 변경시 ui에 알려주는 데이터
    //flow->datastore , livedata ->ui
    val products: LiveData<List<ItemList>> = repository.products.asLiveData()

    val latestProducts: LiveData<List<ItemList>> = repository.products
        .map { products -> products.take(5) }
        .asLiveData()

    val wishlistProducts: LiveData<List<ItemList>> = repository.wishlistProducts.asLiveData()

    init {
        //DataStore가 비어 있으면 더미 데이터를 채움
        //유아이에서 따로 데이터를 초기화 하지 않음
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
