package com.kristianskokars.myplants.feature.addplant.presentation.screen.pickplantsize

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.kristianskokars.myplants.core.data.model.PlantSize
import com.kristianskokars.myplants.feature.navArgs
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class PickPlantSizeViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val initialSize = savedStateHandle.navArgs<PickPlantSizeDialogNavArgs>().initialSize

    val availablePlantSizes = PlantSize.entries.toList()

    var selectedPlantSizeIndex by mutableIntStateOf(initialSize.ordinal)
        private set

    fun onSelectPlantSize(plantSizeIndex: Int) {
        selectedPlantSizeIndex = plantSizeIndex
    }
}
