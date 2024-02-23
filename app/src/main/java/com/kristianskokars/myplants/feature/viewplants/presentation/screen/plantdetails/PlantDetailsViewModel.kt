package com.kristianskokars.myplants.feature.viewplants.presentation.screen.plantdetails

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.kristianskokars.myplants.core.data.local.db.PlantDao
import com.kristianskokars.myplants.feature.navArgs
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class PlantDetailsViewModel @Inject constructor(
    private val plantDao: PlantDao,
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val plantId = savedStateHandle.navArgs<PlantDetailsNavArgs>().id

    @Composable
    fun state(): PlantDetailsState {
        return PlantDetailsState(
            plant = plantDao.getPlant(plantId).collectAsState(initial = null).value
        )
    }
}
