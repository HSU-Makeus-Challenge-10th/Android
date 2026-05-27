package com.example.deku

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.deku.core.common.DEFAULT_HOME_TITLE
import com.example.deku.core.common.EXTRA_HOME_TITLE
import com.example.deku.core.designsystem.theme.DekuTheme
import com.example.deku.feature.main.MainScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        // Splash에서 전달한 title을 HomeScreen까지 내려보내는 7주차 시니어 미션 흐름입니다.
        val homeTitle = intent.getStringExtra(EXTRA_HOME_TITLE) ?: DEFAULT_HOME_TITLE

        setContent {
            DekuTheme {
                MainScreen(homeTitle = homeTitle)
            }
        }
    }
}
