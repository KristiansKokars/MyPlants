package com.kristianskokars.myplants.feature.addplant.presentation.screen.pickwateringdates

import android.os.Parcelable
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.viewmodel.compose.viewModel
import com.kristianskokars.myplants.R
import com.kristianskokars.myplants.core.data.model.Day
import com.kristianskokars.myplants.core.data.model.DayList
import com.kristianskokars.myplants.core.presentation.components.MyPlantsTertiaryButton
import com.kristianskokars.myplants.core.presentation.components.ScreenSurface
import com.kristianskokars.myplants.core.presentation.components.SmallButton
import com.kristianskokars.myplants.core.presentation.theme.Neutralus0
import com.kristianskokars.myplants.core.presentation.theme.Neutralus300
import com.kristianskokars.myplants.core.presentation.theme.Neutralus900
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.ramcosta.composedestinations.result.EmptyResultBackNavigator
import com.ramcosta.composedestinations.result.ResultBackNavigator
import com.ramcosta.composedestinations.spec.DestinationStyle
import kotlinx.parcelize.Parcelize

@Parcelize
data class PickWateringDatesDialogNavArgs(val initialDays: DayList) : Parcelable

@Destination(
    style = DestinationStyle.Dialog.Default::class,
    navArgsDelegate = PickWateringDatesDialogNavArgs::class
)
@Composable
fun PickWateringDatesDialogDestination(
    navigator: DestinationsNavigator,
    viewModel: PickWateringDatesViewModel = viewModel(),
    resultNavigator: ResultBackNavigator<DayList>
) {
    PickWateringDatesDialog(
        resultNavigator = resultNavigator,
        wateringDates = viewModel.wateringDates,
        selectDay = viewModel::onSelectDay,
        onDismiss = navigator::navigateUp
    )
}

@Composable
private fun PickWateringDatesDialog(
    resultNavigator: ResultBackNavigator<DayList>,
    wateringDates: Map<Day, Boolean>,
    selectDay: (Day) -> Unit,
    onDismiss: () -> Unit
) {

    Dialog(
        onDismissRequest = onDismiss
    ) {
        Column(
            modifier = Modifier
                .background(color = Neutralus0, shape = RoundedCornerShape(16.dp))
                .padding(16.dp)
                .fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text(
                text = stringResource(R.string.dates),
                style = MaterialTheme.typography.bodyLarge,
                color = Neutralus900
            )
            wateringDates.forEach { (date, isSelected) ->
                CheckboxOption(
                    checked = isSelected,
                    onClick = { selectDay(date) },
                    text = date.toUIString()
                )
            }
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
                    onClick = { resultNavigator.navigateBack(DayList(wateringDates.mapNotNull { date -> date.key.takeIf { date.value } })) }
                ) {
                    Text(text = stringResource(R.string.got_it))
                }
            }
        }
    }
}

@Composable
private fun CheckboxOption(
    checked: Boolean,
    onClick: (Boolean?) -> Unit,
    text: String
) {
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        Checkbox(
            colors = CheckboxDefaults.colors().copy(uncheckedBorderColor = Neutralus300),
            checked = checked,
            onCheckedChange = onClick
        )
        Text(
            text = text,
            style = MaterialTheme.typography.bodyLarge,
            color = if (checked) Neutralus900 else MaterialTheme.typography.bodyLarge.color,
        )
    }
}

@Preview
@Composable
private fun PickPlantSizeDialogPreview() {
    ScreenSurface {
        PickWateringDatesDialog(
            onDismiss = {},
            resultNavigator = EmptyResultBackNavigator(),
            wateringDates = emptyMap(),
            selectDay = {}
        )
    }
}
