package com.kristianskokars.myplants.core.presentation.components

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.kristianskokars.myplants.core.presentation.theme.TransparentGray
import dev.chrisbanes.haze.HazeState
import dev.chrisbanes.haze.HazeStyle
import dev.chrisbanes.haze.hazeChild

fun Modifier.styledHazeChild(state: HazeState): Modifier = hazeChild(
    state = state,
    shape = RoundedCornerShape(4.dp),
    style = HazeStyle(tint = TransparentGray)
)
