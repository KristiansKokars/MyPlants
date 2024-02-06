package com.kristianskokars.myplants.core.presentation.components

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.kristianskokars.myplants.core.presentation.theme.Neutralus0

@Composable
fun SmallButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    content: @Composable () -> Unit,
) {
    val buttonTextStyle = MaterialTheme.typography.bodySmall.copy(color = Neutralus0)

    Button(
        modifier = modifier,
        contentPadding = PaddingValues(horizontal = 12.dp, vertical = 8.dp),
        shape = RoundedCornerShape(8.dp),
        onClick = onClick
    ) {
        CompositionLocalProvider(LocalTextStyle provides buttonTextStyle) {
            content()
        }
    }
}
