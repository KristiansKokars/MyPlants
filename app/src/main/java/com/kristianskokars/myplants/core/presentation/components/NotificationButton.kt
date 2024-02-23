package com.kristianskokars.myplants.core.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.kristianskokars.myplants.R
import com.kristianskokars.myplants.core.presentation.theme.Neutralus100
import com.kristianskokars.myplants.core.presentation.theme.Neutralus500
import com.kristianskokars.myplants.core.presentation.theme.Red

@Composable
fun NotificationButton(
    hasNotifications: Boolean,
    onClick: () -> Unit,
) {
    Box {
        if (hasNotifications) {
            Box(
                modifier = Modifier
                    .padding(top = 6.dp, end = 8.dp)
                    .zIndex(1f)
                    .background(color = Red, shape = CircleShape)
                    .size(6.dp)
                    .align(Alignment.TopEnd)
            )
        }
        IconButton(
            colors = IconButtonColors(
                containerColor = Neutralus100,
                contentColor = Neutralus500,
                disabledContainerColor = Neutralus100,
                disabledContentColor = Neutralus500
            ),
            onClick = onClick
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_notification),
                contentDescription = stringResource(R.string.view_notifications)
            )
        }
    }
}
