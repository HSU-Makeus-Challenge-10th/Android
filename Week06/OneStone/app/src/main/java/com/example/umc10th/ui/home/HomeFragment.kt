package com.example.umc10th.ui.home

import android.os.Bundle
import android.os.SystemClock
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.navigation.navOptions
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.umc10th.databinding.FragmentHomeBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val args: HomeFragmentArgs by navArgs()
    private val viewModel: HomeViewModel by viewModels()
    private var lastBackPressedAt = 0L

    private lateinit var productAdapter: ProductAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val receivedTitle = args.homeTitle
        Log.d("LIFE_QUIZ", "HomeFragment?먯꽌 ?뺤씤??homeTitle: $receivedTitle")

        args.homeTitle?.let { binding.homeTitle.text = it }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupProductRecyclerView()
        observeProducts()
        setupBackPressedCallback()
        viewModel.loadProducts()
    }

    private fun setupBackPressedCallback() {
        requireActivity().onBackPressedDispatcher.addCallback(
            viewLifecycleOwner,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    val now = SystemClock.elapsedRealtime()
                    if (now - lastBackPressedAt <= 2000L) {
                        requireActivity().finish()
                    } else {
                        lastBackPressedAt = now
                        Toast.makeText(
                            requireContext(),
                            "?쒕쾲 ???꾨Ⅴ硫??깆쓣 醫낅즺?⑸땲??",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }
        )
    }

    private fun observeProducts() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect { uiState ->
                    if (uiState.isLoading) {
                        showLoading()
                    } else {
                        Log.d(TAG, "collect products size=${uiState.products.size}")
                        productAdapter.submitList(uiState.products)
                    }
                }
            }
        }
    }

    private fun showLoading() {
        Log.d(TAG, "showLoading()")
        productAdapter.showLoading()
    }

    private fun setupProductRecyclerView() {
        productAdapter = ProductAdapter(emptyList()) { item ->
            val navController = findNavController()
            val navOptions = navOptions {
                popUpTo(navController.graph.findStartDestination().id) {
                    saveState = false
                }
            }
            val action = HomeFragmentDirections.actionMenuHouseSimpleToMenuListMagnifyingGlass(
                fromCart = false,
                title = item.name,
                imageResId = item.imageResId,
                description = item.description,
                price = item.price
            )
            navController.navigate(action, navOptions)
        }

        binding.productRecyclerView.layoutManager = LinearLayoutManager(
            requireContext(),
            LinearLayoutManager.HORIZONTAL,
            false
        )
        binding.productRecyclerView.adapter = productAdapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        private const val TAG = "HomeFragment"
    }
}
