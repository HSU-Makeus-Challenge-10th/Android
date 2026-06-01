// 스플래시 Compose 화면으로, 일정 시간 후 상위 Activity에 전환 요청을 보냅니다.

package com.example.deku.feature.splash

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.deku.R
import com.example.deku.core.common.SPLASH_HOME_TITLE
import com.example.deku.core.designsystem.theme.DekuTheme
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(onTimeout: () -> Unit) {
    // Unit key를 사용해 최초 진입 시 한 번만 타이머가 시작되도록 합니다.
    LaunchedEffect(Unit) {
        delay(2_000)
        onTimeout()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = SPLASH_HOME_TITLE,
            color = Color(0xFF111111),
            fontSize = 50.sp,
            lineHeight = 58.sp,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(16.dp))
        Image(
            painter = painterResource(id = R.drawable.splash_logo),
            contentDescription = stringResource(id = R.string.splash_brand_logo),
            modifier = Modifier.size(width = 100.dp, height = 80.dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun SplashScreenPreview() {
    DekuTheme {
        SplashScreen(onTimeout = {})
    }
}
