package com.kristianskokars.myplants.feature.addplant.presentation.screen.pickwateringdates

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.kristianskokars.myplants.core.data.model.Day

class PickWateringDatesViewModel : ViewModel() {
    var wateringDates by mutableStateOf(Day.entries.toList().associateWith { date -> date == Day.MONDAY })
        private set

    fun onSelectDay(selectedDay: Day) {
        val newWateringDates = wateringDates.toMutableMap()
        val isSelected = newWateringDates[selectedDay]?.not() ?: return

        newWateringDates[selectedDay] = isSelected
        wateringDates = newWateringDates
    }
}
