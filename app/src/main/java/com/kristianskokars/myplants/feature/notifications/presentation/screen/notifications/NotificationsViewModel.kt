package com.kristianskokars.myplants.feature.notifications.presentation.screen.notifications

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.kristianskokars.myplants.core.data.local.db.PlantNotificationDao
import com.kristianskokars.myplants.core.data.model.PlantNotification
import com.kristianskokars.myplants.feature.destinations.PlantDetailsScreenDestination
import com.kristianskokars.myplants.feature.notifications.presentation.model.NotificationUIModel
import com.kristianskokars.myplants.feature.notifications.presentation.model.NotificationsFilter
import com.kristianskokars.myplants.lib.Navigator
import com.kristianskokars.myplants.lib.launch
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import javax.inject.Inject

@HiltViewModel
class NotificationsViewModel @Inject constructor(
    private val plantNotificationDao: PlantNotificationDao,
    private val navigator: Navigator,
    private val timeZone: TimeZone
) : ViewModel() {
    private var notificationFilter by mutableStateOf(NotificationsFilter.ALL)

    @Composable
    fun state(): NotificationsState {
        return NotificationsState(
            notificationFilter = notificationFilter,
            notifications = allNotifications().toImmutableList()
        )
    }

    fun onEvent(event: NotificationsEvent) {
        when (event) {
            is NotificationsEvent.OnNotificationFilterChange -> onNotificationFilterChange(event.newNotificationFilter)
            is NotificationsEvent.NavigateToPlant -> navigateToPlant(event.notification)
        }
    }

    @Composable
    private fun allNotifications(): List<NotificationUIModel> {
        return plantNotificationDao
            .getNotificationsWithPlant()
            .map { notifications ->
                var lastNotificationDate = LocalDate(1, 1, 1)
                val newNotifications = mutableListOf<NotificationUIModel>()

                notifications.forEach { (notification, plant) ->
                    val notificationDate = Instant
                        .fromEpochMilliseconds(notification.dateInMillis)
                        .toLocalDateTime(timeZone).date
                    if (notificationDate != lastNotificationDate) {
                        newNotifications.add(
                            NotificationUIModel.Date(notification.dateInMillis)
                        )
                    }
                    lastNotificationDate = notificationDate
                    newNotifications.add(
                        NotificationUIModel.Notification(
                            PlantNotification(
                                id = notification.id,
                                plantId = notification.plantId,
                                plantName = plant.name,
                                message = notification.message,
                                dateInMillis = notification.dateInMillis,
                                isSeen = notification.isSeen
                            )
                        )
                    )
                }
                newNotifications
            }
            .collectAsState(initial = emptyList()).value
    }

    private fun onNotificationFilterChange(newNotificationFilter: NotificationsFilter) {
        notificationFilter = newNotificationFilter
    }

    private fun navigateToPlant(notification: PlantNotification) {
        launch {
            // TODO: move scope to DI
            CoroutineScope(Dispatchers.IO).launch {
                plantNotificationDao.markNotificationAsSeen(notification.id)
            }
            navigator.navigate(Navigator.Action.Navigate(PlantDetailsScreenDestination(id = notification.plantId)))
        }
    }
}
