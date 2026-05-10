package com.example.deku.feature.main

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.deku.core.component.MainBottomBar
import com.example.deku.navigation.MainNavGraph
import com.example.deku.navigation.currentBaseRoute
import com.example.deku.navigation.navigateToBottomTab

@Composable
fun MainScreen(homeTitle: String) {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route.currentBaseRoute()

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        containerColor = Color.White,
        bottomBar = {
            MainBottomBar(
                currentRoute = currentRoute,
                onTabSelected = { routeName ->
                    navController.navigateToBottomTab(routeName, homeTitle)
                }
            )
        }
    ) { innerPadding ->
        MainNavGraph(
            navController = navController,
            homeTitle = homeTitle,
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        )
    }
}
