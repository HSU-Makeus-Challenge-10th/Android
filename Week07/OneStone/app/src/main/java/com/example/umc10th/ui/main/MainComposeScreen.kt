package com.example.umc10th.ui.main

import android.app.Activity
import android.os.SystemClock
import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navOptions
import com.example.umc10th.ui.cart.CartScreen
import com.example.umc10th.ui.home.HomeScreen
import com.example.umc10th.ui.profile.ProfileScreen
import com.example.umc10th.ui.purchase.PurchaseScreen
import com.example.umc10th.ui.wishlist.WishlistScreen

@Composable
fun MainComposeScreen(homeTitle: String?) {
    val context = LocalContext.current
    val activity = context as? Activity
    val navController = rememberNavController()
    val backStackEntry by navController.currentBackStackEntryAsState()
    val selectedTab = MainTab.fromRoute(backStackEntry?.destination?.route)
    var lastBackPressedAt by remember { mutableLongStateOf(0L) }

    BackHandler {
        if (selectedTab != MainTab.Home) {
            navController.navigateToTopLevel(MainTab.Home)
            return@BackHandler
        }

        val now = SystemClock.elapsedRealtime()
        if (now - lastBackPressedAt <= 2000L) {
            activity?.finish()
        } else {
            lastBackPressedAt = now
            Toast.makeText(context, "뒤로가기를 한 번 더 누르면 종료됩니다.", Toast.LENGTH_SHORT).show()
        }
    }

    Scaffold(
        containerColor = Color.White,
        contentWindowInsets = WindowInsets(0),
        bottomBar = {
            MainBottomBar(
                selectedTab = selectedTab,
                onTabSelected = { tab ->
                    navController.navigateToTopLevel(tab)
                }
            )
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
                .padding(innerPadding)
                .statusBarsPadding()
        ) {
            NavHost(
                navController = navController,
                startDestination = MainTab.Home.route,
                modifier = Modifier.fillMaxSize(),
                enterTransition = { EnterTransition.None },
                exitTransition = { ExitTransition.None },
                popEnterTransition = { EnterTransition.None },
                popExitTransition = { ExitTransition.None }
            ) {
                composable(MainTab.Home.route) {
                    HomeScreen(title = homeTitle ?: "Discover")
                }
                composable(MainTab.Purchase.route) {
                    PurchaseScreen()
                }
                composable(MainTab.Wishlist.route) {
                    WishlistScreen()
                }
                composable(MainTab.Cart.route) {
                    CartScreen(
                        onOrderClick = {
                            navController.navigateToTopLevel(MainTab.Purchase)
                        }
                    )
                }
                composable(MainTab.Profile.route) {
                    ProfileScreen()
                }
            }
        }
    }
}

private fun androidx.navigation.NavHostController.navigateToTopLevel(tab: MainTab) {
    navigate(
        route = tab.route,
        navOptions = navOptions {
            launchSingleTop = true
            restoreState = true
            popUpTo(graph.findStartDestination().id) {
                saveState = true
            }
        }
    )
}
