package com.kristianskokars.myplants.lib

import androidx.compose.runtime.Composable
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.SaverScope
import androidx.compose.ui.res.stringResource
import com.kristianskokars.myplants.R
import kotlinx.datetime.Clock
import kotlinx.datetime.DatePeriod
import kotlinx.datetime.Instant
import kotlinx.datetime.LocalTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.plus
import kotlinx.datetime.toJavaLocalDateTime
import kotlinx.datetime.toLocalDateTime
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle
import java.util.Locale

object LocalTimeSaver : Saver<LocalTime, String> {
    override fun restore(value: String): LocalTime {
        return LocalTime.parse(value)
    }

    override fun SaverScope.save(value: LocalTime): String {
        return value.toString()
    }

}

/** Formats a date from milliseconds, like: Feb 21 */
@Composable
fun Long.toDateLabel(): String {
    val timeZone = TimeZone.currentSystemDefault()
    val currentTime = Clock.System.now().toLocalDateTime(timeZone)
    val localDateTime = Instant
        .fromEpochMilliseconds(this)
        .toLocalDateTime(timeZone)
    val dateFormatter = DateTimeFormatter
        .ofPattern("MMM dd")
        .withLocale(Locale.US) // we only support english, so having other languages for dates only would feel off

    return when {
        localDateTime.date == currentTime.date -> stringResource(id = R.string.today)
        localDateTime.date + DatePeriod(days = 1) == currentTime.date -> stringResource(R.string.tomorrow)
        else -> localDateTime.toJavaLocalDateTime().format(dateFormatter)
    }
}

/** Formats a time from milliseconds, like: 04:59 */
@Composable
fun Long.toTimeLabel(): String {
    val timeZone = TimeZone.currentSystemDefault()
    val localDateTime = Instant
        .fromEpochMilliseconds(this)
        .toLocalDateTime(timeZone)
    val dateFormatter = DateTimeFormatter
        .ofLocalizedTime(FormatStyle.SHORT)
        .withLocale(Locale.US) // we only support english, so having other languages for dates only would feel off

    return localDateTime.toJavaLocalDateTime().format(dateFormatter)
}
