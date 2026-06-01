package com.example.yido.ui.shop

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.yido.R
import com.example.yido.data.model.Product

@Composable
fun ShopScreen(viewModel: ShopViewModel = hiltViewModel()) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        ShopTabRow(
            currentTabIndex = uiState.currentTabIndex,
            onTabSelected = viewModel::selectTab
        )
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            contentPadding = PaddingValues(start = 12.dp, end = 12.dp, top = 8.dp, bottom = 16.dp),
            modifier = Modifier.fillMaxSize()
        ) {
            items(uiState.filteredProducts, key = { it.name }) { product ->
                ShopProductItem(
                    product = product,
                    onWishClick = { viewModel.toggleWish(product) }
                )
            }
        }
    }
}

@Composable
private fun ShopTabRow(currentTabIndex: Int, onTabSelected: (Int) -> Unit) {
    val tabs = listOf("전체", "Tops & T-Shirts", "sale")
    val activeColor = Color(0xFF000000)
    val inactiveColor = Color(0xFF999999)

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 24.dp, end = 24.dp, top = 24.dp, bottom = 12.dp),
        horizontalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        tabs.forEachIndexed { index, label ->
            val isSelected = currentTabIndex == index
            Column(
                modifier = Modifier
                    .clickable { onTabSelected(index) }
                    .width(IntrinsicSize.Max)
            ) {
                Text(
                    text = label,
                    fontSize = 16.sp,
                    fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                    color = if (isSelected) activeColor else inactiveColor,
                    maxLines = 1
                )
                Spacer(modifier = Modifier.height(8.dp))
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(2.dp)
                        .background(if (isSelected) activeColor else Color.Transparent)
                )
            }
        }
    }
}

@Composable
private fun ShopProductItem(product: Product, onWishClick: () -> Unit) {
    Column(modifier = Modifier.padding(8.dp)) {
        Box(modifier = Modifier.fillMaxWidth()) {
            Image(
                painter = painterResource(product.imageResId),
                contentDescription = product.name,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(1f)
            )
            Box(
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(top = 8.dp, end = 8.dp)
                    .size(36.dp)
                    .clip(CircleShape)
                    .background(Color.White)
                    .clickable(onClick = onWishClick),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    painter = painterResource(
                        if (product.isWished) R.drawable.ic_heart_filled_red
                        else R.drawable.ic_heart_outline
                    ),
                    contentDescription = "위시",
                    tint = Color.Unspecified,
                    modifier = Modifier.size(20.dp)
                )
            }
        }
        Spacer(modifier = Modifier.height(12.dp))
        if (product.isBestSeller) {
            Text(
                text = "BestSeller",
                fontSize = 13.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFFE85D04)
            )
            Spacer(modifier = Modifier.height(2.dp))
        }
        Text(
            text = product.name,
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis
        )
        if (product.subtitle.isNotEmpty()) {
            Text(
                text = product.subtitle,
                fontSize = 13.sp,
                color = Color(0xFF999999)
            )
        }
        if (product.colours.isNotEmpty()) {
            Text(
                text = product.colours,
                fontSize = 13.sp,
                color = Color(0xFF999999)
            )
        }
        Spacer(modifier = Modifier.height(6.dp))
        Text(
            text = product.price,
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black
        )
        Spacer(modifier = Modifier.height(12.dp))
    }
}
