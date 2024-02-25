package com.kristianskokars.myplants.feature.addplant.presentation.screen.pickwateringdates

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.kristianskokars.myplants.core.data.model.Day
import com.kristianskokars.myplants.feature.navArgs
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class PickWateringDatesViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val initialDays = savedStateHandle.navArgs<PickWateringDatesDialogNavArgs>().initialDays.list

    var wateringDates by mutableStateOf(Day.entries.toList().associateWith { date -> date in initialDays })
        private set

    fun onSelectDay(selectedDay: Day) {
        val newWateringDates = wateringDates.toMutableMap()
        val isSelected = newWateringDates[selectedDay]?.not() ?: return

        newWateringDates[selectedDay] = isSelected
        wateringDates = newWateringDates
    }
}
