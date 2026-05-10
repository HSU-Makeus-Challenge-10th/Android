package com.example.umc10th.ui.purchase

import androidx.lifecycle.viewModelScope
import com.example.umc10th.data.model.PurchaseProduct
import com.example.umc10th.data.repository.ProductRepository
import com.example.umc10th.ui.base.BaseViewModel
import com.example.umc10th.ui.base.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.launch

@HiltViewModel
class PurchaseViewModel @Inject constructor(
    private val productRepository: ProductRepository
) : BaseViewModel<PurchaseUiState>(PurchaseUiState()) {
    private var hasStartedLoading = false

    fun loadPurchaseProducts() {
        if (hasStartedLoading) return
        hasStartedLoading = true

        viewModelScope.launch {
            productRepository.initializePurchaseProducts()
            productRepository.getPurchaseProducts().collect { products ->
                setState { copy(products = products) }
            }
        }
    }

    fun toggleWishlist(productId: Int) {
        viewModelScope.launch {
            productRepository.toggleWishlist(productId)
        }
    }
}

data class PurchaseUiState(
    val products: List<PurchaseProduct> = emptyList()
) : UiState
