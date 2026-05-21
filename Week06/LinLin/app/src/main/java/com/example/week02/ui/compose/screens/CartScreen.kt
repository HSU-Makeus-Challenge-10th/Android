package com.example.week02.ui.compose.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.week02.R

@Composable
fun CartScreen(
    onOrder: () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 20.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Spacer(modifier = Modifier.weight(1f))

        Icon(
            painter = painterResource(id = R.drawable.ic_cart_empty),
            contentDescription = "empty cart",
            modifier = Modifier.size(56.dp),
            tint = Color.Black,
        )
        Spacer(modifier = Modifier.height(14.dp))
        Text(
            text = "장바구니가 비어 있습니다.",
            style = MaterialTheme.typography.bodyLarge,
            fontWeight = FontWeight.SemiBold,
            color = Color.Black,
        )
        Spacer(modifier = Modifier.height(6.dp))
        Text(
            text = "제품을 추가하면 여기에 표시됩니다.",
            style = MaterialTheme.typography.bodyMedium,
            color = Color(0xFF8E8E8E),
        )

        Spacer(modifier = Modifier.weight(1f))

        Button(
            onClick = onOrder,
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
                .padding(bottom = 10.dp),
            shape = RoundedCornerShape(28.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color.Black),
        ) {
            Text(text = "주문하기", color = Color.White, fontWeight = FontWeight.SemiBold)
        }
    }
}

