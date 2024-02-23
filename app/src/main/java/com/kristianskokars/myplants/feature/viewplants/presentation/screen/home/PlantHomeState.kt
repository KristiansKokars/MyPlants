package com.kristianskokars.myplants.feature.viewplants.presentation.screen.home

import com.kristianskokars.myplants.core.data.model.Plant
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

data class PlantHomeState(
    val plants: ImmutableList<Plant> = persistentListOf(),
    val plantFilter: PlantFilter = PlantFilter.UPCOMING,
    val hasUnseenNotifications: Boolean = false,
)
