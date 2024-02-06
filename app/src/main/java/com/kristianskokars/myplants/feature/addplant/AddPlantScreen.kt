package com.kristianskokars.myplants.feature.addplant

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.kristianskokars.myplants.R
import com.kristianskokars.myplants.core.presentation.components.BigButton
import com.kristianskokars.myplants.core.presentation.components.MyPlantsTextField
import com.kristianskokars.myplants.core.presentation.components.ScreenSurface
import com.kristianskokars.myplants.core.presentation.components.SmallButton
import com.kristianskokars.myplants.core.presentation.theme.Neutralus0
import com.kristianskokars.myplants.core.presentation.theme.Neutralus100
import com.kristianskokars.myplants.core.presentation.theme.Neutralus500
import com.kristianskokars.myplants.core.presentation.theme.Neutralus900
import com.kristianskokars.myplants.core.presentation.theme.OtherGreen100
import com.ramcosta.composedestinations.annotation.Destination

@Composable
@Destination
fun AddPlantScreen() {
    AddPlantScreenContent()
}

@Composable
private fun AddPlantScreenContent() {
    var plantNameField by remember { mutableStateOf("") }
    var descriptionField by remember { mutableStateOf("") }
    var showPickPlantSizeDialog by remember { mutableStateOf(false) }
    var showPickWateringDatesDialog by remember { mutableStateOf(false) }
    
    if (showPickPlantSizeDialog) {
        PickPlantSizeDialog(onDismiss = { showPickPlantSizeDialog = false })
    }
    if (showPickWateringDatesDialog) {
        PickWateringDatesDialog(onDismiss = { showPickWateringDatesDialog = false })
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        FilledIconButton(
            modifier = Modifier.padding(16.dp),
            colors = IconButtonDefaults.iconButtonColors(
                containerColor = Neutralus0,
                contentColor = Neutralus900
            ),
            onClick = { /*TODO*/ },
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_go_back),
                contentDescription = stringResource(R.string.go_back)
            )
        }
        Column(modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
            Image(
                painter = painterResource(id = R.drawable.ic_placeholder_plant),
                contentDescription = stringResource(R.string.plant_image)
            )
            Spacer(modifier = Modifier.size(48.dp))
            SmallButton(onClick = { /*TODO*/ }) {
                Icon(
                    modifier = Modifier.size(20.dp),
                    painter = painterResource(id = R.drawable.ic_upload),
                    contentDescription = null
                )
                Spacer(modifier = Modifier.size(4.dp))
                Text(text = stringResource(R.string.add_image))
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
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                MyPlantsTextField(
                    modifier = Modifier.fillMaxWidth(),
                    label = { Text(text = stringResource(R.string.plant_name_mandatory) )},
                    value = plantNameField,
                    onValueChange = { plantNameField = it }
                )
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(IntrinsicSize.Min),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Combobox(
                        modifier = Modifier.weight(1f),
                        onClick = { showPickWateringDatesDialog = true },
                        label = { Text(text = stringResource(id = R.string.dates))},
                        text = { Text(text = "Monday") }
                    )
                    Combobox(
                        modifier = Modifier.weight(1f),
                        onClick = {  },
                        label = { Text(text = stringResource(R.string.time))},
                        text = { Text(text = "8:00") }
                    )
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(IntrinsicSize.Min),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    MyPlantsTextField(
                        modifier = Modifier.weight(1f),
                        label = { Text(text = stringResource(R.string.the_amount_of_water_mandatory))},
                        value = "250",
                        suffix = stringResource(R.string.ml),
                        onValueChange = { }
                    )
                    Combobox(
                        modifier = Modifier.weight(1f),
                        onClick = { showPickPlantSizeDialog = true },
                        label = { Text(text = stringResource(R.string.plant_size_mandatory)) },
                        text = { Text(text = "Medium") }
                    )
                }
                Column(modifier = Modifier.fillMaxWidth()) {
                    MyPlantsTextField(
                        modifier = Modifier.fillMaxSize(),
                        label = { Text(text = "Description") },
                        value = descriptionField,
                        onValueChange = { descriptionField = it }
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
                onClick = { /*TODO*/ }
            ) {
                Text(text = stringResource(R.string.create_a_plant))
            }
        }
    }
}

@Composable
fun Combobox(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    label: @Composable () -> Unit,
    text: @Composable () -> Unit,
) {
    Column(modifier = modifier) {
        CompositionLocalProvider(LocalTextStyle provides MaterialTheme.typography.bodySmall) {
            label()
            Spacer(modifier = Modifier.size(8.dp))
            Button(
                modifier = Modifier.fillMaxHeight(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Neutralus100,
                    contentColor = Neutralus500
                ),
                shape = RoundedCornerShape(12.dp),
                onClick = onClick,
            ) {
                // Button provides it's own composition local provider for text styles, so we need to do it again
                CompositionLocalProvider(LocalTextStyle provides MaterialTheme.typography.bodySmall) {
                    text()
                }
                Spacer(modifier = Modifier.weight(1f))
                Icon(
                    painter = painterResource(id = R.drawable.ic_arrow_down),
                    contentDescription = null
                )
            }
        }
    }
}

@Preview
@Composable
fun AddPlantScreenPreview() {
    ScreenSurface(backgroundColor = OtherGreen100) {
        AddPlantScreenContent()
    }
}
