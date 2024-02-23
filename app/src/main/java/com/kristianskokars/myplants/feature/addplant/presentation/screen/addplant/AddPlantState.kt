package com.kristianskokars.myplants.feature.addplant.presentation.screen.addplant

import com.kristianskokars.myplants.core.data.model.Day
import com.kristianskokars.myplants.core.data.model.PlantSize
import kotlinx.datetime.LocalTime

data class AddPlantState(
    val imageUrl: String?,
    val selectedTime: LocalTime,
    val plantName: String,
    val plantDescription: String,
    val canCreatePlant: Boolean,
    val selectedDates: List<Day>,
    val selectedPlantSize: PlantSize,
    val waterAmount: Int,
    val isEditingExistingPlant: Boolean,
)
