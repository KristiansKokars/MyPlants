package com.kristianskokars.myplants.feature.view_plants.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonColors
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.kristianskokars.myplants.R
import com.kristianskokars.myplants.core.presentation.components.MyPlantsTab
import com.kristianskokars.myplants.core.presentation.components.MyPlantsTabRow
import com.kristianskokars.myplants.core.presentation.components.ScreenSurface
import com.kristianskokars.myplants.ui.theme.Accent500
import com.kristianskokars.myplants.ui.theme.Neutralus0
import com.kristianskokars.myplants.ui.theme.Neutralus100
import com.kristianskokars.myplants.ui.theme.Neutralus300
import com.kristianskokars.myplants.ui.theme.Neutralus500
import com.kristianskokars.myplants.ui.theme.Neutralus900
import com.kristianskokars.myplants.ui.theme.Red

@Composable
fun PlantHomeScreen() {
    PlantHomeScreenContent()
}

@Composable
private fun PlantHomeScreenContent() {
    var selectedTabIndex by remember {
        mutableIntStateOf(0)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 36.dp, start = 16.dp, end = 16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = stringResource(R.string.lets_care_my_plants_title), style = MaterialTheme.typography.titleLarge)
            NotificationButton()
        }
        Spacer(modifier = Modifier.size(16.dp))
        MyPlantsTabRow(
            selectedTabIndex = selectedTabIndex,
            containerColor = Color.Transparent,
        ) {
            MyPlantsTab(
                selected = selectedTabIndex == 0,
                onClick = { selectedTabIndex = 0 },
                text = {
                    Text(
                        text = stringResource(R.string.upcoming),
                        style = MaterialTheme.typography.bodyMedium,
                        color = if (selectedTabIndex == 0) Accent500 else Neutralus300
                    )
                }
            )
            MyPlantsTab(
                selected = selectedTabIndex == 1,
                onClick = { selectedTabIndex = 1 },
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
                onClick = { selectedTabIndex = 2 },
                text = {
                    Text(
                        text = stringResource(R.string.history),
                        style = MaterialTheme.typography.bodyMedium,
                        color = if (selectedTabIndex == 2) Accent500 else Neutralus300
                    )
                }
            )
        }
        Column(modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
            Spacer(modifier = Modifier.size(80.dp))
            Image(painter = painterResource(id = R.drawable.plants), contentDescription = null)
            Spacer(modifier = Modifier.size(32.dp))
            Text(text = stringResource(R.string.sorry), style = MaterialTheme.typography.bodyLarge, color = Neutralus900)
            Spacer(modifier = Modifier.size(8.dp))
            Text(
                modifier = Modifier.padding(horizontal = 48.dp),
                textAlign = TextAlign.Center,
                text = stringResource(R.string.no_plants_in_list),
                style = MaterialTheme.typography.bodySmall
            )
            Spacer(modifier = Modifier.size(16.dp))
            Button(
                contentPadding = PaddingValues(horizontal = 60.dp, vertical = 16.dp),
                shape = RoundedCornerShape(10.dp),
                onClick = { /*TODO*/ }
            ) {
                Text(text = stringResource(R.string.add_your_first_plant), style = MaterialTheme.typography.bodyLarge, color = Neutralus0)
            }
        }
    }
}

@Composable
fun NotificationButton() {
    Box {
        Box(
            modifier = Modifier
                .padding(top = 6.dp, end = 8.dp)
                .zIndex(1f)
                .background(color = Red, shape = CircleShape)
                .size(6.dp)
                .align(Alignment.TopEnd)
        )
        IconButton(
            colors = IconButtonColors(
                containerColor = Neutralus100,
                contentColor = Neutralus500,
                disabledContainerColor = Neutralus100,
                disabledContentColor = Neutralus500
            ),
            onClick = { /*TODO*/ }
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_notification),
                contentDescription = stringResource(R.string.view_notifications)
            )
        }
    }
}

@Preview
@Composable
private fun PlantHomeScreenPreview() {
    ScreenSurface {
        PlantHomeScreenContent()
    }
}
