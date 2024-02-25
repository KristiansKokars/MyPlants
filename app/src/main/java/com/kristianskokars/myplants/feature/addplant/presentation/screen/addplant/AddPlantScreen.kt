package com.kristianskokars.myplants.feature.addplant.presentation.screen.addplant

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
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
import com.kristianskokars.myplants.core.data.model.Day
import com.kristianskokars.myplants.core.data.model.DayList
import com.kristianskokars.myplants.core.data.model.PlantSize
import com.kristianskokars.myplants.core.presentation.components.BigButton
import com.kristianskokars.myplants.core.presentation.components.Combobox
import com.kristianskokars.myplants.core.presentation.components.MyPlantsIconButton
import com.kristianskokars.myplants.core.presentation.components.MyPlantsTextField
import com.kristianskokars.myplants.core.presentation.components.PlantsWallpaper
import com.kristianskokars.myplants.core.presentation.components.ScreenSurface
import com.kristianskokars.myplants.core.presentation.components.SmallButton
import com.kristianskokars.myplants.core.presentation.components.TimePickerDialog
import com.kristianskokars.myplants.core.presentation.theme.MyPlantsTheme
import com.kristianskokars.myplants.core.presentation.theme.Neutralus0
import com.kristianskokars.myplants.core.presentation.theme.OtherGreen100
import com.kristianskokars.myplants.feature.addplant.presentation.screen.pickplantsize.PickPlantSizeDialogNavArgs
import com.kristianskokars.myplants.feature.addplant.presentation.screen.pickwateringdates.PickWateringDatesDialogNavArgs
import com.kristianskokars.myplants.feature.destinations.PickPlantSizeDialogDestinationDestination
import com.kristianskokars.myplants.feature.destinations.PickWaterAmountDialogDestination
import com.kristianskokars.myplants.feature.destinations.PickWateringDatesDialogDestinationDestination
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.ramcosta.composedestinations.navigation.EmptyDestinationsNavigator
import com.ramcosta.composedestinations.result.NavResult
import com.ramcosta.composedestinations.result.ResultRecipient
import com.vanpra.composematerialdialogs.rememberMaterialDialogState
import kotlinx.datetime.LocalTime

data class AddPlantNavArgs(
    val plantId: String? = null
)

@Composable
@Destination(navArgsDelegate = AddPlantNavArgs::class)
fun AddPlantScreen(
    viewModel: AddPlantViewModel = hiltViewModel(),
    navigator: DestinationsNavigator,
    pickWateringDatesResultRecipient: ResultRecipient<PickWateringDatesDialogDestinationDestination, DayList>,
    pickPlantSizeResultRecipient: ResultRecipient<PickPlantSizeDialogDestinationDestination, PlantSize>,
    pickWaterAmountResultRecipient: ResultRecipient<PickWaterAmountDialogDestination, Int>,
) {
    val uploadImage = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent(),
        onResult = { uri: Uri? ->
            if (uri == null) return@rememberLauncherForActivityResult

            viewModel.onEvent(AddPlantEvent.OnAddPhoto(uri.toString()))
        }
    )
    val onAddImage = remember { { uploadImage.launch("image/*") } }

    pickWateringDatesResultRecipient.onNavResult { result ->
        when (result) {
            NavResult.Canceled -> { /* Ignored */
            }

            is NavResult.Value -> {
                viewModel.onEvent(AddPlantEvent.OnWateringDatesSelected(result.value.list))
            }
        }
    }
    pickPlantSizeResultRecipient.onNavResult { result ->
        when (result) {
            NavResult.Canceled -> { /* Ignored */
            }

            is NavResult.Value -> {
                viewModel.onEvent(AddPlantEvent.OnPlantSizeSelected(result.value))
            }
        }
    }
    pickWaterAmountResultRecipient.onNavResult { result ->
        when (result) {
            NavResult.Canceled -> { /* Ignored */
            }

            is NavResult.Value -> {
                viewModel.onEvent(AddPlantEvent.OnWaterAmountSelected(result.value))
            }
        }
    }

    AddPlantScreenContent(
        navigator = navigator,
        state = viewModel.state(),
        onEvent = viewModel::onEvent,
        onChangeImageClick = onAddImage,
    )
}

@Composable
private fun AddPlantScreenContent(
    navigator: DestinationsNavigator,
    state: AddPlantState,
    onEvent: (AddPlantEvent) -> Unit,
    onChangeImageClick: () -> Unit,
) {
    ScreenSurface(
        backgroundContent = {
            if (state.imageUrl == null) {
                PlantsWallpaper()
            } else {
                AsyncImage(
                    modifier = Modifier
                        .height(488.dp)
                        .fillMaxWidth(),
                    contentScale = ContentScale.Crop,
                    alignment = Alignment.Center,
                    model = state.imageUrl,
                    contentDescription = stringResource(R.string.plant_image)
                )
            }
        },
        shouldPadAgainstInsets = false,
        backgroundColor = OtherGreen100
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {
            ImageSection(
                modifier = Modifier.height(488.dp),
                imageUrl = state.imageUrl,
                onGoBack = navigator::navigateUp,
                onChangeImageClick = onChangeImageClick
            )
            Box(modifier = Modifier.fillMaxSize()) {
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
                PlantCreationForms(
                    modifier = Modifier.verticalScroll(rememberScrollState()),
                    navigator = navigator,
                    state = state,
                    onEvent = onEvent,
                )
            }
        }
    }
}

