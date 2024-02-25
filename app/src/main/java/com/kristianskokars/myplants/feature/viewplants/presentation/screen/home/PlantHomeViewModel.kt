package com.kristianskokars.myplants.feature.viewplants.presentation.screen.home

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.core.net.toUri
import androidx.lifecycle.ViewModel
import com.kristianskokars.myplants.core.data.local.PlantWateringScheduler
import com.kristianskokars.myplants.core.data.local.db.PlantDao
import com.kristianskokars.myplants.core.data.local.db.PlantNotificationDao
import com.kristianskokars.myplants.core.data.local.db.PlantWateredDateDao
import com.kristianskokars.myplants.core.data.local.db.model.PlantWateredDateDBModel
import com.kristianskokars.myplants.core.data.local.file.FileStorage
import com.kristianskokars.myplants.core.data.model.nextWateringDateInMillis
import com.kristianskokars.myplants.lib.Loadable
import com.kristianskokars.myplants.lib.launch
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toPersistentList
import kotlinx.coroutines.NonCancellable
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.withContext
import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import javax.inject.Inject

@HiltViewModel
class PlantHomeViewModel @Inject constructor(
    private val plantDao: PlantDao,
    private val plantNotificationDao: PlantNotificationDao,
    private val plantWateredDateDao: PlantWateredDateDao,
    private val plantWateringScheduler: PlantWateringScheduler,
    private val fileStorage: FileStorage,
    private val clock: Clock
): ViewModel() {
    private var plantFilter by mutableStateOf(PlantFilter.UPCOMING)

    @Composable
    fun state(): PlantHomeState {
        return PlantHomeState(
            plants = getPlantsByFilter(),
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
    private fun hasUnseenNotifications(): Boolean {
        return plantNotificationDao
            .hasUnseenNotifications()
            .collectAsState(initial = false).value
    }

    @Composable
    private fun getPlantsByFilter(): Loadable<ImmutableList<PlantUIListModel>> {
        return combine(
            plantDao.getPlants(),
            plantWateredDateDao.getDates()
        ) { plants, dates ->
            plants.map { plant ->
                when (plantFilter) {
                    PlantFilter.HISTORY -> {
                        val plantModels = mutableListOf<PlantUIListModel>()
                        dates.filter { it.plantId == plant.id }.forEach { date ->
                            val plantUIListModel = PlantUIListModel(
                                modelId = "${plant.id}.${date.dateInMillis}",
                                plantId = plant.id,
                                name = plant.name,
                                description = plant.description,
                                waterInMilliliters = plant.waterInMilliliters,
                                wateringDateTimeInMillis = date.dateInMillis,
                                pictureUrl = plant.pictureUrl,
                                lastWateredDateInMillis = date.dateInMillis
                            )
                            plantModels.add(plantUIListModel)
                        }
                        plantModels
                    }
                    PlantFilter.FORGOT_TO_WATER -> {
                        val plantModels = mutableListOf<PlantUIListModel>()
                        val currentTime = clock.now()
                        val nextDate = plant.nextWateringDateInMillis(Instant.fromEpochMilliseconds(plant.lastWateredDateInMillis ?: plant.createdAtInMillis))
                        if (currentTime > Instant.fromEpochMilliseconds(nextDate)) {
                            val plantUIListModel = PlantUIListModel(
                                modelId = "${plant.id}.${nextDate}",
                                plantId = plant.id,
                                name = plant.name,
                                description = plant.description,
                                waterInMilliliters = plant.waterInMilliliters,
                                wateringDateTimeInMillis = nextDate,
                                pictureUrl = plant.pictureUrl,
                                lastWateredDateInMillis = plant.lastWateredDateInMillis
                            )
                            plantModels.add(plantUIListModel)
                        }
                        plantModels
                    }
                    PlantFilter.UPCOMING -> {
                        val plantModels = mutableListOf<PlantUIListModel>()
                        var currentTime = clock.now()

                        repeat(7) {
                            val nextDate = plant.nextWateringDateInMillis(currentTime)
                            val plantUIListModel = PlantUIListModel(
                                modelId = "${plant.id}.${nextDate}",
                                plantId = plant.id,
                                name = plant.name,
                                description = plant.description,
                                waterInMilliliters = plant.waterInMilliliters,
                                wateringDateTimeInMillis = nextDate,
                                pictureUrl = plant.pictureUrl,
                                lastWateredDateInMillis = plant.lastWateredDateInMillis
                            )
                            plantModels.add(plantUIListModel)
                            currentTime = Instant.fromEpochMilliseconds(nextDate + 1L)
                        }
                        plantModels
                    }
                }
            }.flatten().sortedBy { it.wateringDateTimeInMillis }.toPersistentList().let { Loadable.Data(it) }
        }.collectAsState(initial = Loadable.Loading).value
    }

    private fun onPlantFilterChange(newPlantFilter: PlantFilter) {
        plantFilter = newPlantFilter
    }

    private fun deletePlant(plantId: String) {
        launch {
            withContext(NonCancellable) {
                plantWateringScheduler.unscheduleWatering(plantId)
                val plant = plantDao.getPlantSingle(plantId)
                plantDao.deletePlant(plantId)
                plant.pictureUrl?.let { fileStorage.deleteFileFromInternalAppStorage(it.toUri()) }
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
