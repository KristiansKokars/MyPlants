package com.kristianskokars.myplants.core.data.local.db

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.kristianskokars.myplants.core.data.model.PLANT_TABLE
import com.kristianskokars.myplants.core.data.model.Plant
import kotlinx.coroutines.flow.Flow

@Dao
interface PlantDao {
    @Upsert
    suspend fun insertPlant(plant: Plant)

    @Query("SELECT * FROM $PLANT_TABLE")
    fun getPlants(): Flow<List<Plant>>

    @Query("SELECT * FROM $PLANT_TABLE WHERE id = :plantId LIMIT 1")
    suspend fun getPlantSingle(plantId: String): Plant

    @Query("SELECT * FROM $PLANT_TABLE WHERE id = :plantId LIMIT 1")
    fun getPlant(plantId: String): Flow<Plant>

    @Query("DELETE FROM $PLANT_TABLE WHERE id = :plantId")
    suspend fun deletePlant(plantId: String)

    @Query("UPDATE $PLANT_TABLE SET lastWateredDateInMillis = :wateredDateInMillis WHERE id = :plantId")
    suspend fun updatePlantLastWateredDate(plantId: String, wateredDateInMillis: Long)
}
