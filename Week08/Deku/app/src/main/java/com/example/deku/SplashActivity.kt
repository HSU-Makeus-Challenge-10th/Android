package com.example.deku

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.deku.core.common.EXTRA_HOME_TITLE
import com.example.deku.core.common.SPLASH_HOME_TITLE
import com.example.deku.core.designsystem.theme.DekuTheme
import com.example.deku.feature.splash.SplashScreen

class SplashActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            DekuTheme {
                SplashScreen(
                    onTimeout = {
                        val intent = Intent(this, MainActivity::class.java).apply {
                            // Activity 간 데이터 전달: MainActivity가 이 값을 읽어 Home route의 인자로 사용합니다.
                            putExtra(EXTRA_HOME_TITLE, SPLASH_HOME_TITLE)
                        }
                        startActivity(intent)
                        finish()
                    }
                )
            }
        }
    }
}
