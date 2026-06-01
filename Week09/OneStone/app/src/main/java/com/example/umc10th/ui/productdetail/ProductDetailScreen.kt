package com.example.umc10th.ui.productdetail

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.umc10th.R

data class ProductDetailUiState(
    val title: String,
    val imageResId: Int,
    val description: String,
    val price: String
)

@Composable
fun ProductDetailScreen(
    product: ProductDetailUiState,
    onBackClick: () -> Unit
) {
    val imageResId = product.imageResId.takeIf {
        it == R.drawable.socks1 ||
            it == R.drawable.socks2 ||
            it == R.drawable.shoes1 ||
            it == R.drawable.shoes2
    } ?: R.drawable.shoes1

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .verticalScroll(rememberScrollState())
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp, top = 16.dp, end = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = R.drawable.icon_back),
                contentDescription = null,
                modifier = Modifier
                    .size(40.dp)
                    .padding(8.dp)
                    .clickable { onBackClick() }
            )
            Text(
                text = product.title,
                color = Color(0xFF111111),
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                textAlign = TextAlign.Center,
                modifier = Modifier.weight(1f)
            )
            Image(
                painter = painterResource(id = R.drawable.ic_list_magnifying_glass),
                contentDescription = null,
                modifier = Modifier
                    .size(40.dp)
                    .padding(8.dp)
            )
        }

        Image(
            painter = painterResource(id = imageResId),
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth()
                .height(240.dp)
                .padding(start = 24.dp, top = 24.dp, end = 24.dp),
            contentScale = ContentScale.Crop
        )

        Text(
            text = product.title,
            color = Color(0xFF111111),
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(start = 24.dp, top = 20.dp, end = 24.dp)
        )
        Text(
            text = product.description,
            color = Color(0xFF767676),
            fontSize = 16.sp,
            modifier = Modifier.padding(start = 24.dp, top = 12.dp, end = 24.dp)
        )
        Text(
            text = product.price,
            color = Color(0xFF111111),
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(start = 24.dp, top = 16.dp, end = 24.dp)
        )
        Text(
            text = "The Nike Everyday Plus Cushioned Socks bring comfort to your workout with extra cushioning under the heel and forefoot and a snug, supportive arch band. Sweat-wicking power and breathability up top help keep your feet dry and cool to help push you through that extra set.\n\nShown: Multi-Color\nStyle: SX6897-965",
            color = Color(0xFF444444),
            fontSize = 15.sp,
            lineHeight = 22.sp,
            modifier = Modifier.padding(start = 24.dp, top = 24.dp, end = 24.dp)
        )

        ProductDetailButton(text = "Select Size")
        ProductDetailButton(text = "Add to Cart", backgroundColor = Color(0xFF111111), textColor = Color.White)
        ProductDetailButton(text = "Wishlist")
        Spacer(modifier = Modifier.height(24.dp))
    }
}

@Composable
private fun ProductDetailButton(
    text: String,
    backgroundColor: Color = Color(0xFFE4E4E4),
    textColor: Color = Color.Black
) {
    Text(
        text = text,
        color = textColor,
        fontSize = 14.sp,
        textAlign = TextAlign.Center,
        modifier = Modifier
            .padding(start = 24.dp, top = 12.dp, end = 24.dp)
            .fillMaxWidth()
            .height(56.dp)
            .background(backgroundColor)
            .padding(top = 18.dp)
    )
}
