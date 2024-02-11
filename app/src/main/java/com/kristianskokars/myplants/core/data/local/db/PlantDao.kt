package com.kristianskokars.myplants.core.data.local.db

import androidx.room.Dao
import androidx.room.Insert
import com.kristianskokars.myplants.core.data.model.Plant

@Dao
interface PlantDao {
    @Insert
    suspend fun insertPlant(plant: Plant)
}
