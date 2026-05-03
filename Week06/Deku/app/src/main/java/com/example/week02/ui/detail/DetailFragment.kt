package com.example.week02.ui.detail

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.example.week02.R
import com.example.week02.databinding.FragmentDetailBinding
import com.example.week02.ui.product.ProductViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class DetailFragment : Fragment(R.layout.fragment_detail) {

    private var _binding: FragmentDetailBinding? = null
    private val binding get() = _binding!!
    private val productViewModel: ProductViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentDetailBinding.bind(view)

        val productId = requireArguments().getInt(ARG_PRODUCT_ID)

        binding.btnBack.setOnClickListener {
            findNavController().navigateUp()
        }

        binding.btnWishList.setOnClickListener {
            productViewModel.toggleWish(productId)
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                productViewModel.uiState.collect { state ->
                    val product = state.productById(productId) ?: return@collect

                    binding.tvTopTitle.text = product.name
                    binding.ivProduct.setImageResource(product.imageResId)
                    binding.tvProductName.text = product.name
                    binding.tvPrice.text = product.price
                    binding.tvDescription.text = product.description
                    binding.btnWishList.text = if (product.isWish) {
                        getString(R.string.detail_wishlist_remove)
                    } else {
                        getString(R.string.detail_wishlist_add)
                    }
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    /// 다른 화면에서 id를 쉽게 담아서 넘거는 함수
    companion object {
        const val ARG_PRODUCT_ID = "productId"

        fun bundle(productId: Int): Bundle {
            return Bundle().apply {
                putInt(ARG_PRODUCT_ID, productId)
            }
        }
    }
}
