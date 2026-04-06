package com.example.umc10th

import android.os.Bundle
import android.os.SystemClock
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.navigation.navOptions
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.umc10th.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val args: HomeFragmentArgs by navArgs()
    private var lastBackPressedAt = 0L

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

    private fun setupProductRecyclerView() {
        val products = listOf(
            Product(R.drawable.shoes1, "Air Jordan XXXVI", "Men's Shoes", "US\$185"),
            Product(R.drawable.shoes2, "Nike Air Force 1 '07", "Women's Shoes", "US\$115"),
            Product(R.drawable.shoes1, "Air Jordan XXXVI", "Men's Shoes", "US\$185"),
            Product(R.drawable.shoes2, "Nike Air Force 1 '07", "Women's Shoes", "US\$115"),
            Product(R.drawable.shoes1, "Air Jordan XXXVI", "Men's Shoes", "US\$185"),
            Product(R.drawable.shoes2, "Nike Air Force 1 '07", "Women's Shoes", "US\$115"),
            Product(R.drawable.shoes1, "Air Jordan XXXVI", "Men's Shoes", "US\$185"),
            Product(R.drawable.shoes2, "Nike Air Force 1 '07", "Women's Shoes", "US\$115"),
            Product(R.drawable.shoes1, "Air Jordan XXXVI", "Men's Shoes", "US\$185"),
            Product(R.drawable.shoes2, "Nike Air Force 1 '07", "Women's Shoes", "US\$115"),
            Product(R.drawable.shoes1, "Air Jordan XXXVI", "Men's Shoes", "US\$185"),
            Product(R.drawable.shoes2, "Nike Air Force 1 '07", "Women's Shoes", "US\$115"),
            Product(R.drawable.shoes1, "Air Jordan XXXVI", "Men's Shoes", "US\$185"),
            Product(R.drawable.shoes2, "Nike Air Force 1 '07", "Women's Shoes", "US\$115"),
            Product(R.drawable.shoes1, "Air Jordan XXXVI", "Men's Shoes", "US\$185"),
            Product(R.drawable.shoes2, "Nike Air Force 1 '07", "Women's Shoes", "US\$115"),
            Product(R.drawable.shoes1, "Air Jordan XXXVI", "Men's Shoes", "US\$185"),
            Product(R.drawable.shoes2, "Nike Air Force 1 '07", "Women's Shoes", "US\$115"),  Product(R.drawable.shoes1, "Air Jordan XXXVI", "Men's Shoes", "US\$185"),
            Product(R.drawable.shoes2, "Nike Air Force 1 '07", "Women's Shoes", "US\$115")

        )

        binding.productRecyclerView.layoutManager = LinearLayoutManager(
            requireContext(),
            LinearLayoutManager.HORIZONTAL,
            false
        )
        binding.productRecyclerView.adapter = ProductAdapter(products) { item ->
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
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
