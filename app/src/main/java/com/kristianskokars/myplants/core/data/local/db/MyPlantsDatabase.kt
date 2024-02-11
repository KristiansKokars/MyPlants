package com.kristianskokars.myplants.core.data.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.kristianskokars.myplants.core.data.model.Plant

@Database(entities = [Plant::class], version = 1)
@TypeConverters(Converters::class)
abstract class MyPlantsDatabase : RoomDatabase() {
    abstract fun plantDao(): PlantDao
}
