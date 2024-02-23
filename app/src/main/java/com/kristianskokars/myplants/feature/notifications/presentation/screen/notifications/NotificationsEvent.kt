package com.kristianskokars.myplants.feature.notifications.presentation.screen.notifications

import com.kristianskokars.myplants.core.data.model.PlantNotification
import com.kristianskokars.myplants.feature.notifications.presentation.model.NotificationsFilter

sealed class NotificationsEvent {
    data class OnNotificationFilterChange(val newNotificationFilter: NotificationsFilter) : NotificationsEvent()
    data class NavigateToPlant(val notification: PlantNotification) : NotificationsEvent()
}
