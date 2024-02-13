package com.kristianskokars.myplants.feature.addplant.presentation.screen.addplant

import com.kristianskokars.myplants.core.data.model.Day
import com.kristianskokars.myplants.core.data.model.PlantSize
import kotlinx.datetime.LocalTime

sealed class AddPlantEvent {
    data class OnSelectTime(val newTime: LocalTime) : AddPlantEvent()
    data class OnPlantNameChange(val newName: String) : AddPlantEvent()
    data class OnPlantDescriptionChange(val newDescription: String) : AddPlantEvent()
    data class OnAddPhoto(val newPhoto: String) : AddPlantEvent()
    data class OnWateringDatesSelected(val newDates: List<Day>) : AddPlantEvent()
    data class OnPlantSizeSelected(val newPlantSize: PlantSize) : AddPlantEvent()
    data class OnWaterAmountSelected(val newWaterAmount: Int) : AddPlantEvent()
    data object CreatePlant : AddPlantEvent()
}
