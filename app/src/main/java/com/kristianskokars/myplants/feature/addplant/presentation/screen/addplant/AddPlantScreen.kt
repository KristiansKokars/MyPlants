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
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.kristianskokars.myplants.R
import com.kristianskokars.myplants.core.data.model.Day
import com.kristianskokars.myplants.core.data.model.DayList
import com.kristianskokars.myplants.core.data.model.PlantSize
import com.kristianskokars.myplants.core.presentation.components.BigButton
import com.kristianskokars.myplants.core.presentation.components.Combobox
import com.kristianskokars.myplants.core.presentation.components.MyPlantsTextField
import com.kristianskokars.myplants.core.presentation.components.PlantsWallpaper
import com.kristianskokars.myplants.core.presentation.components.ScreenSurface
import com.kristianskokars.myplants.core.presentation.components.SmallButton
import com.kristianskokars.myplants.core.presentation.components.TimePickerDialog
import com.kristianskokars.myplants.core.presentation.theme.MyPlantsTheme
import com.kristianskokars.myplants.core.presentation.theme.Neutralus0
import com.kristianskokars.myplants.core.presentation.theme.Neutralus900
import com.kristianskokars.myplants.core.presentation.theme.OtherGreen100
import com.kristianskokars.myplants.feature.destinations.PickPlantSizeDialogDestinationDestination
import com.kristianskokars.myplants.feature.destinations.PickWaterAmountDialogDestination
import com.kristianskokars.myplants.feature.destinations.PickWateringDatesDialogDestinationDestination
import com.kristianskokars.myplants.lib.ObserveAsEvents
import com.kristianskokars.myplants.lib.pxToDp
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.ramcosta.composedestinations.navigation.EmptyDestinationsNavigator
import com.ramcosta.composedestinations.result.NavResult
import com.ramcosta.composedestinations.result.ResultRecipient
import com.vanpra.composematerialdialogs.rememberMaterialDialogState
import kotlinx.datetime.LocalTime

@Composable
@Destination
fun AddPlantScreen(
    viewModel: AddPlantViewModel = viewModel(factory = AddPlantViewModel.Factory),
    navigator: DestinationsNavigator,
    pickWateringDatesResultRecipient: ResultRecipient<PickWateringDatesDialogDestinationDestination, DayList>,
    pickPlantSizeResultRecipient: ResultRecipient<PickPlantSizeDialogDestinationDestination, PlantSize>,
    pickWaterAmountResultRecipient: ResultRecipient<PickWaterAmountDialogDestination, Int>,
) {
    val uploadImage = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent(),
        onResult = { uri: Uri? ->
            if (uri == null) return@rememberLauncherForActivityResult

            viewModel.addPhoto(uri.toString())
        }
    )
    val onAddImage = remember { { uploadImage.launch("image/*")} }

    pickWateringDatesResultRecipient.onNavResult { result ->
        when (result) {
            NavResult.Canceled -> { /* Ignored */ }
            is NavResult.Value -> {
                viewModel.onWateringDatesSelected(result.value.list)
            }
        }
    }
    pickPlantSizeResultRecipient.onNavResult { result ->
        when (result) {
            NavResult.Canceled -> { /* Ignored */ }
            is NavResult.Value -> {
                viewModel.onPlantSizeSelected(result.value)
            }
        }
    }
    pickWaterAmountResultRecipient.onNavResult { result ->
        when (result) {
            NavResult.Canceled -> { /* Ignored */ }
            is NavResult.Value -> {
                viewModel.onWaterAmountSelected(result.value)
            }
        }
    }

    ObserveAsEvents(viewModel.events) { event ->
        when (event) {
            AddPlantViewModel.UIEvent.PlantCreated -> navigator.navigateUp()
        }
    }

    AddPlantScreenContent(
        navigator = navigator,
        plantName = viewModel.plantName,
        plantDescription = viewModel.plantDescription,
        imageUrl = viewModel.imageUrl,
        canCreatePlant = viewModel.canCreatePlant,
        onPlantNameChange = viewModel::onPlantNameChange,
        onPlantDescriptionChange = viewModel::onPlantDescriptionChange,
        onCreatePlant = viewModel::createPlant,
        selectedDates = viewModel.selectedPlantDates,
        selectedPlantSize = viewModel.selectedPlantSize,
        selectedTime = viewModel.selectedTime,
        onSelectTime = viewModel::onSelectTime,
        onChangeImageClick = onAddImage,
        waterAmount = viewModel.waterAmount
    )
}

