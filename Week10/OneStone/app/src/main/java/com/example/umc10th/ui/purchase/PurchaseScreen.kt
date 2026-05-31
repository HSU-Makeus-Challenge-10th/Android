package com.example.umc10th.ui.purchase

import androidx.compose.foundation.background
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
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
import kotlinx.coroutines.launch

@Composable
fun PurchaseScreen(
    viewModel: PurchaseViewModel,
    onProductClick: (PurchaseProduct) -> Unit
) {
    var selectedTabIndex by remember { mutableIntStateOf(0) }
    val uiState by viewModel.uiState.collectAsState()
    val gridState = rememberLazyGridState()
    val coroutineScope = rememberCoroutineScope()
    val showScrollToTop by remember {
        derivedStateOf {
            gridState.firstVisibleItemIndex > 3 ||
                gridState.firstVisibleItemScrollOffset > 600
        }
    }
    val tabs = listOf("전체", "Tops & T-Shirts", "sale")

    LaunchedEffect(Unit) {
        viewModel.loadPurchaseProducts()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        TabRow(
            selectedTabIndex = selectedTabIndex,
            containerColor = Color.White,
            contentColor = Color.Black,
            indicator = { tabPositions ->
                TabRowDefaults.SecondaryIndicator(
                    modifier = Modifier.tabIndicatorOffset(tabPositions[selectedTabIndex]),
                    color = Color.Black
                )
            },
            divider = {
                HorizontalDivider(color = Color(0xFFEAEAEA), thickness = 1.dp)
            }
        ) {
            tabs.forEachIndexed { index, title ->
                Tab(
                    selected = selectedTabIndex == index,
                    onClick = { selectedTabIndex = index },
                    text = {
                        Text(
                            text = title,
                            color = if (selectedTabIndex == index) Color.Black else Color(0xFF8A8A8A),
                            fontSize = 14.sp,
                            fontWeight = if (selectedTabIndex == index) {
                                FontWeight.SemiBold
                            } else {
                                FontWeight.Normal
                            }
                        )
                    }
                )
            }
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        ) {
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                state = gridState,
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(
                    start = 16.dp,
                    top = 20.dp,
                    end = 16.dp,
                    bottom = 84.dp
                ),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalArrangement = Arrangement.spacedBy(24.dp)
            ) {
                items(
                    items = uiState.products,
                    key = { product -> product.id }
                ) { product ->
                    PurchaseProductItem(
                        product = product,
                        onClick = { onProductClick(product) },
                        onWishlistClick = { viewModel.toggleWishlist(product.id) }
                    )
                }
            }

            if (showScrollToTop) {
                Button(
                    onClick = {
                        coroutineScope.launch {
                            gridState.animateScrollToItem(0)
                        }
                    },
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .offset(y = (-20).dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Black,
                        contentColor = Color.White
                    )
                ) {
                    Text(text = "맨 위로", fontSize = 14.sp)
                }
            }
        }
    }
}

@Composable
private fun PurchaseProductItem(
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
                painter = painterResource(
                    id = if (product.isWishlisted) {
                        R.drawable.img_filled_heart
                    } else {
                        R.drawable.img_blank_heart
                    }
                ),
                contentDescription = null,
                modifier = Modifier
                    .padding(top = 6.dp, end = 6.dp)
                    .size(34.dp)
                    .align(androidx.compose.ui.Alignment.TopEnd)
                    .clickable { onWishlistClick() }
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
