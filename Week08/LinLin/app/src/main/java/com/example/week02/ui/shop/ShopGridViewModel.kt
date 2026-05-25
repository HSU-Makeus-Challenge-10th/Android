package com.example.week02.ui.shop

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.week02.data.ProductItem
import com.example.week02.domain.repository.ProductLocalRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class ShopGridUiState(
    val products: List<ProductItem> = emptyList(),
)

@HiltViewModel
class ShopGridViewModel @Inject constructor(
    private val productRepository: ProductLocalRepository,
) : ViewModel() {

    private val _uiState = MutableStateFlow(ShopGridUiState())
    val uiState: StateFlow<ShopGridUiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            productRepository.ensureSeeded()
            productRepository.shopProductsFlow().collect { list ->
                _uiState.update { it.copy(products = list) }
            }
        }
    }

    fun toggleShopHeart(productId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            productRepository.toggleShopHeart(productId)
        }
    }
}
