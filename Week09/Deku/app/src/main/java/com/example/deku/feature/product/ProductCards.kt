// 홈/쇼핑/위시리스트에서 재사용하는 상품 카드와 상품 이미지 컴포넌트 모음입니다.

package com.example.deku.feature.product

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import com.example.deku.R
import com.example.deku.core.designsystem.ColorFrameBackground
import com.example.deku.core.designsystem.ColorTextPrimary
import com.example.deku.core.designsystem.ColorTextSecondary
import com.example.deku.data.ProductItem

@Composable
fun HomeProductCard(
    product: ProductItem,
    onClick: (ProductItem) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .width(220.dp)
            .clip(RoundedCornerShape(8.dp))
            .clickable { onClick(product) }
            .padding(14.dp)
    ) {
        ProductImage(
            product = product,
            modifier = Modifier
                .fillMaxWidth()
                .height(140.dp),
            imagePadding = 20.dp
        )
        Spacer(modifier = Modifier.height(14.dp))
        Text(
            text = product.name,
            color = ColorTextPrimary,
            fontSize = 16.sp,
            lineHeight = 20.sp,
            fontWeight = FontWeight.Bold,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis
        )
        Spacer(modifier = Modifier.height(6.dp))
        Text(
            text = product.price,
            color = ColorTextSecondary,
            fontSize = 14.sp,
            lineHeight = 18.sp
        )
    }
}

@Composable
fun ProductListRow(
    product: ProductItem,
    onClick: (ProductItem) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(8.dp))
            .clickable { onClick(product) }
            .padding(vertical = 10.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        ProductImage(
            product = product,
            modifier = Modifier.size(width = 112.dp, height = 92.dp),
            imagePadding = 14.dp
        )
        Spacer(modifier = Modifier.width(16.dp))
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = product.name,
                color = ColorTextPrimary,
                fontSize = 16.sp,
                lineHeight = 20.sp,
                fontWeight = FontWeight.Bold,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
            Spacer(modifier = Modifier.height(6.dp))
            Text(
                text = product.category,
                color = ColorTextSecondary,
                fontSize = 13.sp,
                lineHeight = 17.sp
            )
            Spacer(modifier = Modifier.height(2.dp))
            Text(
                text = "${product.colorCount} Color",
                color = ColorTextSecondary,
                fontSize = 13.sp,
                lineHeight = 17.sp
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = product.price,
                color = ColorTextPrimary,
                fontSize = 16.sp,
                lineHeight = 20.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Composable
fun ProductGridCard(
    product: ProductItem,
    onClick: (ProductItem) -> Unit,
    onWishClick: (ProductItem) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .clip(RoundedCornerShape(8.dp))
            .clickable { onClick(product) }
            .padding(12.dp)
    ) {
        Box {
            ProductImage(
                product = product,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(132.dp),
                imagePadding = 18.dp
            )
            Surface(
                // 카드 전체 클릭과 위시 버튼 클릭 영역을 분리해 상세 이동과 찜 변경을 구분합니다.
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(10.dp)
                    .size(26.dp)
                    .clip(CircleShape)
                    .clickable { onWishClick(product) },
                shape = CircleShape,
                color = Color.White
            ) {
                Icon(
                    painter = painterResource(
                        id = if (product.isWish) {
                            R.drawable.heart_filled
                        } else {
                            R.drawable.heart
                        }
                    ),
                    contentDescription = if (product.isWish) {
                        "위시리스트 제거"
                    } else {
                        "위시리스트 추가"
                    },
                    tint = Color.Unspecified,
                    modifier = Modifier.padding(6.dp)
                )
            }
        }
        Spacer(modifier = Modifier.height(12.dp))
        Text(
            text = product.name,
            color = ColorTextPrimary,
            fontSize = 15.sp,
            lineHeight = 19.sp,
            fontWeight = FontWeight.Bold,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis
        )
        Spacer(modifier = Modifier.height(6.dp))
        Text(
            text = product.category,
            color = ColorTextSecondary,
            fontSize = 12.sp,
            lineHeight = 16.sp,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
        Spacer(modifier = Modifier.height(2.dp))
        Text(
            text = "${product.colorCount} Color",
            color = ColorTextSecondary,
            fontSize = 12.sp,
            lineHeight = 16.sp
        )
        Spacer(modifier = Modifier.height(10.dp))
        Text(
            text = product.price,
            color = ColorTextPrimary,
            fontSize = 16.sp,
            lineHeight = 20.sp,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
fun ProductImage(
    product: ProductItem,
    modifier: Modifier = Modifier,
    imagePadding: androidx.compose.ui.unit.Dp = 18.dp
) {
    // 상품 이미지 배경과 padding을 공통화해 카드 종류가 달라도 이미지 톤을 맞춥니다.
    Box(
        modifier = modifier.background(ColorFrameBackground),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(id = product.imageResId),
            contentDescription = product.name,
            contentScale = ContentScale.Fit,
            modifier = Modifier
                .fillMaxSize()
                .padding(imagePadding)
        )
    }
}
