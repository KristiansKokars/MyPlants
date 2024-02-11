package com.kristianskokars.myplants.di

import android.content.Context
import androidx.room.Room
import com.kristianskokars.myplants.core.data.local.db.MyPlantsDatabase

class AppContainer(
    applicationContext: Context
) {
    private val database by lazy {
        Room
            .databaseBuilder(applicationContext, MyPlantsDatabase::class.java, "MyPlantsDB")
            .fallbackToDestructiveMigration()
            .build()
    }

    val plantDao by lazy {
        database.plantDao()
    }
}
