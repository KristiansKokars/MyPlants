package com.kristianskokars.myplants.core.presentation.components

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.unit.dp
import com.kristianskokars.myplants.core.presentation.theme.Neutralus0
import com.kristianskokars.myplants.core.presentation.theme.Neutralus900

@Composable
fun MyPlantsIconButton(
    modifier: Modifier = Modifier,
    iconPainter: Painter,
    contentDescription: String,
    onClick: () -> Unit,
) {
    FilledIconButton(
        modifier = modifier.padding(16.dp),
        colors = IconButtonDefaults.iconButtonColors(
            containerColor = Neutralus0,
            contentColor = Neutralus900
        ),
        onClick = onClick,
    ) {
        Icon(
            painter = iconPainter,
            contentDescription = contentDescription
        )
    }
}
