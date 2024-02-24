package com.kristianskokars.myplants.core.data.local.db

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.kristianskokars.myplants.core.data.local.db.model.PLANT_WATERED_DATE_TABLE
import com.kristianskokars.myplants.core.data.local.db.model.PlantWateredDateDBModel
import kotlinx.coroutines.flow.Flow

@Dao
interface PlantWateredDateDao {
    @Upsert
    suspend fun insertDate(date: PlantWateredDateDBModel)
    @Query("SELECT * FROM $PLANT_WATERED_DATE_TABLE")
    fun getDates(): Flow<List<PlantWateredDateDBModel>>
}
