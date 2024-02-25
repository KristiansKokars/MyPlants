package com.kristianskokars.myplants.core.presentation.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex

@Composable
fun ScreenSurface(
    modifier: Modifier = Modifier,
    backgroundColor: Color = MaterialTheme.colorScheme.background,
    backgroundContent: @Composable () -> Unit = { PlantsWallpaper() },
    shouldPadAgainstInsets: Boolean = true,
    content: @Composable () -> Unit,
) {
    Surface(
        modifier = modifier.fillMaxSize(),
        color = backgroundColor
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            Box(modifier = Modifier.zIndex(-10f)) {
                backgroundContent()
            }
            Box(modifier = if (shouldPadAgainstInsets) Modifier.padding(top = 12.dp) else Modifier) {
                content()
            }
        }
    }
}
