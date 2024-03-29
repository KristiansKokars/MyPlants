package com.kristianskokars.myplants.feature.viewplants.presentation.screen.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.kristianskokars.myplants.R
import com.kristianskokars.myplants.core.presentation.components.BigButton
import com.kristianskokars.myplants.core.presentation.components.LinearWhiteFadeOut
import com.kristianskokars.myplants.core.presentation.components.MyPlantsTab
import com.kristianskokars.myplants.core.presentation.components.MyPlantsTabRow
import com.kristianskokars.myplants.core.presentation.components.NotificationButton
import com.kristianskokars.myplants.core.presentation.components.ScreenSurface
import com.kristianskokars.myplants.core.presentation.theme.Accent500
import com.kristianskokars.myplants.core.presentation.theme.MyPlantsTheme
import com.kristianskokars.myplants.core.presentation.theme.Neutralus100
import com.kristianskokars.myplants.core.presentation.theme.Neutralus300
import com.kristianskokars.myplants.core.presentation.theme.Neutralus900
import com.kristianskokars.myplants.feature.destinations.AddPlantScreenDestination
import com.kristianskokars.myplants.feature.destinations.DeletePlantDialogDestinationDestination
import com.kristianskokars.myplants.feature.destinations.NotificationsScreenDestination
import com.kristianskokars.myplants.feature.destinations.PlantDetailsScreenDestination
import com.kristianskokars.myplants.feature.viewplants.presentation.components.PlantCard
import com.kristianskokars.myplants.lib.Loadable
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootNavGraph
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.ramcosta.composedestinations.navigation.EmptyDestinationsNavigator
import com.ramcosta.composedestinations.result.NavResult
import com.ramcosta.composedestinations.result.ResultRecipient

@Composable
@Destination
@RootNavGraph(start = true)
fun PlantHomeScreen(
    viewModel: PlantHomeViewModel = hiltViewModel(),
    navigator: DestinationsNavigator,
    resultRecipient: ResultRecipient<DeletePlantDialogDestinationDestination, DeletePlantResult>
) {
    resultRecipient.onNavResult { result ->
        when (result) {
            NavResult.Canceled -> { /* Ignored */ }
            is NavResult.Value -> {
                if (result.value.shouldDelete) {
                    viewModel.onEvent(PlantHomeEvent.DeletePlant(result.value.plantId))
                }
            }
        }
    }

    PlantHomeScreenContent(
        navigator = navigator,
        state = viewModel.state(),
        onEvent = viewModel::onEvent
    )
}

