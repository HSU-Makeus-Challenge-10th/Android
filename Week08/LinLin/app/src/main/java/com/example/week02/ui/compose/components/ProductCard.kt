package com.example.week02.ui.compose.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
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
import androidx.annotation.DrawableRes

@Composable
fun ProductCard(
    name: String,
    price: String,
    @DrawableRes imageResId: Int,
    modifier: Modifier = Modifier,
) {
    Surface(
        modifier = modifier.width(180.dp),
        shape = RoundedCornerShape(16.dp),
        tonalElevation = 1.dp,
    ) {
        Column(modifier = Modifier.background(MaterialTheme.colorScheme.surface)) {
            Image(
                painter = painterResource(id = imageResId),
                contentDescription = name,
                modifier = Modifier
                    .size(width = 180.dp, height = 180.dp)
                    .clip(RoundedCornerShape(16.dp)),
                contentScale = ContentScale.Crop,
            )
            Column(modifier = Modifier.width(180.dp)) {
                Spacer(modifier = Modifier.height(10.dp))
                Text(
                    text = name,
                    modifier = Modifier,
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.SemiBold,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = price,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                )
                Spacer(modifier = Modifier.height(12.dp))
            }
        }
    }
}

