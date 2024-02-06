package com.kristianskokars.myplants.feature.viewplants.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.kristianskokars.myplants.R
import com.kristianskokars.myplants.core.data.model.Plant
import com.kristianskokars.myplants.core.presentation.components.styledHazeChild
import com.kristianskokars.myplants.ui.theme.Accent100
import com.kristianskokars.myplants.ui.theme.Accent500
import com.kristianskokars.myplants.ui.theme.GrayBorder
import com.kristianskokars.myplants.ui.theme.Neutralus0
import com.kristianskokars.myplants.ui.theme.Neutralus900
import com.kristianskokars.myplants.ui.theme.OtherGreen100
import com.kristianskokars.myplants.ui.theme.TransparentGray
import dev.chrisbanes.haze.HazeState
import dev.chrisbanes.haze.haze

@Composable
fun PlantCard(plant: Plant) {
    val hazeState = remember { HazeState() }

    Column(
        modifier = Modifier
            .background(color = OtherGreen100, shape = RoundedCornerShape(4.dp))
            .border(1.dp, color = GrayBorder, shape = RoundedCornerShape(4.dp))
            .height(256.dp),
        verticalArrangement = Arrangement.SpaceBetween,
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            contentAlignment = Alignment.Center,
        ) {
            Column(
                modifier = Modifier
                    .zIndex(1f)
                    .padding(12.dp)
                    .align(Alignment.TopStart),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Text(
                    modifier = Modifier
                        .styledHazeChild(state = hazeState)
                        .background(TransparentGray, shape = RoundedCornerShape(4.dp))
                        .padding(4.dp),
                    text = stringResource(id = R.string.milliliters, plant.waterInMilliliters),
                    style = MaterialTheme.typography.labelMedium,
                    color = Neutralus0
                )
                Text(
                    modifier = Modifier
                        .styledHazeChild(state = hazeState)
                        .background(TransparentGray, shape = RoundedCornerShape(4.dp))
                        .padding(4.dp),
                    text = stringResource(R.string.today),
                    style = MaterialTheme.typography.labelMedium,
                    color = Neutralus0
                )
            }
            Image(
                modifier = Modifier.haze(state = hazeState),
                painter = painterResource(id = R.drawable.ic_placeholder_plant),
                contentDescription = null
            )
        }
        Row(
            modifier = Modifier
                .background(Neutralus0)
                .padding(12.dp)
                .height(48.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Column {
                Text(
                    text = plant.name,
                    style = MaterialTheme.typography.bodyMedium,
                    color = Neutralus900
                )
                Text(
                    text = plant.description,
                    style = MaterialTheme.typography.labelLarge
                )
            }
            WateringCheckbox(isWatered = true)
        }
    }
}

@Composable
private fun WateringCheckbox(isWatered: Boolean) {
    FilledIconButton(
        modifier = Modifier.size(24.dp),
        colors = IconButtonDefaults.filledIconButtonColors(
            containerColor = Accent500,
            contentColor = Neutralus0,
            disabledContainerColor = Accent100,
            disabledContentColor = Neutralus0
        ),
        enabled = !isWatered,
        shape = RoundedCornerShape(4.dp),
        onClick = { /*TODO*/ }
    ) {
        if (isWatered) {
            Icon(
                modifier = Modifier.padding(3.dp),
                painter = painterResource(id = R.drawable.ic_check),
                contentDescription = stringResource(R.string.plant_is_watered)
            )
        } else {
            Icon(
                modifier = Modifier.padding(3.dp),
                painter = painterResource(id = R.drawable.ic_water_plant),
                contentDescription = stringResource(R.string.needs_watering)
            )
        }
    }
}
