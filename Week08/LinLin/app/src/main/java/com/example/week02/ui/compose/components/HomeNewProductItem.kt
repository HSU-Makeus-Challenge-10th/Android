package com.example.week02.ui.compose.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.Image
import com.example.week02.R
import com.example.week02.data.ProductItem

@Composable
fun HomeNewProductItem(
    product: ProductItem,
    modifier: Modifier = Modifier,
) {
    Surface(
        modifier = modifier.width(180.dp),
        shape = RoundedCornerShape(8.dp),
        color = MaterialTheme.colorScheme.surface,
        tonalElevation = 0.dp,
    ) {
        Column {
            Image(
                painter = painterResource(id = homeRowDrawable(product.id)),
                contentDescription = product.name,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .clip(RoundedCornerShape(topStart = 8.dp, topEnd = 8.dp)),
                contentScale = ContentScale.Crop,
            )
            Text(
                text = product.name,
                modifier = Modifier.padding(horizontal = 8.dp, vertical = 8.dp),
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.Bold,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
            )
            Text(
                text = product.price,
                modifier = Modifier.padding(start = 8.dp, end = 8.dp, bottom = 12.dp),
                style = MaterialTheme.typography.bodySmall,
            )
        }
    }
}

private fun homeRowDrawable(productId: Int): Int = when (productId) {
    1 -> R.drawable.img_air_jordan_xxxvi
    2 -> R.drawable.img_nike_air_force_1_07
    3 -> R.drawable.img_nike_elite_crew
    4 -> R.drawable.img_nike_everyday_plus_cushioned
    5 -> R.drawable.img_jordan_nike_af1_07_essentials
    else -> R.drawable.bg_product_placeholder
}