@Composable
private fun AddPlantScreenContent(
    navigator: DestinationsNavigator,
    plantName: String,
    plantDescription: String,
    imageUrl: String?,
    canCreatePlant: Boolean,
    selectedDates: List<Day>,
    selectedPlantSize: PlantSize,
    selectedTime: LocalTime,
    waterAmount: Int,
    onSelectTime: (LocalTime) -> Unit,
    onPlantNameChange: (String) -> Unit,
    onPlantDescriptionChange: (String) -> Unit,
    onCreatePlant: () -> Unit,
    onChangeImageClick: () -> Unit,
) {
    val density = LocalDensity.current
    var imageHeight by rememberSaveable { mutableIntStateOf(0) }
    var placeholderImageHeight by rememberSaveable { mutableIntStateOf(0) }
    val selectTimeDialogState = rememberMaterialDialogState()
    
    TimePickerDialog(
        initialTime = selectedTime,
        dialogState = selectTimeDialogState,
        onTimeSelected = onSelectTime
    )

    ScreenSurface(
        backgroundContent = {
            if (imageUrl == null) {
                PlantsWallpaper()
            } else {
                AsyncImage(
                    modifier = Modifier.height(imageHeight.pxToDp() + WindowInsets.systemBars.getTop(density).pxToDp() + 48.dp),
                    contentScale = ContentScale.FillBounds,
                    model = imageUrl,
                    contentDescription = stringResource(R.string.plant_image)
                )
            }
        },
        backgroundColor = OtherGreen100
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {
            Column(modifier = Modifier.onSizeChanged { imageHeight = it.height }) {
                Box {
                    FilledIconButton(
                        modifier = Modifier.padding(16.dp),
                        colors = IconButtonDefaults.iconButtonColors(
                            containerColor = Neutralus0,
                            contentColor = Neutralus900
                        ),
                        onClick = navigator::navigateUp,
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_go_back),
                            contentDescription = stringResource(R.string.go_back)
                        )
                    }
                }
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    if (imageUrl == null) {
                        Image(
                            modifier = Modifier
                                .zIndex(-1f)
                                .onSizeChanged { placeholderImageHeight = it.height },
                            painter = painterResource(id = R.drawable.ic_placeholder_plant),
                            contentDescription = stringResource(R.string.plant_image)
                        )
                    } else {
                        Spacer(modifier = Modifier.height(placeholderImageHeight.pxToDp()))
                    }

                    Spacer(modifier = Modifier.size(48.dp))
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
            Spacer(modifier = Modifier.size(16.dp))
            Box(
                modifier = Modifier
                    .background(
                        color = Neutralus0,
                        RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp)
                    )
                    .padding(16.dp)
                    .fillMaxSize(),
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize(),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    MyPlantsTextField(
                        modifier = Modifier.fillMaxWidth(),
                        label = { Text(text = stringResource(R.string.plant_name_mandatory) )},
                        value = plantName,
                        onValueChange = onPlantNameChange
                    )
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(IntrinsicSize.Min),
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        Combobox(
                            modifier = Modifier.weight(1f),
                            onClick = { navigator.navigate(PickWateringDatesDialogDestinationDestination) },
                            label = { Text(text = stringResource(id = R.string.dates))},
                            text = { Text(text = selectedDates.toDayText()) }
                        )
                        Combobox(
                            modifier = Modifier.weight(1f),
                            onClick = selectTimeDialogState::show,
                            label = { Text(text = stringResource(R.string.time))},
                            text = { Text(text = selectedTime.toString()) }
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
                            onClick = { navigator.navigate(PickWaterAmountDialogDestination) },
                            label = { Text(text = stringResource(R.string.the_amount_of_water_mandatory)) },
                            text = { Text(text = "$waterAmount${stringResource(R.string.ml)}") }
                        )
                        Combobox(
                            modifier = Modifier.weight(1f),
                            onClick = { navigator.navigate(PickPlantSizeDialogDestinationDestination) },
                            label = { Text(text = stringResource(R.string.plant_size_mandatory)) },
                            text = { Text(text = selectedPlantSize.toUIString()) }
                        )
                    }
                    Column(modifier = Modifier.fillMaxWidth()) {
                        MyPlantsTextField(
                            modifier = Modifier.fillMaxSize(),
                            label = { Text(text = stringResource(R.string.description)) },
                            value = plantDescription,
                            onValueChange = onPlantDescriptionChange
                        )
                    }
                    Spacer(modifier = Modifier.size(72.dp))
                }
                BigButton(
                    modifier = Modifier
                        .background(Neutralus0)
                        .padding(top = 4.dp, bottom = 16.dp)
                        .fillMaxWidth()
                        .align(Alignment.BottomCenter),
                    enabled = canCreatePlant,
                    onClick = onCreatePlant
                ) {
                    Text(text = stringResource(R.string.create_a_plant))
                }
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
            plantName = "Montera",
            plantDescription = "Short Description",
            imageUrl = null,
            canCreatePlant = true,
            onPlantDescriptionChange = {},
            onPlantNameChange = {},
            onCreatePlant = {},
            onChangeImageClick = {},
            selectedDates = listOf(Day.MONDAY),
            selectedPlantSize = PlantSize.MEDIUM,
            selectedTime = LocalTime(8, 0, 0),
            onSelectTime = {},
            waterAmount = 250
        )
    }
}
