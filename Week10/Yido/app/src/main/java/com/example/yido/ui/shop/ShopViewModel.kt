package com.example.yido.ui.shop

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.yido.data.model.Product
import com.example.yido.domain.repository.ProductRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

data class ShopUiState(
    val allProducts: List<Product> = emptyList(),
    val filteredProducts: List<Product> = emptyList(),
    val currentTabIndex: Int = 0
)

@HiltViewModel
class ShopViewModel @Inject constructor(
    private val productRepository: ProductRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(ShopUiState())
    val uiState: StateFlow<ShopUiState> = _uiState.asStateFlow()

    init {
        loadProducts()
    }

    private fun loadProducts() {
        viewModelScope.launch {
            productRepository.getProductsStream().collect { products ->
                if (products.isEmpty()) {
                    productRepository.saveProducts(productRepository.getDefaultProducts())
                } else {
                    _uiState.value = _uiState.value.copy(allProducts = products)
                    applyFilter(_uiState.value.currentTabIndex)
                }
            }
        }
    }

    fun toggleWish(product: Product) {
        viewModelScope.launch {
            val updated = _uiState.value.allProducts.map {
                if (it.name == product.name) it.copy(isWished = !it.isWished) else it
            }
            productRepository.saveProducts(updated)
        }
    }

    fun selectTab(index: Int) {
        _uiState.value = _uiState.value.copy(currentTabIndex = index)
        applyFilter(index)
    }

    private fun applyFilter(index: Int) {
        val all = _uiState.value.allProducts
        val filtered = when (index) {
            1 -> all.filter { it.category == "Tops & T-Shirts" }
            2 -> all.filter { it.category == "sale" }
            else -> all
        }
        _uiState.value = _uiState.value.copy(filteredProducts = filtered)
    }
}
