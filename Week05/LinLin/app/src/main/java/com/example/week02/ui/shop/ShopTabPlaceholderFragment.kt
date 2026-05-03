package com.example.week02.ui.shop

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import com.example.week02.databinding.FragmentShopTabPlaceholderBinding

class ShopTabPlaceholderFragment : Fragment() {
    private var _binding: FragmentShopTabPlaceholderBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentShopTabPlaceholderBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.tvPlaceholder.text = arguments?.getString(ARG_MESSAGE).orEmpty()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        private const val ARG_MESSAGE = "message"

        fun newInstance(message: String): ShopTabPlaceholderFragment =
            ShopTabPlaceholderFragment().apply {
                arguments = bundleOf(ARG_MESSAGE to message)
            }
    }
}
