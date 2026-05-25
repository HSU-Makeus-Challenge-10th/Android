package com.example.week02.ui.compose.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.week02.R
import com.example.week02.ui.compose.components.HomeNewProductItem
import com.example.week02.ui.home.HomeViewModel
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

@Composable
fun HomeScreen(
    onNavigateCart: () -> Unit,
    viewModel: HomeViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val today = SimpleDateFormat("M월 d일 EEEE", Locale.KOREA).format(Calendar.getInstance().time)

    LazyColumn(
        contentPadding = PaddingValues(bottom = 24.dp),
    ) {
        item {
            Text(
                text = "Discover",
                modifier = Modifier.padding(start = 24.dp, top = 16.dp, end = 24.dp),
                style = MaterialTheme.typography.headlineLarge,
                fontWeight = FontWeight.SemiBold,
            )
        }
        item {
            Text(
                text = today,
                modifier = Modifier.padding(start = 24.dp, top = 6.dp, end = 24.dp),
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
            )
        }
        item {
            Spacer(modifier = Modifier.height(18.dp))
            Surface(
                modifier = Modifier
                    .padding(horizontal = 24.dp)
                    .fillMaxWidth()
                    .height(380.dp),
                shape = RoundedCornerShape(2.dp),
                tonalElevation = 0.dp,
            ) {
                Image(
                    painter = painterResource(id = R.drawable.img_home_banner),
                    contentDescription = "home banner",
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(380.dp)
                        .clip(RoundedCornerShape(2.dp)),
                    contentScale = ContentScale.Crop,
                )
            }
        }
        item {
            Text(
                text = "What's new",
                modifier = Modifier.padding(start = 24.dp, top = 24.dp),
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
            )
        }
        item {
            Text(
                text = "나이키 최신 상품",
                modifier = Modifier.padding(start = 24.dp, top = 4.dp),
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
            )
        }
        item {
            LazyRow(
                modifier = Modifier.padding(top = 12.dp),
                contentPadding = PaddingValues(start = 24.dp, end = 8.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
            ) {
                items(
                    items = uiState.products,
                    key = { product -> product.id },
                ) { product ->
                    HomeNewProductItem(product = product)
                }
            }
        }
    }
}
