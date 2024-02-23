package com.kristianskokars.myplants.core.data.local.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.kristianskokars.myplants.core.data.local.db.model.PlantNotificationDBModel
import com.kristianskokars.myplants.core.data.local.db.model.PlantWateredDateDBModel
import com.kristianskokars.myplants.core.data.model.Plant

@Database(entities = [Plant::class, PlantNotificationDBModel::class, PlantWateredDateDBModel::class], version = 1)
@TypeConverters(Converters::class)
abstract class MyPlantsDatabase : RoomDatabase() {
    abstract fun plantDao(): PlantDao
    abstract fun plantNotificationDao(): PlantNotificationDao
    abstract fun plantWateredDateDao(): PlantWateredDateDao

    companion object {
        private const val DB_NAME = "MyPlantsDB"

        fun create(applicationContext: Context) = Room
            .databaseBuilder(applicationContext, MyPlantsDatabase::class.java, DB_NAME)
            .fallbackToDestructiveMigration()
            .build()
    }
}
