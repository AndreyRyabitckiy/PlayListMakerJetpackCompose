package com.example.playlistmakerjetpackcompose.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import com.example.playlistmakerjetpackcompose.R
import kotlin.math.absoluteValue

private val DarkColorScheme = darkColorScheme(
    onBackground = Color(0xFFFFFFFF),
    background = Color(0xFF1A1B22),
    primary = Color(0xFFFFFFFF),
    secondary = Color(0xFF535459),
    onPrimary = Color(0xFFFFFFFF),
    onSecondary = Color(0xFFAEAFB4)
)

private val LightColorScheme = lightColorScheme(
    onBackground = Color(0xFF1A1B22),
    background = Color(0xFFFFFFFF),
    primary = Color(0xFFAEAFB4),
    secondary = Color(0xFFc5c6c8),
    onPrimary = Color(0xFF535459),
    onSecondary = Color(0xFFAEAFB4)
)

@Composable
fun PlayListMakerJetpackComposeTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
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