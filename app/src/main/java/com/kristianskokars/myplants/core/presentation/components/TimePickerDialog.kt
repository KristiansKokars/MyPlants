package com.kristianskokars.myplants.core.presentation.components

import androidx.compose.material3.LocalTextStyle
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.kristianskokars.myplants.R
import com.kristianskokars.myplants.core.presentation.theme.Accent500
import com.kristianskokars.myplants.core.presentation.theme.Accent600
import com.kristianskokars.myplants.core.presentation.theme.Neutralus100
import com.kristianskokars.myplants.core.presentation.theme.Neutralus300
import com.kristianskokars.myplants.core.presentation.theme.TransparentGray
import com.vanpra.composematerialdialogs.MaterialDialog
import com.vanpra.composematerialdialogs.MaterialDialogState
import com.vanpra.composematerialdialogs.datetime.time.TimePickerDefaults
import com.vanpra.composematerialdialogs.datetime.time.timepicker
import kotlinx.datetime.LocalTime
import kotlinx.datetime.toJavaLocalTime
import kotlinx.datetime.toKotlinLocalTime

@Composable
fun TimePickerDialog(
    initialTime: LocalTime,
    dialogState: MaterialDialogState,
    onTimeSelected: (LocalTime) -> Unit
) {
    MaterialDialog(
        dialogState = dialogState,
        buttons = {
            positiveButton(
                textStyle = LocalTextStyle.current.copy(color = Accent600),
                text = stringResource(R.string.ok)
            )
            negativeButton(
                textStyle = LocalTextStyle.current.copy(color = Neutralus300),
                text = stringResource(R.string.cancel)
            )
        }
    ) {
        timepicker(
            initialTime = initialTime.toJavaLocalTime(),
            colors = TimePickerDefaults.colors(
                selectorColor = Accent500,
                inactivePeriodBackground = Neutralus100,
                inactiveBackgroundColor = Neutralus100,
                activeBackgroundColor = Accent500,
                borderColor = TransparentGray
            ),
            onTimeChange = { time -> onTimeSelected(time.toKotlinLocalTime()) }
        )
    }
}
