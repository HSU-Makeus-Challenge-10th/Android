package com.example.week02.ui.wishlist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.week02.data.ProductItem
import com.example.week02.domain.repository.ProductLocalRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class WishlistUiState(
    val products: List<ProductItem> = emptyList(),
)

@HiltViewModel
class WishlistViewModel @Inject constructor(
    private val productRepository: ProductLocalRepository,
) : ViewModel() {

    private val _uiState = MutableStateFlow(WishlistUiState())
    val uiState: StateFlow<WishlistUiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            productRepository.ensureSeeded()
            productRepository.wishlistProductsFlow().collect { list ->
                _uiState.update { it.copy(products = list) }
            }
        }
    }
}
