package com.kristianskokars.myplants.feature.viewplants.presentation.screen.plantdetails

import android.os.Parcelable
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.kristianskokars.myplants.R
import com.kristianskokars.myplants.core.data.DeepLinks
import com.kristianskokars.myplants.core.data.model.Day
import com.kristianskokars.myplants.core.data.model.Plant
import com.kristianskokars.myplants.core.data.model.PlantSize
import com.kristianskokars.myplants.core.data.model.wateringFrequencyPerWeek
import com.kristianskokars.myplants.core.presentation.components.BigButton
import com.kristianskokars.myplants.core.presentation.components.LinearWhiteFadeOut
import com.kristianskokars.myplants.core.presentation.components.MyPlantsIconButton
import com.kristianskokars.myplants.core.presentation.components.PlantsWallpaper
import com.kristianskokars.myplants.core.presentation.components.ScreenSurface
import com.kristianskokars.myplants.core.presentation.theme.Accent500
import com.kristianskokars.myplants.core.presentation.theme.MyPlantsTheme
import com.kristianskokars.myplants.core.presentation.theme.Neutralus0
import com.kristianskokars.myplants.core.presentation.theme.Neutralus500
import com.kristianskokars.myplants.core.presentation.theme.OtherGreen100
import com.kristianskokars.myplants.feature.addplant.presentation.screen.addplant.AddPlantNavArgs
import com.kristianskokars.myplants.feature.destinations.AddPlantScreenDestination
import com.ramcosta.composedestinations.annotation.DeepLink
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.ramcosta.composedestinations.navigation.EmptyDestinationsNavigator
import kotlinx.parcelize.Parcelize

@Parcelize
data class PlantDetailsNavArgs(val id: String) : Parcelable

@Destination(
    navArgsDelegate = PlantDetailsNavArgs::class,
    deepLinks = [
        DeepLink(uriPattern = DeepLinks.plantPattern)
    ]
)
@Composable
fun PlantDetailsScreen(
    viewModel: PlantDetailsViewModel = hiltViewModel(),
    navigator: DestinationsNavigator
) {
    PlantDetailsScreenContent(
        state = viewModel.state(),
        navigator = navigator
    )
}

