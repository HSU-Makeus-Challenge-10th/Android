package com.umc.yido

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.umc.yido.databinding.FragmentWishlistBinding

class WishlistFragment : Fragment() {

    private var _binding: FragmentWishlistBinding? = null
    private val binding get() = _binding!!

    companion object {
        private const val TAG = "LIFE_QUIZ"
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        Log.d(TAG, "WishlistFragment : onAttach")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "WishlistFragment : onCreate")
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        Log.d(TAG, "WishlistFragment : onCreateView")
        _binding = FragmentWishlistBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d(TAG, "WishlistFragment : onViewCreated")

        val wishList = mutableListOf(
            Product(R.drawable.img_shoe_5, "Nike Air Force 1 '07", "US$115"),
            Product(R.drawable.img_shoe_2, "Air Jordan XXXVI", "US$185"),
            Product(R.drawable.img_shoe_4, "Air Jordan 1 Mid", "US$125")
        )

        val adapter = WishlistProductAdapter(wishList)
        binding.wishlistRvProducts.adapter = adapter
        binding.wishlistRvProducts.layoutManager = GridLayoutManager(requireContext(), 2)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        Log.d(TAG, "WishlistFragment : onDestroyView")
        _binding = null
    }
}