@Composable
private fun PlantHomeScreenContent(
    navigator: DestinationsNavigator,
    state: PlantHomeState,
    onEvent: (PlantHomeEvent) -> Unit,
) {
    val selectedTabIndex by remember(state.plantFilter) {
        derivedStateOf {
            when (state.plantFilter) {
                PlantFilter.UPCOMING -> 0
                PlantFilter.FORGOT_TO_WATER -> 1
                PlantFilter.HISTORY -> 2
            }
        }
    }

    ScreenSurface {
        Scaffold(
            containerColor = Color.Transparent,
            floatingActionButton = {
                if (state.plants is Loadable.Data && state.plants.data.isNotEmpty()) {
                    FloatingActionButton(
                        modifier = Modifier.padding(bottom = 64.dp),
                        elevation = FloatingActionButtonDefaults.loweredElevation(),
                        containerColor = Accent500,
                        contentColor = Neutralus100,
                        onClick = { navigator.navigate(AddPlantScreenDestination())}
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_add),
                            contentDescription = stringResource(R.string.add_new_plant)
                        )
                    }
                }
            },
        ) { padding ->
            Box(
                modifier = Modifier
                    .padding(padding)
                    .fillMaxSize()
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(top = 36.dp, start = 16.dp, end = 16.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = stringResource(R.string.lets_care_my_plants_title),
                            style = MaterialTheme.typography.titleLarge
                        )
                        NotificationButton(
                            hasNotifications = state.hasUnseenNotifications,
                            onClick = { navigator.navigate(NotificationsScreenDestination)}
                        )
                    }
                    Spacer(modifier = Modifier.size(16.dp))
                    MyPlantsTabRow(
                        selectedTabIndex = selectedTabIndex,
                        containerColor = Color.Transparent,
                    ) {
                        MyPlantsTab(
                            selected = selectedTabIndex == 0,
                            onClick = { onEvent(PlantHomeEvent.OnPlantFilterChange(PlantFilter.UPCOMING)) },
                            text = {
                                Text(
                                    text = stringResource(R.string.upcoming),
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = if (selectedTabIndex == 0) Accent500 else Neutralus300
                                )
                            }
                        )
                        MyPlantsTab(
                            selected = selectedTabIndex == 1,
                            onClick = { onEvent(PlantHomeEvent.OnPlantFilterChange(PlantFilter.FORGOT_TO_WATER)) },
                            text = {
                                Text(
                                    text = stringResource(R.string.forgot_to_water),
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = if (selectedTabIndex == 1) Accent500 else Neutralus300
                                )
                            }
                        )
                        MyPlantsTab(
                            selected = selectedTabIndex == 2,
                            onClick = { onEvent(PlantHomeEvent.OnPlantFilterChange(PlantFilter.HISTORY)) },
                            text = {
                                Text(
                                    text = stringResource(R.string.history),
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = if (selectedTabIndex == 2) Accent500 else Neutralus300
                                )
                            }
                        )
                    }
                    Spacer(modifier = Modifier.size(20.dp))
                    when (state.plants) {
                        is Loadable.Data -> {
                            val plants = state.plants.data

                            if (plants.isEmpty()) {
                                NoPlantsInList(onAddYourFirstPlantClick = { navigator.navigate(AddPlantScreenDestination())} )
                            } else {
                                LazyVerticalGrid(
                                    columns = GridCells.Fixed(2),
                                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                                    verticalArrangement = Arrangement.spacedBy(12.dp)
                                ) {
                                    items(plants, key = { it.modelId }) { plant ->
                                        PlantCard(
                                            plant = plant,
                                            onClick = { navigator.navigate(PlantDetailsScreenDestination(id = plant.plantId))},
                                            onLongClick = {
                                                navigator.navigate(
                                                    DeletePlantDialogDestinationDestination(
                                                        plantId = plant.plantId,
                                                        plantName = plant.name,
                                                    )
                                                )
                                            },
                                            onMarkPlantAsWatered = {
                                                onEvent(PlantHomeEvent.MarkPlantAsWatered(plant.plantId))
                                            }
                                        )
                                    }
                                }
                            }
                        }
                        is Loadable.Error -> TODO()
                        Loadable.Loading -> { /* Ignored */ }
                    }
                }
                LinearWhiteFadeOut(modifier = Modifier.align(Alignment.BottomCenter))
            }
        }
    }
}

@Composable
private fun NoPlantsInList(
    onAddYourFirstPlantClick: () -> Unit,
) {
    Column(modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
        Spacer(modifier = Modifier.size(80.dp))
        Image(painter = painterResource(id = R.drawable.plants), contentDescription = null)
        Spacer(modifier = Modifier.size(32.dp))
        Text(
            text = stringResource(R.string.sorry),
            style = MaterialTheme.typography.bodyLarge,
            color = Neutralus900
        )
        Spacer(modifier = Modifier.size(8.dp))
        Text(
            modifier = Modifier.padding(horizontal = 48.dp),
            textAlign = TextAlign.Center,
            text = stringResource(R.string.no_plants_in_list),
            style = MaterialTheme.typography.bodySmall
        )
        Spacer(modifier = Modifier.size(16.dp))
        BigButton(onClick = onAddYourFirstPlantClick) {
            Text(text = stringResource(R.string.add_your_first_plant))
        }
    }
}

@Preview
@Composable
private fun PlantHomeScreenPreview() {
    MyPlantsTheme {
        PlantHomeScreenContent(
            navigator = EmptyDestinationsNavigator,
            state = PlantHomeState(),
            onEvent = {}
        )
    }
}
