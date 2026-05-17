package com.example.deku.feature.home

import android.app.Activity
import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
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
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

private const val BACK_PRESS_INTERVAL_MILLIS = 2_000L

@Composable
fun HomeScreen(
    title: String,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val activity = context as? Activity
    var lastBackPressedAt by remember { mutableStateOf(0L) }
    val today = remember { currentKoreanDate() }

    BackHandler {
        val now = System.currentTimeMillis()
        if (now - lastBackPressedAt <= BACK_PRESS_INTERVAL_MILLIS) {
            activity?.finish()
        } else {
            lastBackPressedAt = now
            Toast.makeText(context, "한 번 더 누르면 종료됩니다.", Toast.LENGTH_SHORT).show()
        }
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(horizontal = 24.dp)
            .padding(top = 50.dp)
    ) {
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
        Spacer(modifier = Modifier.height(80.dp))
        Image(
            painter = painterResource(id = R.drawable.nike_logo),
            contentDescription = stringResource(id = R.string.home_brand_logo),
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .size(220.dp)
        )
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
        HomeScreen(title = SPLASH_HOME_TITLE)
    }
}
