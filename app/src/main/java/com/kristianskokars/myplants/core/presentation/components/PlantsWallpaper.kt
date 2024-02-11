package com.kristianskokars.myplants.core.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import com.kristianskokars.myplants.R

@Composable
fun PlantsWallpaper(modifier: Modifier = Modifier) {
    Image(
        modifier = modifier,
        painter = painterResource(id = R.drawable.bg_screen),
        contentDescription = null
    )
}
