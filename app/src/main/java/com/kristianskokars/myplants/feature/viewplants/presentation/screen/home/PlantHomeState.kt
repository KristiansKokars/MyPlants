package com.kristianskokars.myplants.feature.viewplants.presentation.screen.home

import com.kristianskokars.myplants.lib.Loadable
import kotlinx.collections.immutable.ImmutableList

data class PlantHomeState(
    val plants: Loadable<ImmutableList<PlantUIListModel>> = Loadable.Loading,
    val plantFilter: PlantFilter = PlantFilter.UPCOMING,
    val hasUnseenNotifications: Boolean = false,
)
