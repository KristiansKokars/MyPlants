package com.kristianskokars.myplants.core.data.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.datetime.Clock
import kotlinx.datetime.DatePeriod
import kotlinx.datetime.Instant
import kotlinx.datetime.LocalTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.atTime
import kotlinx.datetime.isoDayNumber
import kotlinx.datetime.plus
import kotlinx.datetime.toInstant
import kotlinx.datetime.toLocalDateTime
import kotlinx.parcelize.Parcelize

const val PLANT_TABLE = "plant"

@Entity(tableName = PLANT_TABLE)
@Parcelize
data class Plant(
    @PrimaryKey val id: String,
    val name: String,
    val description: String,
    val waterInMilliliters: Int,
    val wateringTimeInMillis: Int,
    val wateringDates: List<Day>,
    val pictureUrl: String?,
    val size: PlantSize,
    val lastWateredDateInMillis: Long? = null,
) : Parcelable

fun Plant.wateringFrequencyPerWeek(): Int {
    var frequency = 0
    wateringDates.forEach { date ->
        if (date == Day.EVERYDAY) {
            frequency = 7
            return frequency
        }

        frequency++
    }
    return frequency
}

fun Plant.isWatered(): Boolean {
    val currentTimeInMillis = Clock.System.now().toEpochMilliseconds()
    return lastWateredDateInMillis != null && currentTimeInMillis > lastWateredDateInMillis && nextWateringDateInMillis() > currentTimeInMillis
}

fun Plant.didForgetToWater(): Boolean {
    val currentTimeInMillis = Clock.System.now().toEpochMilliseconds()
    return lastWateredDateInMillis != null && currentTimeInMillis > lastWateredDateInMillis && nextWateringDateInMillis() < currentTimeInMillis
}

fun Plant.nextWateringDateInMillis(
    clock: Clock = Clock.System,
    timeZone: TimeZone = TimeZone.currentSystemDefault(),
): Long {
    val plant = this
    val currentDate = clock.now().toLocalDateTime(timeZone)
    val lastWateredDate = plant.lastWateredDateInMillis?.let { Instant.fromEpochMilliseconds(it) }?.toLocalDateTime(timeZone)
    val wateringTime = LocalTime.fromMillisecondOfDay(plant.wateringTimeInMillis)
    var nextWateringDateInMillis: Long? = null

    plant.wateringDates.forEach { day ->
        when (day) {
            Day.EVERYDAY -> {
                nextWateringDateInMillis = if (currentDate.date == lastWateredDate?.date && currentDate.time > lastWateredDate.time) {
                    currentDate.date.plus(DatePeriod(days = 1)).atTime(wateringTime).toInstant(timeZone).toEpochMilliseconds()
                } else {
                    currentDate.date.atTime(wateringTime).toInstant(timeZone).toEpochMilliseconds()
                }
                return@forEach
            }
            else -> {
                if (currentDate.date == lastWateredDate?.date && day.toDayNumber() == currentDate.dayOfWeek.isoDayNumber) {
                    val nextWateringTime = currentDate.date
                        .plus(DatePeriod(days = 7))
                        .atTime(wateringTime)
                        .toInstant(timeZone)
                        .toEpochMilliseconds()
                    if (nextWateringDateInMillis == null || nextWateringDateInMillis!! > nextWateringTime) {
                        nextWateringDateInMillis = nextWateringTime
                    }
                } else {
                    var daysToAdd = (day.toDayNumber() - currentDate.dayOfWeek.isoDayNumber)
                    if (daysToAdd < 0) {
                        daysToAdd += 7
                    }
                    val nextWateringTime = currentDate.date
                        .plus(DatePeriod(days = daysToAdd))
                        .atTime(wateringTime)
                        .toInstant(timeZone)
                        .toEpochMilliseconds()
                    if (nextWateringDateInMillis == null || nextWateringDateInMillis!! > nextWateringTime) {
                        nextWateringDateInMillis = nextWateringTime
                    }
                }
            }
        }
    }

    return nextWateringDateInMillis!!
}
