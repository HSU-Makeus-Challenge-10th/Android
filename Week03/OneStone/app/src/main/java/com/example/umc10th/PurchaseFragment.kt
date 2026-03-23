package com.example.umc10th

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.navigation.navOptions
import com.example.umc10th.databinding.FragmentPurchaseBinding
import com.google.android.material.tabs.TabLayoutMediator

class PurchaseFragment : Fragment() {

    companion object {
        private const val DETAIL_OPENED_KEY = "detail_opened"
    }

    private var _binding: FragmentPurchaseBinding? = null
    private val binding get() = _binding!!
    private val args: PurchaseFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPurchaseBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val adapter = PurchasePagerAdapter(this)
        binding.purchasePager.adapter = adapter

        TabLayoutMediator(binding.purchaseTabs, binding.purchasePager) { tab, position ->
            tab.text = when (position) {
                0 -> "전체"
                1 -> "Tops & T-Shirts"
                else -> "Shoes"
            }
        }.attach()

        openProductDetailIfNeeded(savedInstanceState == null)
    }

    private fun openProductDetailIfNeeded(isFirstCreation: Boolean) {
        val title = args.title
        val description = args.description
        val price = args.price
        val navController = findNavController()
        val backStackEntry = navController.currentBackStackEntry ?: return
        val detailOpened = backStackEntry.savedStateHandle.get<Boolean>(DETAIL_OPENED_KEY) == true

        if (!isFirstCreation || detailOpened || title == null || description == null || price == null) {
            return
        }

        backStackEntry.savedStateHandle[DETAIL_OPENED_KEY] = true
        val navOptions = navOptions {
            popUpTo(R.id.menu_list_magnifying_glass) {
                // 구매하기까지 지우고 상품 디테일로 이동
                inclusive = true
            }
        }
        val action = PurchaseFragmentDirections.actionMenuListMagnifyingGlassToProductDetailFragment(
            title = title,
            imageResId = args.imageResId,
            description = description,
            price = price
        )
        navController.navigate(action, navOptions)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
