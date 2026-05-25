package com.example.week02.ui.compose.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
fun ProfileScreen() {
    Column(modifier = Modifier.fillMaxSize()) {
        Text(
            text = "프로필",
            modifier = Modifier.padding(horizontal = 20.dp, vertical = 16.dp),
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.SemiBold,
        )
        Spacer(modifier = Modifier.weight(1f))
    }
}

