package com.kristianskokars.myplants.core.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.zIndex
import com.kristianskokars.myplants.R
import com.kristianskokars.myplants.ui.theme.MyPlantsTheme

@Composable
fun ScreenSurface(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit,
) {
    MyPlantsTheme {
        Surface(
            modifier = modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            Box(modifier = Modifier.fillMaxSize()) {
                Image(
                    modifier = Modifier.zIndex(-10f),
                    painter = painterResource(id = R.drawable.bg_screen),
                    contentDescription = null
                )
                Box(modifier = Modifier.safeContentPadding()) {
                    content()
                }
            }
        }
    }
}
