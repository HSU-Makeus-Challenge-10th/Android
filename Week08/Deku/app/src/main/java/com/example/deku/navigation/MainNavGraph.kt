package com.example.deku.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.example.deku.data.ProductItem
import com.example.deku.feature.cart.CartScreen
import com.example.deku.feature.home.HomeScreen
import com.example.deku.feature.profile.ProfileScreen
import com.example.deku.feature.product.ProductDetailScreen
import com.example.deku.feature.shop.ShopScreen
import com.example.deku.feature.wishlist.WishListScreen

@Composable
fun MainNavGraph(
    navController: NavHostController,
    homeTitle: String,
    products: List<ProductItem>,
    onWishClick: (ProductItem) -> Unit,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = MainRoute.Home(title = homeTitle),
        modifier = modifier
    ) {
        composable<MainRoute.Home> { backStackEntry ->
            // Type-safe Navigation으로 전달된 Home title 인자를 꺼냅니다.
            val route = backStackEntry.toRoute<MainRoute.Home>()
            HomeScreen(
                title = route.title,
                products = products,
                onProductClick = { product ->
                    // 상품 id만 상세 route에 넘기고, 상세 화면에서는 현재 상품 목록에서 다시 조회합니다.
                    navController.navigate(MainRoute.ProductDetail(productId = product.id))
                }
            )
        }
        composable<MainRoute.Shop> {
            ShopScreen(
                products = products,
                onProductClick = { product ->
                    navController.navigate(MainRoute.ProductDetail(productId = product.id))
                },
                onWishClick = onWishClick
            )
        }
        composable<MainRoute.WishList> {
            WishListScreen(
                products = products,
                onProductClick = { product ->
                    navController.navigate(MainRoute.ProductDetail(productId = product.id))
                },
                onWishClick = onWishClick
            )
        }
        composable<MainRoute.Cart> {
            CartScreen(
                onOrderClick = {
                    // 장바구니의 주문하기 버튼은 구매하기 탭으로 전환되어야 합니다.
                    navController.navigateToBottomTab(MainRouteName.SHOP, homeTitle)
                }
            )
        }
        composable<MainRoute.Profile> {
            ProfileScreen()
        }
        composable<MainRoute.ProductDetail> { backStackEntry ->
            val route = backStackEntry.toRoute<MainRoute.ProductDetail>()
            ProductDetailScreen(
                product = products.find { product -> product.id == route.productId },
                onBackClick = { navController.navigateUp() },
                onWishClick = onWishClick
            )
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
        // 탭 전환 시 시작 destination까지만 남기고 각 탭의 상태는 가능한 복원합니다.
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
