package com.kristianskokars.myplants.core.data.model

import android.os.Parcelable
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.kristianskokars.myplants.R
import kotlinx.parcelize.Parcelize

@Parcelize
enum class PlantSize : Parcelable {
    SMALL, MEDIUM, LARGE, EXTRA_LARGE;

    @Composable
    fun toUIString() = when (this) {
        SMALL -> stringResource(R.string.small)
        MEDIUM -> stringResource(R.string.medium)
        LARGE -> stringResource(R.string.large)
        EXTRA_LARGE -> stringResource(R.string.extra_large)
    }
}
