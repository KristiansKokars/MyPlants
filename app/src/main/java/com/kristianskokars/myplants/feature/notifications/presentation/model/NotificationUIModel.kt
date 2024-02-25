package com.kristianskokars.myplants.feature.notifications.presentation.model

import com.kristianskokars.myplants.core.data.model.PlantNotification
import com.kristianskokars.myplants.lib.UIText

sealed class NotificationUIModel {
    data class Divider(val text: UIText) : NotificationUIModel()
    data class Date(val dateInMillis: Long) : NotificationUIModel()
    data class Notification(val notification: PlantNotification) : NotificationUIModel()
}
