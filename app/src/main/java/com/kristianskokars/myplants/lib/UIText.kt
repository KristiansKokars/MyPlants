package com.kristianskokars.myplants.lib

import android.content.Context
import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource

sealed class UIText {
    data class StringResource(@StringRes val id: Int) : UIText()

    fun get(context: Context) = when (this) {
        is StringResource -> context.getString(id)
    }

    @Composable
    fun toUIString() = when (this) {
        is StringResource -> stringResource(id = id)
    }
}
