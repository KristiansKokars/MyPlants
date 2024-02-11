package com.kristianskokars.myplants

import android.app.Application
import com.kristianskokars.myplants.di.AppContainer

class MainApp : Application() {
    lateinit var container: AppContainer
        private set

    override fun onCreate() {
        super.onCreate()
        container = AppContainer(applicationContext)
    }
}
