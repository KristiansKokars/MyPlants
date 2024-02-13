package com.kristianskokars.myplants.feature.addplant.presentation.screen.addplant

import androidx.compose.runtime.Composable
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
    private var plantName by savedStateHandle.saveable { mutableStateOf("") }
    private var plantDescription by savedStateHandle.saveable { mutableStateOf("") }
    private var imageUrl by mutableStateOf<String?>(null)
    private var selectedPlantDates by savedStateHandle.saveable { mutableStateOf(listOf(Day.MONDAY)) }
    private var selectedPlantSize by savedStateHandle.saveable { mutableStateOf(PlantSize.MEDIUM) }
    private var waterAmount by savedStateHandle.saveable { mutableIntStateOf(250) }
    private var selectedTime by savedStateHandle.saveable(stateSaver = LocalTimeSaver) { mutableStateOf(LocalTime(8, 0, 0)) }
    private val canCreatePlant by derivedStateOf { plantName.isNotEmpty() }

    @Composable
    fun state(): AddPlantState {
        return AddPlantState(
            imageUrl = imageUrl,
            selectedTime = selectedTime,
            plantName = plantName,
            plantDescription = plantDescription,
            canCreatePlant = canCreatePlant,
            selectedDates = selectedPlantDates,
            selectedPlantSize = selectedPlantSize,
            waterAmount = waterAmount
        )
    }

    fun onEvent(event: AddPlantEvent) {
        when (event) {
            AddPlantEvent.CreatePlant -> createPlant()
            is AddPlantEvent.OnPlantDescriptionChange -> onPlantDescriptionChange(event.newDescription)
            is AddPlantEvent.OnPlantNameChange -> onPlantNameChange(event.newName)
            is AddPlantEvent.OnSelectTime -> onSelectTime(event.newTime)
            is AddPlantEvent.OnAddPhoto -> onAddPhoto(event.newPhoto)
            is AddPlantEvent.OnPlantSizeSelected -> onPlantSizeSelected(event.newPlantSize)
            is AddPlantEvent.OnWaterAmountSelected -> onWaterAmountSelected(event.newWaterAmount)
            is AddPlantEvent.OnWateringDatesSelected -> onWateringDatesSelected(event.newDates)
        }
    }

    private fun onPlantNameChange(value: String) {
        plantName = value
    }

    private fun onPlantDescriptionChange(value: String) {
        plantDescription = value
    }

    private fun createPlant() {
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

    private fun onAddPhoto(url: String) {
        imageUrl = url
    }

    private fun onSelectTime(time: LocalTime) {
        selectedTime = time
    }

    private fun onWateringDatesSelected(dates: List<Day>) {
        selectedPlantDates = if (dates.contains(Day.EVERYDAY)) {
            listOf(Day.EVERYDAY)
        } else {
            dates
        }
    }

    private fun onPlantSizeSelected(plantSize: PlantSize) {
        selectedPlantSize = plantSize
    }

    private fun onWaterAmountSelected(newWaterAmount: Int) {
        waterAmount = newWaterAmount
    }
}
