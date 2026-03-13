package com.example.dailyxp.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable

private val DarkColorScheme = darkColorScheme(
    primary = Teal,
    background = BgDark,
    surface = Surface,
    onPrimary = BgDark,
    onBackground = TextPrimary,
    onSurface = TextPrimary,
)

@Composable
fun DailyXpTheme(
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = DarkColorScheme,
        typography = Typography,
        content = content
    )
}