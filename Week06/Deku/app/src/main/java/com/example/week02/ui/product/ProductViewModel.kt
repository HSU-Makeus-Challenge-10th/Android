package com.example.week02.ui.product

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.week02.domain.repository.ProductRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel
class ProductViewModel @Inject constructor(
    private val repository: ProductRepository,
) : ViewModel() {

    private val _uiState = MutableStateFlow(ProductUiState())
    val uiState: StateFlow<ProductUiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            repository.seedIfNeeded()
        }

        viewModelScope.launch {
            repository.products.collect { products ->
                _uiState.update {
                    it.copy(
                        products = products,
                        latestProducts = products.take(LATEST_PRODUCT_COUNT),
                        wishlistProducts = products.filter { product ->
                            product.isWish
                        },
                        topsProducts = products.filter { product ->
                            product.category == CATEGORY_TOPS
                        },
                        shoesProducts = products.filter { product ->
                            product.category == CATEGORY_SHOES
                        },
                    )
                }
            }
        }
    }

    fun toggleWish(productId: Int) {
        viewModelScope.launch {
            repository.toggleWish(productId)
        }
    }

    companion object {
        private const val LATEST_PRODUCT_COUNT = 5
        private const val CATEGORY_TOPS = "Tops & T-Shirts"
        private const val CATEGORY_SHOES = "Shoes"
    }
}
