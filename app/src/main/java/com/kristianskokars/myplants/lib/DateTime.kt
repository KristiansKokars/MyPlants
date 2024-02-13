package com.kristianskokars.myplants.lib

import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.SaverScope
import kotlinx.datetime.LocalTime

object LocalTimeSaver : Saver<LocalTime, String> {
    override fun restore(value: String): LocalTime {
        return LocalTime.parse(value)
    }

    override fun SaverScope.save(value: LocalTime): String {
        return value.toString()
    }

}
