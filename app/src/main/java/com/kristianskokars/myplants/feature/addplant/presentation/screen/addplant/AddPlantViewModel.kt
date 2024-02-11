package com.kristianskokars.myplants.feature.addplant.presentation.screen.addplant

import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.compose.SavedStateHandleSaveableApi
import androidx.lifecycle.viewmodel.compose.saveable
import com.kristianskokars.myplants.MainApp
import com.kristianskokars.myplants.core.data.local.db.PlantDao
import com.kristianskokars.myplants.core.data.model.Day
import com.kristianskokars.myplants.core.data.model.Plant
import com.kristianskokars.myplants.core.data.model.PlantSize
import com.kristianskokars.myplants.lib.launch
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.datetime.LocalTime

@OptIn(SavedStateHandleSaveableApi::class)
class AddPlantViewModel(
    private val plantDao: PlantDao,
    savedStateHandle: SavedStateHandle,
) : ViewModel() {
    private val _events = Channel<UIEvent>()

    val events = _events.receiveAsFlow()
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
    var selectedTime by savedStateHandle.saveable { mutableStateOf(LocalTime(8, 0, 0)) }
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
            _events.send(UIEvent.PlantCreated)
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

    sealed class UIEvent {
        data object PlantCreated : UIEvent()
    }

    companion object {
        val Factory: ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(
                modelClass: Class<T>,
                extras: CreationExtras
            ): T {
                val application = checkNotNull(extras[APPLICATION_KEY])
                val savedStateHandle = extras.createSavedStateHandle()

                return AddPlantViewModel(
                    (application as MainApp).container.plantDao,
                    savedStateHandle
                ) as T
            }
        }
    }
}
