package com.example.week10

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import com.example.week10.ui.mypage.MyPageScreen
import com.example.week10.ui.theme.Week10Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Week10Theme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    MyPageScreen(
                        modifier = Modifier.padding(innerPadding),
                    )
                }
            }
        }
    }
}
