package com.kristianskokars.myplants.feature.addplant

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
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.kristianskokars.myplants.R
import com.kristianskokars.myplants.core.presentation.components.MyPlantsTertiaryButton
import com.kristianskokars.myplants.core.presentation.components.ScreenSurface
import com.kristianskokars.myplants.core.presentation.components.SmallButton
import com.kristianskokars.myplants.core.presentation.theme.Neutralus0
import com.kristianskokars.myplants.core.presentation.theme.Neutralus900

@Composable
fun PickPlantSizeDialog(
    onDismiss: () -> Unit,
) {
    Dialog(onDismissRequest = onDismiss) {
        Column(
            modifier = Modifier
                .background(color = Neutralus0, shape = RoundedCornerShape(16.dp))
                .padding(16.dp)
                .fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text(
                text = stringResource(R.string.plant_size),
                style = MaterialTheme.typography.bodyLarge,
                color = Neutralus900
            )
            RadioOption(
                selected = false,
                onClick = { /*TODO*/ },
                text = stringResource(R.string.small)
            )
            RadioOption(
                selected = true,
                onClick = { /*TODO*/ },
                text = stringResource(R.string.medium)
            )
            RadioOption(
                selected = false,
                onClick = { /*TODO*/ },
                text = stringResource(R.string.large)
            )
            RadioOption(
                selected = false,
                onClick = { /*TODO*/ },
                text = stringResource(R.string.extra_large)
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
                    onClick = { /*TODO*/ }
                ) {
                    Text(text = stringResource(R.string.got_it))
                }
            }
        }
    }
}

@Composable
private fun RadioOption(
    selected: Boolean,
    onClick: () -> Unit,
    text: String
) {
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        RadioButton(selected = selected, onClick = onClick)
        Text(
            text = text,
            style = MaterialTheme.typography.bodyLarge,
            color = if (selected) Neutralus900 else MaterialTheme.typography.bodyLarge.color,
        )
    }
}

@Preview
@Composable
private fun PickPlantSizeDialogPreview() {
    ScreenSurface {
        PickPlantSizeDialog(onDismiss = {})
    }
}
