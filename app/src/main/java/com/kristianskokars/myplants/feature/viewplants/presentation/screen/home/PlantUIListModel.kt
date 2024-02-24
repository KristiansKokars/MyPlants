package com.kristianskokars.myplants.feature.viewplants.presentation.screen.home


data class PlantUIListModel(
    val modelId: String,
    val plantId: String,
    val name: String,
    val description: String,
    val waterInMilliliters: Int,
    val lastWateredDateInMillis: Long?,
    val wateringDateTimeInMillis: Long,
    val pictureUrl: String?,
)
