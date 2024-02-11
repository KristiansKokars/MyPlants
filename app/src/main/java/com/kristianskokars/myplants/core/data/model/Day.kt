package com.kristianskokars.myplants.core.data.model

import android.os.Parcelable
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.kristianskokars.myplants.R
import kotlinx.parcelize.Parcelize

@Parcelize
enum class Day : Parcelable {
    MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY, SATURDAY, SUNDAY, EVERYDAY;

    @Composable
    fun toUIString() = when (this) {
        MONDAY -> stringResource(R.string.monday)
        TUESDAY -> stringResource(R.string.tuesday)
        WEDNESDAY -> stringResource(R.string.wednesday)
        THURSDAY -> stringResource(R.string.thursday)
        FRIDAY -> stringResource(R.string.friday)
        SATURDAY -> stringResource(R.string.saturday)
        SUNDAY -> stringResource(R.string.sunday)
        EVERYDAY -> stringResource(R.string.everyday)
    }
}

// Compose destinations does not allow to use List<Day> directly as a result type
@Parcelize
data class DayList(val list: List<Day>) : Parcelable
