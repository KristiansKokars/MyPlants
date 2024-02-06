package com.kristianskokars.myplants.core.presentation.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.kristianskokars.myplants.core.presentation.theme.Neutralus100

@Composable
fun MyPlantsTertiaryButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    content: @Composable () -> Unit,
) {
    val buttonTextStyle = MaterialTheme.typography.bodyLarge

    OutlinedButton(
        modifier = modifier,
        shape = RoundedCornerShape(8.dp),
        border = BorderStroke(1.dp, Neutralus100),
        onClick = onClick
    ) {
        CompositionLocalProvider(LocalTextStyle provides buttonTextStyle) {
            content()
        }
    }
}
