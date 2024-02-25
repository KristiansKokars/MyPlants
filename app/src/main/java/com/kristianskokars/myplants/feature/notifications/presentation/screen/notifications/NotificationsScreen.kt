package com.kristianskokars.myplants.feature.notifications.presentation.screen.notifications

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.kristianskokars.myplants.R
import com.kristianskokars.myplants.core.data.model.PlantNotification
import com.kristianskokars.myplants.core.presentation.components.MyPlantsIconButton
import com.kristianskokars.myplants.core.presentation.components.MyPlantsTab
import com.kristianskokars.myplants.core.presentation.components.MyPlantsTabRow
import com.kristianskokars.myplants.core.presentation.components.ScreenSurface
import com.kristianskokars.myplants.core.presentation.theme.Accent500
import com.kristianskokars.myplants.core.presentation.theme.MyPlantsTheme
import com.kristianskokars.myplants.core.presentation.theme.Neutralus0
import com.kristianskokars.myplants.core.presentation.theme.Neutralus100
import com.kristianskokars.myplants.core.presentation.theme.Neutralus300
import com.kristianskokars.myplants.core.presentation.theme.Neutralus500
import com.kristianskokars.myplants.core.presentation.theme.Neutralus900
import com.kristianskokars.myplants.core.presentation.theme.OtherGreen100
import com.kristianskokars.myplants.core.presentation.theme.Red
import com.kristianskokars.myplants.feature.notifications.presentation.model.NotificationUIModel
import com.kristianskokars.myplants.feature.notifications.presentation.model.NotificationsFilter
import com.kristianskokars.myplants.lib.fillParentWidth
import com.kristianskokars.myplants.lib.toDateLabel
import com.kristianskokars.myplants.lib.toTimeLabel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.ramcosta.composedestinations.navigation.EmptyDestinationsNavigator

@Destination
@Composable
fun NotificationsScreen(
    viewModel: NotificationsViewModel = hiltViewModel(),
    navigator: DestinationsNavigator
) {
    NotificationsScreenContent(
        state = viewModel.state(),
        onEvent = viewModel::onEvent,
        navigator = navigator
    )
}

