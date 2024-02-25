package com.kristianskokars.myplants.core.data.model

data class PlantNotification(
    val id: Int = 0,
    val plantId: String,
    val plantName: String,
    val message: String, // TODO: this should not be saved as a string
    val dateInMillis: Long,
    val isSeen: Boolean = false,
)
