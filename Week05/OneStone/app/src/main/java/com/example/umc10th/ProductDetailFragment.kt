package com.example.umc10th

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.umc10th.databinding.FragmentProductDetailBinding

class ProductDetailFragment : Fragment() {

    private var _binding: FragmentProductDetailBinding? = null
    private val binding get() = _binding!!
    private val args: ProductDetailFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProductDetailBinding.inflate(inflater, container, false)
        bindProductDetails()
        setupHeader()
        return binding.root
    }

    private fun bindProductDetails() {
        binding.productDetailHeaderTitle.text = args.title
        binding.productDetailImage.setImageResource(args.imageResId)
        binding.productDetailTitle.text = args.title
        binding.productDetailDescription.text = args.description
        binding.productDetailPrice.text = args.price
    }

    private fun setupHeader() {
        binding.productDetailBackButton.setOnClickListener {
            findNavController().navigateUp()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
