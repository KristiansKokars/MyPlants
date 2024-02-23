package com.kristianskokars.myplants.feature.viewplants.presentation.screen.home

sealed class PlantHomeEvent {
    data class OnPlantFilterChange(val newPlantFilter: PlantFilter) : PlantHomeEvent()
    data class DeletePlant(val plantId: String) : PlantHomeEvent()
    data class MarkPlantAsWatered(val plantId: String) : PlantHomeEvent()
}
