package com.kristianskokars.myplants.core.data.local.db

import androidx.room.Dao
import androidx.room.Upsert
import com.kristianskokars.myplants.core.data.local.db.model.PlantWateredDateDBModel

@Dao
interface PlantWateredDateDao {
    @Upsert
    suspend fun insertDate(date: PlantWateredDateDBModel)
}
