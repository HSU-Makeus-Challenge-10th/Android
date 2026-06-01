package com.example.yido.ui.wishlist

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

@HiltViewModel
class WishlistViewModel @Inject constructor(
    private val productRepository: ProductRepository
) : ViewModel() {

    private val _wishProducts = MutableStateFlow<List<Product>>(emptyList())
    val wishProducts: StateFlow<List<Product>> = _wishProducts.asStateFlow()

    init {
        loadWishProducts()
    }

    private fun loadWishProducts() {
        viewModelScope.launch {
            productRepository.getProductsStream().collect { products ->
                _wishProducts.value = products.filter { it.isWished }
            }
        }
    }
}