@Composable
private fun NotificationsScreenContent(
    state: NotificationsState,
    onEvent: (NotificationsEvent) -> Unit,
    navigator: DestinationsNavigator
) {
    val selectedTabIndex by remember(state.notificationFilter) {
        derivedStateOf {
            when (state.notificationFilter) {
                NotificationsFilter.ALL -> 0
                NotificationsFilter.FORGOT_TO_WATER -> 1
                NotificationsFilter.HISTORY -> 2
            }
        }
    }

    ScreenSurface(
        shouldPadAgainstInsets = false,
        backgroundColor = OtherGreen100
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 48.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(modifier = Modifier.weight(1f)) {
                    MyPlantsIconButton(
                        iconPainter = painterResource(id = R.drawable.ic_go_back),
                        contentDescription = stringResource(R.string.go_back),
                        onClick = navigator::navigateUp
                    )
                }
                Text(
                    modifier = Modifier.weight(1f),
                    text = stringResource(R.string.notifications),
                    style = MaterialTheme.typography.bodyMedium,
                    color = Neutralus900,
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.weight(1f))
            }
            MyPlantsTabRow(
                modifier = Modifier.padding(start = 16.dp, end = 16.dp, bottom = 16.dp),
                selectedTabIndex = selectedTabIndex,
                containerColor = Color.Transparent,
            ) {
                MyPlantsTab(
                    selected = selectedTabIndex == 0,
                    onClick = { onEvent(
                        NotificationsEvent.OnNotificationFilterChange(
                            NotificationsFilter.ALL
                        )
                    ) },
                    text = {
                        Text(
                            text = stringResource(R.string.all_notifications),
                            style = MaterialTheme.typography.bodyMedium,
                            color = if (selectedTabIndex == 0) Accent500 else Neutralus300
                        )
                    }
                )
                MyPlantsTab(
                    selected = selectedTabIndex == 1,
                    onClick = { onEvent(
                        NotificationsEvent.OnNotificationFilterChange(
                            NotificationsFilter.FORGOT_TO_WATER
                        )
                    ) },
                    text = {
                        Text(
                            text = stringResource(R.string.forgot_to_water),
                            style = MaterialTheme.typography.bodyMedium,
                            color = if (selectedTabIndex == 1) Accent500 else Neutralus300
                        )
                    }
                )
                MyPlantsTab(
                    selected = selectedTabIndex == 2,
                    onClick = { onEvent(
                        NotificationsEvent.OnNotificationFilterChange(
                            NotificationsFilter.HISTORY
                        )
                    ) },
                    text = {
                        Text(
                            text = stringResource(R.string.history),
                            style = MaterialTheme.typography.bodyMedium,
                            color = if (selectedTabIndex == 2) Accent500 else Neutralus300
                        )
                    }
                )
            }
            LazyColumn(
                modifier = Modifier
                    .background(
                        color = Neutralus0,
                        RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp)
                    )
                    .padding(16.dp)
                    .fillMaxSize(),
            ) {
                items(
                    state.notifications,
                    key = { when (it) {
                        is NotificationUIModel.Date -> it.dateInMillis
                        is NotificationUIModel.Divider -> it.text
                        is NotificationUIModel.Notification -> it.notification.id
                    } },
                ) { notificationUIModel ->
                    when (notificationUIModel) {
                        is NotificationUIModel.Date -> {
                            Text(
                                modifier = Modifier.padding(vertical = 8.dp),
                                text = notificationUIModel.dateInMillis.toDateLabel(),
                                style = MaterialTheme.typography.bodyMedium
                            )
                        }
                        is NotificationUIModel.Divider -> {
                            Text(
                                text = notificationUIModel.text.toUIString(),
                                style = MaterialTheme.typography.bodyMedium,
                                color = Neutralus500
                            )
                        }
                        is NotificationUIModel.Notification -> {
                            Spacer(modifier = Modifier.size(16.dp))
                            NotificationCard(
                                notification = notificationUIModel.notification,
                                onGoToPlantClick = { onEvent(NotificationsEvent.NavigateToPlant(notificationUIModel.notification)) }
                            )
                            Spacer(modifier = Modifier.size(8.dp))
                            HorizontalDivider(
                                modifier = Modifier.fillParentWidth(16.dp),
                                color = Neutralus100
                            )
                            Spacer(modifier = Modifier.size(16.dp))
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun NotificationCard(
    notification: PlantNotification,
    onGoToPlantClick: () -> Unit,
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(IntrinsicSize.Max),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Box(
                modifier = Modifier
                    .background(OtherGreen100, shape = RoundedCornerShape(8.dp))
                    .size(40.dp)
            ) {
                Image(
                    modifier = Modifier
                        .padding(4.dp)
                        .align(Alignment.Center),
                    painter = painterResource(id = R.drawable.ic_placeholder_plant),
                    contentDescription = null
                )
            }
            Spacer(modifier = Modifier.size(8.dp))
            Column(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight(),
                verticalArrangement = Arrangement.SpaceBetween,
            ) {
                Text(text = stringResource(R.string.daily_plant_notification), style = MaterialTheme.typography.bodySmall, color = Neutralus900)
                Text(text = notification.dateInMillis.toTimeLabel(), style = MaterialTheme.typography.labelLarge, color = Neutralus500)
            }
            if (!notification.isSeen) {
                Box(modifier = Modifier
                    .background(Red, shape = CircleShape)
                    .size(12.dp))
            }
        }
        Spacer(modifier = Modifier.size(8.dp))
        Text(
            text = stringResource(R.string.remember_water_notification_text, notification.plantName),
            style = MaterialTheme.typography.bodySmall
        )
        TextButton(
            onClick = onGoToPlantClick,
            contentPadding = PaddingValues(0.dp)
        ) {
            Text(
                text = stringResource(R.string.go_to_the_plant),
                style = MaterialTheme.typography.bodyMedium,
                color = Accent500
            )
        }
    }
}

@Composable
@Preview
private fun NotificationsScreenPreview() {
    MyPlantsTheme {
        NotificationsScreenContent(
            state = NotificationsState(),
            onEvent = {},
            navigator = EmptyDestinationsNavigator
        )
    }
}
