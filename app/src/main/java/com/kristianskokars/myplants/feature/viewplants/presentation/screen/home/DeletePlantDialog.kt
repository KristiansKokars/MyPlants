package com.kristianskokars.myplants.feature.viewplants.presentation.screen.home

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
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.kristianskokars.myplants.R
import com.kristianskokars.myplants.core.presentation.components.MyPlantsTertiaryButton
import com.kristianskokars.myplants.core.presentation.components.SmallButton
import com.kristianskokars.myplants.core.presentation.theme.Neutralus0
import com.kristianskokars.myplants.core.presentation.theme.Neutralus900
import com.kristianskokars.myplants.core.presentation.theme.Red
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.result.ResultBackNavigator
import com.ramcosta.composedestinations.spec.DestinationStyle
import kotlinx.parcelize.Parcelize

@Parcelize
data class DeletePlantResult(
    val shouldDelete: Boolean = false,
    val plantId: String
) : Parcelable

@Destination(style = DestinationStyle.Dialog.Default::class)
@Composable
fun DeletePlantDialogDestination(
    plantId: String,
    plantName: String,
    resultNavigator: ResultBackNavigator<DeletePlantResult>
) {
    DeletePlantDialog(
        plantId = plantId,
        plantName = plantName,
        resultNavigator = resultNavigator,
    )
}

@Composable
fun DeletePlantDialog(
    plantId: String,
    plantName: String,
    resultNavigator: ResultBackNavigator<DeletePlantResult>
) {
    Dialog(onDismissRequest = { resultNavigator.navigateBack(DeletePlantResult(shouldDelete = false, plantId = plantId)) }) {
        Column(
            modifier = Modifier
                .background(color = Neutralus0, shape = RoundedCornerShape(16.dp))
                .padding(16.dp)
                .fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_delete),
                    contentDescription = null,
                    tint = Red
                )
                Spacer(modifier = Modifier.size(8.dp))
                Text(
                    text = stringResource(R.string.are_you_sure),
                    style = MaterialTheme.typography.bodyLarge,
                    color = Neutralus900
                )
            }
            Text(
                text = stringResource(
                    R.string.do_you_really_want_to_delete_plant,
                    plantName
                ),
                style = MaterialTheme.typography.bodySmall,
                color = Neutralus900
            )
            Spacer(modifier = Modifier.size(8.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                MyPlantsTertiaryButton(
                    modifier = Modifier.weight(1f),
                    onClick = { resultNavigator.navigateBack(DeletePlantResult(shouldDelete = false, plantId = plantId)) }
                ) {
                    Text(text = stringResource(R.string.cancel))
                }
                Spacer(modifier = Modifier.size(8.dp))
                SmallButton(
                    modifier = Modifier.weight(1f),
                    onClick = { resultNavigator.navigateBack(DeletePlantResult(shouldDelete = true, plantId = plantId)) }
                ) {
                    Text(text = stringResource(R.string.got_it))
                }
            }
        }
    }
}
