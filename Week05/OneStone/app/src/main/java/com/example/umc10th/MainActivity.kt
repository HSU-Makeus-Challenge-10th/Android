package com.example.umc10th

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.navOptions
import com.example.umc10th.databinding.ActivityMainBinding
import android.util.Log

class MainActivity : AppCompatActivity() {



    private lateinit var binding: ActivityMainBinding

    private fun navigateToTopLevel(
        itemId: Int,
        navController: androidx.navigation.NavController
    ) {
        if (navController.popBackStack(itemId, false)) {
            return
        }

        val isFromDetail = navController.currentDestination?.id == R.id.product_detail_fragment
        val navOptions = navOptions {
            launchSingleTop = true
            restoreState = !isFromDetail
            popUpTo(navController.graph.findStartDestination().id) {
                saveState = !isFromDetail
            }
        }

        navController.navigate(itemId, null, navOptions)
    }

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        enableEdgeToEdge()


        binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)



        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->

            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())

            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)

            insets

        }



        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fragment_container) as NavHostFragment
        val navController = navHostFragment.navController
        if (savedInstanceState == null) {
            val homeTitle = intent.getStringExtra(EXTRA_HOME_TITLE)
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

    }



    companion object {

        const val EXTRA_HOME_TITLE = "extra_home_title"

    }

}
