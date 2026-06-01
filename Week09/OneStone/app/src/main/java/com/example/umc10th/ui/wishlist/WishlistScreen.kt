package com.example.umc10th.ui.wishlist

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.umc10th.R
import com.example.umc10th.data.model.PurchaseProduct

@Composable
fun WishlistScreen(
    viewModel: WishlistViewModel,
    onProductClick: (PurchaseProduct) -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.loadWishlistProducts()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        Text(
            text = "위시리스트",
            color = Color(0xFF111111),
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(start = 24.dp, top = 44.dp)
        )

        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            contentPadding = PaddingValues(
                start = 12.dp,
                top = 24.dp,
                end = 12.dp,
                bottom = 24.dp
            ),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            items(
                items = uiState.products,
                key = { product -> product.id }
            ) { product ->
                WishlistProductItem(
                    product = product,
                    onClick = { onProductClick(product) },
                    onWishlistClick = { viewModel.toggleWishlist(product.id) }
                )
            }
        }
    }
}

@Composable
private fun WishlistProductItem(
    product: PurchaseProduct,
    onClick: () -> Unit,
    onWishlistClick: () -> Unit
) {
    val imageResId = product.imageResId.takeIf {
        it == R.drawable.socks1 ||
            it == R.drawable.socks2 ||
            it == R.drawable.shoes1 ||
            it == R.drawable.shoes2
    } ?: R.drawable.shoes1

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(1f)
        ) {
            Image(
                painter = painterResource(id = imageResId),
                contentDescription = null,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )
            Image(
                painter = painterResource(id = R.drawable.img_filled_heart),
                contentDescription = null,
                modifier = Modifier
                    .padding(top = 6.dp, end = 6.dp)
                    .size(34.dp)
                    .align(Alignment.TopEnd)
                    .clickable {
                        onWishlistClick()
                    }
            )
        }

        if (product.isBest) {
            Text(
                text = "BestSeller",
                color = Color(0xFFFC5100),
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(top = 12.dp)
            )
        }

        Text(
            text = product.title,
            color = Color(0xFF111111),
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.padding(top = if (product.isBest) 4.dp else 12.dp)
        )
        Text(
            text = product.description,
            color = Color(0xFF767676),
            fontSize = 13.sp,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.padding(top = 6.dp)
        )
        Text(
            text = product.colornum,
            color = Color(0xFF767676),
            fontSize = 13.sp,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
        Text(
            text = product.price,
            color = Color(0xFF111111),
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(top = 8.dp)
        )
    }
}
