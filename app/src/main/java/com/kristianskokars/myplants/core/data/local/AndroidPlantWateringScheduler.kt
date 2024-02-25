package com.kristianskokars.myplants.core.data.local

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import com.kristianskokars.myplants.core.data.DeepLinks
import com.kristianskokars.myplants.core.data.model.Day
import com.kristianskokars.myplants.lib.getNextDateAtDayOfWeek
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.datetime.Clock
import kotlinx.datetime.DatePeriod
import kotlinx.datetime.LocalTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.atTime
import kotlinx.datetime.plus
import kotlinx.datetime.toInstant
import kotlinx.datetime.toLocalDateTime
import java.io.Serializable
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AndroidPlantWateringScheduler @Inject constructor(
    @ApplicationContext private val context: Context,
    private val alarmManager: AlarmManager,
    private val clock: Clock,
    private val timeZone: TimeZone
): PlantWateringScheduler {
    override fun scheduleWatering(days: List<Day>, time: LocalTime, plantId: String, extras: Map<String, Any>) {
        val currentDate = clock.now().toLocalDateTime(timeZone)

        // TODO: only works with everyday setting properly for now
        if (Day.EVERYDAY in days) {
            try {
                val alarmTime = if (currentDate.time > time) {
                    currentDate.date.plus(DatePeriod(days = 1)).atTime(time).toInstant(timeZone).toEpochMilliseconds()
                } else {
                    currentDate.date.atTime(time).toInstant(timeZone).toEpochMilliseconds()
                }

                val pendingIntent = PendingIntent.getBroadcast(
                    context,
                    plantId.hashCode(),
                    Intent(context, PlantWateringBroadcastReceiver::class.java).apply {
                        putExtra(PlantWateringBroadcastReceiver.INTENT_ID, plantId.hashCode())
                        putExtra(PlantWateringBroadcastReceiver.ALARM_TIME, alarmTime)
                        putExtra(PlantWateringBroadcastReceiver.IS_EVERYDAY, true)
                        putExtra(DeepLinks.Type.ID, plantId)
                        for ((key, value) in extras.entries) {
                            putExtra(key, value as Serializable)
                        }
                    },
                    PendingIntent.FLAG_IMMUTABLE
                )

                alarmManager.setExactAndAllowWhileIdle(
                    AlarmManager.RTC_WAKEUP,
                    alarmTime,
                    pendingIntent
                )
            } catch (e: SecurityException) {
                // TODO: handle error
            }
        } else {
            days.forEach { day ->
                try {
                    val alarmTime = currentDate
                        .getNextDateAtDayOfWeek(day)
                        .atTime(time)
                        .toInstant(timeZone)
                        .toEpochMilliseconds()

                    val intentId = "$plantId$day".hashCode()
                    val pendingIntent = PendingIntent.getBroadcast(
                        context,
                        intentId,
                        Intent(context, PlantWateringBroadcastReceiver::class.java).apply {
                            putExtra(PlantWateringBroadcastReceiver.INTENT_ID, intentId)
                            putExtra(PlantWateringBroadcastReceiver.ALARM_TIME, alarmTime)
                            putExtra(PlantWateringBroadcastReceiver.IS_EVERYDAY, false)
                            putExtra(DeepLinks.Type.ID, plantId)
                            for ((key, value) in extras.entries) {
                                putExtra(key, value as Serializable)
                            }
                        },
                        PendingIntent.FLAG_IMMUTABLE
                    )

                    alarmManager.setExactAndAllowWhileIdle(
                        AlarmManager.RTC_WAKEUP,
                        alarmTime,
                        pendingIntent
                    )
                } catch (e: SecurityException) {
                    // TODO: handle error
                }
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
}
