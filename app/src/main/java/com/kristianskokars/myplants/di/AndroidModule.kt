package com.kristianskokars.myplants.di

import com.kristianskokars.myplants.core.data.local.AndroidPlantWateringScheduler
import com.kristianskokars.myplants.core.data.local.PlantWateringScheduler
import com.kristianskokars.myplants.core.data.local.file.AndroidFileStorage
import com.kristianskokars.myplants.core.data.local.file.FileStorage
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class AndroidModule {
    @Binds
    @Singleton
    abstract fun bindFileStorage(androidFileStorage: AndroidFileStorage): FileStorage

    @Binds
    @Singleton
    abstract fun bindPlantWateringScheduler(androidPlantWateringScheduler: AndroidPlantWateringScheduler): PlantWateringScheduler
}
