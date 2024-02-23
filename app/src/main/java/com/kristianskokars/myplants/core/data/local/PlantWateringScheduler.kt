package com.kristianskokars.myplants.core.data.local

import com.kristianskokars.myplants.core.data.model.Day
import kotlinx.datetime.LocalTime

interface PlantWateringScheduler {
    fun scheduleWatering(days: List<Day>, time: LocalTime, plantId: String, extras: Map<String, Any>)
    fun unscheduleWatering(plantId: String)
}
