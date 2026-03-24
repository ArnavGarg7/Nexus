package com.nexus.app.presentation.theme

import android.app.Activity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

private val DarkColorScheme = darkColorScheme(
    primary          = NexusRed,
    onPrimary        = NexusWhite,
    primaryContainer = NexusRedDark,
    secondary        = NexusGold,
    onSecondary      = NexusNavy,
    background       = DarkBackground,
    onBackground     = NexusWhite,
    surface          = DarkSurface,
    onSurface        = DarkOnSurface,
    surfaceVariant   = DarkSurface2,
    outline          = NexusDivider,
    error            = NexusRedLight,
)

private val LightColorScheme = lightColorScheme(
    primary          = NexusRed,
    onPrimary        = NexusWhite,
    primaryContainer = NexusRedLight,
    secondary        = NexusGold,
    onSecondary      = NexusNavy,
    background       = LightBackground,
    onBackground     = NexusNavy,
    surface          = LightSurface,
    onSurface        = LightOnSurface,
    surfaceVariant   = Color(0xFFF0F0F0),
    outline          = NexusMuted,
)

@Composable
fun NexusTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = colorScheme.background.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = !darkTheme
        }
    }
    MaterialTheme(colorScheme = colorScheme, typography = NexusTypography, content = content)
}
