package com.kristianskokars.myplants.core.data.local

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import com.kristianskokars.myplants.core.data.DeepLinks
import com.kristianskokars.myplants.core.data.model.Day
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.datetime.Clock
import kotlinx.datetime.LocalTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.atTime
import kotlinx.datetime.toInstant
import kotlinx.datetime.toLocalDateTime
import java.io.Serializable
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AndroidPlantWateringScheduler @Inject constructor(
    @ApplicationContext private val context: Context,
    private val alarmManager: AlarmManager
): PlantWateringScheduler {
    override fun scheduleWatering(days: List<Day>, time: LocalTime, plantId: String, extras: Map<String, Any>) {
        val currentDate = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())

        // TODO: only works with everyday setting properly for now
        if (Day.EVERYDAY in days) {
            val pendingIntent = PendingIntent.getBroadcast(
                context,
                plantId.hashCode(),
                Intent(context, PlantWateringBroadcastReceiver::class.java).apply {
                    putExtra(DeepLinks.Type.ID, plantId)
                    for ((key, value) in extras.entries) {
                        putExtra(key, value as Serializable)
                    }
                },
                PendingIntent.FLAG_IMMUTABLE
            )

            try {
                alarmManager.setExactAndAllowWhileIdle(
                    AlarmManager.RTC_WAKEUP,
                    currentDate.date.atTime(time).toInstant(TimeZone.currentSystemDefault()).toEpochMilliseconds(),
                    pendingIntent
                )
            } catch (e: SecurityException) {
                // TODO: handle error
            }
        } else {
            days.forEach { day ->
                val pendingIntent = PendingIntent.getBroadcast(
                    context,
                    "$plantId$day".hashCode(),
                    Intent(context, PlantWateringBroadcastReceiver::class.java).apply {
                        putExtra(DeepLinks.Type.ID, plantId)
                        for ((key, value) in extras.entries) {
                            putExtra(key, value as Serializable)
                        }
                    },
                    PendingIntent.FLAG_IMMUTABLE
                )

                alarmManager.setRepeating(
                    AlarmManager.RTC_WAKEUP,
                    currentDate.date.atTime(time).toInstant(TimeZone.currentSystemDefault()).toEpochMilliseconds(),
                    MILLIS_IN_WEEK,
                    pendingIntent
                )
            }
        }
    }

    // TODO: only works with everyday setting for now
    override fun unscheduleWatering(plantId: String) {
        val pendingIntent = PendingIntent.getBroadcast(
            context,
            plantId.hashCode(),
            Intent(context, PlantWateringBroadcastReceiver::class.java),
            PendingIntent.FLAG_IMMUTABLE
        )
        alarmManager.cancel(pendingIntent)
    }

    companion object {
        const val MILLIS_IN_DAY = 86400000L
        const val MILLIS_IN_WEEK = 604800000L
    }
}
