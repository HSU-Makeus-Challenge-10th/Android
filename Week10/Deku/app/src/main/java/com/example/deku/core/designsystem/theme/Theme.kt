// 앱 전체 MaterialTheme을 설정하는 Compose 테마 파일입니다.

package com.example.deku.core.designsystem.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

private val DarkColorScheme = darkColorScheme(
    primary = NikeWhite,
    secondary = NikeGray,
    tertiary = NikeFrameBackground,
    background = NikeBlack,
    surface = NikeBlack,
    onPrimary = NikeBlack,
    onSecondary = NikeWhite,
    onTertiary = NikeBlack,
    onBackground = NikeWhite,
    onSurface = NikeWhite
)

private val LightColorScheme = lightColorScheme(
    primary = NikeBlack,
    secondary = NikeDarkGray,
    tertiary = NikeFrameBackground,
    background = NikeWhite,
    surface = NikeWhite,
    onPrimary = NikeWhite,
    onSecondary = NikeWhite,
    onTertiary = NikeBlack,
    onBackground = NikeBlack,
    onSurface = NikeBlack
)

@Composable
fun DekuTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    // 브랜드 색상을 유지하기 위해 dynamicColor 기본값은 false로 둡니다.
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
