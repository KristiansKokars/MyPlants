package com.kristianskokars.myplants

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.kristianskokars.myplants.core.presentation.components.ScreenSurface
import com.kristianskokars.myplants.feature.view_plants.presentation.PlantHomeScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ScreenSurface {
                PlantHomeScreen()
            }
        }
    }
}
