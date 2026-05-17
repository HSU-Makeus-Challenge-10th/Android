package com.example.umc10th.ui.wishlist

import androidx.lifecycle.viewModelScope
import com.example.umc10th.data.model.PurchaseProduct
import com.example.umc10th.data.repository.ProductRepository
import com.example.umc10th.ui.base.BaseViewModel
import com.example.umc10th.ui.base.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.launch

@HiltViewModel
class WishlistViewModel @Inject constructor(
    private val productRepository: ProductRepository
) : BaseViewModel<WishlistUiState>(WishlistUiState()) {
    private var hasStartedLoading = false

    fun loadWishlistProducts() {
        if (hasStartedLoading) return
        hasStartedLoading = true

        viewModelScope.launch {
            productRepository.initializePurchaseProducts()
            productRepository.getPurchaseProducts().collect { products ->
                setState { copy(products = products.filter { it.isWishlisted }) }
            }
        }
    }

    fun toggleWishlist(productId: Int) {
        viewModelScope.launch {
            productRepository.toggleWishlist(productId)
        }
    }
}

data class WishlistUiState(
    val products: List<PurchaseProduct> = emptyList()
) : UiState
