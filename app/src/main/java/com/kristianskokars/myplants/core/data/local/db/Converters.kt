package com.kristianskokars.myplants.core.data.local.db

import androidx.room.TypeConverter
import com.kristianskokars.myplants.core.data.model.Day

class Converters {
    @TypeConverter
    fun daysToString(days: List<Day>?): String? {
        return days?.joinToString(",") { it.name }
    }

    @TypeConverter
    fun daysFromString(value: String?): List<Day>? {
        return value?.let { value.split(",").map { Day.valueOf(it) } }
    }
}
