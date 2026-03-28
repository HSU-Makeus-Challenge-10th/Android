package com.umc.yido

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.umc.yido.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    companion object {
        private const val TAG = "LIFE_QUIZ"
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        Log.d(TAG, "HomeFragment : onAttach")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "HomeFragment : onCreate")
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        Log.d(TAG, "HomeFragment : onCreateView")
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d(TAG, "HomeFragment : onViewCreated")

        val productList = mutableListOf(
            Product(R.drawable.img_shoe_2, "Air Jordan XXXVI", "US$185", isWished = true),
            Product(R.drawable.img_shoe_5, "Nike Air Force 1 '07", "US$115"),
            Product(R.drawable.img_shoe_4, "Air Jordan 1 Mid", "US$125"),
            Product(R.drawable.img_shoe_1, "Nike Everyday Plus Cushioned", "US$10")
        )

        val adapter = HomeProductAdapter(productList)
        binding.homeRvProducts.adapter = adapter
        binding.homeRvProducts.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        Log.d(TAG, "HomeFragment : onDestroyView")
        _binding = null
    }
}
