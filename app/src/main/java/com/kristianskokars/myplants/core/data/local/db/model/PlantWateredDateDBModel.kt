package com.kristianskokars.myplants.core.data.local.db.model

import androidx.room.Entity

const val PLANT_WATERED_DATE_TABLE = "plant_watered_date"

@Entity(tableName = PLANT_WATERED_DATE_TABLE, primaryKeys = ["plantId", "dateInMillis"])
data class PlantWateredDateDBModel(
    val plantId: String,
    val dateInMillis: Long,
)
