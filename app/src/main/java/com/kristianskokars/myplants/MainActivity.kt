package com.kristianskokars.myplants

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.Modifier
import com.google.accompanist.navigation.material.ExperimentalMaterialNavigationApi
import com.kristianskokars.myplants.core.presentation.components.ScreenSurface
import com.kristianskokars.myplants.core.presentation.theme.OtherGreen100
import com.kristianskokars.myplants.feature.NavGraphs
import com.kristianskokars.myplants.feature.appCurrentDestinationAsState
import com.kristianskokars.myplants.feature.destinations.AddPlantScreenDestination
import com.kristianskokars.myplants.feature.destinations.PlantHomeScreenDestination
import com.ramcosta.composedestinations.DestinationsNavHost
import com.ramcosta.composedestinations.animations.rememberAnimatedNavHostEngine

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterialNavigationApi::class, ExperimentalAnimationApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            val engine = rememberAnimatedNavHostEngine()
            val navController = engine.rememberNavController()
            val currentDestination = navController.appCurrentDestinationAsState().value

            ScreenSurface(
                backgroundColor = when (currentDestination) {
                    AddPlantScreenDestination -> OtherGreen100
                    PlantHomeScreenDestination -> MaterialTheme.colorScheme.background
                    else -> MaterialTheme.colorScheme.background
                }
            ) {
                DestinationsNavHost(
                    modifier = Modifier.fillMaxSize(),
                    navGraph = NavGraphs.root,
                    engine = engine,
                    navController = navController
                )
            }
        }
    }
}
