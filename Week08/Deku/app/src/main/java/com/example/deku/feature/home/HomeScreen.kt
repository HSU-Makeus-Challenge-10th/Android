package com.example.deku.feature.home

import android.app.Activity
import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.deku.R
import com.example.deku.core.common.SPLASH_HOME_TITLE
import com.example.deku.core.designsystem.ColorTextPrimary
import com.example.deku.core.designsystem.ColorTextSecondary
import com.example.deku.core.designsystem.theme.DekuTheme
import com.example.deku.data.ProductCatalog
import com.example.deku.data.ProductItem
import com.example.deku.feature.product.HomeProductCard
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import kotlinx.coroutines.launch

private const val BACK_PRESS_INTERVAL_MILLIS = 2_000L

@Composable
fun HomeScreen(
    title: String,
    products: List<ProductItem>,
    onProductClick: (ProductItem) -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val activity = context as? Activity
    var lastBackPressedAt by remember { mutableStateOf(0L) }
    val today = remember { currentKoreanDate() }
    val listState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()
    // 스크롤 값은 자주 바뀌므로 derivedStateOf로 버튼 노출 조건이 변할 때만 재구성합니다.
    val showScrollToTop by remember {
        derivedStateOf {
            listState.firstVisibleItemIndex > 2
        }
    }

    // Compose에서는 BackHandler로 시스템 뒤로가기를 가로채 2초 안에 두 번 누르면 Activity를 종료합니다.
    BackHandler {
        val now = System.currentTimeMillis()
        if (now - lastBackPressedAt <= BACK_PRESS_INTERVAL_MILLIS) {
            activity?.finish()
        } else {
            lastBackPressedAt = now
            Toast.makeText(context, "한 번 더 누르면 종료됩니다.", Toast.LENGTH_SHORT).show()
        }
    }

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        LazyColumn(
            state = listState,
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(
                start = 24.dp,
                top = 50.dp,
                end = 24.dp,
                bottom = 32.dp
            )
        ) {
            item(contentType = "header") {
                Text(
                    text = title,
                    color = ColorTextPrimary,
                    fontSize = 30.sp,
                    lineHeight = 36.sp,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(10.dp))
                Text(
                    text = today,
                    color = ColorTextSecondary,
                    fontSize = 20.sp,
                    lineHeight = 26.sp
                )
            }

            item(contentType = "logo") {
                Spacer(modifier = Modifier.height(48.dp))
                Image(
                    painter = painterResource(id = R.drawable.nike_logo),
                    contentDescription = stringResource(id = R.string.home_brand_logo),
                    modifier = Modifier
                        .fillParentMaxWidth()
                        .height(220.dp)
                        .padding(horizontal = 36.dp)
                )
            }

            item(contentType = "latestTitle") {
                Spacer(modifier = Modifier.height(28.dp))
                Text(
                    text = "What's New",
                    color = ColorTextPrimary,
                    fontSize = 22.sp,
                    lineHeight = 28.sp,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(6.dp))
                Text(
                    text = "나이키 최신 상품",
                    color = ColorTextSecondary,
                    fontSize = 15.sp,
                    lineHeight = 20.sp
                )
                Spacer(modifier = Modifier.height(18.dp))
            }

            item(contentType = "latestProducts") {
                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    contentPadding = PaddingValues(end = 8.dp)
                ) {
                    items(
                        items = ProductCatalog.latestProducts(products),
                        // Lazy item key는 RecyclerView의 stable id처럼 아이템 상태를 안정적으로 묶어줍니다.
                        key = { product -> product.id },
                        contentType = { "latestProduct" }
                    ) { product ->
                        HomeProductCard(
                            product = product,
                            onClick = onProductClick
                        )
                    }
                }
                Spacer(modifier = Modifier.height(18.dp))
            }
        }

        if (showScrollToTop) {
            Button(
                onClick = {
                    coroutineScope.launch {
                        listState.animateScrollToItem(index = 0)
                    }
                },
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(end = 20.dp, bottom = 20.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Black,
                    contentColor = Color.White
                )
            ) {
                Text(
                    text = "맨 위로",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

private fun currentKoreanDate(): String {
    val formatter = SimpleDateFormat("M월 d일 EEEE", Locale.KOREAN)
    return formatter.format(Date())
}

@Preview(showBackground = true)
@Composable
private fun HomeScreenPreview() {
    DekuTheme {
        HomeScreen(
            title = SPLASH_HOME_TITLE,
            products = ProductCatalog.initialProducts(),
            onProductClick = {}
        )
    }
}
