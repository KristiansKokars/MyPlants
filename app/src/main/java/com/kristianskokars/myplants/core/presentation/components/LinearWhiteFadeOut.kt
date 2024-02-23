package com.kristianskokars.myplants.core.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun LinearWhiteFadeOut(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .background(
                Brush.linearGradient(
                    0.0f to Color.Transparent,
                    0.75f to Color.White,
                    start = Offset(0.0f, 0.0f),
                    end = Offset(0.0f, Offset.Infinite.y)
                )
            )
            .fillMaxWidth()
            .height(64.dp)
    )
}
