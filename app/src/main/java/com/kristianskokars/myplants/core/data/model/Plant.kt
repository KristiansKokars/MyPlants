package com.kristianskokars.myplants.core.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

const val PLANT_TABLE = "plant"

@Entity(tableName = PLANT_TABLE)
data class Plant(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name: String,
    val description: String,
    val waterInMilliliters: Int,
    val wateringDates: List<Day>,
    val pictureUrl: String?
)
