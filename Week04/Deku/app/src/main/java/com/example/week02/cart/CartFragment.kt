package com.example.week02.cart

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.example.week02.R
import androidx.navigation.fragment.findNavController
import com.example.week02.databinding.FragmentCartBinding

class CartFragment : Fragment(R.layout.fragment_cart) {

    private var _binding: FragmentCartBinding? = null
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding = FragmentCartBinding.bind(view)
        binding.btnShop.setOnClickListener {
            findNavController().navigate(R.id.shopFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
