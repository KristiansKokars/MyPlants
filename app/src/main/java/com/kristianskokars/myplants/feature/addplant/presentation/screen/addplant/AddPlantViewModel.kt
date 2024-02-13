package com.kristianskokars.myplants.feature.addplant.presentation.screen.addplant

import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.SavedStateHandleSaveableApi
import androidx.lifecycle.viewmodel.compose.saveable
import com.kristianskokars.myplants.R
import com.kristianskokars.myplants.core.data.local.db.PlantDao
import com.kristianskokars.myplants.core.data.model.Day
import com.kristianskokars.myplants.core.data.model.Plant
import com.kristianskokars.myplants.core.data.model.PlantSize
import com.kristianskokars.myplants.lib.LocalTimeSaver
import com.kristianskokars.myplants.lib.Navigator
import com.kristianskokars.myplants.lib.Toaster
import com.kristianskokars.myplants.lib.UIText
import com.kristianskokars.myplants.lib.launch
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.datetime.LocalTime
import javax.inject.Inject

@OptIn(SavedStateHandleSaveableApi::class)
@HiltViewModel
class AddPlantViewModel @Inject constructor(
    private val plantDao: PlantDao,
    private val navigator: Navigator,
    private val toaster: Toaster,
    savedStateHandle: SavedStateHandle,
) : ViewModel() {
    var plantName by savedStateHandle.saveable { mutableStateOf("") }
        private set
    var plantDescription by savedStateHandle.saveable { mutableStateOf("") }
        private set
    var imageUrl by mutableStateOf<String?>(null)
        private set
    var selectedPlantDates by savedStateHandle.saveable { mutableStateOf(listOf(Day.MONDAY)) }
        private set
    var selectedPlantSize by savedStateHandle.saveable { mutableStateOf(PlantSize.MEDIUM) }
        private set
    var waterAmount by savedStateHandle.saveable { mutableIntStateOf(250) }
        private set
    var selectedTime by savedStateHandle.saveable(stateSaver = LocalTimeSaver) { mutableStateOf(LocalTime(8, 0, 0)) }
        private set
    val canCreatePlant by derivedStateOf { plantName.isNotEmpty() }

    fun onPlantNameChange(value: String) {
        plantName = value
    }

    fun onPlantDescriptionChange(value: String) {
        plantDescription = value
    }

    fun createPlant() {
        if (!canCreatePlant) return

        launch {
            plantDao.insertPlant(
                Plant(
                    name = plantName,
                    description = plantDescription,
                    waterInMilliliters = waterAmount,
                    wateringDates = selectedPlantDates,
                    pictureUrl = imageUrl
                )
            )
            navigator.navigate(Navigator.Action.GoBack)
            toaster.show(Toaster.Message(UIText.StringResource(R.string.plant_created)))
        }
    }

    fun addPhoto(url: String) {
        imageUrl = url
    }

    fun onSelectTime(time: LocalTime) {
        selectedTime = time
    }

    fun onWateringDatesSelected(dates: List<Day>) {
        selectedPlantDates = if (dates.contains(Day.EVERYDAY)) {
            listOf(Day.EVERYDAY)
        } else {
            dates
        }
    }

    fun onPlantSizeSelected(plantSize: PlantSize) {
        selectedPlantSize = plantSize
    }

    fun onWaterAmountSelected(newWaterAmount: Int) {
        waterAmount = newWaterAmount
    }
}
