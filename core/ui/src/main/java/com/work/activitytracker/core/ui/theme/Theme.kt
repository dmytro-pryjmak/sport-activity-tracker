package com.work.activitytracker.core.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val LightColors = lightColorScheme(
    primary = Teal40,
    onPrimary = androidx.compose.ui.graphics.Color.White,
    primaryContainer = Teal90,
    onPrimaryContainer = Teal20,
    secondary = Blue40,
    onSecondary = androidx.compose.ui.graphics.Color.White,
    background = BackgroundLight,
    surface = androidx.compose.ui.graphics.Color.White,
)

private val DarkColors = darkColorScheme(
    primary = Teal80,
    onPrimary = Teal20,
    primaryContainer = Teal30,
    onPrimaryContainer = Teal90,
    secondary = Blue80,
    background = BackgroundDark,
    surface = SurfaceDark,
)

@Composable
fun SportTrackerTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit,
) {
    MaterialTheme(
        colorScheme = if (darkTheme) DarkColors else LightColors,
        typography = SportTrackerTypography,
        content = content,
    )
}
