package com.example.week02.shop

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.week02.R
import com.example.week02.databinding.FragmentDetailBinding

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

        /// LiveData가 변경 시 리사이클러뷰를 갱신
        /// id가 같은 상품 하나만 find로 찾음
        /// 찾은 상품으로 ui구성
        productViewModel.products.observe(viewLifecycleOwner) { products ->
            val product = products.find { it.id == productId } ?: return@observe

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
