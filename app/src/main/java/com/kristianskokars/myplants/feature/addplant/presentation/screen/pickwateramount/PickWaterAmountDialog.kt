package com.kristianskokars.myplants.feature.addplant.presentation.screen.pickwateramount

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.kristianskokars.myplants.R
import com.kristianskokars.myplants.core.presentation.components.MyPlantsTertiaryButton
import com.kristianskokars.myplants.core.presentation.components.SmallButton
import com.kristianskokars.myplants.core.presentation.theme.Neutralus0
import com.kristianskokars.myplants.core.presentation.theme.Neutralus900
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.ramcosta.composedestinations.result.ResultBackNavigator
import com.ramcosta.composedestinations.spec.DestinationStyle
import kotlin.math.roundToInt

@Destination(style = DestinationStyle.Dialog.Default::class)
@Composable
fun PickWaterAmountDialog(
    navigator: DestinationsNavigator,
    resultNavigator: ResultBackNavigator<Int>
) {
    PickWaterAmountDialog(
        onDismiss = navigator::navigateUp,
        resultNavigator = resultNavigator,
    )
}

@Composable
private fun PickWaterAmountDialog(
    onDismiss: () -> Unit,
    resultNavigator: ResultBackNavigator<Int>,
) {
    var value by remember { mutableFloatStateOf(250f) }

    Dialog(onDismissRequest = onDismiss) {
        Column(
            modifier = Modifier
                .background(color = Neutralus0, shape = RoundedCornerShape(16.dp))
                .padding(16.dp)
                .fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text(
                text = stringResource(R.string.water_amount),
                style = MaterialTheme.typography.bodyLarge,
                color = Neutralus900
            )
            Text(text = "${value.roundToInt()}ml")
            Slider(
                value = value,
                onValueChange = { value = it },
                valueRange = 0f..1000f,
                steps = 19,
            )
            Spacer(modifier = Modifier.size(8.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                MyPlantsTertiaryButton(
                    modifier = Modifier.weight(1f),
                    onClick = onDismiss
                ) {
                    Text(text = stringResource(R.string.cancel))
                }
                Spacer(modifier = Modifier.size(8.dp))
                SmallButton(
                    modifier = Modifier.weight(1f),
                    onClick = { resultNavigator.navigateBack(value.toInt()) }
                ) {
                    Text(text = stringResource(R.string.got_it))
                }
            }
        }
    }
}
