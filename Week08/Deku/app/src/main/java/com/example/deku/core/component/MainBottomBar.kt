package com.example.deku.core.component

import androidx.annotation.DrawableRes
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.deku.R
import com.example.deku.core.designsystem.ColorDivider
import com.example.deku.core.designsystem.ColorNavUnselected
import com.example.deku.core.designsystem.ColorTextPrimary
import com.example.deku.navigation.MainRouteName

private data class BottomTabItem(
    val label: String,
    @param:DrawableRes val iconRes: Int,
    val routeName: String
)

private val bottomTabs = listOf(
    BottomTabItem("홈", R.drawable.home, MainRouteName.HOME),
    BottomTabItem("구매하기", R.drawable.shop, MainRouteName.SHOP),
    BottomTabItem("위시리스트", R.drawable.heart, MainRouteName.WISH_LIST),
    BottomTabItem("장바구니", R.drawable.cart, MainRouteName.CART),
    BottomTabItem("프로필", R.drawable.profile, MainRouteName.PROFILE)
)

@Composable
fun MainBottomBar(
    currentRoute: String?,
    onTabSelected: (String) -> Unit
) {
    Surface(
        color = Color.White,
        shadowElevation = 0.dp
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White)
                .navigationBarsPadding()
        ) {
            HorizontalDivider(color = ColorDivider, thickness = 1.dp)
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(64.dp)
                    .padding(horizontal = 14.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                bottomTabs.forEach { item ->
                    val selected = currentRoute == item.routeName
                    val color = if (selected) ColorTextPrimary else ColorNavUnselected

                    Column(
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxHeight()
                            .clip(RoundedCornerShape(8.dp))
                            .clickable { onTabSelected(item.routeName) }
                            .padding(vertical = 6.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Icon(
                            painter = painterResource(id = item.iconRes),
                            contentDescription = item.label,
                            tint = color,
                            modifier = Modifier.size(29.dp)
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = item.label,
                            color = color,
                            fontSize = 10.sp,
                            lineHeight = 12.sp,
                            fontWeight = if (selected) FontWeight.Bold else FontWeight.Normal,
                            maxLines = 1,
                            textAlign = TextAlign.Center
                        )
                    }
                }
            }
        }
    }
}
