package com.example.yido.ui.wishlist

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.yido.data.model.Product

@Composable
fun WishlistScreen(viewModel: WishlistViewModel = hiltViewModel()) {
    val wishProducts by viewModel.wishProducts.collectAsStateWithLifecycle()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        Text(
            text = "위시리스트",
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black,
            modifier = Modifier
                .padding(horizontal = 24.dp)
                .padding(top = 24.dp)
        )
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            contentPadding = PaddingValues(start = 8.dp, end = 8.dp, top = 8.dp, bottom = 16.dp),
            modifier = Modifier.fillMaxSize()
        ) {
            items(wishProducts, key = { it.name }) { product ->
                WishlistProductItem(product)
            }
        }
    }
}

@Composable
private fun WishlistProductItem(product: Product) {
    Column(modifier = Modifier.padding(8.dp)) {
        Image(
            painter = painterResource(product.imageResId),
            contentDescription = product.name,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(1f)
        )
        Spacer(modifier = Modifier.height(8.dp))
        if (product.isBestSeller) {
            Text(
                text = "BestSeller",
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFFE85D04)
            )
            Spacer(modifier = Modifier.height(2.dp))
        }
        Text(
            text = product.name,
            fontSize = 14.sp,
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
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = product.price,
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black
        )
        Spacer(modifier = Modifier.height(8.dp))
    }
}
