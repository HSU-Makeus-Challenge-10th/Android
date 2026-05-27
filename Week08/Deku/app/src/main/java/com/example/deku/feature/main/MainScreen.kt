package com.example.deku.feature.main

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.deku.core.component.MainBottomBar
import com.example.deku.data.ProductCatalog
import com.example.deku.navigation.MainNavGraph
import com.example.deku.navigation.currentBaseRoute
import com.example.deku.navigation.navigateToBottomTab

@Composable
fun MainScreen(homeTitle: String) {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route.currentBaseRoute()
    // 위시 상태는 여러 탭에서 함께 쓰이므로 최상위 화면에 올려 단일 진실 공급원으로 관리합니다.
    var products by remember { mutableStateOf(ProductCatalog.initialProducts()) }

    fun toggleWish(productId: Int) {
        products = products.map { product ->
            if (product.id == productId) {
                product.copy(isWish = !product.isWish)
            } else {
                product
            }
        }
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        containerColor = Color.White,
        bottomBar = {
            MainBottomBar(
                currentRoute = currentRoute,
                onTabSelected = { routeName ->
                    // BottomBar는 routeName만 올리고, 실제 Navigation 처리는 부모가 담당합니다.
                    navController.navigateToBottomTab(routeName, homeTitle)
                }
            )
        }
    ) { innerPadding ->
        MainNavGraph(
            navController = navController,
            homeTitle = homeTitle,
            products = products,
            onWishClick = { product -> toggleWish(product.id) },
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        )
    }
}