@Composable
fun PlantDetailsScreenContent(
    state: PlantDetailsState,
    navigator: DestinationsNavigator,
) {
    ScreenSurface(
        backgroundContent = {
            if (state.plant?.pictureUrl == null) {
                PlantsWallpaper()
            } else {
                AsyncImage(
                    modifier = Modifier.height(488.dp),
                    contentScale = ContentScale.FillBounds,
                    model = state.plant.pictureUrl,
                    contentDescription = stringResource(R.string.plant_image)
                )
            }
        },
        shouldPadAgainstInsets = false,
        backgroundColor = OtherGreen100
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            Column(modifier = Modifier.height(488.dp)) {
                if (state.plant == null) {
                    return@ScreenSurface
                }

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 48.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                ) {
                    MyPlantsIconButton(
                        iconPainter = painterResource(id = R.drawable.ic_go_back),
                        contentDescription = stringResource(R.string.go_back),
                        onClick = navigator::navigateUp
                    )
                    MyPlantsIconButton(
                        iconPainter = painterResource(id = R.drawable.ic_edit),
                        contentDescription = stringResource(R.string.edit_plant),
                        onClick = { navigator.navigate(AddPlantScreenDestination(navArgs = AddPlantNavArgs(state.plant.id))) }
                    )
                }
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(bottom = 48.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.SpaceBetween
                ) {
                    if (state.plant.pictureUrl == null) {
                        Image(
                            modifier = Modifier.zIndex(-1f),
                            alignment = Alignment.Center,
                            painter = painterResource(id = R.drawable.ic_placeholder_plant),
                            contentDescription = stringResource(R.string.plant_image)
                        )
                    } else {
                        Spacer(modifier = Modifier.size(48.dp))
                    }

                    Card(
                        colors = CardDefaults.cardColors(
                            containerColor = Neutralus0
                        )
                    ) {
                        Row(
                            modifier = Modifier
                                .padding(vertical = 12.dp, horizontal = 20.dp)
                                .widthIn(max = 280.dp)
                                .heightIn(max = 48.dp)
                                .fillMaxSize(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically,
                        ) {
                            Column {
                                Text(text = stringResource(R.string.size), style = MaterialTheme.typography.labelMedium, color = Neutralus500)
                                Spacer(modifier = Modifier.size(4.dp))
                                Text(text = state.plant.size.toUIString(), style = MaterialTheme.typography.labelLarge, color = Accent500)
                            }
                            Column {
                                Text(text = stringResource(R.string.water), style = MaterialTheme.typography.labelMedium, color = Neutralus500)
                                Spacer(modifier = Modifier.size(4.dp))
                                Text(
                                    text = stringResource(
                                        id = R.string.milliliters,
                                        state.plant.waterInMilliliters
                                    ),
                                    style = MaterialTheme.typography.labelLarge,
                                    color = Accent500
                                )
                            }
                            Column {
                                Text(text = stringResource(R.string.frequency), style = MaterialTheme.typography.labelMedium, color = Neutralus500)
                                Spacer(modifier = Modifier.size(4.dp))
                                Text(
                                    text = stringResource(R.string.watering_times_week, state.plant.wateringFrequencyPerWeek()),
                                    style = MaterialTheme.typography.labelLarge, color = Accent500
                                )
                            }
                        }
                    }
                }
            }
            Box(
                modifier = Modifier.fillMaxSize()
            ) {
                LinearWhiteFadeOut(
                    modifier = Modifier
                        .zIndex(-10f)
                        .align(Alignment.TopCenter)
                        .height(160.dp)
                        .graphicsLayer {
                            translationY = -64.dp.toPx()
                        }
                )
                Box(
                    modifier = Modifier
                        .graphicsLayer { translationY = -24.dp.toPx() }
                        .background(
                            color = Neutralus0,
                            RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp)
                        )
                        .fillMaxWidth()
                        .height(24.dp)
                )
                Column(
                    modifier = Modifier
                        .background(color = Neutralus0)
                        .padding(horizontal = 16.dp)
                        .padding(bottom = 16.dp)
                        .fillMaxSize(),
                ) {
                    if (state.plant == null) {
                        return@ScreenSurface
                    }

                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f),
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        Text(text = state.plant.name, style = MaterialTheme.typography.titleLarge)
                        Text(
                            modifier = Modifier.verticalScroll(rememberScrollState()),
                            text = state.plant.description,
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                    ) {
                        BigButton(
                            modifier = Modifier
                                .background(Neutralus0)
                                .padding(top = 4.dp, bottom = 16.dp)
                                .fillMaxWidth(),
                            onClick = {}
                        ) {
                            Text(text = stringResource(R.string.mark_as_watered))
                        }
                    }
                }
            }
        }
    }
}

@Preview
@Composable
private fun PlantDetailsScreenPreview() {
    MyPlantsTheme {
        PlantDetailsScreenContent(
            state = PlantDetailsState(
                plant = Plant(
                    id = "0",
                    name = "Monstera",
                    description = "Native to the Yunnan and Sichuan provinces of southern China, the Chinese money plant was first brought to the UK in 1906 by Scottish botanist George Forrest (yes, we know the exact man who found it). It became a popular houseplant later in the 20th century because it is simple to grow and really easy to propagate, meaning friends could pass cuttings around amongst themselves. That earned it the nickname ‘pass it on plant’.",
                    waterInMilliliters = 250,
                    wateringDates = listOf(Day.EVERYDAY),
                    pictureUrl = null,
                    size = PlantSize.MEDIUM,
                    wateringTimeInMillis = 0
                )
            ),
            navigator = EmptyDestinationsNavigator
        )
    }
}
