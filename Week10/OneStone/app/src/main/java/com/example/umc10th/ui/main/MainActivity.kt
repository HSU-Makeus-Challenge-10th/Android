package com.example.umc10th.ui.main

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.umc10th.ui.home.HomeViewModel
import com.example.umc10th.ui.profile.ProfileViewModel
import com.example.umc10th.ui.purchase.PurchaseViewModel
import com.example.umc10th.ui.wishlist.WishlistViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private val homeViewModel: HomeViewModel by viewModels()
    private val purchaseViewModel: PurchaseViewModel by viewModels()
    private val wishlistViewModel: WishlistViewModel by viewModels()
    private val profileViewModel: ProfileViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val homeTitle = intent.getStringExtra(EXTRA_HOME_TITLE)

        /*
        기존 XML + Fragment + BottomNavigationView 기반 구현입니다.
        Compose 미션 진행을 위해 삭제하지 않고 주석 처리했습니다.

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, 0)
            insets
        }

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fragment_container) as NavHostFragment
        val navController = navHostFragment.navController
        if (savedInstanceState == null) {
            val startArgs = Bundle().apply {
                putString("homeTitle", homeTitle)
            }
            navController.setGraph(R.navigation.nav_graph, startArgs)
        }

        binding.bottomNav.setOnItemSelectedListener { item ->
            if (navController.currentDestination?.id == item.itemId) {
                return@setOnItemSelectedListener true
            }

            navigateToTopLevel(item.itemId, navController)
            true
        }

        navController.addOnDestinationChangedListener { _, destination, _ ->
            val matchingItem = binding.bottomNav.menu.findItem(destination.id)
            if (matchingItem != null) {
                matchingItem.isChecked = true
            } else if (destination.id == R.id.product_detail_fragment) {
                binding.bottomNav.menu.findItem(R.id.menu_list_magnifying_glass)?.isChecked = true
            } else {
                for (index in 0 until binding.bottomNav.menu.size()) {
                    binding.bottomNav.menu.getItem(index).isChecked = false
                }
            }
        }
        */

        setContent {
            MainComposeScreen(
                homeTitle = homeTitle,
                homeViewModel = homeViewModel,
                purchaseViewModel = purchaseViewModel,
                wishlistViewModel = wishlistViewModel,
                profileViewModel = profileViewModel
            )
        }
    }

    companion object {
        const val EXTRA_HOME_TITLE = "extra_home_title"
    }
}
