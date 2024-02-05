package com.kristianskokars.myplants.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val LightColorScheme = lightColorScheme(
    primary = Accent600,
    secondary = Accent500,
    tertiary = Accent100,
    background = Neutralus0
)

@Composable
fun MyPlantsTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = LightColorScheme,
        typography = Typography,
        content = content
    )
}
