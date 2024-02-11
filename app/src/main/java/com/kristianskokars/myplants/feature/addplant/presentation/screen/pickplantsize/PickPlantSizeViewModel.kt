package com.kristianskokars.myplants.feature.addplant.presentation.screen.pickplantsize

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.kristianskokars.myplants.core.data.model.PlantSize

class PickPlantSizeViewModel : ViewModel() {
    val availablePlantSizes = PlantSize.entries.toList()

    var selectedPlantSizeIndex by mutableIntStateOf(1)
        private set

    fun onSelectPlantSize(plantSizeIndex: Int) {
        selectedPlantSizeIndex = plantSizeIndex
    }
}
