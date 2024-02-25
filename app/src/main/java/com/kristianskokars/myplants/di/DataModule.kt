package com.kristianskokars.myplants.di

import android.app.AlarmManager
import android.content.Context
import androidx.work.WorkManager
import com.kristianskokars.myplants.core.data.local.db.MyPlantsDatabase
import com.kristianskokars.myplants.core.data.local.db.PlantDao
import com.kristianskokars.myplants.core.data.local.db.PlantNotificationDao
import com.kristianskokars.myplants.core.data.local.db.PlantWateredDateDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataModule {
    @Singleton
    @Provides
    fun providePlantDatabase(@ApplicationContext context: Context) = MyPlantsDatabase.create(context)

    @Singleton
    @Provides
    fun providePlantDao(db: MyPlantsDatabase): PlantDao = db.plantDao()

    @Singleton
    @Provides
    fun providePlantWateredDateDao(db: MyPlantsDatabase): PlantWateredDateDao = db.plantWateredDateDao()

    @Singleton
    @Provides
    fun providePlantNotificationDao(db: MyPlantsDatabase): PlantNotificationDao = db.plantNotificationDao()

    @Singleton
    @Provides
    fun provideAlarmManager(@ApplicationContext context: Context): AlarmManager = context.getSystemService(AlarmManager::class.java)

    @Provides
    @Singleton
    fun provideWorkManager(@ApplicationContext context: Context): WorkManager = WorkManager.getInstance(context)

    @Singleton
    @Provides
    fun provideClock(): Clock = Clock.System

    @Singleton
    @Provides
    fun provideTimeZone(): TimeZone = TimeZone.currentSystemDefault()
}
