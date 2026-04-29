package com.example.umc10th

import android.content.Context
import android.os.Bundle
import android.os.SystemClock
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.navigation.navOptions
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.umc10th.databinding.FragmentHomeBinding
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val args: HomeFragmentArgs by navArgs()
    private var lastBackPressedAt = 0L

    private lateinit var productAdapter: ProductAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val receivedTitle = args.homeTitle
        android.util.Log.d("LIFE_QUIZ", "HomeFragment에서 확인한 homeTitle: $receivedTitle")

        args.homeTitle?.let { binding.homeTitle.text = it }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupProductRecyclerView()
        observeProducts()
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
                            "한번 더 누르면 앱을 종료합니다.",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }
        )
    }


    private fun observeProducts() {
        viewLifecycleOwner.lifecycleScope.launch {
            Log.d(TAG, "observeProducts() start")
            showLoading()

            delay(1500)
            Log.d(TAG, "delay finished")
            initializeProducts(requireContext())
            getProducts(requireContext()).collect { products ->
                Log.d(TAG, "collect products size=${products.size}")
                productAdapter.submitList(products)
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
