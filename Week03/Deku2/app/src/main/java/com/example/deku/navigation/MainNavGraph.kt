package com.example.deku.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.example.deku.feature.cart.CartScreen
import com.example.deku.feature.home.HomeScreen
import com.example.deku.feature.profile.ProfileScreen
import com.example.deku.feature.shop.ShopScreen
import com.example.deku.feature.wishlist.WishListScreen

@Composable
fun MainNavGraph(
    navController: NavHostController,
    homeTitle: String,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = MainRoute.Home(title = homeTitle),
        modifier = modifier
    ) {
        composable<MainRoute.Home> { backStackEntry ->
            val route = backStackEntry.toRoute<MainRoute.Home>()
            HomeScreen(title = route.title)
        }
        composable<MainRoute.Shop> {
            ShopScreen()
        }
        composable<MainRoute.WishList> {
            WishListScreen()
        }
        composable<MainRoute.Cart> {
            CartScreen(
                onOrderClick = {
                    navController.navigateToBottomTab(MainRouteName.SHOP, homeTitle)
                }
            )
        }
        composable<MainRoute.Profile> {
            ProfileScreen()
        }
    }
}

fun NavController.navigateToBottomTab(routeName: String, homeTitle: String) {
    when (routeName) {
        MainRouteName.HOME -> navigateBottom(MainRoute.Home(title = homeTitle))
        MainRouteName.SHOP -> navigateBottom(MainRoute.Shop)
        MainRouteName.WISH_LIST -> navigateBottom(MainRoute.WishList)
        MainRouteName.CART -> navigateBottom(MainRoute.Cart)
        MainRouteName.PROFILE -> navigateBottom(MainRoute.Profile)
    }
}

private fun <T : Any> NavController.navigateBottom(route: T) {
    navigate(route) {
        popUpTo(graph.findStartDestination().id) {
            saveState = true
        }
        launchSingleTop = true
        restoreState = true
    }
}

fun String?.currentBaseRoute(): String? {
    return this
        ?.substringBefore("/")
        ?.substringBefore("?")
}
