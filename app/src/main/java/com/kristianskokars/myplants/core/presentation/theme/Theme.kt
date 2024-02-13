package com.kristianskokars.myplants.core.presentation.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val LightColorScheme = lightColorScheme(
    primary = Accent600,
    onPrimary = Neutralus100,
    surface = Accent500,
    surfaceVariant = Accent100,
    secondary = Accent500,
    tertiary = Accent100,
    background = Neutralus0,
    inverseSurface = Neutralus100,
)

@Composable
fun MyPlantsTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = LightColorScheme,
        typography = Typography,
        content = content
    )
}
