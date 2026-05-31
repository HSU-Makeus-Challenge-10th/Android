package com.example.umc10th.ui.home

import androidx.lifecycle.viewModelScope
import com.example.umc10th.data.model.Product
import com.example.umc10th.data.repository.ProductRepository
import com.example.umc10th.ui.base.BaseViewModel
import com.example.umc10th.ui.base.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val productRepository: ProductRepository
) : BaseViewModel<HomeUiState>(HomeUiState()) {
    private var hasStartedLoading = false

    fun loadProducts() {
        if (hasStartedLoading) return
        hasStartedLoading = true

        viewModelScope.launch {
            setState { copy(isLoading = true) }
            delay(1500)
            productRepository.initializeProducts()
            productRepository.getProducts().collect { products ->
                setState {
                    copy(
                        isLoading = false,
                        products = products
                    )
                }
            }
        }
    }
}

data class HomeUiState(
    val isLoading: Boolean = true,
    val products: List<Product> = emptyList()
) : UiState
