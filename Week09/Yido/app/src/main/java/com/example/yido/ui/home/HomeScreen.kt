package com.example.yido.ui.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
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
import com.example.yido.R
import com.example.yido.data.model.Product

@Composable
fun HomeScreen(viewModel: HomeViewModel = hiltViewModel()) {
    val products by viewModel.products.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .verticalScroll(rememberScrollState())
            .padding(top = 24.dp)
    ) {
        Text(
            text = "Discover",
            fontSize = 32.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black,
            modifier = Modifier.padding(horizontal = 24.dp)
        )
        Text(
            text = "9월 4일 목요일",
            fontSize = 14.sp,
            color = Color(0xFF999999),
            modifier = Modifier
                .padding(horizontal = 24.dp)
                .padding(top = 4.dp)
        )
        Image(
            painter = painterResource(R.drawable.img_nike_hero),
            contentDescription = "Discover",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp)
                .padding(top = 24.dp)
        )
        Text(
            text = "What's new",
            fontSize = 13.sp,
            color = Color(0xFF999999),
            modifier = Modifier
                .padding(horizontal = 24.dp)
                .padding(top = 24.dp)
        )
        Text(
            text = "나이키 최신 상품",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black,
            modifier = Modifier
                .padding(horizontal = 24.dp)
                .padding(top = 4.dp)
        )
        LazyRow(
            contentPadding = PaddingValues(start = 24.dp, top = 16.dp, end = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(products, key = { it.name }) { product ->
                HomeProductItem(product)
            }
        }
        Spacer(modifier = Modifier.height(200.dp))
    }
}

@Composable
private fun HomeProductItem(product: Product) {
    Column(modifier = Modifier.width(200.dp)) {
        Image(
            painter = painterResource(product.imageResId),
            contentDescription = product.name,
            contentScale = ContentScale.Crop,
            modifier = Modifier.size(200.dp)
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
            fontSize = 13.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black
        )
        Spacer(modifier = Modifier.height(8.dp))
    }
}
