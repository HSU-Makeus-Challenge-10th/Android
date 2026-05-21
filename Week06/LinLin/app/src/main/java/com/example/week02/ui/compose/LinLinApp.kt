package com.example.week02.ui.compose

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.example.week02.ui.compose.components.LinLinBottomBar
import com.example.week02.ui.compose.screens.CartScreen
import com.example.week02.ui.compose.screens.HomeScreen
import com.example.week02.ui.compose.screens.ProfileScreen
import com.example.week02.ui.compose.screens.ShopScreen
import com.example.week02.ui.compose.screens.WishlistScreen

@Composable
fun LinLinApp() {
    val navController = rememberNavController()
    val backStackEntry by navController.currentBackStackEntryAsState()
    val destination = backStackEntry?.destination

    val showBottomBar = destination?.let {
        it.hasRoute<AppDestination.Home>() ||
            it.hasRoute<AppDestination.Shop>() ||
            it.hasRoute<AppDestination.Wishlist>() ||
            it.hasRoute<AppDestination.Cart>() ||
            it.hasRoute<AppDestination.Profile>()
    } ?: false

    MaterialTheme {
        Surface(modifier = Modifier.fillMaxSize()) {
            Scaffold(
                bottomBar = {
                    if (showBottomBar) {
                        LinLinBottomBar(
                            currentDestination = destination,
                            onSelectHome = { navController.navigate(AppDestination.Home) { launchSingleTop = true } },
                            onSelectShop = {
                                navController.navigate(AppDestination.Shop) {
                                    launchSingleTop = true
                                    popUpTo(AppDestination.Home) { inclusive = false }
                                }
                            },
                            onSelectWishlist = { navController.navigate(AppDestination.Wishlist) { launchSingleTop = true } },
                            onSelectCart = { navController.navigate(AppDestination.Cart) { launchSingleTop = true } },
                            onSelectProfile = { navController.navigate(AppDestination.Profile) { launchSingleTop = true } },
                        )
                    }
                },
            ) { innerPadding ->
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(MaterialTheme.colorScheme.background)
                        .padding(innerPadding),
                ) {
                    NavHost(
                        navController = navController,
                        startDestination = AppDestination.Home,
                    ) {
                        composable<AppDestination.Home> {
                            HomeScreen(
                                onNavigateCart = { navController.navigate(AppDestination.Cart) },
                            )
                        }
                        composable<AppDestination.Shop> {
                            ShopScreen()
                        }
                        composable<AppDestination.Wishlist> {
                            WishlistScreen()
                        }
                        composable<AppDestination.Cart> { entry ->
                            entry.toRoute<AppDestination.Cart>()
                            CartScreen(
                                onOrder = {
                                    navController.navigate(AppDestination.Shop) {
                                        launchSingleTop = true
                                    }
                                },
                            )
                        }
                        composable<AppDestination.Profile> {
                            ProfileScreen()
                        }
                    }
                }
            }
        }
    }
}

