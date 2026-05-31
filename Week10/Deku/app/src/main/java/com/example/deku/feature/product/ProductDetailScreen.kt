// 상품 상세 화면 UI로, 상품 정보와 위시리스트 추가/제거 동작을 제공합니다.

package com.example.deku.feature.product

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.deku.R
import com.example.deku.core.designsystem.ColorDivider
import com.example.deku.core.designsystem.ColorTextPrimary
import com.example.deku.core.designsystem.ColorTextSecondary
import com.example.deku.core.designsystem.theme.DekuTheme
import com.example.deku.data.ProductCatalog
import com.example.deku.data.ProductItem

@Composable
fun ProductDetailScreen(
    product: ProductItem?,
    onBackClick: () -> Unit,
    onWishClick: (ProductItem) -> Unit,
    modifier: Modifier = Modifier
) {
    if (product == null) {
        // 상세 route에는 id만 전달되므로, 목록에서 못 찾는 예외 상황을 별도 화면으로 처리합니다.
        ProductNotFoundScreen(
            onBackClick = onBackClick,
            modifier = modifier
        )
        return
    }

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(Color.White),
        contentPadding = PaddingValues(
            start = 24.dp,
            top = 28.dp,
            end = 24.dp,
            bottom = 32.dp
        )
    ) {
        // 상세 화면은 긴 설명까지 포함하므로 전체를 LazyColumn에 넣어 작은 화면에서도 스크롤되게 합니다.
        item(contentType = "detail") {
            ProductDetailTopBar(
                title = product.name,
                onBackClick = onBackClick
            )
            Spacer(modifier = Modifier.height(28.dp))
            ProductImage(
                product = product,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(320.dp),
                imagePadding = 18.dp
            )
            Spacer(modifier = Modifier.height(24.dp))
            Text(
                text = product.name,
                color = ColorTextPrimary,
                fontSize = 28.sp,
                lineHeight = 34.sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = product.price,
                color = ColorTextSecondary,
                fontSize = 16.sp,
                lineHeight = 22.sp
            )
            Spacer(modifier = Modifier.height(28.dp))
            Text(
                text = product.description,
                color = ColorTextSecondary,
                fontSize = 15.sp,
                lineHeight = 24.sp
            )
            Spacer(modifier = Modifier.height(28.dp))
            DetailSecondaryButton(
                text = "사이즈 선택",
                onClick = {}
            )
            Spacer(modifier = Modifier.height(28.dp))
            Button(
                onClick = {},
                modifier = Modifier
                    .fillMaxWidth()
                    .height(54.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Black,
                    contentColor = Color.White
                ),
                shape = RoundedCornerShape(14.dp)
            ) {
                Text(
                    text = "장바구니 추가",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
            }
            Spacer(modifier = Modifier.height(28.dp))
            DetailSecondaryButton(
                text = if (product.isWish) {
                    "위시리스트 제거"
                } else {
                    "위시리스트 추가"
                },
                onClick = { onWishClick(product) }
            )
        }
    }
}

@Composable
private fun ProductDetailTopBar(
    title: String,
    onBackClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(40.dp),
        contentAlignment = Alignment.Center
    ) {
        IconButton(
            onClick = onBackClick,
            modifier = Modifier
                .align(Alignment.CenterStart)
                .size(40.dp)
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_arrow_back),
                contentDescription = "뒤로 가기",
                tint = ColorTextPrimary
            )
        }
        Text(
            text = title,
            color = ColorTextPrimary,
            fontSize = 20.sp,
            lineHeight = 24.sp,
            fontWeight = FontWeight.Bold,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 48.dp)
        )
    }
}

@Composable
private fun DetailSecondaryButton(
    text: String,
    onClick: () -> Unit
) {
    OutlinedButton(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .height(54.dp),
        colors = ButtonDefaults.outlinedButtonColors(
            containerColor = Color.White,
            contentColor = ColorTextPrimary
        ),
        border = BorderStroke(1.dp, ColorDivider),
        shape = RoundedCornerShape(14.dp)
    ) {
        Text(
            text = text,
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
private fun ProductNotFoundScreen(
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(start = 24.dp, top = 28.dp, end = 24.dp)
    ) {
        ProductDetailTopBar(
            title = "상품 상세",
            onBackClick = onBackClick
        )
        Text(
            text = "상품을 찾을 수 없습니다.",
            color = ColorTextSecondary,
            fontSize = 16.sp,
            modifier = Modifier.align(Alignment.Center)
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun ProductDetailScreenPreview() {
    DekuTheme {
        ProductDetailScreen(
            product = ProductCatalog.initialProducts().first(),
            onBackClick = {},
            onWishClick = {}
        )
    }
}
