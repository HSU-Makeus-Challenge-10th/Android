package com.example.week02.ui.compose.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.ManageSearch
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.ShoppingBag
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hasRoute
import com.example.week02.ui.compose.AppDestination

@Composable
fun LinLinBottomBar(
    currentDestination: NavDestination?,
    onSelectHome: () -> Unit,
    onSelectShop: () -> Unit,
    onSelectWishlist: () -> Unit,
    onSelectCart: () -> Unit,
    onSelectProfile: () -> Unit,
) {
    val selectedColor = Color.Black
    val unselectedColor = Color(0xFFB0B0B0)

    Surface(color = Color.White) {
        Column {
            HorizontalDivider(color = Color(0xFFEDEDED))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .windowInsetsPadding(WindowInsets.navigationBars)
                    .padding(horizontal = 18.dp, vertical = 10.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                BottomTabItem(
                    selected = currentDestination?.hasRoute<AppDestination.Home>() == true,
                    icon = Icons.Outlined.Home,
                    label = "홈",
                    selectedColor = selectedColor,
                    unselectedColor = unselectedColor,
                    onClick = onSelectHome,
                )
                BottomTabItem(
                    selected = currentDestination?.hasRoute<AppDestination.Shop>() == true,
                    icon = Icons.Outlined.ManageSearch,
                    label = "구매하기",
                    selectedColor = selectedColor,
                    unselectedColor = unselectedColor,
                    onClick = onSelectShop,
                )
                BottomTabItem(
                    selected = currentDestination?.hasRoute<AppDestination.Wishlist>() == true,
                    icon = Icons.Outlined.FavoriteBorder,
                    label = "위시리스트",
                    selectedColor = selectedColor,
                    unselectedColor = unselectedColor,
                    onClick = onSelectWishlist,
                )
                BottomTabItem(
                    selected = currentDestination?.hasRoute<AppDestination.Cart>() == true,
                    icon = Icons.Outlined.ShoppingBag,
                    label = "장바구니",
                    selectedColor = selectedColor,
                    unselectedColor = unselectedColor,
                    onClick = onSelectCart,
                )
                BottomTabItem(
                    selected = currentDestination?.hasRoute<AppDestination.Profile>() == true,
                    icon = Icons.Outlined.Person,
                    label = "프로필",
                    selectedColor = selectedColor,
                    unselectedColor = unselectedColor,
                    onClick = onSelectProfile,
                )
            }
        }
    }
}

@Composable
private fun BottomTabItem(
    selected: Boolean,
    icon: ImageVector,
    label: String,
    selectedColor: Color,
    unselectedColor: Color,
    onClick: () -> Unit,
) {
    val color = if (selected) selectedColor else unselectedColor
    IconButton(onClick = onClick) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Icon(
                imageVector = icon,
                contentDescription = label,
                tint = color,
                modifier = Modifier.size(24.dp),
            )
            Text(
                text = label,
                color = color,
                style = MaterialTheme.typography.labelSmall,
                fontWeight = if (selected) FontWeight.SemiBold else FontWeight.Normal,
            )
        }
    }
}

