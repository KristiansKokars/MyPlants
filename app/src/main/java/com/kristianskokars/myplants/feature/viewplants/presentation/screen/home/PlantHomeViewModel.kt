package com.kristianskokars.myplants.feature.viewplants.presentation.screen.home

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.kristianskokars.myplants.core.data.local.PlantWateringScheduler
import com.kristianskokars.myplants.core.data.local.db.PlantDao
import com.kristianskokars.myplants.core.data.local.db.PlantNotificationDao
import com.kristianskokars.myplants.core.data.local.db.PlantWateredDateDao
import com.kristianskokars.myplants.core.data.local.db.model.PlantWateredDateDBModel
import com.kristianskokars.myplants.core.data.model.Plant
import com.kristianskokars.myplants.core.data.model.didForgetToWater
import com.kristianskokars.myplants.lib.launch
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.toPersistentList
import kotlinx.coroutines.NonCancellable
import kotlinx.coroutines.withContext
import kotlinx.datetime.Clock
import javax.inject.Inject

@HiltViewModel
class PlantHomeViewModel @Inject constructor(
    private val plantDao: PlantDao,
    private val plantNotificationDao: PlantNotificationDao,
    private val plantWateredDateDao: PlantWateredDateDao,
    private val plantWateringScheduler: PlantWateringScheduler,
): ViewModel() {
    private var plantFilter by mutableStateOf(PlantFilter.UPCOMING)

    @Composable
    fun state(): PlantHomeState {
        return PlantHomeState(
            plants = allPlants()
                .filter { plant ->
                    when (plantFilter) {
                        PlantFilter.UPCOMING -> {
                             plant.lastWateredDateInMillis == null || plant.lastWateredDateInMillis > Clock.System.now().toEpochMilliseconds()
                        }
                        PlantFilter.FORGOT_TO_WATER -> plant.didForgetToWater()
                        PlantFilter.HISTORY -> true
                    }
                }
                .toPersistentList(),
            plantFilter = plantFilter,
            hasUnseenNotifications = hasUnseenNotifications()
        )
    }

    fun onEvent(event: PlantHomeEvent) {
        when (event) {
            is PlantHomeEvent.OnPlantFilterChange -> onPlantFilterChange(event.newPlantFilter)
            is PlantHomeEvent.DeletePlant -> deletePlant(event.plantId)
            is PlantHomeEvent.MarkPlantAsWatered -> markPlantAsWatered(event.plantId)
        }
    }

    @Composable
    private fun allPlants(): List<Plant> {
        return plantDao
            .getPlants()
            .collectAsState(initial = emptyList()).value
    }

    @Composable
    private fun hasUnseenNotifications(): Boolean {
        return plantNotificationDao
            .hasUnseenNotifications()
            .collectAsState(initial = false).value
    }

    private fun onPlantFilterChange(newPlantFilter: PlantFilter) {
        plantFilter = newPlantFilter
    }

    private fun deletePlant(plantId: String) {
        launch {
            withContext(NonCancellable) {
                plantWateringScheduler.unscheduleWatering(plantId)
                plantDao.deletePlant(plantId)
            }
        }
    }

    private fun markPlantAsWatered(plantId: String) {
        launch {
            withContext(NonCancellable) {
                val wateredDate = PlantWateredDateDBModel(plantId = plantId, dateInMillis = Clock.System.now().toEpochMilliseconds())
                plantWateredDateDao.insertDate(wateredDate)
                plantDao.updatePlantLastWateredDate(plantId, wateredDate.dateInMillis)
            }
        }
    }
}