@Composable
fun ImageSection(
    modifier: Modifier = Modifier,
    imageUrl: String?,
    onGoBack: () -> Unit,
    onChangeImageClick: () -> Unit,
) {
    Column(modifier = modifier) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 48.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            MyPlantsIconButton(
                iconPainter = painterResource(id = R.drawable.ic_go_back),
                contentDescription = stringResource(R.string.go_back),
                onClick = onGoBack
            )
        }
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 48.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {

            if (imageUrl == null) {
                Image(
                    modifier = Modifier.zIndex(-1f),
                    alignment = Alignment.Center,
                    painter = painterResource(id = R.drawable.ic_placeholder_plant),
                    contentDescription = stringResource(R.string.plant_image)
                )
            } else {
                Spacer(modifier = Modifier.size(48.dp))
            }

            SmallButton(onClick = onChangeImageClick) {
                Icon(
                    modifier = Modifier.size(20.dp),
                    painter = painterResource(id = R.drawable.ic_upload),
                    contentDescription = null
                )
                Spacer(modifier = Modifier.size(4.dp))
                Text(
                    text = if (imageUrl == null)
                        stringResource(R.string.add_image)
                    else stringResource(R.string.change_image)
                )
            }
        }
    }

}

@Composable
private fun PlantCreationForms(
    modifier: Modifier,
    navigator: DestinationsNavigator,
    state: AddPlantState,
    onEvent: (AddPlantEvent) -> Unit
) {
    val selectTimeDialogState = rememberMaterialDialogState()
    TimePickerDialog(
        initialTime = state.selectedTime,
        dialogState = selectTimeDialogState,
        onTimeSelected = { onEvent(AddPlantEvent.OnSelectTime(it)) }
    )

    Column(
        modifier = modifier
            .background(
                color = Neutralus0,
            )
            .padding(horizontal = 16.dp)
            .padding(bottom = 16.dp)
            .fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        MyPlantsTextField(
            modifier = Modifier.fillMaxWidth(),
            label = { Text(text = stringResource(R.string.plant_name_mandatory)) },
            value = state.plantName,
            onValueChange = { onEvent(AddPlantEvent.OnPlantNameChange(it)) }
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(IntrinsicSize.Min),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Combobox(
                modifier = Modifier.weight(1f),
                onClick = {
                    navigator.navigate(
                        PickWateringDatesDialogDestinationDestination(
                            PickWateringDatesDialogNavArgs(DayList(state.selectedDates))
                        )
                    )
                },
                label = { Text(text = stringResource(id = R.string.dates)) },
                text = { Text(text = state.selectedDates.toDayText()) }
            )
            Combobox(
                modifier = Modifier.weight(1f),
                onClick = selectTimeDialogState::show,
                label = { Text(text = stringResource(R.string.time)) },
                text = { Text(text = state.selectedTime.toString()) }
            )
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(IntrinsicSize.Min),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Combobox(
                modifier = Modifier.weight(1f),
                onClick = { navigator.navigate(PickWaterAmountDialogDestination(state.waterAmount.toFloat())) },
                label = { Text(text = stringResource(R.string.the_amount_of_water_mandatory)) },
                text = { Text(text = "${state.waterAmount}${stringResource(R.string.ml)}") }
            )
            Combobox(
                modifier = Modifier.weight(1f),
                onClick = {
                    navigator.navigate(
                        PickPlantSizeDialogDestinationDestination(
                            PickPlantSizeDialogNavArgs(initialSize = state.selectedPlantSize)
                        )
                    )
                },
                label = { Text(text = stringResource(R.string.plant_size_mandatory)) },
                text = { Text(text = state.selectedPlantSize.toUIString()) }
            )
        }
        Column(modifier = Modifier.fillMaxWidth()) {
            MyPlantsTextField(
                modifier = Modifier.fillMaxWidth(),
                label = { Text(text = stringResource(R.string.description)) },
                value = state.plantDescription,
                onValueChange = { onEvent(AddPlantEvent.OnPlantDescriptionChange(it)) }
            )
        }
        Spacer(modifier = Modifier.weight(1f))
        BigButton(
            modifier = Modifier
                .background(Neutralus0)
                .padding(top = 4.dp, bottom = 16.dp)
                .fillMaxWidth(),
            enabled = state.canCreatePlant,
            onClick = {
                onEvent(if (state.isEditingExistingPlant) AddPlantEvent.EditPlant else AddPlantEvent.CreatePlant)
            }
        ) {
            if (state.isEditingExistingPlant) {
                Text(text = stringResource(R.string.save_changes))
            } else {
                Text(text = stringResource(R.string.create_a_plant))
            }
        }
    }
}

@Composable
private fun List<Day>.toDayText(): String {
    if (isEmpty()) return "None"

    if (count() == 1) return first().toUIString()

    val firstThreeLettersOfDays = map { it.toUIString().substring(0, 3) }
    return firstThreeLettersOfDays.joinToString(", ")
}

@Preview
@Composable
fun AddPlantScreenPreview() {
    MyPlantsTheme {
        AddPlantScreenContent(
            navigator = EmptyDestinationsNavigator,
            state = AddPlantState(
                plantName = "Montera",
                plantDescription = "Short Description",
                imageUrl = null,
                canCreatePlant = true,
                selectedDates = listOf(Day.MONDAY),
                selectedPlantSize = PlantSize.MEDIUM,
                selectedTime = LocalTime(8, 0, 0),
                waterAmount = 250,
                isEditingExistingPlant = false
            ),
            onEvent = {},
            onChangeImageClick = {}
        )
    }
}
