package com.example.umc10th

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.umc10th.databinding.FragmentWishlistBinding

class WishlistFragment : Fragment() {

    private var _binding: FragmentWishlistBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentWishlistBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
    }

    private fun setupRecyclerView() {
        val products = listOf(
            WishlistProduct(
                R.drawable.socks1,
                "Nike Everyday Plus Cushioned",
                "Training Ankle Socks (6 Pairs)",
                "5 Colours",
                "US\$10"
            ),
            WishlistProduct(
                R.drawable.socks2,
                "Nike Elite Crew",
                "Basketball Socks",
                "7 Colours",
                "US\$16"
            ),
            WishlistProduct(
                R.drawable.socks1,
                "Nike Everyday Plus Cushioned",
                "Training Ankle Socks (6 Pairs)",
                "5 Colours",
                "US\$10"
            ),
            WishlistProduct(
                R.drawable.socks2,
                "Nike Elite Crew",
                "Basketball Socks",
                "7 Colours",
                "US\$16"
            ),
            WishlistProduct(
                R.drawable.socks1,
                "Nike Everyday Plus Cushioned",
                "Training Ankle Socks (6 Pairs)",
                "5 Colours",
                "US\$10"
            ),
            WishlistProduct(
                R.drawable.socks2,
                "Nike Elite Crew",
                "Basketball Socks",
                "7 Colours",
                "US\$16"
            ),
            WishlistProduct(
                R.drawable.socks1,
                "Nike Everyday Plus Cushioned",
                "Training Ankle Socks (6 Pairs)",
                "5 Colours",
                "US\$10"
            ),
            WishlistProduct(
                R.drawable.socks2,
                "Nike Elite Crew",
                "Basketball Socks",
                "7 Colours",
                "US\$16"
            ),
            WishlistProduct(
                R.drawable.socks1,
                "Nike Everyday Plus Cushioned",
                "Training Ankle Socks (6 Pairs)",
                "5 Colours",
                "US\$10"
            ),
            WishlistProduct(
                R.drawable.socks2,
                "Nike Elite Crew",
                "Basketball Socks",
                "7 Colours",
                "US\$16"
            ),
            WishlistProduct(
                R.drawable.socks1,
                "Nike Everyday Plus Cushioned",
                "Training Ankle Socks (6 Pairs)",
                "5 Colours",
                "US\$10"
            ),
            WishlistProduct(
                R.drawable.socks2,
                "Nike Elite Crew",
                "Basketball Socks",
                "7 Colours",
                "US\$16"
            ),
            WishlistProduct(
                R.drawable.socks1,
                "Nike Everyday Plus Cushioned",
                "Training Ankle Socks (6 Pairs)",
                "5 Colours",
                "US\$10"
            ),
            WishlistProduct(
                R.drawable.socks2,
                "Nike Elite Crew",
                "Basketball Socks",
                "7 Colours",
                "US\$16"
            ),
            WishlistProduct(
                R.drawable.socks1,
                "Nike Everyday Plus Cushioned",
                "Training Ankle Socks (6 Pairs)",
                "5 Colours",
                "US\$10"
            ),
            WishlistProduct(
                R.drawable.socks2,
                "Nike Elite Crew",
                "Basketball Socks",
                "7 Colours",
                "US\$16"
            ),
            WishlistProduct(
                R.drawable.socks1,
                "Nike Everyday Plus Cushioned",
                "Training Ankle Socks (6 Pairs)",
                "5 Colours",
                "US\$10"
            ),
            WishlistProduct(
                R.drawable.socks2,
                "Nike Elite Crew",
                "Basketball Socks",
                "7 Colours",
                "US\$16"
            )
        )

        binding.wishlistRecyclerView.layoutManager = GridLayoutManager(requireContext(), 2)
        binding.wishlistRecyclerView.adapter = WishlistProductAdapter(products) { item ->
            val action = WishlistFragmentDirections.actionMenuHeartStraightToMenuListMagnifyingGlass(
                fromCart = false,
                title = item.title,
                imageResId = item.imageResId,
                description = item.description,
                price = item.price
            )
            findNavController().navigate(action)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
