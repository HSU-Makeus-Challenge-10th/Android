package com.example.deku.feature.shop

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.deku.core.designsystem.ColorNavUnselected
import com.example.deku.core.designsystem.ColorTextPrimary
import com.example.deku.core.designsystem.theme.DekuTheme
import com.example.deku.data.ProductCatalog
import com.example.deku.data.ProductItem
import com.example.deku.feature.product.ProductGridCard

@Composable
fun ShopScreen(
    products: List<ProductItem>,
    onProductClick: (ProductItem) -> Unit,
    onWishClick: (ProductItem) -> Unit,
    modifier: Modifier = Modifier
) {
    val tabs = listOf(
        "전체" to null,
        ProductCatalog.CATEGORY_TOPS to ProductCatalog.CATEGORY_TOPS,
        ProductCatalog.CATEGORY_SHOES to ProductCatalog.CATEGORY_SHOES
    )
    var selectedTabIndex by remember { mutableStateOf(0) }
    val selectedCategory = tabs[selectedTabIndex].second
    val filteredProducts = ProductCatalog.productsByCategory(products, selectedCategory)

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(horizontal = 24.dp)
            .padding(top = 40.dp)
    ) {
        TabRow(
            selectedTabIndex = selectedTabIndex,
            containerColor = Color.White,
            contentColor = ColorTextPrimary
        ) {
            tabs.forEachIndexed { index, tab ->
                val title = tab.first

                Tab(
                    selected = selectedTabIndex == index,
                    onClick = { selectedTabIndex = index },
                    selectedContentColor = ColorTextPrimary,
                    unselectedContentColor = ColorNavUnselected,
                    text = {
                        Text(
                            text = title,
                            fontSize = if (title.length > 8) 13.sp else 14.sp,
                            lineHeight = 18.sp,
                            fontWeight = if (selectedTabIndex == index) {
                                FontWeight.Bold
                            } else {
                                FontWeight.Normal
                            },
                            maxLines = 1
                        )
                    }
                )
            }
        }

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
            // Grid에서도 items + key를 지정해 상품 순서가 바뀌어도 아이템 상태가 흔들리지 않게 합니다.
            items(
                items = filteredProducts,
                key = { product -> product.id },
                contentType = { "shopProduct" }
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

@Preview(showBackground = true)
@Composable
private fun ShopScreenPreview() {
    DekuTheme {
        ShopScreen(
            products = ProductCatalog.initialProducts(),
            onProductClick = {},
            onWishClick = {}
        )
    }
}
