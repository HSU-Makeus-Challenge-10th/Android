package com.example.deku.feature.wishlist

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.deku.core.designsystem.ColorTextPrimary
import com.example.deku.core.designsystem.ColorTextSecondary
import com.example.deku.core.designsystem.theme.DekuTheme
import com.example.deku.data.ProductCatalog
import com.example.deku.data.ProductItem
import com.example.deku.feature.product.ProductGridCard

@Composable
fun WishListScreen(
    products: List<ProductItem>,
    onProductClick: (ProductItem) -> Unit,
    onWishClick: (ProductItem) -> Unit,
    modifier: Modifier = Modifier
) {
    val wishProducts = products.filter { it.isWish }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(horizontal = 24.dp)
            .padding(top = 70.dp)
    ) {
        Text(
            text = "위시리스트",
            color = ColorTextPrimary,
            fontSize = 30.sp,
            lineHeight = 36.sp,
            fontWeight = FontWeight.Bold
        )

        if (wishProducts.isEmpty()) {
            Spacer(modifier = Modifier.height(120.dp))
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.TopCenter
            ) {
                Text(
                    text = "관심 상품이 없습니다\n마음에 드는 상품을 저장해보세요.",
                    color = ColorTextSecondary,
                    textAlign = TextAlign.Center,
                    fontSize = 16.sp,
                    lineHeight = 24.sp
                )
            }
        } else {
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 20.dp),
                contentPadding = PaddingValues(
                    start = 12.dp,
                    end = 12.dp,
                    bottom = 24.dp
                ),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                horizontalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                // 위시리스트는 원본 상품 상태에서 파생된 목록이므로 id를 key로 사용합니다.
                items(
                    items = wishProducts,
                    key = { product -> product.id },
                    contentType = { "wishProduct" }
                ) { product ->
                    ProductGridCard(
                        product = product,
                        onClick = onProductClick,
                        onWishClick = onWishClick
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun WishListScreenPreview() {
    DekuTheme {
        WishListScreen(
            products = ProductCatalog.initialProducts().mapIndexed { index, product ->
                if (index < 3) product.copy(isWish = true) else product
            },
            onProductClick = {},
            onWishClick = {}
        )
    }
}
