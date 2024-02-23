package com.kristianskokars.myplants.feature.notifications.presentation.screen.notifications

import com.kristianskokars.myplants.feature.notifications.presentation.model.NotificationUIModel
import com.kristianskokars.myplants.feature.notifications.presentation.model.NotificationsFilter
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

data class NotificationsState(
    val notificationFilter: NotificationsFilter = NotificationsFilter.ALL,
    val notifications: ImmutableList<NotificationUIModel> = persistentListOf()
)
