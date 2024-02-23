package com.kristianskokars.myplants.core.data.local.db.model

import androidx.room.Entity
import androidx.room.PrimaryKey

const val NOTIFICATION_TABLE = "plantnotification"

@Entity(tableName = NOTIFICATION_TABLE)
data class PlantNotificationDBModel(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val plantId: String,
    val message: String,
    val dateInMillis: Long,
    val isSeen: Boolean = false,
)
