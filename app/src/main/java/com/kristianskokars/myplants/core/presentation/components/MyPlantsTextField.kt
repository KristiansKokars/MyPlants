package com.kristianskokars.myplants.core.presentation.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.kristianskokars.myplants.core.presentation.theme.Neutralus100
import com.kristianskokars.myplants.core.presentation.theme.Neutralus500

@Composable
fun MyPlantsTextField(
    modifier: Modifier = Modifier,
    label: @Composable () -> Unit,
    value: String,
    onValueChange: (String) -> Unit,
    trailingIcon: @Composable (() -> Unit)? = null
) {
    Column(modifier = modifier) {
        CompositionLocalProvider(LocalTextStyle provides MaterialTheme.typography.bodySmall) {
            label()
            Spacer(modifier = Modifier.size(8.dp))
            TextField(
                modifier = modifier,
                value = value,
                onValueChange = onValueChange,
                colors = TextFieldDefaults.colors(
                    focusedTextColor = Neutralus500,
                    unfocusedTextColor = Neutralus500,
                    focusedContainerColor = Neutralus100,
                    unfocusedContainerColor = Neutralus100,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    disabledIndicatorColor = Color.Transparent,
                    errorIndicatorColor = Color.Transparent
                ),
                shape = RoundedCornerShape(12.dp),
                trailingIcon = trailingIcon,
            )
        }
    }
}
